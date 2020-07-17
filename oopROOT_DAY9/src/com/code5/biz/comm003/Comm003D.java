// [1]
package com.code5.biz.comm003;

import java.sql.SQLException;

import com.code5.fw.db.Sql;
import com.code5.fw.web.Box;

/**
 * @author seuk
 * 
 */
class Comm003D {

	/**
	 * 
	 */
	private static String FORM_NO_01 = "COMM003D_01";

	/**
	 * @return
	 * @throws SQLException
	 */
	Box comm00302() throws SQLException {
		return Sql.getSql().getTable(FORM_NO_01).getBox();
	}

}
