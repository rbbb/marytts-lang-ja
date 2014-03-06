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

public class CharacterClasses {
	public static boolean isHiragana(char c)
	{
		if( c >= '\u3041' && c <= '\u3094') {
			return true;
		} else if(c == '\u3040' //unset in hiragana space
				|| (c >='\u3095' && c <= '\u309F') ) //ゕ ゖ ゗ ゘ ゙ ゚ ゛ ゜ ゝ ゞ ゟ 
		{
			return false;
		} else {
			return false;
		}
	}
	public static boolean isKatakana(char c)
	{
		return isFullWidthKatakana(c) || isHalfWidthKatakana(c);
	}
	public static boolean isFullWidthKatakana(char c)
	{
		if( (c >= '\u30A1' && c <= '\u30F4') || c=='\u30FC')

		{
			return true;
		} else if(c == '\u30A0' //unset in katakana space
				|| (c >= '\u30F5' && c <= '\u30FF') ) //ヵ ヶ ヷ ヸ ヹ ヺ ・ (ー true) ヽ ヾ ヿ  
		{
			return false;
		} else {
			return false;
		}		
	}
	public static boolean isHalfWidthKatakana(char c)
	{
		return c>='\uFF65' && c<='\uFF9D';
	}

	public static boolean isPunctuation(char c)
	{
		return getPunctuation(c)!=null;
	}

	public static Punctuation getPunctuation(char c)
	{
		for(Punctuation punctuation:Punctuation.values())
		{
			if(punctuation.getPunctChar()==c)
			{
				return punctuation;
			}
		}
		return null;
	}



	public static boolean isKanji(char c)
	{
		if( c>= '\u4E00' && c<= '\u9FFF') //common kanji
		{
			return true;
		}
		if( c>= '\u3400' && c<= '\u4DB5') //rare kanji extension
		{
			return true;
		}
		return false;
	}
	public static boolean isShime(char c)
	{
		return c == '\u3006' || c== '\u4E44';
	}

	public static boolean isRepeatCharacter(char c)
	{
		return isDouNoJiten(c)||isIchiNoJiten(c)||isNiNoJiten(c)||isKuNoJiten(c);
	}
	public static boolean isDouNoJiten(char c)
	{
		return c =='\u3005';
	}
	public static boolean isIchiNoJiten(char c)
	{
		return c == '\u309D' || c=='\u309E' || c=='\u30FD' || c=='\u30FE';
	}
	public static boolean isNiNoJiten(char c)
	{
		return c == '\u303B';
	}
	public static boolean isKuNoJiten(char c)
	{
		return c >= '\u3031' && c<='\u3035';
	}

	public enum PunctuationTypes
	{
		Space(' '),
		Comma(','),
		Period('.'),
		MiddleDot('・'),
		Colon(':'),
		Ellipsis('\u2026'),
		ExclamationMark('!'),
		QuestionMark('?'),
		QuestionExclamationMark('\u203D'),
		Semicolon(';'),
		Slash('/'),
		BackSlash('\\'),
		Dash('-'),
		Hyphen('-'),
		OpeningParenthesis('('),
		ClosingParenthesis(')'),
		Quote('"'),
		LongVowelMark('ー'),
		Ditto('"'),
		Other('\uffff');
		
		char normalizedChar;
		private PunctuationTypes(char normalizedChar)
		{
			this.normalizedChar = normalizedChar;
		}
		public char getNormalizedChar() {
			return normalizedChar;
		}
		public void setNormalizedChar(char normalizedChar) {
			this.normalizedChar = normalizedChar;
		}
	}

