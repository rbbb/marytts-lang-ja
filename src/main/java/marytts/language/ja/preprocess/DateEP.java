/**
 * Copyright 2002 DFKI GmbH.
 * Copyright 2014 Han Seunghee
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import marytts.util.MaryUtils;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * An expansion pattern implementation for date patterns.
 *
 * Modified from the German version of Marc Schr&ouml;der with
 * - removed all word abbreviations, month names are always numerical
 * - moved up the YYYY MM DD pattern as it's most common
 * - added expansion for day name
 */

public class DateEP extends ExpansionPattern
{
	//those date patterns are very rare in Japanese,
	//which usually abbreviates dates with kanji
	//such as ２００２年１２月２３日
	
	private final String[] _knownTypes = {
			"date",
			"date:dmy",
			"date:ymd",
			"date:dm",
			"date:my",
			"date:y",
			"date:m",
			"date:d",
			"date:mdy",
			"date:md"
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

	// Domain-specific primitives:
	protected final String sDay = "(?:[0０]?[1-9１-９]|[12１２][0-9０-９]|[3３][01０１])";
	protected final String sMonth = "(?:[0０][1-9１-９]|[1１][0-2０-２])";
	protected final String sYear = "(?:([0-9０-９][0-9０-９])?[0-9０-９][0-9０-９])";
	protected final String sAbbrevDayPattern = "(?:[月火水木金土日])";
	protected final String sDot = "[\\.]";
	protected final String sSlash = "[/]";
	protected final String sMinus = "[\\-]";
	protected final String sMatchingChars = "[0-9０-９/\\.\\w\\(\\)月火水木金土日]";
	protected final String sSeparators = (sDot + "|" + sSlash + "|" + sMinus);

	protected static Map<String, String> dayOfWeekExpansion = new HashMap<String, String>();
	static
	{
		dayOfWeekExpansion.put("月", "ゲツヨウビ");
		dayOfWeekExpansion.put("火", "カヨウビ");
		dayOfWeekExpansion.put("水", "スイヨウビ");
		dayOfWeekExpansion.put("木", "モクヨウビ");
		dayOfWeekExpansion.put("金", "キンヨウビ");
		dayOfWeekExpansion.put("土", "ドヨウビ");
		dayOfWeekExpansion.put("日", "ニチヨウビ");
	}

	// Now the actual match patterns:
	protected final Pattern reDOWYearMonthDay =
			Pattern.compile("(?:\\s|\\()*"+"(" + sAbbrevDayPattern + ")" + "(?:" + sDot + "|" + sSlash + "|\\s|\\))*" +
					"(" + sYear + ")" + "(?:" + sDot + "|" + sSlash + ")" +
					"(" + sMonth + ")" + "(?:" + sDot + "|" + sSlash + ")"+
					"(" + sDay + ")");
	protected final Pattern reYearMonthDayDOW =
			Pattern.compile("(" + sYear + ")" + "(?:" + sDot + "|" + sSlash + ")" +
					"(" + sMonth + ")" + "(?:" + sDot + "|" + sSlash + ")"+
					"(" + sDay + ")"+ "(?:" + sDot + "|" + sSlash + "|\\s|\\()*" +
					"(" + sAbbrevDayPattern + ")"+"(?:\\s|\\))*");//the list of allowed characters should prevent already expanded days of week tokens from being expanded again
	protected final Pattern reYearMonthDay =
			Pattern.compile("(" + sYear + ")" + "(?:" + sDot + "|" + sSlash + ")" +
					"(" + sMonth + ")" + "(?:" + sDot + "|" + sSlash + ")"+
					"(" + sDay + ")");
	protected final Pattern reDayMonthYear =
			Pattern.compile("(" + sDay + ")" + "(?:" + sDot + "|" + sSlash + ")" +
					"(" + sMonth + ")" + "(?:" + sDot + "|" + sSlash + ")"+
					"(" + sYear + ")");
	protected final Pattern reMonthDayYear =
			Pattern.compile("(" + sMonth + ")" + "(?:" + sSeparators + ")" +
					"(" + sDay + ")" + "(?:" + sSeparators + ")" +
					"(" + sYear + ")");

	protected final Pattern reDayMonth =
			Pattern.compile("(" + sDay + ")" + "(?:" + sDot + "|" + sSlash + ")" +
					"(" + sMonth + ")" + "(?:" + sDot + "|" + sSlash + ")");

	protected final Pattern reMonthDay =
			Pattern.compile("(" + sMonth + ")" + "(?:" + sSeparators + ")" +
					"(" + sDay + ")" + "(?:" + sSeparators + ")");

	protected final Pattern reMonthYear =
			Pattern.compile("(" + sMonth + ")(" + sSlash + "|" + sDot +
					")(" + sYear + ")");

	protected final Pattern reYear =
			Pattern.compile("(?:([0-9０-９][0-9０-９])?[0-9０-９][0-9０-９])");

	protected final Pattern reMonth =
			Pattern.compile("(" + sMonth + ")" + "(?:" + sDot + ")");

	protected final Pattern reDay =
			Pattern.compile("(" + sDay + ")" + "?:" + sDot);

	private final Pattern reMatchingChars = Pattern.compile(sMatchingChars);
	public Pattern reMatchingChars() { return reMatchingChars; }

	/**
	 * Every subclass has its own logger.
	 * The important point is that if several threads are accessing
	 * the variable at the same time, the logger needs to be thread-safe
	 * or it will produce rubbish.
	 */
	private Logger logger = MaryUtils.getLogger("DateEP");

	public DateEP()
	{
		super();
	}

	protected int canDealWith(String s, int type){
		return match(s, type);
	}


	protected int match(String s, int type)
	{
		switch (type) {
		case 0:
			if (matchDateDMY(s)) return 1;
			if (matchDateYMD(s)) return 2;
			if (matchDateDM(s)) return 3;
			// if (matchDateMY(s)) return 4;
			// if (matchDateMDY(s)) return 8;
			// if (matchDateMD(s)) return 9;
			// if (matchDateM(s)) return 6;
			// if (matchDateD(s)) return 7;
			// if (matchDateY(s)) return 5;
			break;
		case 1:
			if (matchDateDMY(s)) return 1;
			break;
		case 2:
			if (matchDateYMD(s)) return 2;
			break;
		case 3:
			if (matchDateDM(s)) return 3;
			break;
		case 4:
			if (matchDateMY(s)) return 4;
			break;
		case 5:
			if (matchDateY(s)) return 5;
			break;
		case 6:
			if (matchDateM(s)) return 6;
			break;
		case 7:
			if (matchDateD(s)) return 7;
			break;
		case 8:
			if (matchDateMDY(s)) return 8;
			break;
		case 9:
			if (matchDateMD(s)) return 9;
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
		case 1:
			expanded = expandDateDMY(doc, s);
			break;
		case 2:
			expanded = expandDateYMD(doc, s);
			break;
		case 3:
			expanded = expandDateDM(doc, s);
			break;
		case 4:
			expanded = expandDateMY(doc, s);
			break;
		case 5:
			expanded = expandDateYear(doc, s);
			break;
		case 6:
			expanded = expandDateMonth(doc, s);
			break;
		case 7:
			expanded = expandDateDay(doc, s);
			break;
		case 8:
			expanded = expandDateMDY(doc, s);
			break;
		case 9:
			expanded = expandDateMD(doc, s);
			break;
		}
		replaceTokens(tokens, expanded);
		return expanded;
	}

	protected boolean matchDateDMY(String s)
	{
		return reDayMonthYear.matcher(s).matches() ;
	}

	protected boolean matchDateYMD(String s)
	{
		return reYearMonthDay.matcher(s).matches() || reDOWYearMonthDay.matcher(s).matches() || reYearMonthDayDOW.matcher(s).matches();
	}

	protected boolean matchDateDM(String s)
	{
		return reDayMonth.matcher(s).matches() ;
	}

	protected boolean matchDateMY(String s)
	{
		return reMonthYear.matcher(s).matches();
	}

	protected boolean matchDateY(String s)
	{
		return reYear.matcher(s).matches();
	}

	protected boolean matchDateM(String s)
	{
		return reMonth.matcher(s).matches();
	}

	protected boolean matchDateD(String s)
	{
		return reDay.matcher(s).matches();
	}

	protected boolean matchDateMDY(String s)
	{
		return reMonthDayYear.matcher(s).matches();
	}

	protected boolean matchDateMD(String s)
	{
		return reMonthDay.matcher(s).matches();
	}




	protected List<Element> expandDateDMY(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		Matcher reMatcher = reDayMonthYear.matcher(s);
		boolean found = reMatcher.find();
		if (!found) {
			return null;
		}
		String day = reMatcher.group(1);
		exp.addAll(number.expandCounted(doc, day+" 日", false));
		String month = reMatcher.group(2);
		exp.addAll(number.expandCounted(doc, month + " 月", false));
		String year = reMatcher.group(3);
		exp.addAll(number.expandCounted(doc, year+" 年", false));
		return exp;
	}

	protected List<Element> expandDateYMD(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		Matcher reMatcher = reDOWYearMonthDay.matcher(s);
		boolean found = reMatcher.find();
		int dayOfWeekType = 1; 
		if (!found) {
			reMatcher = reYearMonthDayDOW.matcher(s);
			found = reMatcher.find();
			dayOfWeekType = 2; 
		}
		if (!found) {
			reMatcher = reYearMonthDay.matcher(s);
			found = reMatcher.find();
			dayOfWeekType = 3; 
		}
		if (!found) {
			return null;
		}
		String day=null, month=null, year=null, dow=null;
		switch(dayOfWeekType)
		{
		case 1: dow = reMatcher.group(1); year = reMatcher.group(2); month = reMatcher.group(4); day = reMatcher.group(5); break;
		case 2: year = reMatcher.group(1); month = reMatcher.group(3); day = reMatcher.group(4); dow = reMatcher.group(5); break;
		case 3: year = reMatcher.group(1); month = reMatcher.group(3); day = reMatcher.group(4); dow = null; break;
		}

		if (dayOfWeekType == 1) {
			exp.addAll(makeNewTokens(doc, dayOfWeekExpansion.get(dow)));
		} 

		exp.addAll(number.expandCounted(doc, year+" 年", false));
		exp.addAll(number.expandCounted(doc, month + " 月", false));
		exp.addAll(number.expandCounted(doc, day+" 日", false));

		if (dayOfWeekType == 2) {
			exp.addAll(makeNewTokens(doc, dayOfWeekExpansion.get(dow)));
		}
		return exp;
	}

	protected List<Element> expandDateDM(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		Matcher reMatcher = reDayMonth.matcher(s);
		boolean found = reMatcher.find();
		if (!found) {
			return null;
		}
		String day = reMatcher.group(1);
		exp.addAll(number.expandCounted(doc, day+" 日", false));
		String month = reMatcher.group(2);
		exp.addAll(number.expandCounted(doc, month + " 月", false));
		return exp;
	}

	protected List<Element> expandDateMY(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		Matcher reMatcher = reMonthYear.matcher(s);
		boolean found = reMatcher.find();
		if (!found) {
			return null;
		}

		String year = reMatcher.group(3);
		exp.addAll(number.expandCounted(doc, year+" 年", false));

		String month = reMatcher.group(1);
		if (month.charAt(0) == '0' || month.charAt(0)=='０')
			month = month.substring(1);
		exp.addAll(number.expandCounted(doc, month + " 月", false));

		// alternatively: keep the number:
		// exp.addAll(number.expandInteger(doc, month, false));
		return exp;
	}

	protected List<Element> expandDateYear(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		exp.addAll(makeNewTokens(doc, number.expandInteger(s), true, s));
		return exp;
	}

	protected List<Element> expandDateMonth(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		Matcher reMatcher = reMonth.matcher(s);
		boolean found = reMatcher.find();
		if (!found) {
			return null;
		}
		String month = reMatcher.group(1);
		// Leading Zero must be deleted to get month from monthabbr.
		if (month.charAt(0) == '0' || month.charAt(0)=='０')
			month = month.substring(1);
		exp.addAll(number.expandCounted(doc, month + " 月", false));

		return exp;
	}

	protected List<Element> expandDateDay(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		Matcher reMatcher = reDay.matcher(s);
		boolean found = reMatcher.find();
		if (!found) {
			return null;
		}
		String day = reMatcher.group(1);
		//      Leading Zero must be deleted to get month from monthabbr.
		if (day.charAt(0) == '0')
			day = day.substring(1);
		exp.addAll(number.expandCounted(doc, day+" 日", false));
		return exp;
	}

	protected List<Element> expandDateMD(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		Matcher reMatcher = reMonthDay.matcher(s);
		boolean found = reMatcher.find();
		if (!found) {
			return null;
		}

		String year = reMatcher.group(3);
		exp.addAll(number.expandCounted(doc, year+" 年", false));

		String month = reMatcher.group(1);
		if (month.charAt(0) == '0')
		{
			month = month.substring(1);
		}
		exp.addAll(number.expandCounted(doc, month + " 月", false));
		// alternatively: keep the number:
		// exp.addAll(number.expandInteger(doc, month, false));
		String day;
		day = reMatcher.group(2);
		exp.addAll(number.expandCounted(doc, day+" 日", false));

		return exp;
	}

	protected List<Element> expandDateMDY(Document doc, String s)
	{
		ArrayList<Element> exp = new ArrayList<Element>();
		Matcher reMatcher = reMonthDayYear.matcher(s);
		boolean found = reMatcher.find();
		if (!found) {
			return null;
		}
		String day;
		String year;

		day = reMatcher.group(2);
		year = reMatcher.group(3);
		String month = reMatcher.group(1);
		if (month.charAt(0) == '0' || month.charAt(0)=='０')
			month = month.substring(1);

		exp.addAll(number.expandCounted(doc, year+" 年", false));
		exp.addAll(number.expandCounted(doc, month + " 月", false));
		exp.addAll(number.expandCounted(doc, day+" 日", false));

		return exp;
	}

}
