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

package marytts.language.ko.phonemes;

import java.util.ArrayList;



public class KoreanPhonemes {
	private static ArrayList<XSampa.Consonant> consonants = new ArrayList<XSampa.Consonant>();
	private static ArrayList<XSampa.Vowel> vowels = new ArrayList<XSampa.Vowel>();
	static 
	{
		consonants.add(XSampa.findConsonantByIPA("m"));
		consonants.add(XSampa.findConsonantByIPA("p"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Bilabial, XSampa.ConsonantType.Plosive, "p!", "p͈", "strong p", "뿔"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Bilabial, XSampa.ConsonantType.Plosive, "ph", "pʰ", "aspired p", "풀"));
		consonants.add(XSampa.findConsonantByIPA("t"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Alveolar, XSampa.ConsonantType.Plosive, "t^", "t͈", "strong t", "딸"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Alveolar, XSampa.ConsonantType.Plosive, "th", "tʰ", "aspired t", "탈"));
		consonants.add(XSampa.findConsonantByIPA("n"));
		consonants.add(new XSampa.Consonant(true, XSampa.ConsonantPlace.AlveoloPalatal, XSampa.ConsonantType.Plosive, "j", "tɕ", "j (pronounced tsh)", "자다"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.AlveoloPalatal, XSampa.ConsonantType.Plosive, "jh", "t͈ɕ", "aspired j (ch)", "짜다"));
		consonants.add(new XSampa.Consonant(true, XSampa.ConsonantPlace.AlveoloPalatal, XSampa.ConsonantType.Plosive, "j!", "tɕʰ", "strong j", "차다"));
		consonants.add(XSampa.findConsonantByIPA("k"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Velar, XSampa.ConsonantType.Plosive, "k!", "k͈", "strong k", "까다"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Velar, XSampa.ConsonantType.Plosive, "kh", "kʰ", "aspired k", "칼"));
		consonants.add(XSampa.findConsonantByIPA("ŋ"));
		consonants.add(XSampa.findConsonantByIPA("s"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Alveolar, XSampa.ConsonantType.Fricative, "s!", "s͈", "strong s", "쌀"));
		consonants.add(XSampa.findConsonantByIPA("h"));


		//glides
		//'u'isa
		consonants.add(XSampa.findConsonantByIPA("ɰ"));
		//usually noted j, but set to y
		consonants.add(new XSampa.Consonant(false,XSampa.ConsonantPlace.Palatal,XSampa.ConsonantType.Approximant,"y","j","palatal approximant","English yes [jEs], French yeux [j2]"));
		consonants.add(XSampa.findConsonantByIPA("w"));
		consonants.add(XSampa.findConsonantByIPA("ɥ"));

		//end of word
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Bilabial, XSampa.ConsonantType.Plosive, "p|", "p̚", "end of word p", "pap"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Velar, XSampa.ConsonantType.Plosive, "k|", "k̚", "end of word k", "kak"));
		consonants.add(new XSampa.Consonant(false, XSampa.ConsonantPlace.Alveolar, XSampa.ConsonantType.Plosive, "t|", "t̚", "end of word t", "bolit"));
		consonants.add(XSampa.findConsonantByIPA("l"));

		//medial
		consonants.add(XSampa.findConsonantByIPA("g"));//k in the middle of a word
		consonants.add(XSampa.findConsonantByIPA("d"));//t in the middle of a word
		consonants.add(XSampa.findConsonantByIPA("ɦ"));//h in the middle of a word
		consonants.add(XSampa.findConsonantByIPA("ɾ"));//l ...

		//s before i, and strong variant
		consonants.add(XSampa.findConsonantByIPA("ɕ"));
		consonants.add(new XSampa.Consonant(false,XSampa.ConsonantPlace.AlveoloPalatal,XSampa.ConsonantType.Fricative,"s\\!","ɕ͈","strong voiceless alveolo-Palatal Fricative",""));

		//long vowels removed since they are little used in modern Korean 
		//and would require a dictionary *and* word disambiguation *and* a probabilistic parser of sub phrases into words

		vowels.add(XSampa.findVowelByIPA("e"));
		//		vowels.add(new XSampa.Vowel(false,false,XSampa.VowelOpenness.CloseMid,XSampa.VowelPosition.Front,"e:","eː","long close-mid front unrounded vowel","베다"));
		vowels.add(XSampa.findVowelByIPA("i"));
		//		vowels.add(new XSampa.Vowel(false,false,XSampa.VowelOpenness.Close,XSampa.VowelPosition.Front,"i:","iː","long close front unrounded vowel","시장 (market)"));
		vowels.add(new XSampa.Vowel(false, false,false,XSampa.VowelOpenness.CloseMid,XSampa.VowelPosition.Front,"E","ɛ","short close-mid front unrounded vowel","태양"));
		//		vowels.add(new XSampa.Vowel(false,false,XSampa.VowelOpenness.CloseMid,XSampa.VowelPosition.Front,"E:","ɛː","long close-mid front unrounded vowel","태도"));
		vowels.add(XSampa.findVowelByIPA("a"));
		//		vowels.add(new XSampa.Vowel(false,false,XSampa.VowelOpenness.Open,XSampa.VowelPosition.Front,"a:","aː","long open-mid front unrounded vowel","말(language)"));
		vowels.add(XSampa.findVowelByIPA("o"));
		//		vowels.add(new XSampa.Vowel(false,true,XSampa.VowelOpenness.CloseMid,XSampa.VowelPosition.Back,"o:","oː","long close-mid rounded vowel","보리(barley)"));
		vowels.add(XSampa.findVowelByIPA("u"));
		//		vowels.add(new XSampa.Vowel(false,true,XSampa.VowelOpenness.Close,XSampa.VowelPosition.Back,"u:","uː","long close rounded vowel","수박"));
		vowels.add(XSampa.findVowelByIPA("ʌ"));
		//		vowels.add(new XSampa.Vowel(false,false,XSampa.VowelOpenness.OpenMid,XSampa.VowelPosition.Back,"V:","ʌː","long open-mid back unrounded vowel","벌(bee)"));
		vowels.add(XSampa.findVowelByIPA("ɯ"));
		//		vowels.add(new XSampa.Vowel(false,false,XSampa.VowelOpenness.Close,XSampa.VowelPosition.Back,"M:","ɯː","long close back unrounded vowel","음식"));
		vowels.add(XSampa.findVowelByIPA("ø"));
		//		vowels.add(new XSampa.Vowel(false,true,XSampa.VowelOpenness.CloseMid,XSampa.VowelPosition.Front,"2:","øː","long close-mid front rounded vowel","외투"));
	}
	
	public static void main(String[] args) {
		System.out.println(PhonemeToAllophoneXML.makeXml("ko", consonants, vowels));
	}

}