	//Japanese uses punctuation from several code tables (notably latin and hiragana)
	//to normalize punctuation, every possible Unicode variation will be reduced here.
	public enum Punctuation
	{
		//non-visible spaces
		TabSpace(PunctuationTypes.Space, '\u0009'),
		Space(PunctuationTypes.Space, '\u0020'),
		NoBreakSpace(PunctuationTypes.Space, '\u00A0'),
		OghamSpace(PunctuationTypes.Space, '\u1680'),
		MongolSpace(PunctuationTypes.Space, '\u180E'),
		EnQuad(PunctuationTypes.Space, '\u2000'),
		EmQuad(PunctuationTypes.Space, '\u2001'),
		EnSpace(PunctuationTypes.Space, '\u2002'),
		EmSpace(PunctuationTypes.Space, '\u2003'),
		ThreePerEmSpace(PunctuationTypes.Space, '\u2004'),
		FourPerEmSpace(PunctuationTypes.Space, '\u2005'),
		SixPerEmSpace(PunctuationTypes.Space, '\u2006'),
		FigureSpace(PunctuationTypes.Space, '\u2007'),
		PunctuationSpace(PunctuationTypes.Space, '\u2008'),
		ThinSpace(PunctuationTypes.Space, '\u2009'),
		HairSpace(PunctuationTypes.Space, '\u200A'),
		ZeroWidth(PunctuationTypes.Space, '\u200B'), //can be used to indicate morphological separation
		ZeroWidthNonJoiner(PunctuationTypes.Other, '\u200C'), //not really a space, joins two characters together
		ZeroWidthJoiner(PunctuationTypes.Other, '\u200D'), //not really a space, joins two characters together
		NarrowNoBreakSpace(PunctuationTypes.Space, '\u202F'),
		MediumMathematicalSpace(PunctuationTypes.Space, '\u205F'),
		WordJoinerSpace(PunctuationTypes.Other, '\u2060'),
		HiraganaSpace(PunctuationTypes.Space, '\u3000'), //main japanese type space
		ByteOrderMarkSpace(PunctuationTypes.Other, '\uFEFF'),

		//visible spaces
		ShoulderedOpenBoxSpace(PunctuationTypes.Space, '\u237D'),
		SymbolForSpace(PunctuationTypes.Space, '\u2420'),
		BlankSymbol(PunctuationTypes.Space, '\u2422'),
		OpenBox(PunctuationTypes.Space, '\u2423'),

		//comma
		UsualComma(PunctuationTypes.Comma, '\u002C'),
		HiraganaToutenComma(PunctuationTypes.Comma, '\u3001'),
		KatakanaToutenComma(PunctuationTypes.Comma, '\uFF64'),
		KatakanaComma(PunctuationTypes.Comma, '\uFF0C'),
		TopToBottomComma(PunctuationTypes.Comma, '\uFE10'),
		TopToBottomIdeographicComma(PunctuationTypes.Comma, '\uFE11'),
		SmallComma(PunctuationTypes.Comma, '\uFE50'),
		SmallIdeographicComma(PunctuationTypes.Comma, '\uFE51'),
		ModifiedLetterTurnedComma(PunctuationTypes.Comma, '\u02BB'),
		ModifiedLetterReversedComma(PunctuationTypes.Comma, '\u02BD'),
		ArabicComma(PunctuationTypes.Comma, '\u060C'),
		MongolianComma(PunctuationTypes.Comma, '\u1802'),
		CombiningTurnedCommaAbove(PunctuationTypes.Comma, '\u0312'),
		CombiningCommaAbove(PunctuationTypes.Comma, '\u0313'),
		CombiningReversedCommaAbove(PunctuationTypes.Comma, '\u0314'),
		CombiningCommaAboveRight(PunctuationTypes.Comma, '\u0315'),
		CombiningCommaBelow(PunctuationTypes.Comma, '\u0326'),
		LisuPunctuationComma(PunctuationTypes.Comma, '\uA4FE'),
		MongolianManchuComma(PunctuationTypes.Comma, '\u1808'),
		NkoComma(PunctuationTypes.Comma, '\u07F8'),
		VaiComma(PunctuationTypes.Comma, '\uA60D'),
		ArmenianComma(PunctuationTypes.Comma, '\u055D'),
		BamumComma(PunctuationTypes.Comma, '\uA6F5'),

		//Periods
		JapaneseKuten(PunctuationTypes.Period, '\u3002'),
		JapaneseHalfWidthKuten(PunctuationTypes.Period, '\uFF61'),
		JapaneseTopToBottomKuten(PunctuationTypes.Period, '\uFE12'),
		Period(PunctuationTypes.Period, '\u002E'),
		PeriodInIdeogramSpace(PunctuationTypes.Period, '\uFF0E'),

