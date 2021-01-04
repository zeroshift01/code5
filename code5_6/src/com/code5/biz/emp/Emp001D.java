package com.code5.biz.emp;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.SqlRunner;

/**
 * @author seuk
 *
 */
public class Emp001D {

	private static String FORM_NO_01 = "EMP001D_01";
	private static String FORM_NO_02 = "EMP001D_02";

	/**
	 * 
	 */
	private SqlRunner sql = SqlRunner.getSqlRunner();

	/**
	 * @return
	 * @throws SQLException
	 */
	Table emp00101() throws SQLException {
		return sql.getTable(FORM_NO_01);
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int emp00102() throws SQLException {
		return sql.executeSql(FORM_NO_02);
	}

}
