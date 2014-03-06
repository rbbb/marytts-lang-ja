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
package marytts.language.ja.phonemes;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import marytts.language.ja.phonemes.XSampa.Consonant;
import marytts.language.ja.phonemes.XSampa.Vowel;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;




public class PhonemeToAllophoneXML {
	public static String makeXml(String langCode, List<Consonant> consonants, List<Vowel> vowels)
	{
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root element
			//<allophones name="sampa" xml:lang="fr" features="vlng vheight vfront vrnd ctype cplace cvox">
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("allophones");
			rootElement.setAttribute("name", "sampa");
			rootElement.setAttribute("xml:lang", langCode);
			rootElement.setAttribute("features", "vlng vheight vfront vrnd ctype cplace cvox");
			doc.appendChild(rootElement);


			//silence
			//<silence ph="_"/>
			Element silence = doc.createElement("silence");
			silence.setAttribute("ph", "_");
			rootElement.appendChild(silence);

			//consonants
			//<consonant ph="p" ctype="s" cplace="l" cvox="-"/>
			for(Consonant consonant:consonants)
			{
				Comment consonantComment = doc.createComment(consonant.getExample());
				rootElement.appendChild(consonantComment);
				
				Element consonantElement = doc.createElement("consonant");
				consonantElement.setAttribute("ph", consonant.getXSampaCode());
				
				//only currently used in feature selection
				//so the only constraint is that sounds
				//with close place have identical cplace tag
				String consonantPlace;
				switch(consonant.getPlace())
				{

				case LabialVelar:
				case Labiodental:
				case LabioPalatal:
					consonantPlace = "v";
					break;

				case Bilabial: 
					consonantPlace = "b";
					break;

				case Dental:
					consonantPlace = "d";
					break;

				case AlveoloPalatal:
				case Alveolar:
				case Postalveolar:
				case Retroflex:
					consonantPlace = "a";
					break;

				case Epiglottal:
				case Glottal:
				case Pharyngeal:
					consonantPlace = "g";
					break;

				case Palatal:
				case PalatalVelar:
					consonantPlace = "p";

				case Uvular:
					consonantPlace = "u";
					break;

				case Velar:
				case VelarizedAlveolar:
					consonantPlace = "v";
					break;

				default:
					throw new IllegalArgumentException("Unknown consonant place: "+consonant.getPlace());
				}
				consonantElement.setAttribute("cplace", consonantPlace);

				String consonantType;
				switch(consonant.getType())
				{
				case Approximant:
					consonantType = "r"; //glide
					break;

				case Fricative:
				case LateralFricative:
					consonantType = "f"; //fricative
					break;

				case Stop:
					consonantType = "t";//stop
					break;

				case Nasal:
					consonantType = "n";//nasal
					break;

				case Plosive:
				case Implosive:
					consonantType = "s"; //plosives
					break;

				case Flap:
				case LateralApproximant:
				case LateralFlap:
				case Trill:

					consonantType = "l";//liquids
					break;

				default:
					throw new IllegalArgumentException("Unknown constant type "+consonant.getType());
				}
				consonantElement.setAttribute("ctype", consonantType);

				consonantElement.setAttribute("cvoice", consonant.isVoiced()?"+":"-");
				
				rootElement.appendChild(consonantElement);
			}
			
			//vowels
			//<vowel ph="e~" vlng="l" vheight="2" vfront="1" vrnd="-" ((ctype="n")) />
			for(Vowel vowel:vowels)
			{
				Comment vowelComment = doc.createComment(vowel.getExample());
				rootElement.appendChild(vowelComment);
				
				Element vowelElement = doc.createElement("vowel");
				vowelElement.setAttribute("ph", vowel.getXSampaCode());

				//only currently used in feature selection
				//so the only constraint is that sounds
				//with close place have identical cplace tag
				String vowelOpenness;
				switch(vowel.getOpenness())
				{
				case Open:
				case NearOpen:
					vowelOpenness="3";
					break;
					
				case OpenMid:
				case Mid:
				case CloseMid:
					vowelOpenness="2";
					break;
					
				case NearClose:
				case Close:
					vowelOpenness="1";
					break;
					
				default:
					throw new IllegalArgumentException("Unknown vowel openness: "+vowel.getOpenness());
				}
				vowelElement.setAttribute("vheight", vowelOpenness);

				String vowelFrontness;
				switch(vowel.getPosition())
				{
				case Back:
				case NearBack:
					vowelFrontness="3";
					break;
					
				case BackMid:
				case Central:
				case FrontMid:
					vowelFrontness="2";
					break;
					
				case NearFront:
				case Front:
					vowelFrontness="1";
					break;
				default:
					throw new IllegalArgumentException("Unknown vowel position "+vowel.getPosition());
				}
				vowelElement.setAttribute("vfront", vowelFrontness);
				
				vowelElement.setAttribute("vlng", vowel.isLongVowel()?"l":"s");

				vowelElement.setAttribute("vrnd", vowel.isRounded()?"+":"-");
				
				rootElement.appendChild(vowelElement);
			}
			
			
			TransformerFactory factory = TransformerFactory.newInstance();
			  Transformer transformer = factory.newTransformer();

			  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			  transformer.setOutputProperty(OutputKeys.METHOD,"xml");
			  // transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");


			  // create string from xml tree
			  StringWriter sw = new StringWriter();
			  StreamResult result = new StreamResult(sw);
			  DOMSource source = new DOMSource(doc);
			  transformer.transform(source, result);

			  return sw.toString();

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return pce.toString();
		} catch (TransformerException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
