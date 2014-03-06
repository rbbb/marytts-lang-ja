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
import java.util.Collections;
import java.util.List;


public class XSampa {
	
	public interface Phoneme
	{
		String getXSampaCode();
		String getIpaCode();
		String getName();
		String getExample();
	}

	private static ArrayList<Consonant> consonantsPrivate = new ArrayList<Consonant>();
	private static ArrayList<Vowel> vowelsPrivate = new ArrayList<Vowel>();
	static
	{
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Bilabial,ConsonantType.Plosive,"b","b","voiced bilabial plosive","English bed [bEd], French bon [bO~]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Bilabial,ConsonantType.Implosive,"b_<","ɓ","voiced bilabial implosive","Sindhi ɓarʊ [b_<arU]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Palatal,ConsonantType.Plosive,"c","c","voiceless Palatal plosive","Hungarian latyak [\"lQcQk]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Alveolar,ConsonantType.Plosive,"d","d","voiced Alveolar plosive","English dig [dIg], French doigt [dwa]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Retroflex,ConsonantType.Plosive,"d`","ɖ","voiced retroflex plosive","Swedish hord [hu:d`]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Alveolar,ConsonantType.Implosive,"d_<","ɗ","voiced Alveolar implosive","Sindhi ɗarʊ [d_<arU]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Labiodental,ConsonantType.Fricative,"f","f","voiceless Labiodental Fricative","English five [faIv], French femme [fam]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Velar,ConsonantType.Plosive,"g","g","voiced velar plosive","English game [geIm], French longue [lO~g]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Velar,ConsonantType.Implosive,"g_<","ɠ","voiced velar implosive","Sindhi ɠəro [g_<@ro]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Glottal,ConsonantType.Fricative,"h","h","voiceless glottal Fricative","English house [haUs]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Glottal,ConsonantType.Fricative,"h\\","ɦ","voiced glottal Fricative","Czech hrad [h\\rat]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Palatal,ConsonantType.Fricative,"j\\","ʝ","voiced Palatal Fricative","Greek γειά [j\\a]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Velar,ConsonantType.Plosive,"k","k","voiceless velar plosive","English scat [sk{t], Spanish carro [\"kar:o]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Alveolar,ConsonantType.LateralApproximant,"l","l","Alveolar Lateral approximant","English lay [leI], French mal [mal]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Retroflex,ConsonantType.LateralApproximant,"l`","ɭ","retroflex Lateral approximant","Svealand Swedish sorl [so:l`]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Alveolar,ConsonantType.LateralFlap,"l\\","ɺ","Alveolar Lateral flap","Japanese rakuten [l\\akM_0teN\\]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Bilabial,ConsonantType.Nasal,"m","m","bilabial nasal","English mouse [maUs], French homme [Om]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Alveolar,ConsonantType.Nasal,"n","n","Alveolar nasal","English nap [n{p], French non [nO~]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Retroflex,ConsonantType.Nasal,"n`","ɳ","retroflex nasal","Swedish hörn [h2:n`]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Bilabial,ConsonantType.Plosive,"p","p","voiceless bilabial plosive","English speak [spik], French pose [poz], Spanish perro [\"per:o]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Bilabial,ConsonantType.Fricative,"p\\","ɸ","voiceless bilabial Fricative","Japanese fuku [p\\M_0kM]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Uvular,ConsonantType.Plosive,"q","q","voiceless Uvular plosive","Arabic qasbah [\"qQs_Gba]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Alveolar,ConsonantType.Trill,"r","r","Alveolar Trill","Spanish perro [\"per:o]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Retroflex,ConsonantType.Flap,"r`","ɽ","retroflex flap"," "));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Alveolar,ConsonantType.Approximant,"r\\","ɹ","Alveolar approximant","English red [r\\Ed]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Retroflex,ConsonantType.Approximant,"r\\`","ɻ","retroflex approximant","Malayalam വഴി [\\\"v6r\\`i]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Alveolar,ConsonantType.Fricative,"s","s","voiceless Alveolar Fricative","English seem [si:m], French session [se\"sjO~]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Retroflex,ConsonantType.Fricative,"s`","ʂ","voiceless retroflex Fricative","Swedish mars [mas`]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.AlveoloPalatal,ConsonantType.Fricative,"s\\","ɕ","voiceless alveolo-Palatal Fricative","Polish świerszcz [s\\v'erStS]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Alveolar,ConsonantType.Plosive,"t","t","voiceless Alveolar plosive","English stew [stju:], French raté [Ra\"te], Spanish tuyo [\"tujo]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Retroflex,ConsonantType.Plosive,"t`","ʈ","voiceless retroflex plosive","Swedish mört [m2t`]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Labiodental,ConsonantType.Fricative,"v","v","voiced Labiodental Fricative","English vest [vEst], French voix [vwa]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.LabialVelar,ConsonantType.Approximant,"v\\ (or P)","ʋ","Labiodental approximant","Dutch west [v\\Est]/[PEst]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Labiodental,ConsonantType.Approximant,"w","w","labial-velar approximant","English west [wEst], French oui [wi]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Velar,ConsonantType.Fricative,"x","x","voiceless velar Fricative","Scots loch [lOx] or [5Ox]; German Buch, Dach; Spanish caja, gestión"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.PalatalVelar,ConsonantType.Fricative,"x\\","ɧ","voiceless Palatal-velar Fricative","Swedish sjal [x\\A:l]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Alveolar,ConsonantType.Fricative,"z","z","voiced Alveolar Fricative","English zoo [zu:], French azote [a\"zOt]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Retroflex,ConsonantType.Fricative,"z`","ʐ","voiced retroflex Fricative","Mandarin Chinese rang [z`aN]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.AlveoloPalatal,ConsonantType.Fricative,"z\\","ʑ","voiced alveolo-Palatal Fricative","Polish źrebak [\"z\\rEbak]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Bilabial,ConsonantType.Fricative,"B","β","voiced bilabial Fricative","Spanish lavar [la\"Ba4]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Bilabial,ConsonantType.Trill,"B\\","ʙ","bilabial Trill","Reminiscent of shivering (\"brrr\\\")"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Palatal,ConsonantType.Fricative,"C","ç","voiceless Palatal Fricative","German ich [IC], English human [\"Cjum@n] (broad transcription uses [hj-])"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Dental,ConsonantType.Fricative,"D","ð","voiced dental Fricative","English then [DEn]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Labiodental,ConsonantType.Nasal,"F","ɱ","Labiodental nasal","English emphasis [\"EFf@sIs] (spoken quickly, otherwise uses [Emf-])"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Velar,ConsonantType.Fricative,"G","ɣ","voiced velar Fricative","Greek γωνία [Go\"nia], Danish vælge [\"vElG@]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Uvular,ConsonantType.Plosive,"G\\","ɢ","voiced Uvular plosive","Inuktitut nirivvik [niG\\ivvik]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Uvular,ConsonantType.Plosive,"G\\_<","ʛ","voiced Uvular implosive","Mam ʛa [G\\_<a]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Epiglottal,ConsonantType.Fricative,"H\\","ʜ","voiceless epiglottal Fricative"," "));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Palatal,ConsonantType.Nasal,"J","ɲ","Palatal nasal","Spanish año [\"aJo], English canyon [\"k{J@n] (broad transcription uses [-nj-])"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Palatal,ConsonantType.Plosive,"J\\","ɟ","voiced Palatal plosive","Hungarian egy [EJ\\]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Palatal,ConsonantType.Implosive,"J\\_<","ʄ","voiced Palatal implosive","Sindhi ʄaro [J\\_<aro]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Alveolar,ConsonantType.LateralFricative,"K","ɬ","voiceless Alveolar Lateral Fricative","Welsh llaw [KaU]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Alveolar,ConsonantType.LateralFricative,"K\\","ɮ","voiced Alveolar Lateral Fricative"," "));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Palatal,ConsonantType.LateralApproximant,"L","ʎ","Palatal Lateral approximant","Italian famiglia [fa\"miLLa], Castilian llamar [La\"mar], English million [\"mIL@n] (broad transcription uses [-lj-])"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Velar,ConsonantType.LateralApproximant,"L\\","ʟ","velar Lateral approximant",""));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Velar,ConsonantType.Approximant,"M\\","ɰ","velar approximant","Spanish fuego [\"fweM\\o]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Velar,ConsonantType.Nasal,"N","ŋ","velar nasal","English thing [TIN]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Uvular,ConsonantType.Nasal,"N\\","ɴ","Uvular nasal","Japanese san [saN\\]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Labiodental,ConsonantType.Approximant,"P","ʋ","Labiodental approximant","Dutch west [PEst]/[v\\Est], allophone of English phoneme /r\\/"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Uvular,ConsonantType.Fricative,"R","ʁ","voiced Uvular Fricative","German rein [RaIn]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Uvular,ConsonantType.Trill,"R\\","ʀ","Uvular Trill","French roi [R\\wa]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Postalveolar,ConsonantType.Fricative,"S","ʃ","voiceless postAlveolar Fricative","English ship [SIp]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Dental,ConsonantType.Fricative,"T","θ","voiceless dental Fricative","English thin [TIn]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.LabialVelar,ConsonantType.Fricative,"W","ʍ","voiceless labial-velar Fricative","Scots when [WEn]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Uvular,ConsonantType.Fricative,"X","χ","voiceless Uvular Fricative","Klallam sχaʔqʷaʔ [sXa?q_wa?]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Pharyngeal,ConsonantType.Fricative,"X\\","ħ","voiceless pharyngeal Fricative","Arabic <ح>ha’ [X\\A:]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Postalveolar,ConsonantType.Fricative,"Z","ʒ","voiced postAlveolar Fricative","English vision [\"vIZ@n]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Alveolar,ConsonantType.Flap,"4","ɾ","Alveolar flap","Spanish pero [\"pe4o], American English better [\"bE4@`]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.VelarizedAlveolar,ConsonantType.LateralApproximant,"5","ɫ","velarized Alveolar Lateral approximant; also see _e","English milk [mI5k], Portuguese livro [\"5iv4u]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Glottal,ConsonantType.Stop,"?","ʔ","glottal stop","Danish stød [sd2?], Cockney English bottle [\"bQ?l]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Pharyngeal,ConsonantType.Fricative,"?\\","ʕ","voiced pharyngeal fricative","Arabic ع (`ayn) [?\\Ajn]"));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Epiglottal,ConsonantType.Fricative,"<\\","ʢ","voiced epiglottal fricative"," "));
		consonantsPrivate.add(new Consonant(true,ConsonantPlace.Epiglottal,ConsonantType.Plosive,">\\","ʡ","epiglottal plosive"," "));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.Palatal,ConsonantType.Approximant,"j","j","palatal approximant","English yes [jEs], French yeux [j2]"));
		consonantsPrivate.add(new Consonant(false,ConsonantPlace.LabioPalatal,ConsonantType.Approximant,"H","ɥ","labial-palatal approximant","French huit [Hit]"));
		for(Consonant c:consonantsPrivate)
		{
			c.preventFurtherModification();
		}

		
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.Open,VowelPosition.Front,"a","a","open front unrounded vowel","French dame [dam], Spanish padre [\"paD4e]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.CloseMid,VowelPosition.Front,"e","e","close-mid front unrounded vowel","French ses [se], English met [met] (AusE and NZE)"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.Close,VowelPosition.Front,"i","i","close front unrounded vowel","English be [bi:], French oui [wi], Spanish si [si]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.CloseMid,VowelPosition.Back,"o","o","close-mid back rounded vowel","French gros [gRo]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.Close,VowelPosition.Back,"u","u","close back rounded vowel","English boom [bu:m], Spanish su [su]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.Close,VowelPosition.Front,"y","y","close front rounded vowel","French tu [ty] German über [\"y:b6]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.Open,VowelPosition.Back,"A","ɑ","open back unrounded vowel","English father [\"fA:D@(r\\)] (RP and Gen.Am.)"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.OpenMid,VowelPosition.Front,"E","ɛ","open-mid front unrounded vowel","French même [mEm], English met [mEt] (RP and Gen.Am.)"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.NearClose,VowelPosition.NearFront,"I","ɪ","near-close near-front unrounded vowel","English kit [kIt]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.NearClose,VowelPosition.Central,"I\\","ᵻ or ɪ̈","near-close central unrounded vowel","Polish ryba [rI\\bA] "));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.Close,VowelPosition.Back,"M","ɯ","close back unrounded vowel","Korean 으 (eu)"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.Open,VowelPosition.BackMid,"O","ɔ","open-mid back rounded vowel","RP thought [TO:t], American English off [O:f]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.Open,VowelPosition.Back,"Q","ɒ","open back rounded vowel","RP lot [lQt]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.NearClose,VowelPosition.NearBack,"U","ʊ","near-close near-back rounded vowel","English foot [fUt]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.NearClose,VowelPosition.Central,"U\\","ᵿ or ʊ̈","near-close central rounded vowel","English euphoria [jU\\\"fO@r\\i@]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.OpenMid,VowelPosition.Back,"V","ʌ","open-mid back unrounded vowel","RP English strut [str\\Vt]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.NearClose,VowelPosition.NearFront,"Y","ʏ","near-close near-front rounded vowel","German hübsch [hYpS]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.Mid,VowelPosition.Central,"@","ə","schwa","English arena [@\"r\\i:n@]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.CloseMid,VowelPosition.Central,"@\\","ɘ","close-mid central unrounded vowel","Paicĩ kɘ̄ɾɘ [k@\\_M4@\\_M]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.NearOpen,VowelPosition.Front,"{","æ","near-open front unrounded vowel","English trap [tr\\{p]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.Close,VowelPosition.Central,"}","ʉ","close central rounded vowel","Swedish sju [x\\}:]; AuE/NZE boot [b}:t]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.Close,VowelPosition.Central,"1","ɨ","close central unrounded vowel","Welsh tu [t1], American English rose's [\"r\\oUz1z]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.CloseMid,VowelPosition.Front,"2","ø","close-mid front rounded vowel","Danish købe [\"k2:b@], French deux [d2]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.OpenMid,VowelPosition.Central,"3","ɜ","open-mid central unrounded vowel","English nurse [n3:s] (RP) or [n3`s] (Gen.Am.)"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.Open,VowelPosition.Central,"3\\","ɞ","open-mid central rounded vowel","Irish tomhail[t3\\:l']"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.NearOpen,VowelPosition.Central,"6","ɐ","near-open central vowel","German besser [\"bEs6], Australian English mud [m6d]"));
		vowelsPrivate.add(new Vowel(false,false,false,VowelOpenness.CloseMid,VowelPosition.Back,"7","ɤ","close-mid back unrounded vowel","Estonian kõik [k7ik], Vietnamese mơ [m7_M]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.CloseMid,VowelPosition.Central,"8","ɵ","close-mid central rounded vowel","Swedish buss [b8s]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.OpenMid,VowelPosition.Front,"9","œ","open-mid front rounded vowel","French neuf [n9f], Danish drømme [dR9m@]"));
		vowelsPrivate.add(new Vowel(false,false,true,VowelOpenness.Open,VowelPosition.Front,"&","ɶ","open front rounded vowel","Swedish skörd [x\\&d`]"));
		for(Vowel v:vowelsPrivate)
		{
			v.preventFurtherModification();
		}
	}
	public static List<Consonant> consonants = Collections.unmodifiableList(consonantsPrivate);
	public static List<Vowel> vowels = Collections.unmodifiableList(vowelsPrivate);
	
	public static Consonant findConsonantByIPA(String ipaCode)
	{
		for(Consonant consonant:consonants)
		{
			if(consonant.getIpaCode().equals(ipaCode))
			{
				return consonant;
			}
		}
		//return null;
		throw new IllegalArgumentException("Unknown IPA: "+ipaCode);
	}
	
	public static Vowel findVowelByIPA(String ipaCode)
	{
		for(Vowel vowel:vowels)
		{
			if(vowel.getIpaCode().equals(ipaCode))
			{
				return vowel;
			}
		}
		return null;
	}
	
	public enum ConsonantPlace
	{
		Bilabial,
		Labiodental,
		LabialVelar,
		Dental,
		VelarizedAlveolar,
		Alveolar,
		Postalveolar,
		Retroflex,
		AlveoloPalatal,
		Palatal,
		PalatalVelar,
		Velar,
		Uvular,
		Pharyngeal,
		Epiglottal,
		Glottal, 
		LabioPalatal, 
	}
	
	public enum ConsonantType
	{
		Plosive,
		Implosive,
		Fricative,
		LateralFricative,
		Approximant,
		LateralApproximant,
		Flap,
		LateralFlap,
		Nasal,
		Trill, 
		Stop
	}
	
	public static class Consonant implements Phoneme
	{
		private boolean noModification;
		
		private boolean voiced;
		private ConsonantPlace place;
		private ConsonantType type;
		private String xSampaCode;
		private String ipaCode;
		private String name;
		private String example;

		public Consonant(Consonant copiedConsonant)
		{
			this.noModification = false;
			this.voiced = copiedConsonant.voiced;
			this.place = copiedConsonant.place;
			this.type = copiedConsonant.type;
			this.xSampaCode = copiedConsonant.xSampaCode;
			this.ipaCode = copiedConsonant.ipaCode;
			this.name = copiedConsonant.name;
			this.example = copiedConsonant.example;
		}
		
		public Consonant(boolean voiced, ConsonantPlace place, ConsonantType type, String xSampaCode, String ipaCode, String name, String wikipediaExample)
		{
			this.noModification = true;
			this.voiced = voiced;
			this.place = place;
			this.type = type;
			this.xSampaCode = xSampaCode;
			this.ipaCode = ipaCode;
			this.name = name;
			this.example = wikipediaExample;
		}
		public boolean isVoiced() {
			return voiced;
		}
		public ConsonantPlace getPlace() {
			return place;
		}
		public ConsonantType getType() {
			return type;
		}
		public String getXSampaCode() {
			return xSampaCode;
		}
		public String getIpaCode() {
			return ipaCode;
		}
		public String getName() {
			return name;
		}
		public String getExample() {
			return example;
		}

		public void setVoiced(boolean voiced) {
			if(noModification){throw new IllegalArgumentException("This consonant cannot be modified, copy it or create a new one");}
			this.voiced = voiced;
		}

		public void setPlace(ConsonantPlace place) {
			if(noModification){throw new IllegalArgumentException("This consonant cannot be modified, copy it or create a new one");}
			this.place = place;
		}

		public void setType(ConsonantType type) {
			if(noModification){throw new IllegalArgumentException("This consonant cannot be modified, copy it or create a new one");}
			this.type = type;
		}

		public void setXSampaCode(String xSampaCode) {
			if(noModification){throw new IllegalArgumentException("This consonant cannot be modified, copy it or create a new one");}
			this.xSampaCode = xSampaCode;
		}

		public void setIpaCode(String ipaCode) {
			if(noModification){throw new IllegalArgumentException("This consonant cannot be modified, copy it or create a new one");}
			this.ipaCode = ipaCode;
		}

		public void setName(String name) {
			if(noModification){throw new IllegalArgumentException("This consonant cannot be modified, copy it or create a new one");}
			this.name = name;
		}

		public void setExample(String example) {
			if(noModification){throw new IllegalArgumentException("This consonant cannot be modified, copy it or create a new one");}
			this.example = example;
		}
		public void preventFurtherModification() {
			noModification = true;
		}
	}
	
	public enum VowelOpenness
	{
		Open,
		NearOpen,
		OpenMid,
		Mid,
		CloseMid,
		NearClose,
		Close
	}
	
	public enum VowelPosition
	{
		Front,
		NearFront,
		FrontMid,
		Central,
		BackMid,
		NearBack,
		Back
	}
	
	public static class Vowel implements Phoneme
	{
		private boolean noModification;
		private boolean nasalized;
		private boolean roundedness;
		private VowelOpenness openness;
		private VowelPosition position ;
		private String xSampaCode;
		private String ipaCode;
		private String name;
		private String example;
		private boolean longVowel;
		
		public Vowel(Vowel copiedVowel)
		{
			this.noModification = false;
			this.longVowel = copiedVowel.longVowel;
			this.nasalized = copiedVowel.nasalized;
			this.roundedness = copiedVowel.roundedness;
			this.openness = copiedVowel.openness;
			this.position = copiedVowel.position;
			this.xSampaCode = copiedVowel.xSampaCode;
			this.ipaCode = copiedVowel.ipaCode;
			this.name = copiedVowel.name;
			this.example = copiedVowel.example;
		}
		public Vowel(boolean longVowel, boolean nasalized, boolean roundedness, VowelOpenness openness, VowelPosition position, String xSampaCode, String ipaCode, String name, String example)
		{
			this.noModification = false;
			this.longVowel = longVowel;
			this.nasalized = nasalized;
			this.roundedness = roundedness;
			this.openness = openness;
			this.position = position;
			this.xSampaCode = xSampaCode;
			this.ipaCode = ipaCode;
			this.name = name;
			this.example = example;
		}
		
		public boolean isLongVowel() {
			return longVowel;
		}
		public boolean isNasalized() {
			return nasalized;
		}
		public boolean isRounded()
		{
			return roundedness;
		}
		public VowelOpenness getOpenness(){
			return openness;
		}
		public VowelPosition getPosition(){
			return position;
		}
		public String getXSampaCode() {
			return xSampaCode;
		}
		public String getIpaCode() {
			return ipaCode;
		}
		public String getName() {
			return name;
		}
		public String getExample() {
			return example;
		}
		public void setNasalized(boolean nasalized) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.nasalized = nasalized;
		}
		public void setRoundedness(boolean roundedness) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.roundedness = roundedness;
		}
		public void setOpenness(VowelOpenness openness) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.openness = openness;
		}
		public void setPosition(VowelPosition position) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.position = position;
		}
		public void setxSampaCode(String xSampaCode) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.xSampaCode = xSampaCode;
		}
		public void setIpaCode(String ipaCode) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.ipaCode = ipaCode;
		}
		public void setName(String name) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.name = name;
		}
		public void setExample(String example) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.example = example;
		}
		public void setLongVowel(boolean longVowel) {
			if(noModification){throw new IllegalArgumentException("This vowel cannot be modified, copy it or create a new one");}
			this.longVowel = longVowel;
		}	
		public void preventFurtherModification() {
			noModification = true;
		}
	}	
}
