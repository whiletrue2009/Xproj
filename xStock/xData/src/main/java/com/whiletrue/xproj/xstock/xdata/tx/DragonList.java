package com.whiletrue.xproj.xstock.xdata.tx;

/**
 * 指定日期区间的上榜代码
 * 
 * @author whiletrue
 *
 */
public class DragonList {

	private DragonList() {

	}

	/**
	 * lhb
	 * 当日：http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_js?t=0&c=&b=&e=&p=
	 * 1&l=&ol=6&o=desc c:code b:startDate e:endDate
	 * 
	 */
	private static final String lhb = "http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_js?t=2&c=%s&b=%s&e=%s&p=1&l=&ol=6&o=desc";

	private static final String result_key = "tt";

	private static final String js_key = "var tt = JSON.stringify(oLhbDatas._datas);"
			+ result_key;

//	private final String[] lhKeyStr = new String[] { "code", "name", "type",
//			"place", "xiwei", "buyCount", "sellCount" };
	
	
	private static final String[] lhbListKeyArr = new String[] { "date","code", "name","reason", "type",
			"price", "amplitude" };
	
	public static String[] getDfKeyArr() {

		return lhbListKeyArr;
	}
	
	public static String getJsKey() {

		return js_key;
	}
	
	public static String getResultKey() {

		return result_key;
	}

	public static String getTodayLhbUrl() {

		return getLhbUrlByCodeAndDate("", "", "");
	}

	public static String getLhbUrlByDate(String begin, String end) {

		return getLhbUrlByCodeAndDate("", begin, end);
	}

	public static String getLhbUrlByCode(String code) {

		return getLhbUrlByCodeAndDate(code, "", "");
	}

	public static String getLhbUrlByCodeAndDate(String code, String begin,
			String end) {

		return String.format(lhb, code, begin, end);
	}

}
