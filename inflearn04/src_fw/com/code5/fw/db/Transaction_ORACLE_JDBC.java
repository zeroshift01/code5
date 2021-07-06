package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class Transaction_ORACLE_JDBC extends Transaction {

	private static String URL = InitYaml.get().dec("Transaction_ORACLE_JDBC.URL");

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		// 오라클 드라이버 사용 Connection 생성
		URL.toString();
		return null;

	}

}