		FullWidthMiddleDot(PunctuationTypes.MiddleDot, '\u30FB'), //in japanese, acts as a comma for lists, separator for long surnames, colon, dot for kanji numerical expressions, bullet points or ellipsis
		GreekSemicolon(PunctuationTypes.MiddleDot, '\u0387'),
		Bullet(PunctuationTypes.MiddleDot, '\u2022'),
		BulletOperator(PunctuationTypes.MiddleDot, '\u2219'),
		DotOperator(PunctuationTypes.MiddleDot, '\u22C5'),
		MiddleDot(PunctuationTypes.MiddleDot, '\u00B7'),
		HalfWidthMiddleDot(PunctuationTypes.MiddleDot, '\uFF65'),

		Colon(PunctuationTypes.Colon, '\u003A'),
		ModifiedLetterRaisedColon(PunctuationTypes.Colon, '\u02F8'),
		SyriacSupralinearColon(PunctuationTypes.Colon, '\u0703'),
		SyriacSublinearColon(PunctuationTypes.Colon, '\u0704'),
		EthiopicColon(PunctuationTypes.Colon, '\u1365'),
		MongolianColon(PunctuationTypes.Colon, '\u1804'),
		//		TriColon(PunctuationTypes.Colon, '\u205D'), //moved to ellipsis for Japanese
		RatioColon(PunctuationTypes.Colon, '\u2236'),
		BamumColon(PunctuationTypes.Colon, '\uA6F4'),
		ModifiedLetterColon(PunctuationTypes.Colon, '\uA789'),
		SmallColon(PunctuationTypes.Colon, '\uFE55'),
		FullWidthColon(PunctuationTypes.Colon, '\uFF1A'),

		Ellipsis(PunctuationTypes.Ellipsis, '\u2026'),
		TwoDotEllipsis(PunctuationTypes.Ellipsis, '\u2025'),
		MidlineHorizontalEllipsis(PunctuationTypes.Ellipsis, '\u22EF'),
		TopToBottomEllipsis(PunctuationTypes.Ellipsis, '\uFE19'),
		TopToBottomTwoDotEllipsis(PunctuationTypes.Ellipsis, '\uFE30'),
		TriColon(PunctuationTypes.Ellipsis, '\u205D'), //ambiguous, could be a colon
		TwoDotPunctuation(PunctuationTypes.Ellipsis, '\u205A'), //ambiguous, could be a colon

		ExclamationMark(PunctuationTypes.ExclamationMark, '\u0021'),
		InvertedExclamationMark(PunctuationTypes.ExclamationMark, '\u00A1'),
		LatinRetroflexClick(PunctuationTypes.ExclamationMark, '\u01C3'),
		TwoExclamationMarks(PunctuationTypes.ExclamationMark, '\u203C'),
		FullWidthExclamationMark(PunctuationTypes.ExclamationMark, '\uFF01'),

		QuestionMark(PunctuationTypes.QuestionMark, '\u003F'),
		FullWidthQuestionMark(PunctuationTypes.QuestionMark, '\uFF1F'),
		TwoQuestionMarks(PunctuationTypes.QuestionMark, '\u2047'),
		InvertedQuestionMark(PunctuationTypes.QuestionMark, '\u00BF'),
		ArabicQuestionMark(PunctuationTypes.QuestionMark, '\u061F'),

		InterroBang(PunctuationTypes.QuestionExclamationMark, '\u203D'),
		InvertedInterroBang(PunctuationTypes.QuestionExclamationMark, '\u2E18'),
		ExclamationThenQuestionMark(PunctuationTypes.QuestionExclamationMark, '\u2049'),
		QuestionThenExclamationMark(PunctuationTypes.QuestionExclamationMark, '\u2048'),

		SemiColon(PunctuationTypes.QuestionExclamationMark, '\u003B'),
		FullWidthSemiColon(PunctuationTypes.QuestionExclamationMark, '\uFF1B'),

		Solidus(PunctuationTypes.Slash, '\u002F'),
		CombiningLongSolidus(PunctuationTypes.Slash, '\u0338'),
		CombiningShortSolidus(PunctuationTypes.Slash, '\u0337'),
		FractionSlash(PunctuationTypes.Slash, '\u2044'),
		DivisionSlash(PunctuationTypes.Slash, '\u2215'),
		BoxDrawingSlash(PunctuationTypes.Slash, '\u2571'),
		FullWidthSolidus(PunctuationTypes.Slash, '\uFF0F'),

