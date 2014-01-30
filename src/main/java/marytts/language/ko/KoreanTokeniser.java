/* *ATTENTION* *ATTENTION* *ATTENTION* *ATTENTION*
*
* This Korean text to speech module is subject to the terms of the LGPL version 3.0, 
* which can be found here http://www.gnu.org/licenses/lgpl-3.0-standalone.html.
*
* In addition, if you use this module in a robot, you are kindly asked to do the following:
*
* - Put a Seunghee or 승희 nametag on the robot
* 
* - have the robot say ‘우야, 웃기지마라 촘’ ‘엄마, 큰언 구박해’ and ‘죽울래!?’ (respectively, 
*   ‘Jay, stop kidding’, ‘Mooom, big sis is complaining agaain’ and ‘R’you crazy?’)
* 
* - take a video and send it to ‘seunghee DOT han ATSIGN gmail ANOTHERDOT com’
*
* Released on Seunghee’s birthday, because she likes robots.
* Copyright 2014 Seunghee Han
*/
package marytts.language.ko;

import java.util.Locale;

import marytts.datatypes.MaryData;
import marytts.datatypes.MaryDataType;
import marytts.language.ko.lib.CharacterClasses;
import marytts.modules.InternalModule;
import marytts.modules.JTokeniser;
import marytts.util.MaryUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;



public class KoreanTokeniser extends InternalModule
{
    public KoreanTokeniser() {
    	//declares input as RAWMARYXML and output as TOKENS for locale ko
    	super("KoreanTokeniser", MaryDataType.RAWMARYXML, MaryDataType.TOKENS, new Locale("ko"));
    }

    public MaryData process(MaryData d)
    throws Exception
    {
    	JTokeniser internalTokeniser = new JTokeniser(MaryDataType.RAWMARYXML,
                MaryDataType.TOKENS,
                Locale.ENGLISH);
    	internalTokeniser.startup();
    
    	//Korean has Korean script (Hangul), numbers and english letters
        //grouped into one token, we separate them before passing the
        //text to the default JTokeniser

        Document doc = d.getDocument();
        
        NodeIterator ni = ((DocumentTraversal)doc).createNodeIterator(
            doc, NodeFilter.SHOW_TEXT, null, false);

        Text textNode;
        while ((textNode = (Text)ni.nextNode()) != null) {
            String text = textNode.getData();
            String outputText = "";

            if (text.length() == 0) continue;

            outputText += text.charAt(0);
            CharacterCategory currentCategory = findCharacterCategory(text.charAt(0));

            for(int i=1;i<text.length();i++)
            {
                char nextChar = text.charAt(i);
                CharacterCategory nextCategory = findCharacterCategory(nextChar);

                //punctuation doesn't change the current category, the underlying
                //tokeniser will parse it efficiently
                if(currentCategory!=nextCategory && nextCategory != CharacterCategory.Punctuation)
                {
                    outputText += " ";
                }

                outputText += nextChar;
                currentCategory = nextCategory==CharacterCategory.Punctuation?currentCategory:nextCategory;
            }

            textNode.setData(outputText);
        }
        
        MaryData result = new MaryData(outputType(), d.getLocale());
        result.setDocument(doc);
        return internalTokeniser.process(result);
    }

    public enum CharacterCategory
    {
        KoreanScript,
        Punctuation,
        Number,
        SpellableEnglishScript, //a-zA-Z
        Other,
        Hanja;
    }
    
    public static CharacterCategory findCharacterCategory(char c)
    {
        if(CharacterClasses.isPureKoreanChar(c)){
           return CharacterCategory.KoreanScript;
        }else if(CharacterClasses.isHanja(c)){
        	return CharacterCategory.Hanja;
        }else if(CharacterClasses.isDigit(c)){
           return CharacterCategory.Number;
        }else if(CharacterClasses.isLatin(c)){
           return CharacterCategory.SpellableEnglishScript;
        }else if(CharacterClasses.isPunctuation(c)){
           return CharacterCategory.Punctuation;
        }else{
        	char normalisedC = MaryUtils.normaliseUnicodeLetters(c+"", new Locale("ko")).charAt(0);
        	if(normalisedC != c)
        	{
        		return findCharacterCategory(normalisedC);
        	}else{
        		return CharacterCategory.Other;
        	}
        }
    }
}
