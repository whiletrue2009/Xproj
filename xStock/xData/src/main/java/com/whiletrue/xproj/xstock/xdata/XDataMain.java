package com.whiletrue.xproj.xstock.xdata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.whiletrue.xproj.xstock.js.XDataJsParser;
import com.whiletrue.xproj.xstock.util.JdbcUtil;
import com.whiletrue.xproj.xstock.xdata.domain.DataFrameVo;
import com.whiletrue.xproj.xstock.xdata.service.LhbService;
import com.whiletrue.xproj.xstock.xdata.tx.DragonDetail;
import com.whiletrue.xproj.xstock.xdata.tx.DragonList;

public class XDataMain {
//	public static String url_163 = "http://quotes.money.163.com/service/chddata.html?code=0601398&start=20000720&end=20150508";
//
//	public static String url_demo = "http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_xx_js?c=000559&b=20140228&l=070001";

	// http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_js?t=2&c=&b=20161226&e=20161230&p=1&l=&ol=6&o=desc
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(XDataMain.class);
	
	
	public static void main(String[] args) throws IOException, SQLException {
		LhbService lhbService = new LhbService();
		
		DataFrameVo df = lhbService.getLatestLhb();
		
		lhbService.insertLhbList(df);
		logger.info(df.toString());
		
		for(Map<String,String> map : df.getDf()){
			String code = map.get("code");
			String date = map.get("date").replaceAll("-", "");
			String type = map.get("type");
			logger.info("query detail : {} , {}",code,date);
			DataFrameVo df2 = lhbService.getLhbDetailByCode(code, date,type);
			logger.info(df2.toString());
			lhbService.insertLhbDetail(df2);
		}
	}

}
