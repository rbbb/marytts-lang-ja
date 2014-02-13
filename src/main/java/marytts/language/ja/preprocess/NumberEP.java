/**
 * Copyright 2002 DFKI GmbH.
 * Copyright 2014 Han Seunghee
 * All Rights Reserved.  Use is subject to license terms.
 *
 * This file is part of MARY TTS.
 *
 * MARY TTS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package marytts.language.ja.preprocess;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import marytts.language.ja.lib.JapaneseNumberToWords;
import marytts.language.ja.lib.JapaneseNumberToWords.CounterWord;
import marytts.util.MaryUtils;
import marytts.util.dom.MaryDomUtils;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * An expansion pattern implementation for basic number patterns.
 *
 * @author Marc Schr&ouml;der
 */

public class NumberEP extends ExpansionPattern
{
    private final String[] _knownTypes = {
        "number",
        "number:float",
        "number:counted",
        "number:integer",
        "number:digits",
    };
    /**
     * Every subclass has its own list knownTypes, 
     * an internal string representation of known types.
     * These are possible values of the <code>type</code> attribute to the
     * <code>say-as</code> element, as defined in MaryXML.dtd.
     * If there is more than one known type, the first type
     * (<code>knownTypes[0]</code>) is expected to be the most general one,
     * of which the others are specialisations.
     */
    private final List<String> knownTypes = Arrays.asList(_knownTypes);
    public List<String> knownTypes() { return knownTypes; }

    // Domain-specific primitives:
    // (floats and integers are recognised up to hundreds of millions (nine digits))
    private static String tempCounterWords;
    private static String tempCounterWordCharacters="";
    static
    {
    	tempCounterWords = "";
    	for(CounterWord counterWord:JapaneseNumberToWords.CounterWord.values())
    	{
    		if(tempCounterWords.length()==0)
    		{
    			tempCounterWords += "(?:"+counterWord.getKanji();
    		}else{
    			tempCounterWords += "|"+counterWord.getKanji();
    		}
    		tempCounterWords += "|"+counterWord.getHiragana();
    		tempCounterWordCharacters += counterWord.getKanji();
    	}
    	tempCounterWords += ")";
    	tempCounterWordCharacters += "\u3041-\u3094";
    }
    protected static final String sCounterWords = tempCounterWords;
//    protected static final String sFloat = "(?:-?(?:[1-9][0-9]{0,8}|0)?(?:\\.)[0-9]+)";
    protected static final String sFloat = "(?:-?(?:[1-9][0-9]{0,8}|0)?(?:\\.|,)[0-9]+)";
    protected static final String sInteger = "(?:-?[1-9][0-9]*|0)";
    protected static final String sCountedInteger = "(?:-?[0-9]+)";
    protected static final String sDigits = "(?:[0-9.,]*[0-9][.,]?)";

    // Now the actual match patterns:
    private final String peekChars = "\uAC00-\uD7A3";
    protected final Pattern reCountedInteger = Pattern.compile("("+sCountedInteger+")"+"(?:\\s*)"+"("+sCounterWords+")(["+peekChars+"]*)");
    protected final Pattern reFloat = Pattern.compile(sFloat);
    protected final Pattern reInteger = Pattern.compile(sInteger);
    protected final Pattern reDigits = Pattern.compile(sDigits);

    //when a token matches the peek pattern, we stop adding tokens
    //so we get the counter word and a few hangul but not all the rest of the sentence
    private final Pattern peekPattern = Pattern.compile("["+peekChars+"]+");
    
    private final Pattern reMatchingChars = Pattern.compile("[0-9"+peekChars+"\\-\\.,\\s"+tempCounterWordCharacters+"]");
    public Pattern reMatchingChars() { return reMatchingChars; }

    /**
     * in Korean, the counter words can be in the next token
     * so, contrary to usual, we allow numbers across token boundaries
     */
    protected boolean allowMultipleTokens() { return true; }

    /**
     * Every subclass has its own logger.
     * The important point is that if several threads are accessing
     * the variable at the same time, the logger needs to be thread-safe
     * or it will produce rubbish.
     */
    private Logger logger = MaryUtils.getLogger("NumberEP");

    public NumberEP()
    {
        super();
    }

    protected int match(String s, int type)
    {
        switch (type) {
        case 0:
        	if (matchFloat(s)) return 1;
        	if (matchCounted(s)) return 2;
            if (matchInteger(s)) return 3;
            if (matchDigits(s)) return 4;
            break;
        case 1:
            if (matchFloat(s)) return 1;
            break;
        case 2:
            if (matchCounted(s)) return 2;
            break;
        case 3:
            if (matchInteger(s)) return 3;
            break;
        case 4:
            if (matchDigits(s)) return 4;
            break;
        }
        return -1;
    }


