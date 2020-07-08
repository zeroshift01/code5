package com.code5.biz.comm001;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;
import com.code5.fw.web.Box;

/**
 * @author seuk
 * 
 *         °Ô½ÃÆÇ
 *
 */
public class Comm001D {

	/**
	 * 
	 */
	private Sql sql = Sql.getSql();

	/**
	 * 
	 */
	private static String FORM_NO_01 = "COMM001D_01";

	/**
	 * 
	 */
	private static String FORM_NO_02 = "COMM001D_02";

	/**
	 * 
	 */
	private static String FORM_NO_03 = "COMM001D_03";

	/**
	 * @return
	 */
	Table comm00101() throws SQLException {

		return sql.getTable(FORM_NO_01);
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	Box comm00102() throws SQLException {
		return sql.getTable(FORM_NO_02).getBox();
	}

	/**
	 * @throws SQLException
	 */
	int comm00103() throws SQLException {
		return sql.executeSql(FORM_NO_03);
	}
}
