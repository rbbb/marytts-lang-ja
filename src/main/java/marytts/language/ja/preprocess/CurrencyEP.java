/**
 * Copyright 2002 DFKI GmbH.
 * Copyright 2014 Han Seunghee
 * Uses modified currency data from wikipedia, so probably available under CC-BY-SA as well.
 *  
 * All Rights Reserved.  Use is subject to license terms.
 *
 * This file is part of MARY TTS.
 *
 * MARY TTS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package marytts.language.ja.preprocess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import marytts.util.MaryUtils;
import marytts.util.dom.MaryDomUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * based on the original currency EP
 */

public class CurrencyEP extends ExpansionPattern
{
    private final String[] _knownTypes = {
        "currency"
    };
    /**
     * Every subclass has its own list knownTypes, 
     * an internal string representation of known types.
     * These are possible values of the <code>type</code> attribute to the
     * <code>say-as</code> element, as defined in MaryXML.dtd.
     * If there is more than one known type, the first type
     * (<code>knownTypes[0]</code>) is expected to be the most general one,
     * of which the others are specialisations.
     */
    private final List<String> knownTypes = Arrays.asList(_knownTypes);
    public List<String> knownTypes() { return knownTypes; }

    private final String[] _currencySymbolNames = {
        new Character((char)8364).toString(), "ユーロ",
         "$", "ドル",
        new Character((char)165).toString(), "エン",
        new Character((char)163).toString(), "UKポンド",
        "sFr.", "スイス・フラン",
        "Kr.", "コルナ",
        "Euro", "ユーロ",
        
        //from http://ja.wikipedia.org/wiki/ISO_4217
        "AED","UAEディルハム",
        "AFN","アフガニ",
        "ALL","レク",
        "AMD","ドラム",
        "ANG","アンティル・ギルダー",
        "AOA","クワンザ",
        "ARS","アルゼンチン・ペソ",
        "AUD","オーストラリア・ドル",
        "AWG","アルバ・フロリン",
        "AZN","アゼルバイジャン・マナト",
        "BAM","ダカンマルク",
        "BBD","バルバドス・ドル",
        "BDT","タカ",
        "BGN","レフ",
        "BHD","バーレーン・ディナール",
        "BIF","ブルンジ・フラン",
        "BMD","バミューダ・ドル",
        "BND","ブルネイ・ドル",
        "BOB","ボリビアーノ",
        "BRL","レアル",
        "BSD","バハマ・ドル",
        "BTN","ニュルタム",
        "BWP","プラ",
        "BYR","ベラルーシ・ルーブル",
        "BZD","ベリーズ・ドル",
        "CAD","カナダ・ドル",
        "CDF","コンゴ・フラン",
        "CHF","スイス・フラン",
        "CLP","チリ・ペソ",
        "CNY","ジンミンゲン",
        "COP","コロンビア・ペソ",
        "CRC","コスタリカ・コロン",
        "CSD","セルビア・ディナール",
        "CUC","ダカンペソ",
        "CUP","キューバ・ペソ",
        "CVE","カーボベルデ・エスクード",
        "CZK","チェコ・コルナ",
        "DJF","ジブチ・フラン",
        "DKK","デンマーク・クローネ",
        "DOP","ドミニカ・ペソ",
        "DZD","アルジェリア・ディナール",
        "EGP","エジプト・ポンド",
        "ERN","ナクファ",
        "ETB","ブル",
        "EUR","ユーロ",
        "FJD","フィジー・ドル",
        "FKP","フォークランドショトウ・ポンド",
        "GBP","UKポンド",
        "GEL","ラリ",
        "GGP","ガーンジー・ポンド",
        "GHS","セディ",
        "GIP","ジブラルタル・ポンド",
        "GMD","ダラシ",
        "GNF","ギニア・フラン",
        "GTQ","ケツァル",
        "GYD","ガイアナ・ドル",
        "HKD","ホンコンドル",
        "HNL","レンピラ",
        "HRK","クーナ",
        "HTG","グールド",
        "HUF","フォリント",
        "IDR","ルピア",
        "ILS","シンシェケル",
        "INR","インド・ルピー",
        "IQD","イラク・ディナール",
        "IRR","イラン・リヤル",
        "ISK","アイスランド・クローナ",
        "JMD","ジャマイカ・ドル",
        "JOD","ヨルダン・ディナール",
        "JPY","エン",
        "KES","ケニア・シリング",
        "KGS","キルギス・ソム",
        "KHR","リエル",
        "KMF","コモロ・フラン",
        "KPW","ウォン",
        "KRW","ウォン",
        "KWD","クウェート・ディナール",
        "KYD","ケイマンショトウ・ドル",
        "KZT","テンゲ",
        "LAK","キープ",
        "LBP","レバノン・ポンド",
        "LKR","スリランカ・ルピー",
        "LRD","リベリア・ドル",
        "LSL","ロチ",
        "LTL","リタス",
        "LYD","リビア・ディナール",
        "MAD","モロッコ・ディルハム",
        "MDL","モルドバ・レウ",
        "MGA","アリアリ",
        "MKD","マケドニア・ディナール",
        "MMK","チャット",
        "MNT","トゥグルグ",
        "MOP","パタカ",
        "MRO","ウギア",
        "MUR","モーリシャス・ルピー",
        "MVR","ルフィヤ",
        "MWK","マラウイ・クワチャ",
        "MXN","メキシコ・ペソ",
        "MYR","リンギット",
        "MZN","メティカル",
        "NAD","ナミビア・ドル",
        "NGN","ナイラ",
        "NIO","ニカラグア・コルドバ",
        "NOK","ノルウェー・クローネ",
        "NPR","ネパール・ルピー",
        "NZD","ニュージーランド・ドル",
        "OMR","オマーン・リアル",
        "PAB","バルボア",
        "PEN","ヌエボ・ソル",
        "PGK","キナ",
        "PHP","フィリピン・ペソ",
        "PKR","パキスタン・ルピー",
        "PLN","ズウォティ",
        "PYG","グアラニー",
        "QAR","カタール・リヤル",
        "RON","シンルーマニア・レウ",
        "RUB","ロシア・ルーブル",
        "RWF","ルワンダ・フラン",
        "SAR","サウジアラビア・リヤル",
        "SBD","ソロモンショトウドル",
        "SCR","セーシェル・ルピー",
        "SDG","スーダン・ポンド",
        "SEK","スウェーデン・クローナ",
        "SGD","シンガポール・ドル",
        "SHP","セントヘレナ・ポンド",
        "SLL","レオン",
        "SOS","ソマリア・シリング",
        "SRD","スリナム・ドル",
        "SSP","ミナミスーダン・ポンド",
        "STD","ドブラ",
        "SVC","サルバドール・コロン",
        "SYP","シリア・ポンド",
        "SZL","リランゲニ",
        "THB","バーツ",
        "TJS","ソモニ",
        "TMT","トルクメニスタン・マナト",
        "TND","チュニジア・ディナール",
        "TOP","パアンガ",
        "TRY","シントルコリラ",
        "TTD","トリニダード・トバゴ・ドル",
        "TWD","ニュータイワンドル",
        "TZS","タンザニア・シリング",
        "UAH","フリヴニャ",
        "UGX","ウガンダ・シリング",
        "USD","ドル",
        "UYU","ウルグアイ・ペソ",
        "UZS","スム",
        "VEF","ボリバル",
        "VND","ドン",
        "VUV","バツ",
        "WST","タラ",
        "XAF","CFAフラン",
        "XAG","シロガネ",
        "XAU","コガネ",
        "XCD","ヒガシカリブ・ドル",
        "XDR","トクベツヒキダシケン",
        "XOF","CFAフラン",
        "XPD","パラジウム",
        "XPF","CFPフラン",
        "XPT","ハッキン",
        "XTS","テスト用コード",
        "YER","イエメン・リアル",
        "ZAR","ランド",
        "ZMK","ザンビア・クワチャ",
        "ZWD","ジンバブエ・ドル",
   
        //currencies no longer used (including pre-euro european)
        "ADP","アンドラ・ペセタ",
        "AFA","アフガニ",
        "AON","シンクワンザ",
        "ATS","オーストリア・シリング",
        "BEF","ベルギー・フラン",
        "BGL","レフ",
        "CSK","チェコスロバキア・コルナ",
        "CYP","キプロス・ポンド",
        "DDM","ヒガシドイツ・マルク",
        "DEM","ドイツマルク",
        "ECS","スクレ",
        "EEK","クローン",
        "ESP","ペセタ",
        "FIM","フィンランド・マルッカ",
        "FRF","フランス・フラン",
        "GHC","セディ",
        "GRD","ドラクマ",
        "GWP","ギニアビサウ・ペソ",
        "IEP","アイルランド・ポンド",
        "ITL","イタリア・リラ",
        "LUF","ルクセンブルク・フラン",
        "LVL","ラッツ",
        "MGF","マダガスカル・フラン",
        "MTL","マルタ・リラ",
        "MXP","メキシコ・ペソ",
        "NLG","オランダ・ギルダー",
        "PLZ","ズウォティ",
        "PTE","ポルトガル・エスクード",
        "ROL","キュウルーマニア・レウ",
        "RUR","ルーブル",
        "SDD","スーダン・ディナール",
        "SIT","トラール",
        "SKK","スロバキア・コルナ",
        "SRG","スリナム・ギルダー",
        "SUR","ルーブル",
        "TPE","ポルトガル領ティモール・エスクード",
        "TRL","トルコリラ",
        "VEB","ボリバル",
        "XEU","モトユロ",
        "YUD","ユーゴスラビア・ディナール",
        "YUM","ユーゴスラビア・ディナール",
        "ZMK","ザンビア・クワチャ",
        "ZRN","シンザイール",
        "ZRZ","ザイール",
        "ZWD","ジンバブエ・ドル",
        "ZWN","ジンバブエ・ドル",
        "ZWR","ジンバブエ・ドル",
    };
    private final Map<String, String> currencySymbolNames =
        MaryUtils.arrayToMap(_currencySymbolNames);
    // Domain-specific primitives:
    protected final String sCurrencySymbol = getCurrencySymbols();
    protected final String sCurrencyAmount =
        "(?:" + NumberEP.sInteger + "(?:[,.](?:-|[0-9][0-9]))?)";
    protected final String sCurrencyAmountSubstructure =
        "(?:(" + NumberEP.sInteger + ")(?:[,.](-|[0-9][0-9]))?)";
    // in this, first parenthesis are the wholes and second paren are the cents.
    // We don't use sMatchingChars here, but override isCandidate().

