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

package marytts.language.ko.lib;


public class KoreanGraphemeToPhoneme {

	private static final char NO_CHAR = ' ';
	public static String extractPhonemes(String phrase, HanjaDictionary hanjaDictionary)
	{
		//TODO: to add dictionary based lengthening, add another stream marking the syllables
		//for which vowels have to be lengthened, and modify the phoneme computations
		//TODO: chicken exception

		boolean wordStart = false;
		boolean wordMiddle = false;

		//
		// replace hanja, add word boundary at beginning and end
		//
		String analyzedPhrase = " "+replaceHanjaAndLatin(phrase, hanjaDictionary)+" ";
		
		
		char remainingEnding = NO_CHAR;
		String phonemeResults = "";

		for(int i=0;i<analyzedPhrase.length();i++)
		{
			char currChar = analyzedPhrase.charAt(i);

			if(CharacterClasses.isPunctuation(currChar) || !CharacterClasses.isPureKoreanChar(currChar))
			{
				//we are at word boundary, process the jong (bottom) part
				//of the previous character with end of word pronunciation rules
				//most mixed consonants (ps, lm) should not occur but are handled anyways
				String finalPhoneme=null;
				switch(remainingEnding)
				{
				case NO_CHAR: break;
				default:       /*do nothing? probably foreign word*/  break;
				case 'ㄱ':       finalPhoneme="k|"; break;
				case 'ㄲ':       finalPhoneme="k|"; break;
				case 'ㄳ':       finalPhoneme="k|";        break;
				case 'ㄴ':       finalPhoneme="n";        break;
				case 'ㄵ':       finalPhoneme="n";        break;
				case 'ㄶ':       finalPhoneme="n";        break;
				case 'ㄷ':       finalPhoneme="t|";        break;
				case 'ㄹ':       finalPhoneme="l";        break;
				case 'ㄺ':       finalPhoneme="k";        break;
				case 'ㄻ':       finalPhoneme="m";       break;
				case 'ㄼ':       finalPhoneme="l";       break;
				case 'ㄽ':       finalPhoneme="l";       break;
				case 'ㄾ':       finalPhoneme="l";       break;
				case 'ㄿ':       finalPhoneme="l";       break;
				case 'ㅀ':       finalPhoneme="l";       break;
				case 'ㅁ':       finalPhoneme="m";       break;
				case 'ㅂ':       finalPhoneme="p|";       break;
				case 'ㅄ':       finalPhoneme="l";       break;
				case 'ㅅ':       finalPhoneme="t|";       break;
				case 'ㅆ':       finalPhoneme="t|";       break;
				case 'ㅇ':       finalPhoneme="N";       break;
				case 'ㅈ':       finalPhoneme="t|";       break;
				case 'ㅊ':       finalPhoneme="t|";       break;
				case 'ㅋ':       finalPhoneme="k|";       break;
				case 'ㅌ':       finalPhoneme="t|";       break;
				case 'ㅍ':       finalPhoneme="p|";       break;
				case 'ㅎ':              break;
				}
				phonemeResults += finalPhoneme!=null?finalPhoneme+" ":"";
				remainingEnding=' ';
				phonemeResults+=currChar;
				wordStart=true;
				wordMiddle=false;
								
				continue;
			}

			SplitHangul currentHangul = SplitHangul.split(currChar);

			char startConsonant = currentHangul.cho!=' '?currentHangul.cho:NO_CHAR;
			char middleVowel = currentHangul.jung!=' '?currentHangul.jung:NO_CHAR;
			char endConsonant = currentHangul.jong!=' '?currentHangul.jong:NO_CHAR;


			RemainingConsonantType remainingConsonantType;
			switch(remainingEnding)
			{
			case 'ㄱ': 
			case 'ㄲ':
			case 'ㅋ':
				remainingConsonantType = RemainingConsonantType.KType;
				break;
			case 'ㄴ':
				remainingConsonantType = RemainingConsonantType.NType;
				break;
			case 'ㅍ':
			case 'ㅂ':
				remainingConsonantType = RemainingConsonantType.PType;
				break;
			case 'ㄹ':
				remainingConsonantType = RemainingConsonantType.LType;
				break;
			case 'ㅁ':
				remainingConsonantType = RemainingConsonantType.MType;
				break;

			case 'ㄷ':
			case 'ㅅ':
			case 'ㅆ':
			case 'ㅈ':
			case 'ㅊ':
			case 'ㅌ':
				remainingConsonantType = RemainingConsonantType.TType;
				break;
			case 'ㅎ':
				remainingConsonantType = RemainingConsonantType.HType;
				break;
			case 'ㅇ':
				remainingConsonantType = RemainingConsonantType.NGType;
				break;
			case 'ㄳ':
			case 'ㄵ':
			case 'ㄶ':
			case 'ㄺ':
			case 'ㄻ':
			case 'ㄼ':
			case 'ㄽ':
			case 'ㄾ':
			case 'ㄿ':
			case 'ㅀ':
			case 'ㅄ':
				remainingConsonantType = RemainingConsonantType.DoubleConsonantType;
				break;
			default:
				remainingConsonantType = RemainingConsonantType.Absent;
				break;
			}

			boolean hasStartConsonant;
			switch(startConsonant)
			{
			case 'ㄱ':
			case 'ㄲ':
			case 'ㄴ':
			case 'ㄷ':
			case 'ㄸ':
			case 'ㄹ':
			case 'ㅁ':
			case 'ㅂ':
			case 'ㅃ':
			case 'ㅅ':
			case 'ㅆ':
			case 'ㅈ':
			case 'ㅉ':
			case 'ㅊ':
			case 'ㅋ':
			case 'ㅌ':
			case 'ㅍ':
			case 'ㅎ':
				hasStartConsonant = true;
				break;
			case 'ㅇ':
				hasStartConsonant = false;
				break;
			default: //stray characters, foreign characters
				hasStartConsonant = false;
				break;
			}

			if(!hasStartConsonant)
			{
				if(remainingConsonantType!=RemainingConsonantType.DoubleConsonantType)
				{
					phonemeResults += phonemeForConsonantVowelPair(remainingEnding, middleVowel, wordStart, wordMiddle)+" ";
				}else{
					phonemeResults += phonemeForDoubleConsonantVowelPair(remainingEnding, middleVowel, wordStart, wordMiddle)+" ";
				}
			}else if(remainingConsonantType==RemainingConsonantType.Absent){
				phonemeResults += phonemeForConsonantVowelPair(startConsonant, middleVowel, wordStart, wordMiddle)+" ";
			}else{
				//compute consonant mixing
				PhonemeConsonantPair mixResult = computeConsonantMixing(remainingEnding, remainingConsonantType, startConsonant);
				phonemeResults += mixResult.phoneme.length()>0 ? (mixResult.phoneme+" "):"";
				phonemeResults += phonemeForConsonantVowelPair(mixResult.consonant, middleVowel, wordStart, wordMiddle)+" ";
			}

			//shift the end consonant, update position booleans
			remainingEnding = endConsonant;
			wordStart=false;
			wordMiddle=true;
		}

		return phonemeResults;
	}

