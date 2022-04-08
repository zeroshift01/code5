package com.biz.brd;

import java.sql.SQLException;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author zero
 *
 */
public class BrdD {

	/**
	 * 
	 */
	private Sql sql = new Sql(this);

	/**
	 * @return
	 * @throws SQLException
	 */
	Table brd01010() throws SQLException {
		return sql.getTable("LIST");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	Box brd01030() throws SQLException {
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
	int brd01031() throws SQLException {
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
	void brd02010() throws SQLException {
		sql.executeSql("DELETEALL");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int brd02020() throws SQLException {
		return sql.executeSql("UPDATE_ALL");
	}

}
