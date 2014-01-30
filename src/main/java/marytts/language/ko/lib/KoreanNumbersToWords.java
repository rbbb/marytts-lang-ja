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
import java.util.ArrayList;
import java.util.List;


public class KoreanNumbersToWords {
	public enum CountingType
	{
		NativeKorean,
		SinoKorean
	}
	public enum CounterWord
	{
		//time, sino-korean except for hours
		Year("년", CountingType.SinoKorean, false),
		Month("월", CountingType.SinoKorean, false),
		Week("주", CountingType.SinoKorean, false),
		Week2("주일", CountingType.SinoKorean, false),
		Day("일", CountingType.SinoKorean, false),
		Hour("시", CountingType.NativeKorean, true),
		Minute("분", CountingType.SinoKorean, false),
		Second("초", CountingType.SinoKorean, false),
		
		//native
		NumberOfPeople("명", CountingType.NativeKorean, true),
		NumberOfHumanCorpse("구", CountingType.NativeKorean, true),
		NumberOfAnimals("마리", CountingType.NativeKorean, true),
		NumberOfThinThings("장", CountingType.NativeKorean, true), //sheets of paper, CDs...
		NumberOfBigMachines("대", CountingType.NativeKorean, true), //vehicles, fridge, washing machine, industrial apparatus ...
		NumberOfClothes("벌", CountingType.NativeKorean, true),
		NumberOfGlasses("잔", CountingType.NativeKorean, true),
		NumberOfBottles("병", CountingType.NativeKorean, true),
		NumberOfTree("그루", CountingType.NativeKorean, true),
		Age("살", CountingType.NativeKorean, true),
		NumberOfBooks("권", CountingType.NativeKorean, true),
		NumberOfTimes("번", CountingType.NativeKorean, true),
		NumberOfShoes("켤레", CountingType.NativeKorean, true),
		NumberOfSackOrPencil("자루", CountingType.NativeKorean, true),
		NumberOfBoxes("상자", CountingType.NativeKorean, true),
		NumberOfBigFlatThing("판", CountingType.NativeKorean, true),
		NumberOfPlates("접시", CountingType.NativeKorean, true),
		NumberOfPairs("쌍", CountingType.NativeKorean, true),
		NumberOfPieces("조각", CountingType.NativeKorean, true),
		
		NumberOfThings("상자", CountingType.NativeKorean, true),
		
		NumberOfThings2("개", CountingType.NativeKorean, true),
		
		//one is irregular
		//첫째, 첫번째
		Ordinal("째", CountingType.NativeKorean, false),
		Ordinal2("번째", CountingType.NativeKorean, true),
		
		//sino-korean
		NumberOfClasses("교시", CountingType.SinoKorean, false),
		QuantityOfKoreanMoney("원", CountingType.SinoKorean, false),
		QuantityOfDollar("달러", CountingType.SinoKorean, false),
		HouseNumber("번지", CountingType.SinoKorean, false),
		NumberOfPeople2("인", CountingType.SinoKorean, false),
		NumberOfServings("인분", CountingType.SinoKorean, false),
		Floor("층", CountingType.SinoKorean, false),
		Number("호", CountingType.SinoKorean, false), //house number, line number
		Episode("회", CountingType.SinoKorean, false),
		Kilo("킬로", CountingType.SinoKorean, false), //meter, gram, meter per hour ... since it's > 100 it uses Sino-Korean numbers
		Kilo2("키로", CountingType.SinoKorean, false),
		Meters("미터", CountingType.SinoKorean, false),
		Meters2("m", "미터", CountingType.SinoKorean, false),
		Miles("마일", CountingType.SinoKorean, false),
		Knots("노트", CountingType.SinoKorean, false),
		Degrees("도", CountingType.SinoKorean, false),
		NumberOfBytes("바이트", CountingType.SinoKorean, false),
		HorsePower("마력", CountingType.SinoKorean, false);
		
		
		private String hangul;
		private String replacementHangul;
		private CountingType countingType;
		private boolean nativeIsAttributive;

		CounterWord(String hangul, CountingType countingType, boolean nativeIsAttributive)
		{
			this.hangul = hangul;
			this.replacementHangul = hangul;
			this.countingType = countingType;
			this.nativeIsAttributive = nativeIsAttributive;
		}
		