	//expand all character replacements
	// hanja gets replaced by its hangul
	// latin alphabet is replaced by its pronunciation
	// missing hanja is replaced by 'hanja' and unknown characters by 'euk'
	private static String replaceHanjaAndLatin(String phrase,
			HanjaDictionary hanjaDictionary) {
		
		String result = "";
		for(int i=0;i<phrase.length();i++)
		{
			char currChar = phrase.charAt(i);
			if(CharacterClasses.isHanja(currChar))
			{
				String pronunciation = hanjaDictionary.getPronunciationOrNull(currChar);
				if(pronunciation!=null)
				{
					result += pronunciation;
				}else{
					result += " 한자 ";//leave some whitespace to not influence the pronunciation of neighbouring characters
				}
			}else if(CharacterClasses.isDigit(currChar)){
				//no digits should appear here, they should have been transformed to hangul
				//by the preprocessor
				//in case there are still digits remaining because they didn't match any known pattern
				//we spell them out (one by one, so no 1-2 correction) here
				result += " "+KoreanNumbersToWords.toSpelledOutNumber(currChar+"").get(0)+" ";
			}else if(CharacterClasses.isPunctuation(currChar) || CharacterClasses.isPureKoreanChar(currChar)){
				result += currChar;
			}else{
				String replacement = NonKoreanAlphabetToKoreanWord.toKorean(currChar);
				if(replacement!=null)
				{
					result += replacement;
				}else{
					result += " 윽 "; //weird sound to indicate missing character
				}
			}
		}
		return result;
	}

