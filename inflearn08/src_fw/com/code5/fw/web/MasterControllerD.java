package com.code5.fw.web;

import java.sql.SQLException;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxLocal;
import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author zero
 *
 */
public class MasterControllerD {

	/**
	 * 
	 */
	private Sql sql = new Sql(this);

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

		Box box = new BoxLocal();
		box.put("KEY", KEY);
		Table table = sql.getTableByCache(FORM_NO_01, box);
		return table.getBox();
	}

	/**
	 * @param KEY
	 * @return
	 * @throws SQLException
	 */
	Box getView(String KEY) throws SQLException {

		Box box = new BoxLocal();
		box.put("KEY", KEY);
		Table table = sql.getTableByCache(FORM_NO_02, box);
		return table.getBox();
	}

}