    // Now the actual match patterns:
    protected final Pattern reCurrencyLeading =
        Pattern.compile("(" + sCurrencySymbol + ")(" + sCurrencyAmount + ")");
    protected final Pattern reCurrencyTrailing =
        Pattern.compile("(" + sCurrencyAmount + ")(" + sCurrencySymbol + ")");
    protected final Pattern reCurrencyAmountSubstructure =
        Pattern.compile(sCurrencyAmountSubstructure);

    private final Pattern reMatchingChars = null;
    public Pattern reMatchingChars() { return reMatchingChars; }

    /**
     * Every subclass has its own logger.
     * The important point is that if several threads are accessing
     * the variable at the same time, the logger needs to be thread-safe
     * or it will produce rubbish.
     */
    //private Logger logger = MaryUtils.getLogger("CurrencyEP");


    // Only used to initialise sCurrencySymbol from _currencySymbolNames[]:
    private String getCurrencySymbols()
    {
        StringBuilder _sCurrencySymbol = new StringBuilder("(?:\\$");
        for (int i=0; i<_currencySymbolNames.length; i+=2) {
            if (!_currencySymbolNames[i].equals("$")) {
                // $ needs to be quoted in regular expression
                _sCurrencySymbol.append("|" + _currencySymbolNames[i]);
            }
        }
        _sCurrencySymbol.append(")");
        return _sCurrencySymbol.toString();
    }

