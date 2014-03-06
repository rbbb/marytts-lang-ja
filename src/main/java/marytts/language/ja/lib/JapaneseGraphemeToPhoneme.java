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

package marytts.language.ja.lib;

import java.util.ArrayList;
import java.util.List;

import marytts.language.ja.phonemes.JapanesePhonemes;
import marytts.language.ja.phonemes.XSampa.Consonant;
import marytts.language.ja.phonemes.XSampa.Phoneme;
import marytts.language.ja.phonemes.XSampa.Vowel;
import marytts.util.MaryUtils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class JapaneseGraphemeToPhoneme {

	static Logger logger = MaryUtils.getLogger(JapaneseGraphemeToPhoneme.class.getCanonicalName());
	
	public static String extractPhonemes(String phrase)//, ReadingDictionary readingDictionary)
	{
		//
		// replace kanji, special characters, add word boundary at beginning and end
		//
		String analyzedPhrase = " "+replaceHanjaAndLatin(phrase);//, readingDictionary)+" ";

		List<Phoneme> phonemes = new ArrayList<Phoneme>(analyzedPhrase.length());

		
		
		boolean seenSmallTsu = false;
		boolean seenO = false;
		for(int i=0;i<analyzedPhrase.length();i++)
		{
			char currChar = analyzedPhrase.charAt(i);
			
			boolean newSeenO = false;
			boolean newSeenSmallTsu = false;
			
			boolean seenGlideEraser = false;
			boolean seenNormalIKana = false;
			boolean seenVorF = false;
			
			
			switch(currChar)
			{
			case 'ぁ':
			case 'ァ':
			case 'あ':
			case 'ア': phonemes.add(JapanesePhonemes.A); break;
			case 'ぃ':
			case 'ィ':
			case 'い':
			case 'イ': phonemes.add(JapanesePhonemes.I); break;
			case 'ぅ':
			case 'ゥ':
			case 'う': 
			case 'ウ': phonemes.add(seenO?JapanesePhonemes.O:JapanesePhonemes.U); break;
			case 'ぇ':
			case 'ェ':
			case 'え': 
			case 'エ': phonemes.add(JapanesePhonemes.E); break;
			case 'を':
			case 'ヲ': phonemes.add(JapanesePhonemes.O); break;
			case 'ぉ':
			case 'ォ':
			case 'お':
			case 'オ': newSeenO = true; phonemes.add(JapanesePhonemes.O); break;
			
			case 'ゔ':
			case 'ヴ': seenVorF = true; phonemes.add(JapanesePhonemes.V); break;
			
			
			case 'か':
			case 'カ': phonemes.add(seenSmallTsu?JapanesePhonemes.KK:JapanesePhonemes.K);phonemes.add(JapanesePhonemes.A); break;
			case 'き':
			case 'キ': phonemes.add(seenSmallTsu?JapanesePhonemes.KK:JapanesePhonemes.K);seenNormalIKana = true; break;
			case 'く': 
			case 'ク': phonemes.add(seenSmallTsu?JapanesePhonemes.KK:JapanesePhonemes.K);phonemes.add(JapanesePhonemes.U); break;
			case 'け': //TODO: need small KE ? check pronunciation
			case 'ケ': phonemes.add(seenSmallTsu?JapanesePhonemes.KK:JapanesePhonemes.K);phonemes.add(JapanesePhonemes.E); break;
			case 'こ': 
			case 'コ': phonemes.add(seenSmallTsu?JapanesePhonemes.KK:JapanesePhonemes.K);phonemes.add(JapanesePhonemes.O); break;
			case 'が': 
			case 'ガ': phonemes.add(seenSmallTsu?JapanesePhonemes.GG:JapanesePhonemes.G);phonemes.add(JapanesePhonemes.A); break;
			case 'ぎ':
			case 'ギ': phonemes.add(seenSmallTsu?JapanesePhonemes.GG:JapanesePhonemes.G);seenNormalIKana = true; break;
			case 'ぐ': 
			case 'グ': phonemes.add(seenSmallTsu?JapanesePhonemes.GG:JapanesePhonemes.G);phonemes.add(JapanesePhonemes.U); break;
			case 'げ':
			case 'ゲ': phonemes.add(seenSmallTsu?JapanesePhonemes.GG:JapanesePhonemes.G);phonemes.add(JapanesePhonemes.E); break;
			case 'ご': 
			case 'ゴ': phonemes.add(seenSmallTsu?JapanesePhonemes.GG:JapanesePhonemes.G);phonemes.add(JapanesePhonemes.O); break;
			
			
			case 'さ':
			case 'サ': phonemes.add(seenSmallTsu?JapanesePhonemes.SS:JapanesePhonemes.S);phonemes.add(JapanesePhonemes.A); break;
			case 'ざ':
			case 'ザ': phonemes.add(seenSmallTsu?JapanesePhonemes.ZZ:JapanesePhonemes.Z);phonemes.add(JapanesePhonemes.A); break;
			case 'し':
			case 'シ': phonemes.add(seenSmallTsu?JapanesePhonemes.SSH:JapanesePhonemes.SH); seenGlideEraser = true; break;
			case 'じ':
			case 'ジ': phonemes.add(seenSmallTsu?JapanesePhonemes.JJ:JapanesePhonemes.J); seenGlideEraser = true; break;
			case 'す':
			case 'ス': phonemes.add(seenSmallTsu?JapanesePhonemes.SS:JapanesePhonemes.S);phonemes.add(JapanesePhonemes.U); break;
			case 'ず':
			case 'ズ': phonemes.add(seenSmallTsu?JapanesePhonemes.ZZ:JapanesePhonemes.Z);phonemes.add(JapanesePhonemes.U); break;
			case 'せ':
			case 'セ': phonemes.add(seenSmallTsu?JapanesePhonemes.SS:JapanesePhonemes.S);phonemes.add(JapanesePhonemes.E); break;
			case 'ぜ':
			case 'ゼ': phonemes.add(seenSmallTsu?JapanesePhonemes.ZZ:JapanesePhonemes.Z);phonemes.add(JapanesePhonemes.E); break;
			case 'そ':
			case 'ソ': newSeenO = true; phonemes.add(seenSmallTsu?JapanesePhonemes.SS:JapanesePhonemes.S);phonemes.add(JapanesePhonemes.O); break;
			case 'ぞ':
			case 'ゾ': newSeenO = true; phonemes.add(seenSmallTsu?JapanesePhonemes.ZZ:JapanesePhonemes.Z);phonemes.add(JapanesePhonemes.O); break;
			
			case 'た':
			case 'タ': phonemes.add(seenSmallTsu?JapanesePhonemes.TT:JapanesePhonemes.T);phonemes.add(JapanesePhonemes.A); break;
			case 'だ':
			case 'ダ': phonemes.add(seenSmallTsu?JapanesePhonemes.DD:JapanesePhonemes.D);phonemes.add(JapanesePhonemes.A); break;
			case 'ち':
			case 'チ': phonemes.add(seenSmallTsu?JapanesePhonemes.TTCH:JapanesePhonemes.TCH); seenGlideEraser = true; break;
			case 'ぢ':
			case 'ヂ': phonemes.add(seenSmallTsu?JapanesePhonemes.JJ:JapanesePhonemes.J); seenGlideEraser = true; break;
			case 'つ':
			case 'ツ': phonemes.add(seenSmallTsu?JapanesePhonemes.TTS:JapanesePhonemes.TS);phonemes.add(JapanesePhonemes.U); break;
			
			case 'っ':
			case 'ッ': newSeenSmallTsu = true; break;
			
			case 'づ':
			case 'ヅ': phonemes.add(seenSmallTsu?JapanesePhonemes.ZZ:JapanesePhonemes.Z);phonemes.add(JapanesePhonemes.U); break;
			case 'て':
			case 'テ': phonemes.add(seenSmallTsu?JapanesePhonemes.TT:JapanesePhonemes.T);phonemes.add(JapanesePhonemes.E); break;
			case 'で':
			case 'デ': phonemes.add(seenSmallTsu?JapanesePhonemes.DD:JapanesePhonemes.D);phonemes.add(JapanesePhonemes.E); break;
			case 'と':
			case 'ト': newSeenO = true; phonemes.add(seenSmallTsu?JapanesePhonemes.TT:JapanesePhonemes.T);phonemes.add(JapanesePhonemes.O); break;
			case 'ど':
			case 'ド': newSeenO = true; phonemes.add(seenSmallTsu?JapanesePhonemes.DD:JapanesePhonemes.D);phonemes.add(JapanesePhonemes.O); break;
			
			case 'な':
			case 'ナ': phonemes.add(JapanesePhonemes.N);phonemes.add(JapanesePhonemes.A); break;
			case 'に':
			case 'ニ': phonemes.add(JapanesePhonemes.N); seenNormalIKana = true; break;
			case 'ぬ':
			case 'ヌ': phonemes.add(JapanesePhonemes.N);phonemes.add(JapanesePhonemes.U); break;
			case 'ね':
			case 'ネ': phonemes.add(JapanesePhonemes.N);phonemes.add(JapanesePhonemes.E); break;
			case 'の':
			case 'ノ': newSeenO = true; phonemes.add(JapanesePhonemes.N);phonemes.add(JapanesePhonemes.O); break;
			
			case 'は':
			case 'ハ': phonemes.add(JapanesePhonemes.H);phonemes.add(JapanesePhonemes.A); break;
			case 'ひ':
			case 'ヒ': phonemes.add(JapanesePhonemes.H); seenNormalIKana = true; break;
			case 'ふ':
			case 'フ': phonemes.add(JapanesePhonemes.F); seenVorF = true; break;
			case 'へ':
			case 'ヘ': phonemes.add(JapanesePhonemes.H);phonemes.add(JapanesePhonemes.E); break;
			case 'ほ':
			case 'ホ': newSeenO = true; phonemes.add(JapanesePhonemes.H);phonemes.add(JapanesePhonemes.O); break;
				
			case 'ぱ':
			case 'パ': phonemes.add(seenSmallTsu?JapanesePhonemes.PP:JapanesePhonemes.P);phonemes.add(JapanesePhonemes.A); break;
			case 'ぴ':
			case 'ピ': phonemes.add(seenSmallTsu?JapanesePhonemes.PP:JapanesePhonemes.P); seenNormalIKana = true; break;
			case 'ぷ':
			case 'プ': phonemes.add(seenSmallTsu?JapanesePhonemes.PP:JapanesePhonemes.P);phonemes.add(JapanesePhonemes.U); break;
			case 'ペ':
			case 'ぺ': phonemes.add(seenSmallTsu?JapanesePhonemes.PP:JapanesePhonemes.P);phonemes.add(JapanesePhonemes.E); break;
			case 'ぽ':
			case 'ポ': newSeenO = true; phonemes.add(seenSmallTsu?JapanesePhonemes.PP:JapanesePhonemes.P);phonemes.add(JapanesePhonemes.O); break;
			
			case 'ば':
			case 'バ': phonemes.add(seenSmallTsu?JapanesePhonemes.BB:JapanesePhonemes.B);phonemes.add(JapanesePhonemes.A); break;
			case 'び':
			case 'ビ': phonemes.add(seenSmallTsu?JapanesePhonemes.BB:JapanesePhonemes.B); seenNormalIKana = true; break;
			case 'ぶ':
			case 'ブ': phonemes.add(seenSmallTsu?JapanesePhonemes.BB:JapanesePhonemes.B);phonemes.add(JapanesePhonemes.U); break;
			case 'べ':
			case 'ベ': phonemes.add(seenSmallTsu?JapanesePhonemes.BB:JapanesePhonemes.B);phonemes.add(JapanesePhonemes.E); break;
			case 'ぼ':
			case 'ボ': newSeenO = true; phonemes.add(seenSmallTsu?JapanesePhonemes.BB:JapanesePhonemes.B);phonemes.add(JapanesePhonemes.O); break;
			
			case 'ま':
			case 'マ': phonemes.add(JapanesePhonemes.M);phonemes.add(JapanesePhonemes.A); break;
			case 'み':
			case 'ミ': phonemes.add(JapanesePhonemes.M);seenNormalIKana = true; break;
			case 'む':
			case 'ム': phonemes.add(JapanesePhonemes.M);phonemes.add(JapanesePhonemes.U); break;
			case 'め':
			case 'メ': phonemes.add(JapanesePhonemes.M);phonemes.add(JapanesePhonemes.E); break;
			case 'も':
			case 'モ': newSeenO = true; phonemes.add(JapanesePhonemes.M);phonemes.add(JapanesePhonemes.O); break;
			
			case 'や':
			case 'ヤ':
			case 'ゃ':
			case 'ャ': phonemes.add(JapanesePhonemes.Yglide);phonemes.add(JapanesePhonemes.A); break;
			case 'ゆ':
			case 'ユ':
			case 'ゅ':
			case 'ュ': phonemes.add(JapanesePhonemes.Yglide);phonemes.add(JapanesePhonemes.U);break;
			case 'ょ':
			case 'ョ':
			case 'よ':
			case 'ヨ': newSeenO = true; phonemes.add(JapanesePhonemes.Yglide);phonemes.add(JapanesePhonemes.O); break;
			
			case 'ら':
			case 'ラ': phonemes.add(JapanesePhonemes.RL);phonemes.add(JapanesePhonemes.A); break;
			case 'り':
			case 'リ': phonemes.add(JapanesePhonemes.RL); seenNormalIKana = true; break;
			case 'る':
			case 'ル': phonemes.add(JapanesePhonemes.RL);phonemes.add(JapanesePhonemes.U); break;
			case 'れ':
			case 'レ': phonemes.add(JapanesePhonemes.RL);phonemes.add(JapanesePhonemes.E); break;
			case 'ろ':
			case 'ロ': newSeenO = true; phonemes.add(JapanesePhonemes.RL);phonemes.add(JapanesePhonemes.O); break;
			
			case 'わ':
			case 'ワ': phonemes.add(JapanesePhonemes.W); phonemes.add(JapanesePhonemes.A); break;
			
			case 'ん':
			case 'ン': phonemes.add(JapanesePhonemes.Nalone); break;
			
			case 'ー':
			case '-':
				Phoneme latestPhoneme = phonemes.size()>0?phonemes.get(phonemes.size()-1):null;
				if(latestPhoneme != null && latestPhoneme instanceof Vowel) //do not lengthen ん
				{
					phonemes.add(latestPhoneme);
				}
				break;
			
			default: 
				if(CharacterClasses.isHiragana(currChar)||CharacterClasses.isKatakana(currChar))
				{
					logger.log(Level.WARN, "Common character not set: "+currChar);
				}else if(CharacterClasses.isPunctuation(currChar)){
					logger.log(Level.DEBUG, "ignoring punctuation: "+currChar);
				}else{
					logger.log(Level.DEBUG, "Unknown character: "+currChar);
				}
				break;
			}
			
			//true for chi, ji, di, shi
			//shi+small ya -> sh a
			if(seenGlideEraser)
			{
				boolean glideErased = false;
				if(i<analyzedPhrase.length()-1)
				{
					char peekNextChar = analyzedPhrase.charAt(i+1);
					switch(peekNextChar)
					{
					case 'ゃ':
					case 'ャ':
						phonemes.add(JapanesePhonemes.A);
						glideErased = true;
						break;
					case 'ゅ':
					case 'ュ':
						phonemes.add(JapanesePhonemes.U);
						glideErased = true;
						break;
					case 'ょ':
					case 'ョ': 
						newSeenO = true; 
						phonemes.add(JapanesePhonemes.O); 
						glideErased = true;
						break;					
					}
				}
				if(glideErased){
					i++;
				}else{
					phonemes.add(JapanesePhonemes.I);
				}
			}
			
			//true for ki,gi,ni,hi,mi,ri
			//ni+ya -> n y(==j) a
			if(seenNormalIKana)
			{
				boolean iGlided = false;
				if(i<analyzedPhrase.length()-1)
				{
					char peekNextChar = analyzedPhrase.charAt(i+1);
					switch(peekNextChar)
					{
					case 'ゃ':
					case 'ャ':
						phonemes.add(JapanesePhonemes.Yglide);
						phonemes.add(JapanesePhonemes.A);
						iGlided = true;
						break;
					case 'ゅ':
					case 'ュ':
						phonemes.add(JapanesePhonemes.Yglide);
						phonemes.add(JapanesePhonemes.U);
						iGlided = true;
						break;
					case 'ょ':
					case 'ョ': 
						newSeenO = true; 
						phonemes.add(JapanesePhonemes.Yglide);
						phonemes.add(JapanesePhonemes.O); 
						iGlided = true;
						break;
					
					}
				}
				if(iGlided){
					i++;
				}else{
					phonemes.add(JapanesePhonemes.I);
				}
			}
			
			//ヴぁ　-> V A
			//ふぁ　-> F A
			if(seenVorF)
			{
				boolean vExtended = false;
				if(i<analyzedPhrase.length()-1)
				{
					char peekNextChar = analyzedPhrase.charAt(i+1);
					switch(peekNextChar)
					{
					case 'ァ':
					case 'ぁ':
						phonemes.add(JapanesePhonemes.A);
						vExtended = true;
						break;
					case 'ィ':
					case 'ぃ':
						phonemes.add(JapanesePhonemes.I);
						vExtended = true;
						break;
					case 'ぅ':
					case 'ゥ':
						phonemes.add(JapanesePhonemes.U);
						vExtended = true;
						break;
					case 'ぇ':
					case 'ェ':
						phonemes.add(JapanesePhonemes.E);
						vExtended = true;
						break;
					case 'ぉ':
					case 'ォ':
						phonemes.add(JapanesePhonemes.O);
						vExtended = true;
						break;
					default:
						//consonants are always released in Japanese
						phonemes.add(JapanesePhonemes.U);
						vExtended = false; //!
						break;
					}
				}
				if(vExtended){
					i++;
				}
			}

			
			//update state variables
			seenSmallTsu = newSeenSmallTsu;
			seenO = newSeenO;
		}			
		
		//TODO: handling of kana repeating characters
		//TODO: kana for ゐ ... are not translated since they can be interpreted as i, wi 
		//TODO: wa-tenten can also be used for V A, currently not even marked as valid kana
		
		String resultString = "";
		for(Phoneme p:phonemes)
		{
			resultString += p.getXSampaCode();
//			resultString += p.getIpaCode();
//			resultString += p.getName().toLowerCase();
			
			resultString += " ";
		}
		return resultString;
	}
	

	// normalize all punctuation
	// expand all character replacements
	// latin alphabet is replaced by its pronunciation
	private static String replaceHanjaAndLatin(String phrase)
			//,ReadingDictionary readingDictionary) 
	{
		String normalized = CharacterClasses.normalizeString(phrase);
		
//		String replaceKanji;
//		if(readingDictionary!=null)
//		{
//			replaceKanji = readingDictionary.getPronunciation(normalized);
//		}else{
//			replaceKanji = normalized;
//		}
		
		String result = "";
		for(int i=0;i<normalized.length();i++)
		{
			char currChar = normalized.charAt(i);
			if(CharacterClasses.isKanji(currChar))
			{
				result += "カンジ";
			}else{
				String replaceLatin = NonJapaneseAlphabetToJapaneseWord.toJapanese(currChar);
				if(replaceLatin!=null){
					result += replaceLatin;
				}else{
					result += currChar;
				}
			}
		}
		
		return result;
	}

	
	public static void main(String[] args) throws Exception
	{
		//ReadingDictionary dic = null;//new ReadingDictionary(new FileInputStream("/home/xnote/zevoice/marytts/marytts-lang-ja/src/main/resources/marytts/language/ja/lexicon/edict2u"));
		System.out.println(JapaneseGraphemeToPhoneme.extractPhonemes("きょう"));//, dic));
		System.out.println(JapaneseGraphemeToPhoneme.extractPhonemes("さないものであったギリシアえられているしかもたないためである"));//, dic));

		for(Consonant c:JapanesePhonemes.consonants)
		{
			System.out.println(c.getExample());
			System.out.println("->"+extractPhonemes(c.getExample()));//, null));
		}
		for(Vowel v:JapanesePhonemes.vowels)
		{
			System.out.println(v.getExample());
			System.out.println("->"+extractPhonemes(v.getExample()));//, null));
		}
	}
}
