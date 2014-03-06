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

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import marytts.datatypes.MaryData;
import marytts.datatypes.MaryDataType;
import marytts.datatypes.MaryXML;
import marytts.exceptions.MaryConfigurationException;
import marytts.fst.FSTLookup;
import marytts.language.ja.lib.JapaneseGraphemeToPhoneme;
import marytts.modules.InternalModule;
import marytts.modules.phonemiser.AllophoneSet;
import marytts.modules.phonemiser.TrainedLTS;
import marytts.util.dom.MaryDomUtils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.traversal.NodeIterator;


/* The Japanese phonemiser module 
 * based on the phonemiser from Marc Schr&ouml;der, Sathish
 */

public class JapanesePhonemiser extends InternalModule
{

    protected Map<String, List<String>> userdict;
    protected FSTLookup lexicon;
    protected TrainedLTS lts;
    protected boolean removeTrailingOneFromPhones = true;

    protected AllophoneSet allophoneSet;
    
//    ReadingDictionary hanjaDictionary = new ReadingDictionary();

    public JapanesePhonemiser()
    throws IOException,  MaryConfigurationException
    {
        super("Phonemiser", MaryDataType.WORDS, MaryDataType.PHONEMES, new Locale("ja"));
    }
    
    public MaryData process(MaryData d)
        throws Exception
    {
        Document doc = d.getDocument();
        NodeIterator it = MaryDomUtils.createNodeIterator(doc, doc, MaryXML.TOKEN);
        Element t = null;
        while ((t = (Element) it.nextNode()) != null) {
                String text;
                
                // Do not touch tokens for which a transcription is already
                // given (exception: transcription contains a '*' character:
                if (t.hasAttribute("ph") &&
                    !t.getAttribute("ph").contains("*")) {
                    continue;
                } 
                if (t.hasAttribute("sounds_like"))
                    text = t.getAttribute("sounds_like");
                else
                    text = MaryDomUtils.tokenText(t);
                
                String pos = null;
                // use part-of-speech if available
                if (t.hasAttribute("pos")){
                    pos = t.getAttribute("pos");
                }
                
                if (text != null && !text.equals("") && (pos==null || !pos.startsWith("$")/*punctuation*/)) {
                    // If text consists of several parts (e.g., because that was
                    // inserted into the sounds_like attribute), each part
                    // is transcribed separately.
                    StringBuilder ph = new StringBuilder();
                    String g2pMethod = null;
                    StringTokenizer st = new StringTokenizer(text, " -");
                    while (st.hasMoreTokens()) {
                        String graph = st.nextToken();
                        StringBuilder helper = new StringBuilder();
                        
                        
                        String phon = phonemise(graph, pos, helper);
                        
                        
                        if (ph.length() == 0) { // first part
                            // The g2pMethod of the combined beast is
                            // the g2pMethod of the first constituant.
                            g2pMethod = helper.toString();
                            ph.append(phon);
                        } else { // following parts
                            ph.append(" - ");
                            // Reduce primary to secondary stress:
                            ph.append(phon.replace('\'', ','));
                       }
                    }
                    
                    if (ph != null && ph.length() > 0) {
                        setPh(t, ph.toString());
                        t.setAttribute("g2p_method", g2pMethod);
                    }
            }
        }
        MaryData result = new MaryData(outputType(), d.getLocale());
        result.setDocument(doc);
        return result;
    }

    /**
     * Phonemise the word text. Since Japanese pronunciation is
     * regular after transformation to kana, the phonemisation is ruled based,
     * exceptions: at least ha -> wa (handled in the POS tagger), and Inoue (last name)
     * 
     * @param text the textual (graphemic) form of a word.
     * @param pos the part-of-speech of the word
     * @param g2pMethod This is an awkward way to return a second
     * String parameter via a StringBuilder. If a phonemisation of the text is
     * found, this parameter will be filled with the method of phonemisation
     * ("lexicon", ... "rules"). 
     * @return a phonemisation of the text if one can be generated, or
     * null if no phonemisation method was successful.
     */
    public String phonemise(String text, String pos, StringBuilder g2pMethod)
    {
    	g2pMethod.append("rules");
    	return JapaneseGraphemeToPhoneme.extractPhonemes(text);// kanjiDictionary); not required if using kuromoji
    }
    
    
    
    protected void setPh(Element t, String ph)
    {
        if (!t.getTagName().equals(MaryXML.TOKEN))
            throw new DOMException(DOMException.INVALID_ACCESS_ERR,
                                   "Only t elements allowed, received " +
                                   t.getTagName() + ".");
        if (t.hasAttribute("ph")) {
            String prevPh = t.getAttribute("ph");
            // In previous sampa, replace star with sampa:
            String newPh = prevPh.replaceFirst("\\*", ph);
            t.setAttribute("ph", newPh);
        } else {
            t.setAttribute("ph", ph);
        }
    }
}
