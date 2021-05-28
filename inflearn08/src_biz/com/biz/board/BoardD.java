package com.biz.board;

import java.sql.SQLException;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author zero
 *
 */
public class BoardD {

	/**
	 * 
	 */
	private Sql sql = new Sql(this);

	/**
	 * @return
	 * @throws SQLException
	 */
	Table list() throws SQLException {
		return sql.getTable("LIST");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	Box select() throws SQLException {
		return sql.getTable("SELECT").getBox();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	void write() throws SQLException {
		sql.executeSql("WRITE");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int update() throws SQLException {
		return sql.executeSql("UPDATE");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int delete() throws SQLException {
		return sql.executeSql("DELETE");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	void deleteAll() throws SQLException {
		sql.executeSql("DELETEALL");
	}

}
