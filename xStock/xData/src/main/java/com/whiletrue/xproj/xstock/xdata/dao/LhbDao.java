package com.whiletrue.xproj.xstock.xdata.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiletrue.xproj.xstock.util.JdbcUtil;
import com.whiletrue.xproj.xstock.xdata.domain.DataFrameVo;
import com.whiletrue.xproj.xstock.xdata.tx.DragonDetail;
import com.whiletrue.xproj.xstock.xdata.tx.DragonList;

public class LhbDao {

	private static final Logger logger = LoggerFactory.getLogger(LhbDao.class);

	private String[] getValueArr(Map<String, String> map, String[] keyArr) {

		String[] arr = new String[keyArr.length];
		int index = 0;
		for (String key : keyArr) {
			arr[index++] = map.get(key);
		}
		return arr;
	}

	public void insertLhbList(DataFrameVo listDf) {

		QueryRunner queryRunner = new QueryRunner();
		ArrayHandler arrayHandler = new ArrayHandler();

		Connection conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO t_lhb_list(date,code, name,reason, type,price, amplitude) VALUES(?,?,?,?,?,?,?)";

		for (Map<String, String> map : listDf.getDf()) {
			String[] valueArr = this.getValueArr(map, DragonList.getDfKeyArr());
			Object[] objectArr = null;
			try {
				objectArr = queryRunner.insert(conn, sql, arrayHandler,
						valueArr);
				logger.info("insert: {}" , map);
				logger.info(StringUtils.join(objectArr, ","));
			} catch (SQLException e) {
				logger.error(map.toString(),e);
			}
			
		}
		
		DbUtils.closeQuietly(conn);
	}
	
	public void insertLhbDetail(DataFrameVo detailDf) throws SQLException {

		QueryRunner queryRunner = new QueryRunner();
		ArrayHandler arrayHandler = new ArrayHandler();

		Connection conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO t_lhb_detail(code, name,type,ranking, date,yyb,butCount,sellCount ) VALUES(?,?,?,?,?,?,?,?)";

		for (Map<String, String> map : detailDf.getDf()) {
			String[] valueArr = this.getValueArr(map, DragonDetail.getDfKeyArr());
			
			Object[] objectArr = null;
			try {
				objectArr = queryRunner.insert(conn, sql, arrayHandler,
						valueArr);
				logger.info(StringUtils.join(objectArr, ","));
				logger.info("insert: {}" , map);
			} catch (SQLException e) {
				logger.error(map.toString(),e);
			}
			
//			Object[] objectArr = queryRunner.insert(conn, sql, arrayHandler,
//					valueArr);
//			logger.info(StringUtils.join(objectArr, ","));
		}
		
		DbUtils.closeQuietly(conn);
	}
	
	

}