		CounterWord(String hangul, String replacementHangul, CountingType countingType, boolean nativeIsAttributive)
		{
			this.hangul = hangul;
			this.countingType = countingType;
			this.nativeIsAttributive = nativeIsAttributive;
			this.replacementHangul = replacementHangul;
		}


		public String getHangul() {
			return hangul;
		}

		public CountingType getCountingType() {
			return countingType;
		}

		public boolean nativeIsAttributive() {
			return nativeIsAttributive;
		}
		
		public String getReplacementHangul() {
			return replacementHangul;
		}
		
	}
	//returns tokens to spell out a word in a given counting word context
	//numbers and punctuation is a string containing the longest possible number
	//of consecutive numbers and punctuation found in the text
	//counter is the counting word or null
	
	public static List<String> toWords(String numbersAndPunctuation, String counter)
	{
		for(CounterWord counterWord:CounterWord.values())
		{
			if(counterWord.getHangul().equals(counter))
			{
				//exception: first time
				if(numbersAndPunctuation.trim().equals("1") && (counter.equals("번째") || counter.equals("째")))
				{
					List<String> result = new ArrayList<String>();
					result.add("첫");
					result.add(counter);
					return result;
				}
				
				List<String> result;
				
				switch(counterWord.countingType)
				{
				case NativeKorean: 
					long number = removePunctuationAndCastToLong(numbersAndPunctuation);
					if(number<1 || number >99)
					{
						result = toSinoKoreanNumber(number);
					}else{
						result = toNativeKoreanNumber((int)number, counterWord.nativeIsAttributive());	
					}
					break;
				case SinoKorean: 
					result = toSinoKoreanNumber(removePunctuationAndCastToLong(numbersAndPunctuation));
					break;
				default:throw new IllegalArgumentException("Impossible case, only two ways to count in Korean");
				}
				result.add(counterWord.replacementHangul); //we replace the latin alphabet counter with its hangul representation
				return result;
			}
		}
		
		return toSinoKoreanNumber(removePunctuationAndCastToLong(numbersAndPunctuation));
	}
	private static long removePunctuationAndCastToLong(String s)
	{
		long multiplier = s.startsWith("-")?-1:1;
		long number = 0;
		for(int i=0;i<s.length();i++)
		{
			switch(s.charAt(i))
			{
			case '0': number = number*10+0; break;
			case '1': number = number*10+1; break;
			case '2': number = number*10+2; break;
			case '3': number = number*10+3; break;
			case '4': number = number*10+4; break;
			case '5': number = number*10+5; break;
			case '6': number = number*10+6; break;
			case '7': number = number*10+7; break;
			case '8': number = number*10+8; break;
			case '9': number = number*10+9; break;
			}
		}
		return number * multiplier;
	}
	
	public static List<String> toNativeKoreanNumber(String numberString, boolean attributive)
	{
		long number = removePunctuationAndCastToLong(numberString);
		if(number<1 || number>99)
		{
			throw new IllegalArgumentException("Native Korean numbers usually only represent numbers 0-99");
		}
		return toNativeKoreanNumber((int)number, attributive);
	}
	public static List<String> toNativeKoreanNumber(int number, boolean attributive)
	{
		if(number<1 || number>99)
		{
			throw new IllegalArgumentException("Native Korean numbers usually only represent numbers 0-99");
		}
		
		List<String> result = new ArrayList<String>();
		
		if(number==0)
		{
			result.add("영");//sino-korean, native Korean does not have 0
			return result;
		}

		int units = number%10;
		int tens = (number-units)/10;
		switch(tens)
		{
		case 0: break;
		case 1: result.add("열"); break;
		case 2:
			if(units==0)
			{
				result.add("스무");
			}else{
				result.add("스물");
			}
			break;
		case 3: result.add("서른"); break;
		case 4: result.add("마흔"); break;
		case 5: result.add("쉰"); break;
		case 6: result.add("예순"); break;
		case 7: result.add("일흔"); break;
		case 8: result.add("여든"); break;
		case 9: result.add("아흔"); break;
		default: throw new IllegalArgumentException("Entry condition into the function not respected, "+number);
		}
		switch(units)
		{
		case 0: break;
		case 1: 
			if(tens==0 && attributive)
			{
				result.add("한");
			}else{
				result.add("하나");
			}
			break;
		case 2:
			if(tens==0 && attributive)
			{
				result.add("두");
			}else{
				result.add("둘");
			}
			break;
		case 3: 
			if(tens==0 && attributive)
			{
				result.add("세");
			}else{
				result.add("셋");
			}
			break;
		case 4:
			if(tens==0 && attributive)
			{
				result.add("네");
			}else{
				result.add("넷");
			}
			break;
		case 5: result.add("다섯"); break;
		case 6: result.add("여섯"); break;
		case 7: result.add("일곱"); break;
		case 8: result.add("여덟"); break;
		case 9: result.add("아홉"); break;
		default: throw new IllegalArgumentException("Entry condition into the function not respected, "+number);
		}
		return result;
	}

