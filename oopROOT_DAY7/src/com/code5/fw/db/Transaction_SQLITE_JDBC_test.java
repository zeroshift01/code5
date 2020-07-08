package com.code5.fw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author seuk
 *
 */
class Transaction_SQLITE_JDBC_test {

	public static void main(String[] xxx) throws Exception {

		Transaction transaction = Transaction.getTransaction();
		Connection conn = transaction.getConnection();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM FRAME_SQL");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("SQL"));
		}

	}

}
