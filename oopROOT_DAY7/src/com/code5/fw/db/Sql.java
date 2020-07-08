package com.code5.fw.db;

import java.sql.SQLException;

import com.code5.fw.data.Table;

/**
 * @author seuk
 *
 */
public class Sql {

	/**
	 * 
	 */
	private static Sql sql = new Sql();

	/**
	 * 
	 */
	private Sql() {

	}

	/**
	 * @return
	 */
	public static Sql getSql() {
		return sql;
	}

	/**
	 * @return
	 */
	public Table getData(String FORM_NO) throws SQLException {

		return new Table();
	}

	/**
	 * @return
	 */
	public int executeSql();

}