	public static List<String> toSinoKoreanNumber(String numberString)
	{
		return toSinoKoreanNumber(Long.parseLong(numberString));
	}
	public static List<String> toSinoKoreanNumber(long number)
	{
		String numberString = ""+number;
		
		List<String> result = new ArrayList<String>();
		if(number==0)
		{
			result.add("영");
			return result;
		}
		
		if(number<0L)
		{
			result.add("마이너스");// 'minus'
			numberString = numberString.substring(1);
		}
		
		int orderInMyriad = 0; //0 for units, 1 for tens, 2 for hundreds, 3 for thousands
		int orderOfMyriad = 0; //0 for unit, 1 for 10 000, 2 for 100 000 000...
		boolean currentMyriadHasDigit = false;
		for(int i=0;i<numberString.length();i++)
		{
			char currentDigitChar = numberString.charAt(i);
			orderInMyriad = (numberString.length()-i-1) % 4;
			orderOfMyriad = (numberString.length()-i-1-orderInMyriad) / 4;
			currentMyriadHasDigit = currentMyriadHasDigit || currentDigitChar!='0';
			switch(currentDigitChar)
			{
			case '0': break;
			case '1': result.add("일"); break;
			case '2': result.add("이"); break;
			case '3': result.add("삼"); break;
			case '4': result.add("사"); break;
			case '5': result.add("오"); break;
			case '6': result.add("육"); break;
			case '7': result.add("칠"); break;
			case '8': result.add("팔"); break;
			case '9': result.add("구"); break;
			default: throw new IllegalArgumentException("Entry condition into the function not respected, "+number);
			}
			if(currentDigitChar!='0')
			{
				switch(orderInMyriad)
				{
				case 0: break;
				case 1: result.add("십"); break;//10s
				case 2: result.add("백"); break;//100s
				case 3: result.add("천"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+number);
				}
			}
			if(orderInMyriad==0)
			{
				if(currentMyriadHasDigit)
				{
					switch(orderOfMyriad)
					{
					case 0: break;
					case 1: result.add("만"); break; //10^4 萬
					case 2: result.add("억"); break; //10^8 億
					case 3: result.add("조"); break; //10^12 兆
					case 4: result.add("경"); break; //10^16 京
					case 5: result.add("해"); break; //10^20 垓
					case 6: result.add("자"); break; //10^24 秭
					case 7: result.add("양"); break; //10^28 穰
					case 8: result.add("구"); break; //10^32 溝
					case 9: result.add("간"); break; //10^36 澗
					case 10: result.add("정"); break; //10^40 正 
					case 11: result.add("재"); break; //10^44 載
					case 12: result.add("극"); break; //10^48 極
					case 13: result.add("항하사"); break; //10^52 恒河沙
					case 14: result.add("아승기"); break; //10^56 阿僧祇
					case 15: result.add("나유타"); break; //10^60 那由他
					case 16: result.add("불가사의"); break; //10^64 不可思議
					case 17: result.add("무량대수"); break; //10^68 無量大數
					default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+number);
					}
				}
				currentMyriadHasDigit = false;
			}
			orderOfMyriad += orderInMyriad==3?1:0;
			orderInMyriad = (orderInMyriad+1) % 4;
		}

