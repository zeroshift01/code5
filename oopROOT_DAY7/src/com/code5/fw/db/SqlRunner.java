package com.code5.fw.db;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.web.Box;

/**
 * @author seuk
 *
 */
interface SqlRunner {

	/**
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(Transaction transaction, Box box, String FORM_NO) throws SQLException;

	/**
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(Transaction transaction, Box box, String FORM_NO) throws SQLException;

}
