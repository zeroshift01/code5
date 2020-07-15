package com.code5.fw.web;

import java.sql.SQLException;

import com.code5.fw.db.Sql;

/**
 * @author seuk
 *
 */
public class MasterControllerD {

	/**
	 * 
	 */
	private static String FORM_NO_01 = "MASTERCONTROLLERD_01";

	/**
	 * 
	 */
	private static String FORM_NO_02 = "MASTERCONTROLLERD_02";

	/**
	 * 
	 * [1]
	 * 
	 * @param KEY
	 * @return
	 * @throws SQLException
	 */
	Box getController(String KEY) throws SQLException {

		Box box = Box.getThread();

		// [2]
		box.put("KEY", KEY);

		// [3]
		return Sql.getSql().getTable(FORM_NO_01).getBox();
	}

	/**
	 * 
	 * [2]
	 * 
	 * @param KEY
	 * @return
	 * @throws SQLException
	 */
	Box getView(String KEY) throws SQLException {

		Box box = Box.getThread();

		// [2]
		box.put("KEY", KEY);

		// [3]
		return Sql.getSql().getTable(FORM_NO_02).getBox();
	}

}
