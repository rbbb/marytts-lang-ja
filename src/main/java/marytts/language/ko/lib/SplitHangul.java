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

import java.util.Random;

public class SplitHangul {
	public char jong;
	public char jung;
	public char cho;
	
	private SplitHangul()
	{
		//no public constructor
	}
	
	public static SplitHangul split(char composedHangul)
	{
		int charCodePoint = composedHangul & 0xFFFF;
		if(!isComposedHangul(charCodePoint))
		{
			throw new IllegalArgumentException("Not Hangul: "+composedHangul);
		}
		charCodePoint -= 0xAC00;
		
		int choIndex = (charCodePoint)/(21*28);
		int jungJong = charCodePoint%(21*28);
		int jungIndex = jungJong/28;
		int jongIndex = jungJong%28;
		
		SplitHangul result = new SplitHangul();
		result.cho = Cho.values()[choIndex].compatibilityChar;
		result.jung = Jung.values()[jungIndex].compatibilityChar;
		result.jong = Jong.values()[jongIndex].compatibilityChar;
		
		return result;
		
	}
	
	@Override
	public String toString() {
		return "("+cho+"+"+jung+(jong!=' ' ? "+"+jong : "")+")";
	}
	
	public static boolean isComposedHangul(int charCodePoint)
	{
		if( charCodePoint >= 0xAC00 && charCodePoint <= 0xD7A3 ) {
			return true;
		}else if( charCodePoint >= 0xD7A4 && charCodePoint <= 0xD7AF ) {
			return false; //in Hangul code table but not allocated
		} else {
			return false;
		}
	}
	
	
	public enum Cho
	{
		//sorted in Unicode main Hangul table order
		KIYEOK('ᄀ','ㄱ'),
		SSANGKIYEOK('ᄁ','ㄲ'),
		NIEUN('ᄂ','ㄴ'),
		TIKEUT('ᄃ','ㄷ'),
		SSANGTIKEUT('ᄄ','ㄸ'),
		RIEUL('ᄅ','ㄹ'),
		MIEUM('ᄆ','ㅁ'),
		PIEUP('ᄇ','ㅂ'),
		SSANGPIEUP('ᄈ','ㅃ'),
		SIOS('ᄉ','ㅅ'),
		SSANGSIOS('ᄊ','ㅆ'),
		IEUNG('ᄋ','ㅇ'),
		CIEUC('ᄌ','ㅈ'),
		SSANGCIEUC('ᄍ','ㅉ'),
		CHIEUCH('ᄎ','ㅊ'),
		KHIEUKH('ᄏ','ㅋ'),
		THIEUTH('ᄐ','ㅌ'),
		PHIEUPH('ᄑ','ㅍ'),
		HIEUH('ᄒ','ㅎ');
		char positionedChar; //JAMO
		char compatibilityChar; //compatibility JAMO
		Cho(char positionedChar, char compatibilityChar)
		{
			this.positionedChar = positionedChar;
			this.compatibilityChar = compatibilityChar;
		}
		public char getPositionedChar() {
			return positionedChar;
		}
		public char getCompatibilityChar() {
			return compatibilityChar;
		}
	}
	public enum Jung
	{
	A('\u1161','\u314F'),
	AE('\u1162','\u3150'),
	YA('\u1163','\u3151'),
	YAE('\u1164','\u3152'),
	EO('\u1165','\u3153'),
	E('\u1166','\u3154'),
	YEO('\u1167','\u3155'),
	YE('\u1168','\u3156'),
	O('\u1169','\u3157'),
	WA('\u116A','\u3158'),
	WAE('\u116B','\u3159'),
	OE('\u116C','\u315A'),
	YO('\u116D','\u315B'),
	U('\u116E','\u315C'),
	WEO('\u116F','\u315D'),
	WE('\u1170','\u315E'),
	WI('\u1171','\u315F'),
	YU('\u1172','\u3160'),
	EU('\u1173','\u3161'),
	YI('\u1174','\u3162'),
	I('\u1175','\u3163');
		