		return result;
	}
	public static List<String> toSpokenFloat(String numberString)
	{
		String integerPart = "";
		boolean inIntegerPart = true;
		List<String> result = null;
		boolean dotWasOutput = false;
		for(int i=0;i<numberString.length();i++)
		{
			if(inIntegerPart)
			{
				if(numberString.charAt(i)=='.' || numberString.charAt(i)==',')
				{
					inIntegerPart = false;
					if(integerPart.equals("-")) // -.329
					{
						result = new ArrayList<String>();
						result.add("마이너스");// 'minus'
						result.add("영");
					}else if(integerPart.length()==0) { // .314
						result = new ArrayList<String>();
						result.add("영"); 
					}else{
						result = toSinoKoreanNumber(integerPart);
					}
				}else{
					integerPart += numberString.charAt(i);
				}
			}else{
//				if(result==null)
//				{
//					result = new ArrayList<String>();
//					result.add("영");
//				}
				if(!dotWasOutput)
				{
					result.add("점");
					dotWasOutput = true;
				}
				switch(numberString.charAt(i))
				{
				case '0': result.add("영"); break;
				case '1': result.add("일"); break;
				case '2': result.add("이"); break;
				case '3': result.add("삼"); break;
				case '4': result.add("사"); break;
				case '5': result.add("오"); break;
				case '6': result.add("육"); break;
				case '7': result.add("칠"); break;
				case '8': result.add("팔"); break;
				case '9': result.add("구"); break;
				default: break;				
				}
			}
		}
		if(result==null)
		{
			result = new ArrayList<String>();
			result.add("영");
		}
		return result;
	}

	public static List<String> toSpelledOutNumber(long number)
	{
		return toSpelledOutNumber(number+"");
	}

	public static List<String> toSpelledOutNumber(String numberString)
	{
		List<String> result = new ArrayList<String>();

		boolean currentHanaTu = false;
		boolean nextHanaTu = false;
		for(int i=0;i<numberString.length();i++)
		{
			//1 and 2 sound alike, so if they
			//occur side by side 'il' 'i' is
			//replaced by 'hana' 'tu'
			if(numberString.startsWith("12", i) || numberString.startsWith("21", i) || numberString.startsWith("11", i) || numberString.startsWith("22", i))
			{
				currentHanaTu = true;
				nextHanaTu = true;
			}else if(nextHanaTu){
				currentHanaTu = true;
				nextHanaTu = false;
			}else{
				currentHanaTu = false;
				nextHanaTu = false;
			}
			switch(numberString.charAt(i))
			{
			case '0': result.add("공");break;
			case '1':
				if(currentHanaTu){
					result.add("하나");
				}else{
					result.add("일");
				}
				break;
			case '2':
				if(currentHanaTu){	
					result.add("두");
				}else{
					result.add("이");
				}
				break;
			case '3': result.add("삼"); break;
			case '4': result.add("사"); break;
			case '5': result.add("오"); break;
			case '6': result.add("육"); break;
			case '7': result.add("칠"); break;
			case '8': result.add("팔"); break;
			case '9': result.add("구"); break;
			default: throw new IllegalArgumentException("Long to string should not yield characters outside of range 0-9: '"+numberString+"'");
			}
		}
		return result;
	}

//	public static void main(String[] args)
//	{
//		System.out.println(toWords( "1", "번째"));
//		long[] nums = new long[]{442,55874,7476422367L,87435431557L,6453847577874876483L,0,001432,4300000543500006345L, 12211204919310312L};
//		for(long num:nums)
//		{
//			System.out.println("Number "+num);
//			printStringArray("Spelled out", toSpelledOutNumber(num));
//			if(num<100 && num >0)
//			{
//				printStringArray("Native korean attributive", toNativeKoreanNumber((int)num, true));
//				printStringArray("Native korean not attributive", toNativeKoreanNumber((int)num, false));
//			}
//			printStringArray("Sino korean", toSinoKoreanNumber(num));
//
//		}
//		String[] numFloats = new String[]{"1.2213200032","29.213", "9328.00032"};
//		for(String numFloat:numFloats)
//		{
//			System.out.println("Number "+numFloat);
//			printStringArray("Spelled out", toSpokenFloat(numFloat));
//		}
//	}
//	public static void printStringArray(String title, List<String> array)
//	{
//		String result = title+":";
//		for(String arrayMember:array)
//		{
//			result+=" "+arrayMember;
//		}
//		System.out.println(result);
//	}
}
