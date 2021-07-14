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
		if (table.size() != 1) {
			throw new SQLException("KEY [" + KEY + "] 를 확인해주세요.");
		}
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
		Table table = sql.getTable("MASTERCONTROLLERD_02", box);
		if (table.size() != 1) {
			throw new SQLException("KEY [" + KEY + "] 를 확인해주세요.");
		}
		return table.getBox();
	}

}
