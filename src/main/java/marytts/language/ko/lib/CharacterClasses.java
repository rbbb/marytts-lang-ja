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

public class CharacterClasses {
	public static boolean isPureKoreanChar(char c)
	{
		int x = (c & 0xFFFF);
		if( x >= 0xAC00 && x <= 0xD7A3 ) {
			return true;
		} else if(x >= 0x3131 && x <= 0x3163) {
			return true;
		} else {
			return false;
		}
	}
	private static final String PUNCTUATION = ".,-()?!{};:'\"[]…";
	public static boolean isPunctuation(char c)
	{
		return PUNCTUATION.contains(c+"");
	}
	
	public static boolean isHanja(char c)
	{
		int x = (c & 0xFFFF);
		if( x>= 0x4E00 && x<= 0x9FFF)
		{
			return true;
		}
		return false;
	}
	
	public static boolean isDigit(char c)
	{
		return "0123456789".contains(c+"");
	}

	public static boolean isLatin(char c) {
		return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(c+"");
	}
}