	private static PhonemeConsonantPair computeConsonantMixing(
			char remainingEnding, RemainingConsonantType remainingConsonantType, char startConsonant) {

		if(remainingConsonantType==RemainingConsonantType.DoubleConsonantType)
		{
			String addedPhoneme;
			char remainingConsonant;
			RemainingConsonantType newRemainingConsonantType;
			switch(remainingEnding)
			{
			case 'ㄳ': addedPhoneme = "k|"; remainingConsonant='ㅅ'; newRemainingConsonantType=RemainingConsonantType.TType; break;
			case 'ㄺ': addedPhoneme = "k|"; remainingConsonant='ㄹ'; newRemainingConsonantType=RemainingConsonantType.LType; break;
			case 'ㄵ': addedPhoneme = "n"; remainingConsonant='ㅈ'; newRemainingConsonantType=RemainingConsonantType.TType; break;
			case 'ㄶ': addedPhoneme = "n"; remainingConsonant='ㅎ'; newRemainingConsonantType=RemainingConsonantType.HType; break;
			case 'ㄻ': addedPhoneme = "m"; remainingConsonant='ㄹ'; newRemainingConsonantType=RemainingConsonantType.LType; break;
			case 'ㄼ': addedPhoneme = "p|"; remainingConsonant='ㄹ'; newRemainingConsonantType=RemainingConsonantType.LType; break;
			case 'ㄿ': addedPhoneme = "p|"; remainingConsonant='ㄹ'; newRemainingConsonantType=RemainingConsonantType.LType; break;
			case 'ㄽ': addedPhoneme = "l"; remainingConsonant='ㅅ'; newRemainingConsonantType=RemainingConsonantType.TType; break;
			case 'ㄾ': addedPhoneme = "l"; remainingConsonant='ㅌ'; newRemainingConsonantType=RemainingConsonantType.TType; break;
			case 'ㅀ': addedPhoneme = "l"; remainingConsonant='ㅎ'; newRemainingConsonantType=RemainingConsonantType.HType; break;
			case 'ㅄ': addedPhoneme = "p|"; remainingConsonant='ㅅ'; newRemainingConsonantType=RemainingConsonantType.TType; break;
			default: throw new IllegalArgumentException("Incorrect double consonant "+remainingEnding);
			}
			PhonemeConsonantPair subResult = computeConsonantMixing(remainingConsonant, newRemainingConsonantType, startConsonant);
			subResult.phoneme = addedPhoneme+" "+subResult.phoneme;
			return subResult;
		}

		
		PhonemeConsonantPair result = new PhonemeConsonantPair();
		switch(startConsonant)
		{
		case 'ㄱ':
			switch(remainingConsonantType)
			{
			case KType: result.phoneme="k|"; result.consonant='ㄲ'; return result;
			case TType: result.phoneme="t|"; result.consonant='ㄲ'; return result;
			case PType: result.phoneme="p|"; result.consonant='ㄲ'; return result;
			case HType: result.phoneme=""; result.consonant='ㅋ'; return result;
			default: break;//default rule
			}
			break;
		case 'ㄴ':
			switch(remainingConsonantType)
			{
			case NGType:
			case KType: result.phoneme="N"; result.consonant='ㄴ'; return result;
			case TType: result.phoneme="n"; result.consonant='ㄴ'; return result; //TODO: check
			case NType:
			case LType:
				if(startConsonant=='ㄴ' && remainingConsonantType==RemainingConsonantType.NType)
				{
					result.phoneme="n"; result.consonant='ㄴ'; return result;
				}else{
					result.phoneme="l"; result.consonant='ㄹ'; return result;
				}
			case MType: 
			case PType: result.phoneme="m"; result.consonant='ㄴ'; return result;
			
			default: break;//default rule
			}
			break;
		case 'ㅁ':
			switch(remainingConsonantType)
			{
			case NGType:
			case KType: result.phoneme="N"; result.consonant='ㅁ'; return result;
			case TType: 
			case NType: result.phoneme="n"; result.consonant='ㅁ'; return result;
			case LType: result.phoneme="l"; result.consonant='ㅁ'; return result;
			case MType: 
			case PType: result.phoneme="m"; result.consonant='ㅁ'; return result;
			
			default: break;//default rule
			}
			break;

		case 'ㄷ':
			switch(remainingConsonantType)
			{
			case KType: result.phoneme="k|"; result.consonant='ㄸ'; return result;
			case TType: result.phoneme="t|"; result.consonant='ㄸ'; return result; //TODO: check
			case PType: result.phoneme="p|"; result.consonant='ㄸ'; return result;
			case HType: result.phoneme=""; result.consonant='ㅋ'; return result;
			default: break;//default rule
			}
			break;
		case 'ㄹ':
			break;//should not happen in woorimal
		case 'ㅂ':
			switch(remainingConsonantType)
			{
			case KType: result.phoneme="k|"; result.consonant='ㅃ'; return result;
			case TType: result.phoneme="t|"; result.consonant='ㅃ'; return result;
			case PType: result.phoneme="p|"; result.consonant='ㅃ'; return result;
			case HType: result.phoneme=""; result.consonant='ㅍ'; return result;
			default: break;//default rule
			}
			break;
		case 'ㅅ':
			switch(remainingConsonantType)
			{
			case KType: result.phoneme="k|"; result.consonant='ㅆ'; return result;
			case TType: result.phoneme="t|"; result.consonant='ㅆ'; return result;
			case PType: result.phoneme="p|"; result.consonant='ㅆ'; return result;
			case HType: result.phoneme=""; result.consonant='ㅅ'; return result;
			default: break;//default rule
			}
			break;
		case 'ㅈ':
			switch(remainingConsonantType)
			{
			case KType: result.phoneme="k|"; result.consonant='ㅉ'; return result;
			case TType: result.phoneme="t|"; result.consonant='ㅉ'; return result;
			case PType: result.phoneme="p|"; result.consonant='ㅉ'; return result;
			case HType: result.phoneme=""; result.consonant='ㅊ'; return result;
			default: break;//default rule
			}
			break;
		
		case 'ㅆ':
		case 'ㅃ':
		case 'ㅉ':
		case 'ㄸ':
		case 'ㅊ':
		case 'ㅋ':
		case 'ㅌ':
		case 'ㅍ':
		case 'ㄲ':
			//default rule
			break;
		case 'ㅎ':
			switch(remainingConsonantType)
			{
			case KType: result.phoneme=""; result.consonant='ㅋ'; return result;
			case TType: result.phoneme=""; result.consonant='ㅌ'; return result;
			case PType: result.phoneme=""; result.consonant='ㅍ'; return result;
			case HType: result.phoneme=""; result.consonant='ㅎ'; return result;
			case NGType: result.phoneme="N"; result.consonant='ㅎ'; return result;
			case MType: result.phoneme="m"; result.consonant='ㅎ'; return result;
			case NType: result.phoneme="n"; result.consonant='ㅎ'; return result;
			case LType: result.phoneme="l"; result.consonant='ㅎ'; return result;
			default: 
				//default h rule, h disappears
				//some rules above can be removed if h is to disappear
				result.phoneme=""; result.consonant=remainingEnding; return result;
			}
		case 'ㅇ':
		default: //stray characters, foreign characters
			throw new IllegalArgumentException("Incorrect start consonant for mixing: "+startConsonant);
		}

		//default if there is little interaction
		PhonemeConsonantPair defaultResult = new PhonemeConsonantPair();
		switch(remainingConsonantType)
		{
		case Absent:
		default:
		case DoubleConsonantType:
			throw new IllegalArgumentException("Should not be called with no consonant mixing");
		case HType: defaultResult.phoneme="h"; break;
		case KType: defaultResult.phoneme="k|"; break;
		case LType: defaultResult.phoneme="l"; break;
		case MType: defaultResult.phoneme="m"; break;
		case NGType: defaultResult.phoneme="N"; break;
		case NType: defaultResult.phoneme="n"; break;
		case PType: defaultResult.phoneme="p|"; break;
		case TType: defaultResult.phoneme="t|"; break;
		}
		defaultResult.consonant = startConsonant;
		return defaultResult;
	}
	private static String getVowelPhoneme(char vowel)
	{
		switch(vowel)
		{
		case 'ㅏ': return "a";
		case 'ㅐ': return "E";
		case 'ㅑ': return "y a";
		case 'ㅒ': return "y E";
		case 'ㅓ': return "V";
		case 'ㅔ': return "e";
		case 'ㅕ': return "y V";
		case 'ㅖ': return "y e";
		case 'ㅗ': return "o";
		case 'ㅘ': return "w a";
		case 'ㅙ': return "w E";
		case 'ㅚ': return "2";
		case 'ㅛ': return "y o";
		case 'ㅜ': return "u";
		case 'ㅝ': return "w V";
		case 'ㅞ': return "w e";
		case 'ㅟ': return "H i";
		case 'ㅠ': return "y u";
		case 'ㅡ': return "M";
		case 'ㅢ': return "M\\ i";
		case 'ㅣ': return "i";
		default: throw new IllegalArgumentException("Unknown Korean vowel: "+vowel);
		}
	}

