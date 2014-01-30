package marytts.language.ko;

import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.traversal.TreeWalker;

import marytts.datatypes.MaryData;
import marytts.datatypes.MaryDataType;
import marytts.datatypes.MaryXML;
import marytts.language.ko.lib.CharacterClasses;
import marytts.modules.InternalModule;
import marytts.util.dom.MaryDomUtils;


//TODO: use JHanNanum from of KAIST
//output tags:
//-NC (common noun), NQ (proper nouns), NB (noun), NP (pronoun), NN (number)
//-PV (verb), PA (adjective), PX (secondary verb/adjective, as in kajiko-kada)
//-MM (pre-noun determiner), MA (adverb)
//-II (Interjection)
//-JC (function (subj/obj..) determining postpositions), JX (case (to/with..) postpositions), JP (depiction postpositions (copula))
//-E series are suffixes affixed to verbs, not sure about exact classification
//-EP (???), EC (connective verb/adj suffix), ET (flexible verb/adj suffix, changes nature or meaning), EF (terminating suffix)
//-XP (prefix), XS (suffix)
//-S (symbol)
//-F (foreign language word)
public class KoreanPOSTagger extends InternalModule{
	
	public KoreanPOSTagger()
	{
        super("OpenNLPPosTagger",
                MaryDataType.WORDS,
                MaryDataType.PARTSOFSPEECH,
                new Locale("ko"));
	}
	
    public MaryData process(MaryData d)
    throws Exception
    {
        Document doc = d.getDocument(); 
        NodeIterator sentenceIt = MaryDomUtils.createNodeIterator(doc, doc, MaryXML.SENTENCE);
        Element sentence;
        while ((sentence = (Element) sentenceIt.nextNode()) != null) {
            TreeWalker tokenIt = MaryDomUtils.createTreeWalker(sentence, MaryXML.TOKEN);
            Element t;
            while ((t = (Element) tokenIt.nextNode()) != null) {
                String tokenText = MaryDomUtils.tokenText(t);
                boolean isPunct = true;
                for(int i=0;i<tokenText.length();i++)
                {
                	if(!CharacterClasses.isPunctuation(tokenText.charAt(i)))
                	{
                		isPunct = false;
                		break;
                	}
                }
                t.setAttribute("pos", isPunct?"$PUNCT":"content");
            }
        }
        MaryData output = new MaryData(outputType(), d.getLocale());
        output.setDocument(doc);
        return output;
    }

}