    protected int canDealWith(String input, int typeCode)
    {
        switch (typeCode) {
        case 0:
            if (matchFloat(input)) return 1;
            if (matchCounted(input)) return 2;
            if (matchInteger(input)) return 3;
            if (matchDigits(input)) return 4;
            break;
        case 1:
            if (matchFloat(input)) return 1;
            break;
        case 2:
            if (matchCounted(input)) return 2;
            break;
        case 3: // integer
            if (matchInteger(input)) return 3;
            break;
        case 4:
            if (matchDigits(input)) return 4;
            break;
	    }
        return -1; // no, cannot deal with it as the given type
    }


    protected List<Element> expand(List<Element> tokens, String s, int type)
    {
        if (tokens == null) 
            throw new NullPointerException("Received null argument");
        if (tokens.isEmpty()) 
            throw new IllegalArgumentException("Received empty list");
        Document doc = ((Element)tokens.get(0)).getOwnerDocument();
        // we expect type to be one of the return values of match():
        List<Element> expanded = null;
        switch (type) {
        case 1:
            expanded = expandFloat(doc, s, true);
            break;
        case 2:
            expanded = expandCounted(doc, s, true);
            break;
        case 3:
            expanded = expandInteger(doc, s, true);
            break;
        case 4:
            expanded = expandDigits(doc, s, true);
            break;
        }
        replaceTokens(tokens, expanded);
        return expanded;
    }
    
    @Override
    protected boolean isLastCandidate(Element t) {
    	 return peekPattern.matcher(MaryDomUtils.tokenText(t)).find();
    }

    protected boolean matchFloat(String s)
    {
        return reFloat.matcher(s).matches();
    }
    
    protected boolean matchCounted(String s)
    {
        return reCountedInteger.matcher(s).matches();
    }

    protected boolean matchInteger(String s)
    {
        return reInteger.matcher(s).matches();
    }

    protected boolean matchDigits(String s)
    {
        return reDigits.matcher(s).matches();
    }

    protected List<Element> expandCounted(Document doc, String s, boolean createMtu)
    {        
    	Matcher m = reCountedInteger.matcher(s.trim());
    	if(!m.matches())
    	{
    		throw new IllegalArgumentException("Only integer with a counting word can go through this function, not "+s+" with pattern "+reCountedInteger);
    	}
        String integer = m.group(1);
        String counter = m.group(2);
        String remainingCharacters = m.group(3);
        
        List<String> integerWords = JapaneseNumberToWords.toWords(integer, counter);
        String expanded = "";
        for(String integerWord:integerWords)
        {
        	expanded += integerWord+" ";
        }
        expanded += remainingCharacters;
        return makeNewTokens(doc, expanded, createMtu, s);
    }

    
    protected List<Element> expandInteger(Document doc, String s, boolean createMtu)
    {
    	String remove0 = s;
        while (remove0.length() > 1 && remove0.startsWith("0")) remove0 = remove0.substring(1);
        return expandInteger(doc, remove0, createMtu, s);
    }

    protected List<Element> expandInteger(Document doc, String s, boolean createMtu, String orig)
    {
        String expString = expandInteger(s);
        return makeNewTokens(doc, expString, createMtu, orig);
    }

    protected String expandInteger(String s)
    {
    	List<String> integerWords = JapaneseNumberToWords.toWords(s, null);
        String expanded = "";
        for(String integerWord:integerWords)
        {
        	expanded += integerWord+" ";
        }
        return expanded.trim();
    }

    /**
     * This will correctly expand integers as well, although
     * matchFloat() does not match them.
     * This seems to be convenient in cases where "some number",
     * i.e. integer or float, was matched, and needs to be expanded.
     */
    protected List<Element> expandFloat(Document doc, String s, boolean createMtu)
    {
        String expString = expandFloat(s);
        return makeNewTokens(doc, expString, createMtu, s);
    }
    protected String expandFloat(String number)
    {
    	List<String> integerWords = JapaneseNumberToWords.toSpokenFloat(number);
        String expanded = "";
        for(String integerWord:integerWords)
        {
        	expanded += integerWord+" ";
        }
        return expanded.trim();
    }

    protected List<Element> expandDigits(Document doc, String s, boolean createMtu)
    {
        String expString = expandDigits(s);
        return makeNewTokens(doc, expString, createMtu, s);
    }
    protected String expandDigits(String digits)
    {
    	List<String> integerWords = JapaneseNumberToWords.toSpelledOutNumber(digits.replaceAll("[\\.,]", ""));
        String expanded = "";
        for(String integerWord:integerWords)
        {
        	expanded += integerWord+" ";
        }
        return expanded.trim();
    }

}