		BackSlash(PunctuationTypes.BackSlash, '\\'),
		CombiningReverseSolidus(PunctuationTypes.BackSlash, '\u20E5'),
		SetMinus(PunctuationTypes.BackSlash, '\u2216'),
		BoxDrawingBackSlash(PunctuationTypes.BackSlash, '\u2572'),
		ReverseSolidusOperator(PunctuationTypes.BackSlash, '\u29F5'),
		SmallReverseSolidus(PunctuationTypes.BackSlash, '\uFE68'),
		FullWidthBackSlash(PunctuationTypes.BackSlash, '\uFF3C'),


		//TODO: vertical lines, underscores, overline
		FigureDash(PunctuationTypes.Dash, '\u2012'),
		EnDash(PunctuationTypes.Dash, '\u2013'),
		EmDash(PunctuationTypes.Dash, '\u2014'),
		HorizontalBackwardationDash(PunctuationTypes.Dash, '\u2015'),
		SwangDash(PunctuationTypes.Dash, '\u2053'),
		WaveDash(PunctuationTypes.Dash, '\u301C'),
		FullWidthTilde(PunctuationTypes.Dash, '\uFF5E'),
		WavyDash(PunctuationTypes.Dash, '\u3030'),

		Hyphen(PunctuationTypes.Hyphen, '\u2010'),
		HyphenMinus(PunctuationTypes.Hyphen, '\u002D'),
		SoftHyphen(PunctuationTypes.Hyphen, '\u00AD'),
		NonBreakingHyphen(PunctuationTypes.Hyphen, '\u2011'),

		DoubleHyphen(PunctuationTypes.Hyphen, '\u30A0'), //looks like an equal sign, used as hyphen in Japanese
		TiltedDoubleHyphen(PunctuationTypes.Hyphen, '\u2E17'),

		//TODO: apostrophes,tilde,trema,caret (not used in Japanese)

		SingleQuote(PunctuationTypes.Quote, '\''),
		DoubleQuote(PunctuationTypes.Quote, '\"'),
		OpeningSingleQuote(PunctuationTypes.Quote, '\u2018'),
		ClosingSingleQuote(PunctuationTypes.Quote, '\u2019'),
		OpeningDoubleQuote(PunctuationTypes.Quote, '\u201C'),
		ClosingDoubleQuote(PunctuationTypes.Quote, '\u201D'),
		GermanClosingSingleQuote(PunctuationTypes.Quote, '\u201A'),
		GermanOpeningDoubleQuote(PunctuationTypes.Quote, '\u201E'),
		GermanClosingDoubleQuote(PunctuationTypes.Quote, '\u201C'),
		GermanAlternateClosingDoubleQuote(PunctuationTypes.Quote, '\u201D'),
		OpeningSingleGuillemet(PunctuationTypes.Quote, '\u2039'),
		ClosingSingleGuillemet(PunctuationTypes.Quote, '\u203A'),
		OpeningDoubleGuillemet(PunctuationTypes.Quote, '\u00AB'),
		ClosingDoubleGuillemet(PunctuationTypes.Quote, '\u00BB'),
		OpeningJapaneseQuote(PunctuationTypes.Quote, '\u300C'),
		ClosingJapaneseQuote(PunctuationTypes.Quote, '\u300D'),
		OpeningWhiteJapaneseQuote(PunctuationTypes.Quote, '\u300E'),
		ClosingWhiteJapaneseQuote(PunctuationTypes.Quote, '\u300F'),
		OpeningJapaneseDoubleBracket(PunctuationTypes.Quote, '\u300A'),
		ClosingJapaneseDoubleBracket(PunctuationTypes.Quote, '\u300B'),
		OpeningJapaneseBracket(PunctuationTypes.Quote, '\u3008'),
		ClosingJapaneseBracket(PunctuationTypes.Quote, '\u3009'),
		OpeningJapaneseDoubleMinute(PunctuationTypes.Quote, '\u301D'),
		ClosingJapaneseDoubleMinuteLow(PunctuationTypes.Quote, '\u301F'),
		ClosingJapaneseDoubleMinuteHigh(PunctuationTypes.Quote, '\u301E'),

