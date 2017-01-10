package com.whiletrue.xproj.xstock.xdata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiletrue.xproj.xstock.xdata.domain.DataFrameVo;
import com.whiletrue.xproj.xstock.xdata.service.LhbService;
import com.whiletrue.xproj.xstock.xdata.tx.DragonDetail;

public class XDataMain {
	// public static String url_163 =
	// "http://quotes.money.163.com/service/chddata.html?code=0601398&start=20000720&end=20150508";
	//
	// public static String url_demo =
	// "http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_xx_js?c=000559&b=20140228&l=070001";

	// http://stock.finance.qq.com/cgi-bin/sstock/q_lhb_js?t=2&c=&b=20161226&e=20161230&p=1&l=&ol=6&o=desc

	private static final Logger logger = LoggerFactory
			.getLogger(XDataMain.class);

	private static final ExecutorService fixedThreadPool = Executors
			.newFixedThreadPool(10);

	private static void concurrentVersion() throws IOException, SQLException {
		final LhbService lhbService = new LhbService();

		DataFrameVo df = lhbService.getLhbListByCodeAndDate("", "20170101",
				"20170110");

		lhbService.insertLhbList(df);
		logger.info(df.toString());

		for (Map<String, String> map : df.getDf()) {
			final String code = map.get("code");
			final String date = map.get("date").replaceAll("-", "");
			final String type = map.get("type");
			logger.info("query detail : {} , {}", code, date);

			fixedThreadPool.submit(new Runnable() {

				public void run() {
					DataFrameVo df2;
					try {
						df2 = lhbService.getLhbDetailByCode(code, date, type);
						logger.info(df2.toString());
						lhbService.insertLhbDetail(df2);
					} catch (MalformedURLException e) {
						logger.error(e.toString(), e);
					} catch (IOException e) {
						logger.error(e.toString(), e);
					} catch (SQLException e) {
						logger.error(e.toString(), e);
					}
				}
			});
			
		}
		lhbService.cleanData();
	}

	private static void commonVersion() throws IOException, SQLException {
		final LhbService lhbService = new LhbService();

		DataFrameVo df = lhbService.getLhbListByCodeAndDate("", "20170101",
				"20170110");

		lhbService.insertLhbList(df);
		logger.info(df.toString());

		for (Map<String, String> map : df.getDf()) {
			final String code = map.get("code");
			final String date = map.get("date").replaceAll("-", "");
			final String type = map.get("type");
			logger.info("query detail : {} , {}", code, date);

			DataFrameVo df2 = lhbService.getLhbDetailByCode(code, date, type);
			logger.info(df2.toString());
			if (type.equals(DragonDetail.day_sum_070001)) {
				lhbService.insertLhbDetail(df2);
			} else if (type.equals(DragonDetail.accumulate_070005)) {
				lhbService.insertLhbDetailSum(df2);
			} else {
				logger.error("$$$$$$$$ code ：{} , date {} ,type {}", code,
						date, type);
			}

		}

		lhbService.cleanData();
	}

	public static void main(String[] args) throws IOException, SQLException {
		commonVersion();
	}

}
