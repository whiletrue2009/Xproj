package com.whiletrue.xproj.xstock.xpolicy;

import java.sql.SQLException;

import com.whiletrue.xproj.xstock.xpolicy.stillin.service.StillInService;

/**
 * 使用2天及2天以上的数据，找到那些进了lhb，还未出局的yyb
 * 
 * @author whiletrue
 *
 */
public class StillInMain {

	public static void main(String[] args) throws SQLException {
		
		StillInService service = new StillInService();
		service.findYyb("");
	}

}
