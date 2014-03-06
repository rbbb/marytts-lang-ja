/* *ATTENTION* *ATTENTION* *ATTENTION* *ATTENTION*
*
* This Japanese text to speech module is subject to the terms of the LGPL version 3.0, 
* which can be found here http://www.gnu.org/licenses/lgpl-3.0-standalone.html.
*
* In addition, if you use this module in something cool like a robot, you are kindly asked to do the following:
*
* - Put a Seunghee or スンヒ nametag on the robot
* 
* - have the robot say ‘ウヤ, 今ヤめて’ ‘かあああさん, ねさん文句ばっか’, 'あかはんはそんな!?' (respectively, 
*   ‘Jay, stop kidding’, ‘Mooom, big sis is complaining agaain’, 'R'you'kidding')
* 
* - take a video and send it to ‘hansteffi ATSIGN gmail ANOTHERDOT com’
*
* 日本で出会ったスンヒのヴァレンタイン
* Copyright 2014 Seunghee Han
*/
package marytts.language.ja;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import marytts.datatypes.MaryData;
import marytts.datatypes.MaryDataType;
import marytts.datatypes.MaryXML;
import marytts.language.ja.lib.CharacterClasses;
import marytts.language.ja.lib.CharacterClasses.Punctuation;
import marytts.language.ja.lib.CharacterClasses.PunctuationTypes;
import marytts.modules.InternalModule;
import marytts.util.dom.MaryDomUtils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import com.atilika.kuromoji.AbstractTokenizer;
import com.atilika.kuromoji.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;


public class JapaneseTokeniserAndPosTagger extends InternalModule
{
    public JapaneseTokeniserAndPosTagger() {
    	//declares input as RAWMARYXML and output as TOKENS for locale ja
    	super("JapaneseTokeniser", MaryDataType.RAWMARYXML, MaryDataType.PARTSOFSPEECH, new Locale("ja"));
    	
    	//use kuromoji to find pronunciations and POS tags
    	this.tokenizer = Tokenizer.builder().build();
    }
    AbstractTokenizer tokenizer;

