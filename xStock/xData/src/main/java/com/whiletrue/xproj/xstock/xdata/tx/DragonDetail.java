package com.whiletrue.xproj.xstock.xdata.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 指定日期上榜代码的买卖详细
 * 
 * @author whiletrue
 *
 */
public class DragonDetail {
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(DragonDetail.class);
	
	
	private DragonDetail() {

	}

	/**
	 * lhb
	 * 当日：http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_js?t=0&c=&b=&e=&p=
	 * 1&l=&ol=6&o=desc c:code b:startDate e:endDate
	 * 
	 */
//	private static final String lhb_detail_sh = "http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_xx_js?c=%s&b=%s&l=070001";
	
	private static final String lhb_detail = "http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_xx_js?c=%s&b=%s&l=%S";
	
	
	private static final String result_key = "tt";

	private static final String js_key = "var tt = JSON.stringify(oLhbxxDatas._datas);"
			+ result_key;

	private static final String[] lhbListKeyArr = new String[] { 
			"code", "name","type","ranking", "date","yyb","butCount","sellCount" };

	public static final String day_sum_070001 = "070001";
	public static final String accumulate_070005 = "070005";
	
	public static String[] getDfKeyArr() {

		return lhbListKeyArr;
	}

	public static String getJsKey() {

		return js_key;
	}

	public static String getResultKey() {

		return result_key;
	}


	public static String getLhbDetailUrlByCodeAndDate(String code, String date,String type) {

		
		return String.format(lhb_detail, code, date,type);
		
//		if(code.startsWith("6")){
//			return String.format(lhb_detail_sh, code, date);
//		}else if(code.startsWith("0") || code.startsWith("3")){
//			return String.format(lhb_detail_sz, code, date);
//		}else{
//			logger.error("unexpect code : " + code);
//			throw new RuntimeException("unexpect code : " + code);
//		}
		
	}

}
