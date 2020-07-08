package com.code5.fw.db;

import java.sql.Connection;

/**
 * @author seuk
 *
 */
class Transaction_SQLITE_JDBC_test {

	public static void main(String[] xxx) throws Exception {

		Transaction transaction = Transaction.getTransaction();
		Connection conn = transaction.getConnection();
		
		conn.createStatement().execute("INSERT INTO SS(A,B) VALUES (1,2)");

	}
	
	
	
}
