package com.biz.board;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author zero
 *
 */
public class BoardD {

	private Sql sql = new Sql(this);

	/**
	 * @return
	 * @throws SQLException
	 */
	Table list() throws SQLException {

		return sql.getTable("SELECT");
	}

	/**
	 * @throws SQLException
	 */
	void write() throws SQLException {

		sql.executeSql("WRITE");
	}

}
