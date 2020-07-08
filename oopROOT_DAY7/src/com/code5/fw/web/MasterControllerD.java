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
	 * @return
	 * @throws SQLException
	 */
	Box getSubController() throws SQLException {

		Box box = Box.getThread();
		String KEY = box.s("pathInfo");
		box.put("KEY", KEY);

		Sql sql = Sql.getSql();

		return sql.getTable(FORM_NO_01).getBox(0);

	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Box getJspByKey(String JSP_KEY) throws SQLException {

		Box box = Box.getThread();
		box.put("JSP_KEY", JSP_KEY);

		Sql sql = Sql.getSql();

		return sql.getTable(FORM_NO_02).getBox(0);
	}

}
