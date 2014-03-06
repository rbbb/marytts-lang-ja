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

import java.util.ArrayList;

import marytts.language.ja.phonemes.XSampa.Consonant;
import marytts.language.ja.phonemes.XSampa.Vowel;



public class JapanesePhonemes {
	public static ArrayList<XSampa.Consonant> consonants = new ArrayList<XSampa.Consonant>();
	public static ArrayList<XSampa.Vowel> vowels = new ArrayList<XSampa.Vowel>();
	
	public static Consonant K;
	static{
		K = new Consonant(XSampa.findConsonantByIPA("k"));
		K.setExample("かくれこころのかけら [k]");
		consonants.add(K);
	}
	public static Consonant KK;
	static{
		KK = new Consonant(K);
		KK.setIpaCode("kk");
		KK.setXSampaCode("kk");
		KK.setExample("っか [kk]");
		consonants.add(KK);
	}
	public static Consonant G;
	static{
		G = new Consonant(XSampa.findConsonantByIPA("g"));
		G.setExample("がぎぐ～げご [g]");
		consonants.add(G);
	}
	public static Consonant GG;
	static{
		GG = new Consonant(G);
		GG.setIpaCode("gg");
		GG.setXSampaCode("gg");
		GG.setExample("っぐ [gg]");
		consonants.add(GG);
	}
	public static Consonant S;
	static{
		S = new Consonant(XSampa.findConsonantByIPA("s"));
		S.setExample("ささきせんせい [s]");
		consonants.add(S);
	}
	public static Consonant SS;
	static{
		SS = new Consonant(S);
		SS.setExample("っせ [s]");
		SS.setIpaCode("ss");
		SS.setXSampaCode("ss");
		consonants.add(SS);
	}
	public static Consonant Z;
	static{
		Z = new Consonant(XSampa.findConsonantByIPA("z"));
		Z.setExample("ぞうのぞうはzooでじ [z]");
		consonants.add(Z);
	}
	public static Consonant ZZ;
	static{
		ZZ = new Consonant(Z);
		ZZ.setExample("っぜ [zz]");
		ZZ.setIpaCode("zz");
		ZZ.setXSampaCode("zz");
		consonants.add(ZZ);
	}
	public static Consonant J;
	static{
		//pronounced dz or z depending on context/speaker
		J = new XSampa.Consonant(true, XSampa.ConsonantPlace.AlveoloPalatal, XSampa.ConsonantType.Plosive, "z\\", "d͡ʑ/ʑ", "j", "じじ [ʑ or d͡ʑ]");
		consonants.add(J);
	}
	public static Consonant JJ;
	static{
		//pronounced dz or z depending on context/speaker
		JJ = new XSampa.Consonant(J);
		JJ.setExample("っじ　[ddʑ]");
		JJ.setIpaCode("ddʑ");
		JJ.setXSampaCode("_dz\\");
		consonants.add(JJ);
	}
	public static Consonant SH;
	static{
		SH = new Consonant(XSampa.findConsonantByIPA("ɕ"));
		SH.setExample("しゃしゅしゃしん [ɕ]");
		consonants.add(SH);
	}
	public static Consonant SSH;
	static{
		//pronounced dz or z depending on context/speaker
		SSH = new XSampa.Consonant(SH);
		SSH.setExample("っしょ　[_ɕ]");
		SSH.setIpaCode("_ɕ"); //??
		SSH.setXSampaCode("_s\\");
		consonants.add(SSH);
	}
	
	public static Consonant T;
	static {
		T = new Consonant(XSampa.findConsonantByIPA("t"));
		T.setExample("たたないというよりたてないて [t]");
		consonants.add(T);
	}
	public static Consonant TT;
	static{
		//pronounced dz or z depending on context/speaker
		TT = new XSampa.Consonant(T);
		TT.setExample("った　[tt]");
		TT.setIpaCode("tt");
		TT.setXSampaCode("tt");
		consonants.add(TT);
	}
	
	public static Consonant D;
	static {
		D = new Consonant(XSampa.findConsonantByIPA("d"));
		D.setExample("むだだなのでだめだ [d]");
		consonants.add(D);
	}
	public static Consonant DD;
	static{
		//pronounced dz or z depending on context/speaker
		DD = new XSampa.Consonant(D);
		DD.setExample("っだ　[dd]");
		DD.setIpaCode("dd");
		DD.setXSampaCode("dd");
		consonants.add(DD);
	}
	
