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

public class NonKoreanAlphabetToKoreanWord {
	public static String toKorean(char c)
	{
		char lowercaseC = Character.toLowerCase(c);
		switch(lowercaseC)
		{
		case 'a':
			return "에이";
		case 'b':
			return "비";
		case 'c':
			return "시";
		case 'd':
			return "디";
		case 'e':
			return "이";
		case 'f':
			return "에프";
		case 'g':
			return "지";
		case 'h':
			return "에이치";
		case 'i':
			return "아이";
		case 'j':
			return "제이";
		case 'k':
			return "케이";
		case 'l':
			return "엘";
		case 'm':
			return "엠";
		case 'n':
			return "엔";
		case 'o':
			return "오";
		case 'p':
			return "피";
		case 'q':
			return "큐";
		case 'r':
			return "아르";
		case 's':
			return "에스";
		case 't':
			return "티";
		case 'u':
			return "유";
		case 'v':
			return "브이";
		case 'w':
			return "더블유";
		case 'x':
			return "엑스";
		case 'y':
			return "와이";
		case 'z':
			return "제트";
		case 'α':
			return "알파";
		case 'β':
			return "베타";
		case 'γ':
			return "감마";
		case 'δ':
			return "델타";
		case 'ε':
			return "엡실론";
		case 'ζ':
			return "제타";
		case 'η':
			return "에타";
		case 'θ':
			return "세타";
		case 'ι':
			return "요타";
		case 'κ':
			return "카파";
		case 'λ':
			return "람다";
		case 'μ':
			return "뮤";
		case 'ν':
			return "뉴";
		case 'ξ':
			return "크시";
		case 'ο':
			return "오미크론";
		case 'π':
			return "파이";
		case 'ρ':
			return "로";
		case 'σ':
		case 'ς':
			return "시그마";
		case 'τ':
			return "타우";
		case 'υ':
			return "윕실론";
		case 'φ':
			return "피";
		case 'χ':
			return "카이";
		case 'ψ':
			return "프시";
		case 'ω':
			return "오메가";
		//태극기??
		case '☰':
			return "역경건";
		case '☱':
			return "역경태";
		case '☲':
			return "역경이";
		case '☳':
			return "역경진";
		case '☴':
			return "역경손";
		case '☵':
			return "역경감";
		case '☶':
			return "역경간";
		case '☷':
			return "역경곤";
		default:
			return null;
		}
	}
}
