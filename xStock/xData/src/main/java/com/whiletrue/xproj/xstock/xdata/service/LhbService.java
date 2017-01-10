package com.whiletrue.xproj.xstock.xdata.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiletrue.xproj.xstock.js.XDataJsParser;
import com.whiletrue.xproj.xstock.xdata.dao.LhbDao;
import com.whiletrue.xproj.xstock.xdata.domain.DataFrameVo;
import com.whiletrue.xproj.xstock.xdata.tx.DragonDetail;
import com.whiletrue.xproj.xstock.xdata.tx.DragonList;

public class LhbService {

	private static final Logger logger = LoggerFactory
			.getLogger(LhbService.class);

	LhbDao lhbDao = null;

	public LhbService() {
		super();
		lhbDao = new LhbDao();
	}

	public DataFrameVo getLatestLhb() throws MalformedURLException, IOException {

		return this.getLhbListByCodeAndDate("", "", "");

	}

	/**
	 * 根据code和制定日期范围获取
	 * 
	 * @param code
	 * @param begin
	 * @param end
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public DataFrameVo getLhbListByCodeAndDate(String code, String begin,
			String end) throws MalformedURLException, IOException {

		String url = DragonList.getLhbUrlByCodeAndDate(code, begin, end);
		logger.info("########### url is: {}" ,url);
		
		Document document = Jsoup.parse(new URL(url).openStream(), "GBK", url);
		Elements el = document.getElementsByTag("body");
		String jsData = el.get(0).text();
		logger.info(jsData);
		String str = XDataJsParser.parseJS(jsData, DragonList.getJsKey());
		logger.info(str);
		return new DataFrameVo(str, DragonList.getDfKeyArr());
	}

	
	public void insertLhbList(DataFrameVo df) throws SQLException{
		this.lhbDao.insertLhbList(df);
	}
	
	public void insertLhbDetail(DataFrameVo df) throws SQLException{
		this.lhbDao.insertLhbDetail(df);
	}
	
	public void insertLhbDetailSum(DataFrameVo df) throws SQLException{
		this.lhbDao.insertLhbDetailSum(df);
	}
	
	public void cleanData() throws SQLException{
		this.lhbDao.cleanData();
	}
	
	
	/**
	 * 根据code取得lhb详细买卖信息
	 * 
	 * 默认取得当
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public DataFrameVo getLhbDetailByCode(String code, String date,String type)
			throws MalformedURLException, IOException {

		String url = DragonDetail.getLhbDetailUrlByCodeAndDate(code, date,type);
		logger.info("######## url is:{}" ,url);
		Document document = Jsoup.parse(new URL(url).openStream(), "GBK", url);
		Elements el = document.getElementsByTag("body");
		String jsData = el.get(0).text();
		String str = XDataJsParser.parseJS(jsData, DragonDetail.getJsKey());

		logger.info(str);
		return new DataFrameVo(str, DragonDetail.getDfKeyArr());
	}

}