    public MaryData process(MaryData d)
    throws Exception
    {    
    	//Japanese has Sino-Japanese characters (kanji), alphabet for asian words (hiragana)
    	//foreign loan words (katakana), latin alphabet (romaji), arabic numbers and ideogram numbers
    	//words are not separated by spaces so word boundary is determined statistically
    	//also, because text can use latin and japanese pages, punctuation can have several codes
    	//for comma, parentheses, etc...
    	
    	//because the theme particle HA is pronounced WA, we have to do POS tagging
    	//or develop an analysis marking this particle
    	
    	//because of Sino-Japanese characters (kanji), there has to be a word dictionary somehow
    	//especially since their pronunciation changes onyomi/kunyomi (native or borrowed pronunciation)
    	
    	//we use kuromoji to do the tokenisation, POS tagging, kanji pronunciation and HA->WA disambiguation
    	
    	//another solution would be to have a function to mark HA->WA, and have a kanji dictionary
    	//the latter part is already implemented in ReadingDictionary.
    	
    	NodeIterator tokenIterator = MaryDomUtils.createNodeIterator(d.getDocument(), "t");
    	if(tokenIterator.nextNode()!=null)
    	{
    		//the document has already been tokenized
    		//TODO: get POS and pronunciation for each element
    		MaryData result = new MaryData(outputType(), d.getLocale());
    		result.setDocument(d.getDocument());
            return result;	
    	}    	
    	
        Document doc = d.getDocument();
        
        NodeIterator ni = MaryDomUtils.createNodeIterator(doc, "p");

        Node paragraphNode;

        while ((paragraphNode = ni.nextNode()) != null) {
        	
        	String text = MaryDomUtils.getPlainTextBelow(paragraphNode);
        	
        	//cut into phrases
        	List<String> phrases = new ArrayList<String>();
        	String currPhrase = "";
        	for(int i=0;i<text.length();i++)
        	{
        		Punctuation punctuationEnum = CharacterClasses.getPunctuation(text.charAt(i));
        		if(punctuationEnum!=null && punctuationEnum.getPunctuationType()==PunctuationTypes.Space)
        		{
        			//Japanese usually doesn't have spaces and they will prevent the POS tagger from parsing
        			continue;
        		}
        		currPhrase += text.charAt(i);
        		if(punctuationEnum!=null && punctuationEnum.getPunctuationType()==PunctuationTypes.Period)
        		{
        			phrases.add(currPhrase);
        			currPhrase = "";
        		}
        	}
        	if(currPhrase.length()>0)
        	{
        		phrases.add(currPhrase);
        	}
        	
        	//remove paragraph text
        	setParagraphText((Element)paragraphNode,"");
            
        	for(String phrase:phrases)
        	{
        		Element sentence = MaryXML.createElement(doc, MaryXML.SENTENCE);
        		
	            for (Token parserToken : tokenizer.tokenize(phrase)) {
	                
	            	boolean isPunctuationToken = true;
	            	for(int i=0;i<parserToken.getSurfaceForm().length();i++)
	            	{
	            		if(CharacterClasses.getPunctuation(parserToken.getSurfaceForm().charAt(i))==null)
	            		{
	            			isPunctuationToken = false;
	            			break;
	            		}
	            	}
	            	
	            	Element createdToken = MaryXML.createElement(doc, MaryXML.TOKEN);
	            	
	            	if(isPunctuationToken)
	            	{
	            		createdToken.setAttribute("pos", "$"+parserToken.getSurfaceForm());
	            	}else{
		            	createdToken.setAttribute("pos", parserToken.getPartOfSpeech());
		            	
		            	if(parserToken.getReading()!=null && parserToken.getReading().length()>0) //only if in underlying dictionary
		            	{
		            		createdToken.setAttribute("sounds_like", parserToken.getReading());
		            	}
		            	
		            	//the part justifying the POS tagger
		            	if( (parserToken.getSurfaceForm().equals("は") ||
		            			parserToken.getSurfaceForm().equals("ハ"))
		            			&& parserToken.getPartOfSpeech().equals("助詞,係助詞,*,*"))
		            	{
		            		createdToken.setAttribute("sounds_like", "ワ");	
		            	}
		            	
		            	//TODO: Inoue != イノーエ
		            	//TODO: set g2p properly (always lexicon + phonology rules,
		            	//with little way to get a pronunciation in case of unknown kanji word)
	            	}
	            	 MaryDomUtils.setTokenText(createdToken, parserToken.getSurfaceForm());
	            	
	            	 sentence.appendChild(createdToken);
	       
	            }
	            
	            paragraphNode.appendChild(sentence);
        	}
        }
        
        MaryData result = new MaryData(outputType(), d.getLocale());
        
        result.setDocument(doc);
        return result;
    }
    public static void setParagraphText(Element t, String s)
    {
        if (!t.getNodeName().equals(MaryXML.PARAGRAPH))
            throw new DOMException(DOMException.INVALID_ACCESS_ERR,
                                   "Only " + MaryXML.PARAGRAPH + " elements allowed, received " +
                                   t.getNodeName() + ".");
        // Here, we rely on the fact that a t element has at most
        // one TEXT child with non-whitespace content:
        Document doc = t.getOwnerDocument();
        NodeIterator textIt = ((DocumentTraversal)doc).
            createNodeIterator(t, NodeFilter.SHOW_TEXT, null, false);
        Text text = null;
        String textString = null;
        while ((text = (Text) textIt.nextNode()) != null) {
            textString = text.getData().trim();
            if (!textString.equals("")) break;
        }
        if (text == null) { // token doesn't have a non-whitespace text child yet
            text = (Text)t.getOwnerDocument().createTextNode(s);
            t.appendChild(text);
        } else { // found the one text element with non-whitespace content
            // overwrite it:
            text.setData(s);
        }
    }

}
