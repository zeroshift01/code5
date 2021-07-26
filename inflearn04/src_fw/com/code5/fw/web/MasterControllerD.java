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
	 * @param KEY
	 * @return
	 * @throws SQLException
	 */
	Box getController(String KEY) throws SQLException {

		Box box = new BoxLocal();
		box.put("KEY", KEY);
		Table table = sql.getTable("MASTERCONTROLLERD_01", box);
		return table.getBox();
	}

}