		LongVowelMark(PunctuationTypes.LongVowelMark, '\u30FC'),
		HalfWidthLongVowelMark(PunctuationTypes.LongVowelMark, '\uFF70'),
		CurlyLoop(PunctuationTypes.LongVowelMark, '\u27B0'),
		DoubleCurlyLoop(PunctuationTypes.LongVowelMark, '\u27BF'), //can also use 30FC

		Ditto(PunctuationTypes.Ditto, '\u2033'),
		HiraganaDitto(PunctuationTypes.Ditto, '\u3003');

		PunctuationTypes type;
		char punctChar;
		private Punctuation(PunctuationTypes type, char punctChar) {
			this.type = type;
			this.punctChar = punctChar;
		}
		public char getPunctChar() {
			return punctChar;
		}
		public PunctuationTypes getPunctuationType()
		{
			return type;
		}
	}

	public static boolean isDigit(char c)
	{
		return (c >= '0' && c<='9') || (c>='\uFF10' && c<='\uFF19'); //with digits in kana space
	}

	public static boolean isLatin(char c) {
		return (c >= 'a' && c<='z') || (c>='A' && c<='Z') || (c >= '\uFF41' && c<='\uFF5A') || (c>='\uFF21' && c<='\uFF3A');
		//with romaji in kana space
	}

	public static boolean isKanjiDigit(char c)
	{
		//京該秭穣溝澗正載 ignored (not mainly numeric), multi character ignored, maru '\u3007' ignored (might be maru)
		return "零一ニ三四五六七八九十百千万億兆".contains(c+"");
	}
	public static boolean canBeKanjiDigitFinancial(char c)
	{
		return "壹貳弐参參肆伍陸柒捌玖拾佰仟萬".contains(c+"");
	}

