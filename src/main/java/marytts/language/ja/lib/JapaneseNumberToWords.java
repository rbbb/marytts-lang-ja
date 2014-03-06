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


public class JapaneseNumberToWords {
	//returns tokens to spell out a word in a given counting word context
	//numbers and punctuation is a string containing the longest possible number
	//of consecutive numbers and punctuation found in the text
	//counter is the counting word or null
	
	public enum CountingType
	{
		NativeJapaneseTensed, //みっつ、よっつ
		NativeJapaneseNotTensed, //ふたさら、みさら？？　っ　そくおん　ない場合
		SinoJapanese
	}
	
	//for ichi-ka-getsu  -> ikka-getsu
	public enum WordBeginType 
	{
		KType,
		SType,
		TType,
		HAType,
		HIType,
		HOType,
		HEType,
		FUType,
		PType,
		WaType,
		OtherType
	}
	public enum CounterWord
	{
		Year("年","ねん", CountingType.SinoJapanese, WordBeginType.OtherType),
		Month("月","がつ", CountingType.SinoJapanese, WordBeginType.OtherType),
		MonthSpan("か月","かげつ", CountingType.SinoJapanese, WordBeginType.KType),
		MonthSpan2("カ月","かげつ", CountingType.SinoJapanese, WordBeginType.KType),
		MonthSpan3("ケ月","かげつ", CountingType.SinoJapanese, WordBeginType.KType),
		MonthSpan4("ヵ月","かげつ", CountingType.SinoJapanese, WordBeginType.KType),
		MonthSpan5("ヶ月","かげつ", CountingType.SinoJapanese, WordBeginType.KType),
		MonthSpan6("箇月","かげつ", CountingType.SinoJapanese, WordBeginType.KType),
		MonthSpan7("個月","かげつ", CountingType.SinoJapanese, WordBeginType.KType),
		Week("週間","しゅうかん", CountingType.SinoJapanese, WordBeginType.SType),
		Day("日","にち", CountingType.SinoJapanese, WordBeginType.OtherType),//irregular
		Hour("時", "じ", CountingType.SinoJapanese, WordBeginType.OtherType), //used for 時間　as well
		Minute("分", "ふん", CountingType.SinoJapanese, WordBeginType.FUType),
		Second("秒", "びょう", CountingType.SinoJapanese, WordBeginType.OtherType),
		Stays("泊", "はく", CountingType.SinoJapanese, WordBeginType.HAType),
		Nights("晩","ばん",CountingType.SinoJapanese,WordBeginType.OtherType),
		Nights2("夜","や ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Age("才", "さい", CountingType.SinoJapanese, WordBeginType.SType),
		Age2("歳", "さい", CountingType.SinoJapanese, WordBeginType.SType),
		Generation("代","だい",CountingType.SinoJapanese,WordBeginType.OtherType),
		Generation2("世","せい",CountingType.SinoJapanese,WordBeginType.SType),
		
		NumberOfThings("つ", "つ", CountingType.NativeJapaneseTensed, null), //with exception on 5
		NumberOfThings2("個", "こ", CountingType.SinoJapanese, WordBeginType.KType),
		NumberOfThings3("箇", "こ", CountingType.SinoJapanese, WordBeginType.KType),
		NumberOfThings4("个", "こ", CountingType.SinoJapanese, WordBeginType.KType),
		NumberOfThings5("ヶ", "こ", CountingType.SinoJapanese, WordBeginType.KType),
		
		NumberOfPeoplePolite("名", "めい", CountingType.SinoJapanese, WordBeginType.OtherType),
		//!!after 3, Sino-Japanese
		NumberOfPeople("人", "リ", CountingType.NativeJapaneseNotTensed, WordBeginType.OtherType),
		NumberOfPeople2("人","にん",CountingType.SinoJapanese,WordBeginType.OtherType),
		Children("児","じ ",CountingType.SinoJapanese,WordBeginType.OtherType),
		
		Portions("人前","にんまえ",CountingType.SinoJapanese,WordBeginType.OtherType),
		CupsAndGlasses("杯","はい",CountingType.SinoJapanese,WordBeginType.HAType),
		PartOfMeal("品","ひん",CountingType.SinoJapanese,WordBeginType.HIType),
		PartOfMeal2("品","しな ",CountingType.SinoJapanese,WordBeginType.SType),//?? never reached
		Loaves("斤","きん",CountingType.SinoJapanese,WordBeginType.KType),
		Slices("切れ","きれ",CountingType.SinoJapanese,WordBeginType.KType),
		PressedSushiPieces("貫","かん",CountingType.SinoJapanese,WordBeginType.KType),
		BattleshipSushiPieces("艦","かん",CountingType.SinoJapanese,WordBeginType.KType),
		ShotsOfAlcohol("献","こん",CountingType.SinoJapanese,WordBeginType.KType),
		CupAndSaucer("客","きゃ",CountingType.SinoJapanese,WordBeginType.KType),
		PairsOfChopsticks("膳","ぜん ",CountingType.SinoJapanese,WordBeginType.OtherType),
		BagsOfRice("俵","たわら ",CountingType.SinoJapanese,WordBeginType.TType),

		
		LongThinObjects("本","ほん",CountingType.SinoJapanese,WordBeginType.HOType),
		TypesOrSpecies("種類 or 種","しゅるい ",CountingType.SinoJapanese,WordBeginType.SType),

		
		Multiple("倍","ばい",CountingType.SinoJapanese,WordBeginType.OtherType),
		Number("番","ばん",CountingType.SinoJapanese,WordBeginType.OtherType),
		RankStep("段","だん",CountingType.SinoJapanese,WordBeginType.OtherType),
		DegreeOccurence("度","ど ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Times("回","かい",CountingType.SinoJapanese,WordBeginType.KType),
		
		SmallFish("尾","び ",CountingType.SinoJapanese,WordBeginType.OtherType),
		SmallAnimals("匹","ひき",CountingType.SinoJapanese,WordBeginType.HIType),
		Birds("羽","わ ",CountingType.SinoJapanese,WordBeginType.WaType),
		HeadOfCattleOrLargeAnimal("頭","とう ",CountingType.SinoJapanese,WordBeginType.TType),
		

		MusicalPiece("曲","きょ",CountingType.SinoJapanese,WordBeginType.KType),
		Characters("字","じ ",CountingType.SinoJapanese,WordBeginType.OtherType),
		ScenesOfPlay("場","ば ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Paragraph("段落","だん",CountingType.SinoJapanese,WordBeginType.OtherType),
		CopiesOfMagazine("部","ぶ ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Sentences("文","ぶん",CountingType.SinoJapanese,WordBeginType.OtherType),
		MusicalBeats("拍子","びょ",CountingType.SinoJapanese,WordBeginType.OtherType),
		SequenceOfLetter("筆","ふで",CountingType.SinoJapanese,WordBeginType.FUType),
		HangingScrolls("幅","ふく",CountingType.SinoJapanese,WordBeginType.FUType),
		Words("語","ご ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Words2("言","ごん",CountingType.SinoJapanese,WordBeginType.OtherType),
		LinesOfText("行","ぎょ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Episode("話","わ ",CountingType.SinoJapanese,WordBeginType.WaType),
		Poetry("句","く ",CountingType.SinoJapanese,WordBeginType.KType),
		Poetry2("首","しゅ ",CountingType.SinoJapanese,WordBeginType.SType),
		KanjiStrokes("画","かく",CountingType.SinoJapanese,WordBeginType.KType),
		LawArticlesOrRays("条","じょ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Drafts("稿","こう",CountingType.SinoJapanese,WordBeginType.KType),
		Pages("ページ, 頁","ぺーじ ",CountingType.SinoJapanese,WordBeginType.PType),
		Letters("通","つう ",CountingType.SinoJapanese,WordBeginType.TType),
		Books("冊","さつ ",CountingType.SinoJapanese,WordBeginType.SType),
		
		VehicleMachine("台","だい",CountingType.SinoJapanese,WordBeginType.OtherType),
		AircraftMachines("機","き ",CountingType.SinoJapanese,WordBeginType.KType),
		Ships("隻","せき ",CountingType.SinoJapanese,WordBeginType.SType),
		RailwayCars("両","りょう ",CountingType.SinoJapanese,WordBeginType.OtherType),
		
		Countries("ヶ国","かこ",CountingType.SinoJapanese,WordBeginType.KType),
		Countries2("箇国","かこ",CountingType.SinoJapanese,WordBeginType.KType),
		TownBlocksToolsServings("丁","ちょ",CountingType.SinoJapanese,WordBeginType.TType),
		TownBlocks("町","ちょ",CountingType.SinoJapanese,WordBeginType.TType),
		District("区","く ",CountingType.SinoJapanese,WordBeginType.KType),
		PiecesOfLand("筆","ひつ",CountingType.SinoJapanese,WordBeginType.HIType),
		Tsubo("坪","つぼ ",CountingType.SinoJapanese,WordBeginType.TType),
		TatamiMats("畳","じょ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Houses("軒","けん",CountingType.SinoJapanese,WordBeginType.KType),
		Houses2("戸","こ ",CountingType.SinoJapanese,WordBeginType.KType),
		
		Guns("挺","ちょ",CountingType.SinoJapanese,WordBeginType.TType),
		Sword("振","ふり",CountingType.SinoJapanese,WordBeginType.FUType),
		Cannons("門","もん",CountingType.SinoJapanese,WordBeginType.OtherType),
		
		SuitsOfClothing("着","ちゃ",CountingType.SinoJapanese,WordBeginType.TType),
		
		PacketsOfPowder("服","ふく",CountingType.SinoJapanese,WordBeginType.FUType),
		Pills("錠","じょ",CountingType.SinoJapanese,WordBeginType.OtherType),
		DropsOfLiquid("滴","てき ",CountingType.SinoJapanese,WordBeginType.TType),

		Lessons("課","か ",CountingType.SinoJapanese,WordBeginType.KType),
		Schools("校","こう",CountingType.SinoJapanese,WordBeginType.KType),
		Class("学級","がっ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Class2("クラス","くら",CountingType.SinoJapanese,WordBeginType.KType),

		Sets("具","ぐ ",CountingType.SinoJapanese,WordBeginType.OtherType),
		Losses("敗","はい",CountingType.SinoJapanese,WordBeginType.HAType),
		Boxes("箱","はこ",CountingType.SinoJapanese,WordBeginType.HAType),
		Umbrellas("張","はり",CountingType.SinoJapanese,WordBeginType.HAType),
		GodsOrMemorialTablets("柱","はし",CountingType.SinoJapanese,WordBeginType.HAType),
		Ejections("発","はつ",CountingType.SinoJapanese,WordBeginType.HAType),
		FootSteps("歩","ほ ",CountingType.SinoJapanese,WordBeginType.HOType),
		Votes("票","ひょ",CountingType.SinoJapanese,WordBeginType.HIType),
		Frames("架","か ",CountingType.SinoJapanese,WordBeginType.KType),
		Stocks("株","かぶ",CountingType.SinoJapanese,WordBeginType.KType),
		Floors("階","かい",CountingType.SinoJapanese,WordBeginType.KType),
		BusRoutes("系統","けい",CountingType.SinoJapanese,WordBeginType.KType),
		Cases("件","けん",CountingType.SinoJapanese,WordBeginType.KType),
		GravesReactors("基","き ",CountingType.SinoJapanese,WordBeginType.KType),
		Banks("行","こう",CountingType.SinoJapanese,WordBeginType.KType),
		FramePanels("齣, コマ","こま",CountingType.SinoJapanese,WordBeginType.KType),
		Accounts("口","くち",CountingType.SinoJapanese,WordBeginType.KType),
		Groups("組","くみ",CountingType.SinoJapanese,WordBeginType.KType),
		Chairs("脚","きゃ",CountingType.SinoJapanese,WordBeginType.KType),
		BoardgameOrTVStation("局","きょ",CountingType.SinoJapanese,WordBeginType.KType),
		ThinFlatObject("枚","まい",CountingType.SinoJapanese,WordBeginType.OtherType),
		RolledThings("巻","まき",CountingType.SinoJapanese,WordBeginType.OtherType),
		TheatricalActs("幕","まく",CountingType.SinoJapanese,WordBeginType.OtherType),
		Surfaces("面","めん",CountingType.SinoJapanese,WordBeginType.OtherType),
		Questions("問","もん",CountingType.SinoJapanese,WordBeginType.OtherType),
		FoldedBoxes("折","おり",CountingType.SinoJapanese,WordBeginType.OtherType),
		Examples("例","れい",CountingType.SinoJapanese,WordBeginType.OtherType),
		Bowings("礼","れい",CountingType.SinoJapanese,WordBeginType.OtherType),
		RingsOrFlowers("輪","りん ",CountingType.SinoJapanese,WordBeginType.OtherType),
		ChestOfDrawers("棹","さお ",CountingType.SinoJapanese,WordBeginType.SType),
		SeatsOrMeetings("席","せき ",CountingType.SinoJapanese,WordBeginType.SType),
		Businesses("社","しゃ ",CountingType.SinoJapanese,WordBeginType.SType),
		SetsOfThings("式","しき ",CountingType.SinoJapanese,WordBeginType.SType),
		Wins("勝","しょう ",CountingType.SinoJapanese,WordBeginType.SType),
		PairOfFootwear("足","そく ",CountingType.SinoJapanese,WordBeginType.SType),
		Bundles("束","たば ",CountingType.SinoJapanese,WordBeginType.TType),
		Bodies("体","たい ",CountingType.SinoJapanese,WordBeginType.TType),
		PointsOrPieces("点","てん ",CountingType.SinoJapanese,WordBeginType.TType),
		Combinations("通り","とおり ",CountingType.SinoJapanese,WordBeginType.TType),
		PhoneCalls("通話","つうわ ",CountingType.SinoJapanese,WordBeginType.TType),
		Bundles2("把","わ ",CountingType.SinoJapanese,WordBeginType.WaType);		
		

		
		private String kanji;
		private String hiragana;
		private CountingType countingType;
		private WordBeginType wordBeginType;
		private CounterWord(String kanji, String hiragana,
				CountingType countingType, WordBeginType wordBeginType) {
			this.kanji = kanji;
			this.hiragana = hiragana;
			this.countingType = countingType;
			this.wordBeginType = wordBeginType;
		}
		public String getKanji() {
			return kanji;
		}
		public String getHiragana() {
			return hiragana;
		}
		public CountingType getCountingType() {
			return countingType;
		}
		public WordBeginType getWordBeginType() {
			return wordBeginType;
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
			if(counterWord.getKanji().equals(counter))
			{
				List<String> result;
				
				//exception: 人
				if(counter.equals("人"))
				{
					if(numbersAndPunctuation.length()>1)
					{
						result = toSinoJapaneseNumber(numbersAndPunctuation, CounterWord.NumberOfPeople2); //the one with ーにん
					}else{
						int num = Integer.parseInt(removePunctuation(numbersAndPunctuation));
						if(num==1)
						{
							result = new ArrayList<String>();
							result.add("ひとり");
						}else if(num==2){
							result = new ArrayList<String>();
							result.add("ふたり");
						}else{
							result = toSinoJapaneseNumber(numbersAndPunctuation, CounterWord.NumberOfPeople2);
						}
						return result;
					}
					break;
				}

				//exception 日
				if(counter.equals("日"))
				{
					if(numbersAndPunctuation.length()>2)
					{
						result = toSinoJapaneseNumber(numbersAndPunctuation, CounterWord.NumberOfPeople2); //the one with ーにん
					}else{
						int num = Integer.parseInt(removePunctuation(numbersAndPunctuation));
						result = new ArrayList<String>();
						switch(num)
						{
						case 1: result.add("ついたち"); break;
						case 2: result.add("ふつか"); break;
						case 3: result.add("みっか"); break;
						case 4: result.add("よっか"); break;
						case 5: result.add("いつか"); break;
						case 6:result.add("むっか");break;
						case 7: result.add("なのか"); break;
						case 8: result.add("ようか"); break;
						case 9: result.add("ここのか"); break;
						case 10: result.add("とうか"); break;
						case 20: result.add("はつか"); break;
						case 24: result.add("にじゅうよっか"); break;
						//default: use rule
						}
						if(result.size()>0){
							return result;
						}
					}
					break;
				}
				
				
				String number = removePunctuation(numbersAndPunctuation);
				switch(counterWord.countingType)
				{
				case NativeJapaneseTensed:
				case NativeJapaneseNotTensed:
					result = toNativeJapaneseNumber(number, counter, counterWord.countingType==CountingType.NativeJapaneseTensed);
					break;
				case SinoJapanese:
					result = toSinoJapaneseNumber(number, counterWord);
					break;
				default:throw new IllegalArgumentException("Impossible case, only two ways to count in Korean");
				}
				return result;
			}
		}
		
		List<String> result = toSinoJapaneseNumber(numbersAndPunctuation, null);
		if(counter!=null)
		{
			result.add(counter);
		}
		return result;
	}

	private static String removePunctuation(String s)
	{
		boolean minusSeen = false;
		boolean numberSeen = false;
		String number = "";
		for(int i=0;i<s.length();i++)
		{
			switch(s.charAt(i))
			{
			case '-':
				if(!minusSeen && !numberSeen)
				{
					number += '-';
				}
				minusSeen = true; 
				break;
			case '0':
			case '０':
				number += '0'; numberSeen = true; break;
			case '1':
			case '１':
				number += '1'; numberSeen = true; break;
			case '2':
			case '２':
				number += '2'; numberSeen = true; break;
			case '3':
			case '３':
				number += '3'; numberSeen = true; break;
			case '4':
			case '４':
				number += '4'; numberSeen = true; break;
			case '5':
			case '５':
				number += '5'; numberSeen = true; break;
			case '6':
			case '６':
				number += '6'; numberSeen = true; break;
			case '7':
			case '７':
				number += '7'; numberSeen = true; break;
			case '8':
			case '８':
				number += '8'; numberSeen = true; break;
			case '9':
			case '９':
				number += '9'; numberSeen = true; break;
			}
		}
		return number;
	}

	
	public static List<String> toNativeJapaneseNumber(String numberString, String counter, boolean tensed)
	{
		numberString = removePunctuation(numberString);
		if(numberString.length()>8)
		{
			throw new IllegalArgumentException("Native Japanese numbers usually only represent numbers 1-100000000");
		}
		int number = Integer.parseInt(numberString);
		if(number<1 || number>100000000)
		{
			throw new IllegalArgumentException("Native Japanese numbers usually only represent numbers 1-100000000");
		}
		return toNativeJapaneseNumber((int)number, counter, tensed);
	}
	public static List<String> toNativeJapaneseNumber(int number, String counter, boolean tensed)
	{
		if(number<1 || number>=100000000)
		{
			throw new IllegalArgumentException("Native Japanese numbers usually only represent numbers 0-10");
		}
		
		List<String> result = new ArrayList<String>();
		
		
		int tempNumber = number;
		String root = null;
		
		boolean hasYorodu = false;
		
		int n10_7 = tempNumber / 10000000;
		root = getNativeNumberRootOrNull(n10_7);
		if(root!=null)
		{
			result.add(root+"ち");
			hasYorodu = true;
		}
		tempNumber = tempNumber % 10000000;
		
		int n10_6 = tempNumber / 1000000;
		root = getNativeNumberRootOrNull(n10_6);
		if(root!=null)
		{
			if(n10_6==1)
			{
				result.add("もも");	
			}else{
				result.add(root+"お");
			}
			hasYorodu = true;
		}
		tempNumber = tempNumber % 1000000;
		
		int n10_5 = tempNumber / 100000;
		root = getNativeNumberTensOrNull(n10_5); //tens
		if(root!=null)
		{
			if(n10_5==1)
			{
				result.add("もも");	
			}else{
				result.add(root+"お");
			}
			hasYorodu = true;
		}
		tempNumber = tempNumber % 100000;
		
		int n10_4 = tempNumber / 10000;
		root = getNativeNumberRootOrNull(n10_4); //tens
		if(root!=null)
		{
			result.add(root);
			hasYorodu = true;
		}
		tempNumber = tempNumber % 10000;
		
		if(hasYorodu)
		{
			result.add("よろづ");
		}
		
		int n10_3 = tempNumber / 1000;
		root = getNativeNumberRootOrNull(n10_3);
		if(root!=null)
		{
				result.add(root+"ち");
		}
		tempNumber = tempNumber % 1000;
		
		int n10_2= tempNumber / 100;
		root = getNativeNumberRootOrNull(n10_2);
		if(root!=null)
		{
			if(n10_2==1)
			{
				result.add("もも");
			}else{
				result.add(root+"お");
			}
		}
		tempNumber = tempNumber % 100;
		
		int n10_1= tempNumber / 10;
		root = getNativeNumberTensOrNull(n10_1); //tens
		if(root!=null)
		{
			result.add(root);
		}
		tempNumber = tempNumber % 10;
		
		if(result.size()!=0)
		{
			result.add("あまり");
		}
		
		switch(tempNumber)
		{
		case 1:result.add("ひと"+counter);break;
		case 2:result.add("ふた"+counter);break;
		case 3:
			if(tensed)
			{
				result.add("みっ"+counter);
			}else{
				result.add("み"+counter);
			}
			break;
		case 4:
			if(tensed)
			{
				result.add("よっ"+counter);
			}else{
				result.add("よ"+counter);
			}
			break;
		case 5:
			if(counter.equals("つ"))
			{
				result.add("いつつ");	
			}else{
				result.add("い"+counter);
			}
			break;
		case 6:
			if(tensed)
			{
				result.add("むっ"+counter);
			}else{
				result.add("む"+counter);
			}
			break;
		case 7: result.add("なな"+counter); break;
		case 8: 
			if(tensed)
			{
				result.add("やっ"+counter); //urusei
			}else{
				result.add("や"+counter);
			}
			break;
		case 9: result.add("ここの"+counter); break;
		default: throw new IllegalArgumentException("Entry condition into the function not respected, "+number);
		}
		
		if(result.size()==0)
		{
			result.add("ゼロ"); //should not happen, but might need to be spelled out
		}
		return result;
	}
	
	private static String getNativeNumberRootOrNull(int number)
	{
		switch(number)
		{
		case 0:return null;
		case 1:return ""; //no stem to the word, but the counting word will be present
		case 2:return "ふた";
		case 3:return "み";
		case 4:return "よ";
		case 5:return "い";
		case 6:return "む";
		case 7:return "なな";
		case 8:return "や";
		case 9:return "ここの";
		default: throw new IllegalArgumentException("Entry condition into the function not respected, "+number);
		}
	}
	
	private static String getNativeNumberTensOrNull(int number)
	{
		switch(number)
		{
		case 0:return null;
		case 1:return "とう";
		case 2:return "はたち";
		case 3:return "みそじ";
		case 4:return "よそじ";
		case 5:return "いそじ";
		case 6:return "むそじ";
		case 7:return "ななそじ";
		case 8:return "やそじ";
		case 9:return "ここのそじ";
		default: throw new IllegalArgumentException("Entry condition into the function not respected, "+number);
		}
	}
	
	

	//there is no function to expand numbers in Kanji (Sino-Japanese characters)
	//because this is a kanji translation only, no need to insert number words
	//use removePunctuation to remove non number characters
	public static List<String> toSinoJapaneseNumber(long number, CounterWord counterWord){
		return toSinoJapaneseNumber(number+"", counterWord);
	}
	public static List<String> toSinoJapaneseNumber(String numberString){
		return toSinoJapaneseNumber(Long.parseLong(numberString), null);
	}
	public static List<String> toSinoJapaneseNumber(long number){
		return toSinoJapaneseNumber(number+"", null);
	}
	public static List<String> toSinoJapaneseNumber(String numberString, CounterWord counterWord)
	{
		numberString = removePunctuation(numberString);
		
		List<String> result = new ArrayList<String>();
		
		if(numberString.startsWith("-"))
		{
			result.add("マイナス");// 'minus'
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
			case '0':
			case '０':break;
			case '1':
			case '１':
				switch(orderInMyriad)
				{
				case 0: result.add("いち"); break;
				case 1: result.add("じゅう"); break;//10s
				case 2: result.add("ひゃく"); break;//100s
				case 3: result.add("せん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			case '2':
			case '２':
				result.add("に"); 
				switch(orderInMyriad)
				{
				case 0: break;
				case 1: result.add("じゅう"); break;//10s
				case 2: result.add("ひゃく"); break;//100s
				case 3: result.add("せん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			case '3':
			case '３':
				result.add("さん"); 
				switch(orderInMyriad)
				{
				case 0: break;
				case 1: result.add("じゅう"); break;//10s
				case 2: result.add("びゃく"); break;//100s
				case 3: result.add("ぜん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			
			case '4':
			case '４':
				result.add("よん"); 
				switch(orderInMyriad)
				{
				case 0: break;
				case 1: result.add("じゅう"); break;//10s
				case 2: result.add("ひゃく"); break;//100s
				case 3: result.add("せん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			case '5':
			case '５':
				result.add("ご"); 
				switch(orderInMyriad)
				{
				case 0: break;
				case 1: result.add("じゅう"); break;//10s
				case 2: result.add("ひゃく"); break;//100s
				case 3: result.add("せん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			case '6': 
			case '６':
				switch(orderInMyriad)
				{
				case 0: result.add("ろく"); break;
				case 1: result.add("ろく"); result.add("じゅう"); break;//10s
				case 2: result.add("ろっぴゃく"); break;//100s
				case 3: result.add("ろく"); result.add("せん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			case '7':
			case '７':
				result.add("なな"); 
				switch(orderInMyriad)
				{
				case 0: break;
				case 1: result.add("じゅう"); break;//10s
				case 2: result.add("ひゃく"); break;//100s
				case 3: result.add("せん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			case '8':
			case '８':
				switch(orderInMyriad)
				{
				case 0: result.add("はち");break;
				case 1: result.add("はち");result.add("じゅう"); break;//10s
				case 2: result.add("はっぴゃく"); break;//100s
				case 3: result.add("はっせん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			case '9':
			case '９':
				result.add("きゅう");
				switch(orderInMyriad)
				{
				case 0: break;
				case 1: result.add("じゅう"); break;//10s
				case 2: result.add("ひゃく"); break;//100s
				case 3: result.add("せん"); break;//1000s
				default: throw new IllegalArgumentException("Loop invariant not respected, "+orderInMyriad+" "+numberString);
				}
				break;
			default: throw new IllegalArgumentException("Entry condition into the function not respected, "+numberString);
			}
			
			if(orderInMyriad==0)
			{
				if(currentMyriadHasDigit)
				{
					switch(orderOfMyriad)
					{
					case 0: break;
					case 1: result.add("まん"); break; //10^4 万
					case 2: result.add("おく"); break; //10^8 億
					case 3: result.add("ちょう"); break; //10^12 兆
					case 4: result.add("けい"); break; //10^16 京
					case 5: result.add("がい"); break; //10^20 該
					case 6: result.add("じょ"); break; //10^24 秭, alternatively 𥝱　し
					case 7: result.add("じょう"); break; //10^28 穣
					case 8: result.add("こう"); break; //10^32 溝
					case 9: result.add("かん"); break; //10^36 澗
					case 10: result.add("せい"); break; //10^40 正 
					case 11: result.add("さい"); break; //10^44 載
					case 12: result.add("こく"); break; //10^48 極
					case 13: result.add("こうがしゃ"); break; //10^52 恒河沙
					case 14: result.add("あそうぎ"); break; //10^56 阿僧祇
					case 15: result.add("なゆた"); break; //10^60 那由他
					case 16: result.add("ふかしぎ"); break; //10^64 不可思議
					case 17: result.add("むりょうたいすう"); break; //10^68 無量大數
					
//	                  for negative powers of ten, TODO?
//					  -1   分　　　    bun
//				      -2   厘　　　    rin
//				      -3   毛　　　    mou
//				      -4   糸　　　    shi
//				      -5   忽　　　    kotsu
//				      -6   微　　　    bi
//				      -7   繊　　　    sen
//				      -8   沙　　　    sha
//				      -9   塵　　　    jin
//				     -10   埃　　　    ai
//				     -11   渺　　　    byou
//				     -12   漠　　　    baku
//				     -13   模糊　　    moko
//				     -14   逡巡　　    shunjun
//				     -15   須臾　　    shuyu
//				     -16   瞬息　　    shunsoku
//				     -17   弾指　　    danshi
//				     -18   刹那　　    setsuna
//				     -19   六徳　　    rittoku
//				     -20   空虚　　    kuukyo
//				     -21   清浄　　    seijou

					default: 
						result.clear();
						result.add("かぞえきれない");
						if(counterWord!=null)
						{
							result.add(counterWord.getHiragana());
						}
						return result;
					}
				}
				currentMyriadHasDigit = false;
			}
		}
		
		if(result.size()==0)
		{
			result.add("ゼロ");
		}
		
		//tensification
		if(counterWord!=null)
		{
			String hiraganaCounter = counterWord.getHiragana();
			
			String lastResultWord = result.get(result.size()-1);
			String replacement = lastResultWord;
			
			switch(counterWord.getWordBeginType())
			{
			case PType:
			case KType:
				if(lastResultWord.equals("ろく"))
				{
					replacement = "ろっ";
				}else if(lastResultWord.equals("ひゃく")){
					replacement = "ひゃっ";
				}
				//fall through to S/T type to handle 1,8,10
			case SType:
			case TType:
				if(lastResultWord.equals("いち"))
				{
					replacement = "いっ";
				}else if(lastResultWord.equals("はち")){
					replacement = "はっ";
				}else if(lastResultWord.equals("じゅう")){
					replacement = "じゅっ"; //alternatively, じっ
				}
				break;
			case FUType:
				if(lastResultWord.equals("いち")){
					replacement = "いっ";
					hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("さん")){
					hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("よん")){
					hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("ろく")){
					replacement = "ろっ";
					hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("はち")){
				    replacement = "はっ";
				    hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("じゅう")){
					replacement = "じゅっ"; //alternatively, じっ
					hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("ひゃく")){
					replacement = "ひゃっ";
					hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("せん")){
					hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("まん")){
					hiraganaCounter = "ぷ"+hiraganaCounter.substring(1);
				}
				break;
			case HAType:
			case HIType:
			case HEType:
			case HOType:
				String start3_1000_10000;
				String start1_4_6_8_10_100;
				switch(counterWord.wordBeginType)
				{
				case HAType: start1_4_6_8_10_100 = "ぱ";start3_1000_10000 = "ば"; break;
				case HIType: start1_4_6_8_10_100 = "ぴ";start3_1000_10000 = "び"; break;
				case HEType: start1_4_6_8_10_100 = "ぺ";start3_1000_10000 = "べ"; break;
				case HOType: start1_4_6_8_10_100 = "ぽ";start3_1000_10000 = "ぼ"; break;
				default: throw new IllegalArgumentException("Case above modified");
				}
				if(lastResultWord.equals("いち")){
					replacement = "いっ";
					hiraganaCounter = start1_4_6_8_10_100+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("さん")){
					hiraganaCounter = start3_1000_10000+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("よん")){
					hiraganaCounter = start1_4_6_8_10_100+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("ろく")){
					replacement = "ろっ";
					hiraganaCounter = start1_4_6_8_10_100+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("はち")){
				    replacement = "はっ";
				    hiraganaCounter = start1_4_6_8_10_100+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("じゅう")){
					replacement = "じゅっ"; //alternatively, じっ
					hiraganaCounter = start1_4_6_8_10_100+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("ひゃく")){
					replacement = "ひゃっ";
					hiraganaCounter = start1_4_6_8_10_100+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("せん")){
					hiraganaCounter = start3_1000_10000+hiraganaCounter.substring(1);
				}else if(lastResultWord.equals("まん")){
					hiraganaCounter = start3_1000_10000+hiraganaCounter.substring(1);
				}
				break;
			default:
				//keep the last digit's word and hiragana counter
			}
			result.set(result.size()-1, replacement);
			result.add(hiraganaCounter);
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
						result.add("マイナス");// 'minus'
						result.add("れい");
					}else if(integerPart.length()==0) { // .314
						result = new ArrayList<String>();
						result.add("れい"); 
					}else{
						result = toSinoJapaneseNumber(integerPart, null);
					}
				}else{
					integerPart += numberString.charAt(i);
				}
			}else{
				if(!dotWasOutput)
				{
					result.add("てん");
					dotWasOutput = true;
				}
				switch(numberString.charAt(i))
				{
				case '0':
				case '０':
					result.add("れい"); break;
				case '1':
				case '１':
					result.add("いち"); break;
				case '2':
				case '２':
					result.add("に"); break;
				case '3':
				case '３':
					result.add("さん"); break;
				case '4':
				case '４':
					result.add("よん"); break;
				case '5':
				case '５':
					result.add("ご"); break;
				case '6':
				case '６':
					result.add("ろく"); break;
				case '7':
				case '７':
					result.add("なな"); break;
				case '8':
				case '８':
					result.add("はち"); break;
				case '9':
				case '９':
					result.add("きゅう"); break;
				default: break;				
				}
			}
		}
		if(result==null)
		{
			result = new ArrayList<String>();
			result.add("れい");
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

		for(int i=0;i<numberString.length();i++)
		{
			switch(numberString.charAt(i))
			{
			case '0':
			case '０':
				result.add("ゼロ"); break;
			case '1':
			case '１':
				result.add("いち"); break;
			case '2':
			case '２':
				result.add("に"); break;
			case '3':
			case '３':
				result.add("さん"); break;
			case '4':
			case '４':
				result.add("よん"); break;
			case '5':
			case '５':
				result.add("ご"); break;
			case '6':
			case '６':
				result.add("ろく"); break;
			case '7':
			case '７':
				result.add("なな"); break;
			case '8':
			case '８':
				result.add("はち"); break;
			case '9':
			case '９':
				result.add("きゅう"); break;
			default: throw new IllegalArgumentException("Long to string should not yield characters outside of range 0-9: '"+numberString+"'");
			}
		}
		return result;
	}
	
	public static void main(String[] args)
	{
		System.out.println(toWords("13441541","回"));
		System.out.println(toWords("137654","つ"));
		System.out.println(toWords("13441541","台"));
		System.out.println(toWords("137654","ぱく"));
		
		//exceptions
		System.out.println(toWords("20","日"));
		System.out.println(toWords("０","日"));
		System.out.println(toWords("0","日"));
		System.out.println(toWords("8","日"));
		System.out.println(toWords("1","人"));
		System.out.println(toWords("13","人"));
		System.out.println(toWords("5","つ"));
		System.out.println(toWords("1","つ"));
		System.out.println(toWords("9","つ"));
		
	}
}
