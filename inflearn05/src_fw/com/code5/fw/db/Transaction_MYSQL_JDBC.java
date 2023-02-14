package com.code5.fw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zero
 *
 */
public class Transaction_MYSQL_JDBC extends Transaction {

	static {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		// MYSQL 접속정보를 적어주세요.
		// 공개된 레퍼지토리(public) 에 해당 코드는 등록하지 마세요.
		String url = "jdbc:mysql://10.1.2.3:12223/mysql";
		String id = "code5";
		String pin = "code55";

		Connection conn = DriverManager.getConnection(url, id, pin);
		return conn;

	}

}