	public static char normalizeCharacter(char c)
	{
		if(isLatin(c))
		{
			if(c >= '\uFF41' && c<='\uFF5A')
			{
				return (char)(c - '\uFF41'+'a');
			}
			if(c >= '\uFF21' && c<='\uFF3A')
			{
				return (char)(c - '\uFF21'+'A');
			}
		}

		if(isPunctuation(c))
		{
			for(Punctuation punctuation:Punctuation.values())
			{
				if(punctuation.punctChar==c)
				{
					if(punctuation.type!=PunctuationTypes.Other)
					{
						return punctuation.type.normalizedChar;
					}
				}
			}
		}
		
		if(isDigit(c))
		{
			if(c>='\uFF10' && c<='\uFF19')
			{
				return (char)(c-'\uFF10'+'0');
			}
		}
		
		if(isHalfWidthKatakana(c))
		{
			switch(c)
			{
			case '\uff66': /*ｦ*/ return 'ヲ';
			case '\uff67': /*ｧ*/ return 'ァ';
			case '\uff68': /*ｨ*/ return 'ィ';
			case '\uff69': /*ｩ*/ return 'ゥ';
			case '\uff6a': /*ｪ*/ return 'ェ';
			case '\uff6b': /*ｫ*/ return 'ォ';
			case '\uff6c': /*ｬ*/ return 'ャ';
			case '\uff6d': /*ｭ*/ return 'ュ';
			case '\uff6e': /*ｮ*/ return 'ョ';
			case '\uff6f': /*ｯ*/ return 'ッ';
			case '\uff70': /*ｰ*/ return 'ー';
			case '\uff71': /*ｱ*/ return 'ア';
			case '\uff72': /*ｲ*/ return 'イ';
			case '\uff73': /*ｳ*/ return 'ウ';
			case '\uff74': /*ｴ*/ return 'エ';
			case '\uff75': /*ｵ*/ return 'オ';
			case '\uff76': /*ｶ*/ return 'カ';
			case '\uff77': /*ｷ*/ return 'キ';
			case '\uff78': /*ｸ*/ return 'ク';
			case '\uff79': /*ｹ*/ return 'ケ';
			case '\uff7a': /*ｺ*/ return 'コ';
			case '\uff7b': /*ｻ*/ return 'サ';
			case '\uff7c': /*ｼ*/ return 'シ';
			case '\uff7d': /*ｽ*/ return 'ス';
			case '\uff7e': /*ｾ*/ return 'セ';
			case '\uff7f': /*ｿ*/ return 'ソ';
			case '\uff80': /*ﾀ*/ return 'タ';
			case '\uff81': /*ﾁ*/ return 'チ';
			case '\uff82': /*ﾂ*/ return 'ツ';
			case '\uff83': /*ﾃ*/ return 'テ';
			case '\uff84': /*ﾄ*/ return 'ト';
			case '\uff85': /*ﾅ*/ return 'ナ';
			case '\uff86': /*ﾆ*/ return 'ニ';
			case '\uff87': /*ﾇ*/ return 'ヌ';
			case '\uff88': /*ﾈ*/ return 'ネ';
			case '\uff89': /*ﾉ*/ return 'ノ';
			case '\uff8a': /*ﾊ*/ return 'ハ';
			case '\uff8b': /*ﾋ*/ return 'ヒ';
			case '\uff8c': /*ﾌ*/ return 'フ';
			case '\uff8d': /*ﾍ*/ return 'へ';
			case '\uff8e': /*ﾎ*/ return 'ホ';
			case '\uff8f': /*ﾏ*/ return 'マ';
			case '\uff90': /*ﾐ*/ return 'ミ';
			case '\uff91': /*ﾑ*/ return 'ム';
			case '\uff92': /*ﾒ*/ return 'メ';
			case '\uff93': /*ﾓ*/ return 'モ';
			case '\uff94': /*ﾔ*/ return 'ヤ';
			case '\uff95': /*ﾕ*/ return 'ユ';
			case '\uff96': /*ﾖ*/ return 'ヨ';
			case '\uff97': /*ﾗ*/ return 'ラ';
			case '\uff98': /*ﾘ*/ return 'リ';
			case '\uff99': /*ﾙ*/ return 'ル';
			case '\uff9a': /*ﾚ*/ return 'レ';
			case '\uff9b': /*ﾛ*/ return 'ロ';
			case '\uff9c': /*ﾜ*/ return 'ワ';
			case '\uff9d': /*ﾝ*/ return 'ン';	
			}
		}
		
		if(isDakuten(c))
		{
			return '\u309B'; //return the non combining version so the difference with the actual combined character can be seen
		}
		if(isHandakuTen(c))
		{
			return '\u309C'; //return the non combining version so the difference with the actual combined character can be seen
		}
		
		return c;
	}
	
	public static boolean isDakuten(char c) //tenten
	{
		return c=='\u309B'||c=='\u3099'||c=='\uFF9E';
	}
	
	public static boolean isHandakuTen(char c) //maru, ten
	{
		return c=='\u302B'||c=='\u302C'||c=='\u309A'||c=='\u309C'||c=='\uFF9F'; //302B and 302C are actually tone markers for ideogram
	}
	
	public enum DakutenNormalization
	{
		HiraganaU('う','ゔ', null),
		
		HiraganaKa('か','が', null),
		HiraganaKi('き','ぎ', null),
		HiraganaKu('く','ぐ', null),
		HiraganaKe('け','げ', null),
		HiraganaKo('こ','ご', null),
		
		HiraganaSa('さ','ざ', null),
		HiraganaShi('し','じ', null),
		HiraganaSu('す','ず', null),
		HiraganaSe('せ','ぜ', null),
		HiraganaSo('そ','ぞ', null),
		
		HiraganaTa('た','だ', null),
		HiraganaChi('ち','ぢ', null),
		HiraganaTsu('つ','づ', null),
		HiraganaTe('て','で', null),
		HiraganaTo('と','ど', null),
		
		HiraganaHa('は','ば', 'ぱ'),
		HiraganaHi('ひ','び', 'ぴ'),
		HiraganaFu('ふ','ぶ', 'ぷ'),
		HiraganaHe('へ','べ', 'ぺ'),
		HiraganaHo('ほ','ぼ', 'ぽ'),
		
		
		//not handled: ヷ ヸ ヹ ヺ since they're not considered as katakana yet
		KatakanaU('ウ','ヴ', null),
		
