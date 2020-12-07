package com.code5.fw.web;

import java.sql.SQLException;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxLocal;
import com.code5.fw.db.SqlRunner;

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
	 * @param KEY
	 * @return
	 * @throws SQLException
	 */
	Box getController(String KEY) throws SQLException {

		// [1]
		Box box = new BoxLocal();
		box.put("KEY", KEY);
		return SqlRunner.getSqlRunner().getTable(box, FORM_NO_01).getBox();
	}

	/**
	 * @param KEY
	 * @return
	 * @throws SQLException
	 */
	Box getView(String KEY) throws SQLException {

		// [2]
		Box box = new BoxLocal();
		box.put("KEY", KEY);
		return SqlRunner.getSqlRunner().getTable(box, FORM_NO_02).getBox();
	}

}
