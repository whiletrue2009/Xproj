package com.whiletrue.xproj.xstock.xpolicy.stillin.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiletrue.xproj.xstock.util.JdbcUtil;

public class StillInDao {

	private static final Logger logger = LoggerFactory
			.getLogger(StillInDao.class);

	public List<String> getLastestDay() throws SQLException {

		QueryRunner queryRunner = new QueryRunner();

		Connection conn = JdbcUtil.getConnection();

		String sql = "select  distinct date  from t_lhb_detail order by date desc";

		List<String> dateList = queryRunner.query(conn, sql, new ColumnListHandler<String>("date"));
		
		logger.info("###### getLastestDay: {}", dateList);
		return dateList;
	}
	
	
	public List<String> getCalcCode(String date) throws SQLException {

		QueryRunner queryRunner = new QueryRunner();

		Connection conn = JdbcUtil.getConnection();

		String sql = "select  code  from t_lhb_list where date=? ";

		List<String> codeList = queryRunner.query(conn, sql, new ColumnListHandler<String>("code"),new String[]{date});
		
		logger.info("###### getCalcCode: {}", codeList);
		return codeList;
	}
	
	
}
