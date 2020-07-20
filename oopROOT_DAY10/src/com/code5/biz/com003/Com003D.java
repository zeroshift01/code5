// [1]
package com.code5.biz.com003;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author seuk
 * 
 */
public class Com003D {

	/**
	 * 
	 */
	private static String FORM_NO_01 = "COM003D_01";

	/**
	 * 
	 */
	private static String FORM_NO_02 = "COMM003D_02";

	/**
	 * @return
	 * @throws SQLException
	 */
	Table comm00302() throws SQLException {
		return Sql.getSql().getTable(FORM_NO_01);
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int comm00302_2() throws SQLException {
		return Sql.getSql().executeSql(FORM_NO_02);
	}

}
