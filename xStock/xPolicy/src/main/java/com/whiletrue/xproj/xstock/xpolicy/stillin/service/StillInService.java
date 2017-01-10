package com.whiletrue.xproj.xstock.xpolicy.stillin.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiletrue.xproj.xstock.xpolicy.stillin.dao.StillInDao;

public class StillInService {

	private static final Logger logger = LoggerFactory
			.getLogger(StillInDao.class);

	private static final int calcDays = 5;

	private StillInDao stillInDao = new StillInDao();

	
	public void findYyb(String code) throws SQLException {
		
		List<String> latestDays = this.getCalcDateList();
		
		String date  = latestDays.get(0);
		logger.info("####### date : {}" ,date);
		List<String> codeList = this.getCalcCode(date);
		logger.info("####### codeList : {}" ,codeList);
		
	}
	
	
	/**
	 * 获得要计算的天数
	 * @return
	 * @throws SQLException 
	 */
	private List<String> getCalcDateList() throws SQLException {
		List<String> latestDays = stillInDao.getLastestDay();

		logger.info("findYyb : {}", latestDays);

		int n = latestDays.size() > calcDays ? calcDays : latestDays.size();

		List<String> dateList = new ArrayList<String>();
		for (String date : latestDays) {
			if (n-- > 0) {
				dateList.add(date);
			}
		}

		return dateList;
	}
	
	
	/**
	 * 
	 * 获得要计算的代码
	 * 
	 * @param date
	 * @return
	 * @throws SQLException 
	 */
	private List<String> getCalcCode(String date ) throws SQLException{
		
		return stillInDao.getCalcCode(date);
	}

}
