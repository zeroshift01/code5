package com.code5.biz.comm002;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author seuk
 * 
 */
class Comm002D {

	/**
	 * 
	 */
	private static String FORM_NO_01 = "COMM002D_01";


	/**
	 * @return
	 * @throws SQLException
	 */
	Table getBoard() throws SQLException {
		return Sql.getSql().getTable(FORM_NO_01);
	}

}