	private static String phonemeForConsonantVowelPair(char consonant,
			char middleVowel, boolean wordStart, boolean wordMiddle) {


		//TODO: has many more cases than this but would weaken the feature space of analyzable phonemes
		//for example, add consonant for tchi, this might get detected by the HMM phoneme waveform tree
		String vowelPhoneme = " "+getVowelPhoneme(middleVowel);
		boolean isIVowel = vowelPhoneme.trim().equals("i");
		boolean isYVowel = vowelPhoneme.trim().startsWith("y");

		switch(consonant)
		{
		case 'ㄱ': 
			if(wordStart){
				return "k"+vowelPhoneme;
			}else{
				return "g"+vowelPhoneme;
			}
		case 'ㄲ': return "k!"+vowelPhoneme;
		case 'ㄴ': return "n"+vowelPhoneme;
		case 'ㄷ': return (wordStart?"t":"d")+vowelPhoneme; //changes pronunciation before i, probably detectable by feature
		case 'ㄸ': return "t^"+vowelPhoneme;
		case 'ㄹ': return "4"+vowelPhoneme; //is not pronounced the beginning of a word, but is not written either, if it exists it is usually a loanword & pronounced
		case 'ㅁ': return "m"+vowelPhoneme;
		case 'ㅂ': return (wordStart?"p":"b")+vowelPhoneme;
		case 'ㅃ': return "p!"+vowelPhoneme;
		case 'ㅅ': 
			if(isIVowel || isYVowel)
			{
				return "s\\"+vowelPhoneme;
			}else{
				return "s"+vowelPhoneme;
			}
		case 'ㅆ':
			if(isIVowel || isYVowel)
			{
				return "s\\!"+vowelPhoneme;
			}else{
				return "s!"+vowelPhoneme;
			}
		case 'ㅈ':
			return "j"+vowelPhoneme;
		case 'ㅉ':
			return "j!"+vowelPhoneme;
		case 'ㅊ':
			return "jh"+vowelPhoneme;
		case 'ㅋ':
			return "kh"+vowelPhoneme;
		case 'ㅌ':
			return (isIVowel || isYVowel)?"jh":"th"+vowelPhoneme;
		case 'ㅍ':
			return "ph"+vowelPhoneme;
		case 'ㅎ':
			return "h"+vowelPhoneme;
		case ' ':
			return vowelPhoneme.substring(1); //remove the leading space we added
		case 'ㅇ':
			return "N"+vowelPhoneme; //this is the previous ng
		default: 
			throw new IllegalArgumentException("Unknown pure consonant (double removed): "+consonant);
		}
	}
	private static String phonemeForDoubleConsonantVowelPair(
			char remainingEnding, char middleVowel, boolean wordStart,
			boolean wordMiddle) {
		switch(remainingEnding)
		{
		case 'ㄳ': return "k "+phonemeForConsonantVowelPair('ㅅ', middleVowel, wordStart, wordMiddle);
		case 'ㄵ': return "n "+phonemeForConsonantVowelPair('ㅈ', middleVowel, wordStart, wordMiddle);
		case 'ㄶ': return phonemeForConsonantVowelPair('ㄴ', middleVowel, wordStart, wordMiddle);
		case 'ㄺ': return "l "+phonemeForConsonantVowelPair('ㄱ', middleVowel, wordStart, wordMiddle);
		case 'ㄻ': return "l "+phonemeForConsonantVowelPair('ㅁ', middleVowel, wordStart, wordMiddle);
		case 'ㄼ': return "l "+phonemeForConsonantVowelPair('ㅂ', middleVowel, wordStart, wordMiddle);
		case 'ㄽ': return "l "+phonemeForConsonantVowelPair('ㅅ', middleVowel, wordStart, wordMiddle);
		case 'ㄾ': return "l "+phonemeForConsonantVowelPair('ㅌ', middleVowel, wordStart, wordMiddle);
		case 'ㄿ': return "l "+phonemeForConsonantVowelPair('ㅍ', middleVowel, wordStart, wordMiddle);
		case 'ㅀ': return phonemeForConsonantVowelPair('ㄹ', middleVowel, wordStart, wordMiddle);
		case 'ㅄ': return "p "+phonemeForConsonantVowelPair('ㅅ', middleVowel, wordStart, wordMiddle);
		default: throw new IllegalArgumentException("Should not be called without double final consonant"); 
		}
	}


	private enum RemainingConsonantType
	{
		Absent,
		HType,
		KType,
		NGType,
		LType,
		MType,
		NType,
		PType,
		TType,
		DoubleConsonantType;
	}

	private static class PhonemeConsonantPair
	{
		String phoneme;
		char consonant;
	}

}