	public static Consonant TCH;
	static
	{
		//could be noted as T + SH ? better not because that would require 'previous phoneme' feature, and 'previous-previous phoneme' feature for っち
		TCH = new Consonant(new XSampa.Consonant(false, XSampa.ConsonantPlace.AlveoloPalatal, XSampa.ConsonantType.Plosive, "ts\\", "tɕ", "aspired j (ch)", "ちちゃんちょう～ちゃない [tɕ]"));
		consonants.add(TCH);
	}
	public static Consonant TTCH;
	static{
		//pronounced dz or z depending on context/speaker
		TTCH = new XSampa.Consonant(TCH);
		TTCH.setExample("っちゃ　[_tɕ]");
		TTCH.setIpaCode("_tɕ");
		TTCH.setXSampaCode("tts\\");
		consonants.add(TTCH);
	}
	public static Consonant TS;
	static 
	{
		//could be noted as T + S ?　same problem 
		TS = new Consonant(new XSampa.Consonant(false, XSampa.ConsonantPlace.AlveoloPalatal, XSampa.ConsonantType.Plosive, "ts", "tɕ", "aspired j (ch)", "つつみいつつ [ts]"));
		consonants.add(TS);
	}
	public static Consonant TTS;
	static{
		TTS = new XSampa.Consonant(TS);
		TTS.setExample("っつ　[_ts]");
		TTS.setIpaCode("_ts");
		TTS.setXSampaCode("tts");
		consonants.add(TTS);
	}
	public static Consonant N;
	static
	{
		N = new Consonant(XSampa.findConsonantByIPA("n"));
		N.setExample("ばななアイスのむなのにねねない [n]");
		consonants.add(N);
	}
	public static Consonant H;
	static
	{
		H = new Consonant(XSampa.findConsonantByIPA("h"));
		H.setExample("ほほキスほうほう、ほちきすとおなじあほ [h]");
		consonants.add(H);
	}
	public static Consonant P;
	static
	{
		P = new Consonant(XSampa.findConsonantByIPA("p"));
		P.setExample("ペラペラくんピンポンぽい [p]");
		consonants.add(P);
	}
	public static Consonant PP;
	static{
		PP = new XSampa.Consonant(P);
		PP.setExample("っぱ　[_p]");
		PP.setIpaCode("_p");
		PP.setXSampaCode("pp");
		consonants.add(PP);
	}
	public static Consonant B;
	static
	{
		B = new Consonant(XSampa.findConsonantByIPA("b"));
		B.setExample("アリス、ばらぼろぼろばれちゃうよ [b]");
		consonants.add(B);
	}
	public static Consonant BB;
	static{
		//pronounced dz or z depending on context/speaker
		BB = new XSampa.Consonant(B);
		BB.setExample("っば　[_b]");
		BB.setIpaCode("_b");
		BB.setXSampaCode("bb");
		consonants.add(BB);
	}
	public static Consonant F;
	static
	{
		F = new Consonant(XSampa.findConsonantByIPA("ɸ"));
		F.setExample("ふぃふォふぃフォふふふふふ (爆）[ɸ]");
		consonants.add(F);
	}
	public static Consonant M;
	static
	{
		M = new Consonant(XSampa.findConsonantByIPA("m"));
		M.setExample("まもるままにマリモおまもりもてまりました [m]");
		consonants.add(M);
	}
	public static Consonant Yglide;
	static
	{
		Yglide = new Consonant(XSampa.findConsonantByIPA("j"));
		Yglide.setExample("にゃんにゃん、よいようやくぎゅっとやってきました [j]");
		consonants.add(Yglide);
	}
	public static Consonant RL;
	static
	{
		RL = new Consonant(XSampa.findConsonantByIPA("ɾ"));
		RL.setExample("リリース　（そうぞうてき） [ɾ]");
		consonants.add(RL);
	}
	public static Consonant W;
	static
	{
		W = new Consonant(XSampa.findConsonantByIPA("w"));
		W.setExample("わんわんはわににくわれた [w]");
		consonants.add(W);
	}
	public static Consonant Nalone;
	//nasalized
	static
	{
		Nalone = new Consonant(XSampa.findConsonantByIPA("ŋ"));
		Nalone.setExample("あんぱんまんじゃん　[ŋ]");
		consonants.add(Nalone);
	}
	
	public static Consonant V;
	static{
		V = new Consonant(XSampa.findConsonantByIPA("v"));
		V.setExample("ヴォドカヴルストヴォイス [v]");
		consonants.add(V);
	}

	
	

	public static Vowel A;
	static
	{
		A = new Vowel(XSampa.findVowelByIPA("a"));
		A.setExample("かなだらまぱさじゃちゃかたぱは [a]");
		vowels.add(A);
	}
	public static Vowel I;
	static
	{
		I = new Vowel(XSampa.findVowelByIPA("i"));
		I.setExample("いきいきこいい [i]");
		vowels.add(I);
	}
	public static Vowel U;
	static
	{
		U = new Vowel(XSampa.findVowelByIPA("u"));
		U.setExample("ゆるゆるつくるくくるできる[u]");
		vowels.add(U);
	}
	public static Vowel E;
	static
	{
		E = new Vowel(XSampa.findVowelByIPA("e"));
		E.setExample("");
		vowels.add(E);
	}
	public static Vowel O;
	static
	{
		O = new Vowel(XSampa.findVowelByIPA("o"));
		O.setExample("");
		vowels.add(O);
	}
	
	public static void main(String[] args) {
		System.out.println(PhonemeToAllophoneXML.makeXml("ja", consonants, vowels));
	}

}