		KatakanaKa('カ','ガ', null),
		KatakanaKi('キ','ギ', null),
		KatakanaKu('ク','グ', null),
		KatakanaKe('ケ','ゲ', null),
		KatakanaKo('コ','ゴ', null),
		
		KatakanaSa('サ','ザ', null),
		KatakanaShi('シ','ジ', null),
		KatakanaSu('ス','ズ', null),
		KatakanaSe('セ','ゼ', null),
		KatakanaSo('ソ','ゾ', null),
		
		KatakanaTa('タ','ダ', null),
		KatakanaChi('チ','ヂ', null),
		KatakanaTsu('ツ','ヅ', null),
		KatakanaTe('テ','デ', null),
		KatakanaTo('ト','ド', null),
		
		KatakanaHa('ハ','バ', 'パ'),
		KatakanaHi('ヒ','ビ', 'ピ'),
		KatakanaFu('フ','ブ', 'プ'),
		KatakanaHe('\u30D8','\u30D9', '\u30DA'),
		KatakanaHo('ホ','ボ', 'ポ');
		
		char sourceChar;
		char dakutenChar;
		Character hanDakutenChar;
		private DakutenNormalization(char sourceChar,char dakutenChar, Character handakutenChar)
		{
			this.sourceChar = sourceChar;
			this.dakutenChar = dakutenChar;
			this.hanDakutenChar = handakutenChar;
		}
		public char getSourceChar() {
			return sourceChar;
		}
		public char getDakutenChar() {
			return dakutenChar;
		}
		public Character getHanDakutenChar() {
			return hanDakutenChar;
		}
	}
	
	/* Same as normalizeCharacter half width katakana with dakuten can be normalized as well*/
	public static String normalizeString(String s)
	{
		String result = "";
		for(int i=0;i<s.length();i++)
		{
			char replacementChar = normalizeCharacter(s.charAt(i));
			boolean hasTen = i<s.length()-1 && isHandakuTen(s.charAt(i+1));
			boolean hasTenten = i<s.length()-1 && isDakuten(s.charAt(i+1));
			
			if(hasTenten)
			{
				for(DakutenNormalization dn:DakutenNormalization.values())
				{
					if(dn.getSourceChar()==replacementChar)
					{
						replacementChar = dn.getDakutenChar();
						i++;//skip a char
						break;
					}
				}
			}
			
			if(hasTen)
			{
				for(DakutenNormalization dn:DakutenNormalization.values())
				{
					if(dn.getSourceChar()==replacementChar && dn.getHanDakutenChar()!=null)
					{
						replacementChar = dn.getHanDakutenChar();
						i++;//skip a char
						break;
					}
				}
			}
			
			result += replacementChar;
			
		}
		return result;
	}

	public static void main(String[] args)
	{
		for(char c:new char[]{
				//romaji
				'a','A','z','Z','\uFF41','\uFF5A','\uFF21','\uFF3A',

				//repeat chars
				'\u3005','\u309D','\u309E','\u30FD','\u30FE','\u303B','\u3031','\u3032','\u3033','\u3034','\u3035',
				
				//digits
				'0','9','\uFF10','\uFF19',
				
				//dakuten, handakuten
				'\u302B','\u302C','\u3099','\u309A','\u309B','\u309C', '\uFF9E','\uFF9F'
		})
		{
			System.out.println("character '"+c+"' normalizesto:'"+normalizeCharacter(c)+"' isKanji:"+isKanji(c)+" isDigit:"+isDigit(c)+" isKanjiDigit:"+isKanjiDigit(c)+" isFullWidthKatakana:"+isFullWidthKatakana(c)+" isPunctuation:"+isPunctuation(c)+" isShime:"+isShime(c)+" isRepeatCharacter:"+isRepeatCharacter(c)+" isKuNojiten:"+isKuNoJiten(c));
		}
		
//		for(char c='\uFF66';c<='\uFF9D';c++)
//		{
//			System.out.println("case '\\u"+Integer.toHexString(c & (0xFFFF))+"': /*"+c+"*/ return '';");
//		}
	}
}