		char positionedChar; //JAMO
		char compatibilityChar; //compatibility JAMO
		Jung(char positionedChar, char compatibilityChar)
		{
			this.positionedChar = positionedChar;
			this.compatibilityChar = compatibilityChar;
		}
		public char getPositionedChar() {
			return positionedChar;
		}
		public char getCompatibilityChar() {
			return compatibilityChar;
		}
	}

	public enum Jong
	{	
		Empty(' ',' '), //no Jong
		KIEYOK('\u11A8','\u3131'),
		SSANGKIYEOK('\u11A9','\u3132'),
		KIYEOKSIOS('\u11AA','\u3133'),
		NIEUN('\u11AB','\u3134'),
		NIEUNCIEUC('\u11AC','\u3135'),
		NIEUNHIEUH('\u11AD','\u3136'),
		TIKEUT('\u11AE','\u3137'),
		//no DD
		RIEUL('\u11AF','\u3139'),
		RIEULKIYEOK('\u11B0','\u313A'),
		RIEULMIEUM('\u11B1','\u313B'),
		RIEULPIEUP('\u11B2','\u313C'),
		RIEULSIOS('\u11B3','\u313D'),
		RIEULTIKEUT('\u11B4','\u313E'),
		RIEULPIYEUP('\u11B5','\u313F'),
		RIEULHIEUH('\u11B6','\u3140'),
		MIEUM('\u11B7','\u3141'),
		PIEUP('\u11B8','\u3142'),
		//no BB
		PIEUPSIOS('\u11B9','\u3144'),
		SIOS('\u11BA','\u3145'),
		SSANGSIOS('\u11BB','\u3146'),
		IEUNG('\u11BC','\u3147'),
		CIEUC('\u11BD','\u3148'),
		//no JJ
		CHIEUCH('\u11BE','\u314A'),
		KHIEUKH('\u11BF','\u314B'),
		THIEUTH('\u11C0','\u314C'),
		PHIEUPH('\u11C1','\u314D'),
		HIEUH('\u11C2','\u314E');
		
		char positionedChar; //JAMO
		char compatibilityChar; //compatibility JAMO
		Jong(char positionedChar, char compatibilityChar)
		{
			this.positionedChar = positionedChar;
			this.compatibilityChar = compatibilityChar;
		}
		public char getPositionedChar() {
			return positionedChar;
		}
		public char getCompatibilityChar() {
			return compatibilityChar;
		}
	}

	
	public static void main(String[] args)
	{
		System.out.println("Cho (upper left consonant)");
		for(Cho hChar:Cho.values())
		{
			System.out.println("CHOSEONG "+hChar.name()+":  "+hChar.compatibilityChar+"     "+hChar.positionedChar);
			System.out.println(hChar.name()+" codes: U0x"+Integer.toHexString(hChar.compatibilityChar)+" U0x"+Integer.toHexString(hChar.positionedChar));
		}
		System.out.println("");
		
		System.out.println("Jung (middle vowel)");
		for(Jung jung:Jung.values())
		{
			System.out.println("JUNGSEONG "+jung.name()+":  "+jung.compatibilityChar+"     "+jung.positionedChar);
			System.out.println(jung.name()+" codes: U0x"+Integer.toHexString(jung.compatibilityChar)+" U0x"+Integer.toHexString(jung.positionedChar));
		}
		System.out.println("");
		
		System.out.println("Jong (lower consonant)");
		for(Jong hChar:Jong.values())
		{
			System.out.println("JONGSEONG "+hChar.name()+":  "+hChar.compatibilityChar+"     "+hChar.positionedChar);
			System.out.println(hChar.name()+" codes: U0x"+Integer.toHexString(hChar.compatibilityChar)+" U0x"+Integer.toHexString(hChar.positionedChar));
		}
		System.out.println("");
		
		Random r = new Random();
		for(int i=0;i<30;i++)
		{
			int newCharCodepoint = 0xAC00 + r.nextInt(0xD7A3-0xAC00+1);
			char c = (char)newCharCodepoint;
			
			SplitHangul split = split(c);
			System.out.println(c+" = "+split.toString());
		}
	}
}
