/**
 * Copyright 2002 DFKI GmbH.
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

package marytts.language.ko.preprocess;

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
 * An expansion pattern implementation for currency patterns.
 *
 * @author Marc Schr&ouml;der
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
        "DM", "독일 마르크",
        new Character((char)8364).toString(), "유로",
         "$", "달러",
        "FF", "프랑스 프랑",
        new Character((char)165).toString(), "일본 엔",
        new Character((char)163).toString(), "파운드",
        "sFr.", "스위스 프랑",
        "Kr.", "크로네",
        "ATS", "오스트리아 실링",
        "BEF", "벨기에 프랑",
        "GBP", "파운드 스털링",
        // avoid "CAD", "kanadische Dollar", because of Computer aided design
        "DKK", "덴마크 크로네",
        "NLG", "네덜란드 휠던",
        "EUR", "유로",
        "Euro", "유로",
        "FRF", "프랑스 프랑",
        "DEM", "독일 마르크",
        "GRD", "그리스 드라크마",
        "IEP", "아일랜드 파운드",
        "ITL", "이탈리아 리라",
        "LUF", "룩셈부르크 프랑",
        // avoid PLZ polish Zloty because of PLZ Postleitzahl
        "PTE", "포르투갈 이스쿠두",
        "ESP", "스페인 페세타",
        "SEK", "스웨덴 크로나",
        
        //from http://ko.wikipedia.org/wiki/ISO_4217
        "AED", "아랍에미리트 디르함",
        "AFN", "아프가니스탄 아프가니",
        "ALL", "알바니아 렉",
        "AMD", "아르메니아 드람",
        "ANG", "네덜란드령 안틸레스 휠던",
        "AOA", "앙골라 콴자",
        "ARS", "아르헨티나 페소",
        "AUD", "오스트레일리아 달러",
        "AWG", "아루바 플로린",
        "AZN", "아제르바이잔 마나트",
        "BAM", "보스니아 헤르체고비나 태환 마르크",
        "BBD", "바베이도스 달러",
        "BDT", "방글라데시 타카",
        "BGN", "불가리아 레프",
        "BHD", "바레인 디나르",
        "BMD", "버뮤다 달러",
        "BND", "브루나이 링깃",
        "BOB", "볼리비아 볼리비아노",
        "BRL", "브라질 헤알",
        "BSD", "바하마 달러",
        "BTN", "부탄 눌트럼",
        "BWP", "보츠와나 풀라",
        "BYR", "벨라루스 루블",
        "BZD", "벨리즈 달러",
        "CAD", "캐나다 달러",
        "CDF", "콩고 프랑",
        "CHF", "스위스 프랑",
        "CLP", "칠레 페소",
        "CNY", "런민비 (위안)",
        "COP", "콜롬비아 페소",
        "CRC", "코스타리카 콜론",
        "CUC", "쿠바 태환 페소",
        "CUP", "쿠바 페소",
        "CVE", "카보베르데 에스쿠도",
        "CZK", "체코 코루나",
        "DJF", "지부티 프랑",
        "DKK", "덴마크 크로네",
        "DOP", "도미니카 페소",
        "DZD", "알제리 디나르",
        "EGP", "이집트 파운드",
        "ERN", "에리트레아 낙파",
        "ETB", "에티오피아 비르",
        "EUR", "유로",
        "FJD", "피지 달러",
        "FKP", "포클랜드 제도 파운드",
        "GBP", "영국 파운드",
        "GEL", "그루지야(조지아) 라리",
        "GHS", "가나 세디",
        "GIP", "지브롤터 파운드",
        "GMD", "감비아 달라시",
        "GNF", "기니 프랑",
        "GTQ", "과테말라 케찰",
        "GYD", "가이아나 달러",
        "HKD", "홍콩 달러",
        "HNL", "온두라스 렘피라",
        "HRK", "크로아티아 쿠나",
        "HTG", "아이티 구르드",
        "HUF", "헝가리 포린트",
        "IDR", "인도네시아 루피아",
        "ILS", "이스라엘 신 셰켈",
        "INR", "인도 루피",
        "IQD", "이라크 디나르",
        "IRR", "이란 리알",
        "ISK", "아이슬란드 크로나",
        "JMD", "자메이카 달러",
        "JOD", "요르단 디나르",
        "JPY", "일본 엔",
        "KES", "케냐 실링",
        "KGS", "키르기스스탄 솜",
        "KHR", "캄보디아 리엘",
        "KMF", "코모로 프랑",
        "KPW", "조선민주주의인민공화국 원",
        "KRW", "대한민국 원",
        "KWD", "쿠웨이트 디나르",
        "KYD", "케이맨 제도 달러",
        "KZT", "카자흐스탄 텡게",
        "LAK", "라오스 킵",
        "LBP", "레바논 파운드",
        "LKR", "스리랑카 루피",
        "LRD", "라이베리아 달러",
        "LSL", "레소토 로티",
        "LTL", "리투아니아 리타스",
        "LYD", "리비아 디나르",
        "MAD", "모로코 디르함",
        "MDL", "몰도바 레우",
        "MGA", "마다가스카르 아리아리",
        "MKD", "마케도니아 데나르",
        "MMK", "미얀마 챠트",
        "MNT", "몽골 투그릭",
        "MOP", "마카오 파타카",
        "MRO", "모리타니 우기야",
        "MUR", "모리셔스 루피",
        "MVR", "몰디브 루피야",
        "MWK", "말라위 콰차",
        "MXN", "멕시코 페소",
        "MYR", "말레이시아 링깃",
        "MZN", "모잠비크 메티칼",
        "NAD", "나미비아 달러",
        "NGN", "나이지리아 나이라",
        "NIO", "니카라과 코르도바",
        "NOK", "노르웨이 크로네",
        "NPR", "네팔 루피",
        "NZD", "뉴질랜드 달러",
        "OMR", "오만 리알",
        "PAB", "파나마 발보아",
        "PEN", "페루 누에보 솔",
        "PGK", "파푸아 뉴기니 키나",
        "PHP", "필리핀 페소",
        "PKR", "파키스탄 루피",
        "PLN", "폴란드 즈워티",
        "PYG", "파라과이 과라니",
        "QAR", "카타르 리얄",
        "RON", "루마니아 레우",
        "RSD", "세르비아 디나르",
        "RUB", "러시아 루블",
        "RWF", "르완다 프랑",
        "SAR", "사우디아라비아 리얄",
        "SBD", "솔로몬 제도 달러",
        "SCR", "세이셸 루피",
        "SDG", "수단 파운드",
        "SEK", "스웨덴 크로나",
        "SGD", "싱가포르 달러",
        "SHP", "세인트헬레나 파운드",
        "SLL", "시에라리온 레온",
        "SOS", "소말리아 실링",
        "SRD", "수리남 달러",
        "SSD", "남수단 파운드",
        "STD", "상투메 프린시페 도브라",
        "SYP", "시리아 파운드",
        "SZL", "스와질란드 릴랑게니",
        "THB", "타이 밧",
        "TJS", "타지키스탄 소모니",
        "TMM", "투르크메니스탄 마나트",
        "TND", "튀니지 디나르",
        "TOP", "통가 파앙가",
        "TRY", "터키 리라",
        "TTD", "트리니다드 토바고 달러",
        "TWD", "신 타이완 달러",
        "TZS", "탄자니아 실링",
        "UAH", "우크라이나 흐리브냐",
        "UGX", "우간다 실링",
        "USD", "미국 달러",
        "UYU", "우루과이 페소",
        "UZS", "우즈베키스탄 솜",
        "VEF", "베네수엘라 볼리바르",
        "VND", "베트남 동",
        "VUV", "바누아투 바투",
        "WST", "사모아 탈라",
        "XAF", "CFA 프랑",
        "XAG", "은",
        "XAU", "금",
        "XCD", "동카리브 달러",
        "XDR", "특별 인출권",
        "XOF", "CFA 프랑",
        "XPD", "팔라듐",
        "XPF", "CFP 프랑",
        "XPT", "백금",
        "XTS", "테스트용 코드",
        "XXX", "통화 없음",
        "YER", "예멘 리알",
        "ZAR", "남아프리카 공화국 랜드",
        "ZMW", "잠비아 콰차",
        "ZWL", "짐바브웨 달러",

        
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