    public CurrencyEP()
    {
        super();
    }

    protected boolean isCandidate(Element t)
    {
        String s = MaryDomUtils.tokenText(t);
        return (s.length() <= 4 ||
                number.isCandidate(t)||
                matchCurrency(s));
    }

    
    protected int canDealWith(String s, int type){
        return match(s, type);
    }
    
    
    protected int match(String s, int type)
    {
        switch (type) {
        case 0:
            if (matchCurrency(s)) return 0;
            break;
        }
        return -1;
    }

    protected List<Element> expand(List<Element> tokens, String s, int type)
    {
        if (tokens == null) 
            throw new NullPointerException("Received null argument");
        if (tokens.isEmpty()) 
            throw new IllegalArgumentException("Received empty list");
        Document doc = ((Element)tokens.get(0)).getOwnerDocument();
        // we expect type to be one of the return values of match():
        List<Element> expanded = null;
        switch (type) {
        case 0:
            expanded = expandCurrency(doc, s);
            break;
        }
        replaceTokens(tokens, expanded);
        return expanded;
    }

    private boolean matchCurrency(String s)
    {
        return reCurrencyLeading.matcher(s).matches() || reCurrencyTrailing.matcher(s).matches();
    }

    protected List<Element> expandCurrency(Document doc, String s)
    {
        ArrayList<Element> exp = new ArrayList<Element>();
	StringBuilder sb = new StringBuilder();
        String currency = null;
        String amount = null;
        Matcher reMatcher = reCurrencyLeading.matcher(s);
        if (reMatcher.find()) { // OK, matched
            currency = reMatcher.group(1);
            amount = reMatcher.group(2);
        } else {
            reMatcher = reCurrencyTrailing.matcher(s);
            if (!reMatcher.find()) return null;
            amount = reMatcher.group(1);
            currency = reMatcher.group(2);
        }
        // Now in amount, find wholes and cents:
        reMatcher = reCurrencyAmountSubstructure.matcher(amount);
        reMatcher.find();
        String wholes = reMatcher.group(1);

        sb.append(number.expandInteger(wholes));
        sb.append(" ");
        String currencyName = (String)currencySymbolNames.get(currency);
        sb.append(currencyName);
        
        String cents = reMatcher.group(2);
        if (cents != null && cents.length() > 0 && !cents.equals("-")) {
            // OK, cents are two digits
            sb.append(" ");
            sb.append(number.expandInteger(cents));
        }
        exp.addAll(makeNewTokens(doc, sb.toString(), true, s));
        return exp;
    }


}
