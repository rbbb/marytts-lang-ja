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


public class NonJapaneseAlphabetToJapaneseWord {
	public static String toJapanese(char c)
	{
		char lowercaseC = Character.toLowerCase(c);
		switch(lowercaseC)
		{
		case 'a':
			return "エー";
		case 'b':
			return "ビー";
		case 'c':
			return "シー";
		case 'd':
			return "ディー";
		case 'e':
			return "イー";
		case 'f':
			return "エフ";
		case 'g':
			return "ジー";
		case 'h':
			return "エッチ";
		case 'i':
			return "アイ";
		case 'j':
			return "ジェー";
		case 'k':
			return "ケー";
		case 'l':
			return "エル";
		case 'm':
			return "エム";
		case 'n':
			return "エヌ";
		case 'o':
			return "オー";
		case 'p':
			return "ピー";
		case 'q':
			return "キュー";
		case 'r':
			return "アール";
		case 's':
			return "エス";
		case 't':
			return "ティー";
		case 'u':
			return "ユー";
		case 'v':
			return "ヴィー";
		case 'w':
			return "ダブリュー";
		case 'x':
			return "エックス";
		case 'y':
			return "ワイ";
		case 'z':
			return "ゼット";
			
		case 'α':
			return "アルファ";
		case 'β':
			return "ベータ";
		case 'γ':
			return "ガンマ";
		case 'δ':
			return "デルタ";
		case 'ε':
			return "エプシロン";
		case 'ζ':
			return "ゼータ";
		case 'η':
			return "エータ";
		case 'θ':
			return "テータ";
		case 'ι':
			return "イオタ";
		case 'κ':
			return "カッパ";
		case 'λ':
			return "ラムダ";
		case 'μ':
			return "ミュー";
		case 'ν':
			return "ニュー";
		case 'ξ':
			return "クシー";
		case 'ο':
			return "オミクロン";
		case 'π':
			return "パイ";
		case 'ρ':
			return "ロー";
		case 'σ':
		case 'ς':
			return "シグマ";
		case 'τ':
			return "タウ";
		case 'υ':
			return "ウプシロン";
		case 'φ':
			return "ファイ";
		case 'χ':
			return "キー";
		case 'ψ':
			return "プシー";
		case 'ω':
			return "オメガ";
		default:
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println("\uFF41".toLowerCase()+"A".toLowerCase());
	}
}
