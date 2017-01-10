package com.whiletrue.xproj.xstock.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiletrue.xproj.xstock.xdata.XDataMain;
import com.whiletrue.xproj.xstock.xdata.domain.DataFrameVo;

public class JdbcUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(JdbcUtil.class);
	
	private static final String  dbPath = "E:\\dev\\sqlite\\lhb\\lhb2.db";
	

	public static Connection getConnection() {

		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
		} catch (ClassNotFoundException e) {
			logger.error(e.toString(), e);
		} catch (SQLException e) {
			logger.error(e.toString(), e);
		}

		logger.info("Opened database successfully");

		return conn;
	}

	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error(e.toString(), e);
		}
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		 
		Connection conn = JdbcUtil.getConnection();
		
		Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists people;");
        stat.executeUpdate("create table people (name, occupation);");
        PreparedStatement prep = conn.prepareStatement(
            "insert into people values (?, ?);");

        prep.setString(1, "Gandhi");
        prep.setString(2, "politics");
        prep.addBatch();
        prep.setString(1, "Turing");
        prep.setString(2, "computers");
        prep.addBatch();
        prep.setString(1, "Wittgenstein");
        prep.setString(2, "smartypants");
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);

        ResultSet rs = stat.executeQuery("select * from people;");
        while (rs.next()) {
            System.out.println("name = " + rs.getString("name"));
            System.out.println("job = " + rs.getString("occupation"));
        }
        rs.close();
     	
        
        QueryRunner queryRunner = new QueryRunner();
        ArrayHandler arrayHandler = new ArrayHandler();
        try {

            String sql = "INSERT INTO user(name, age) VALUES(?,?)";
            Object[] objectArr= queryRunner.insert(conn, sql, arrayHandler, "樂天", 18);
            System.out.println("数组长度：" + objectArr.length + "；第0个元素的值：" +objectArr[0]);
            objectArr = queryRunner.insert(conn, sql, arrayHandler, "樂天天", 19);
            System.out.println("数组长度：" + objectArr.length + "；第0个元素的值：" +objectArr[0]);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        
	}

}
