package com.code5.fw.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

/**
 * @author seuk
 *
 */
public class Transaction_test {

	public static void main(String[] xxx) throws Exception {

		SQLiteConfig config = new SQLiteConfig();
		Connection conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\onedrive\\public\\sqlitecode5.db",
				config.toProperties());

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from FW_SQL");
			while (rs.next()) {
				System.out.println(rs.getString("KEY"));
			}

			int cnt = st.executeUpdate("UPDATE FW_SQL SET KEY = KEY WHERE 1 = 2");
			System.out.println(cnt);

			rs.close();
			st.close();

			conn.commit();

		} catch (Exception e) {
			conn.rollback();
		}

		conn.close();

	}

}
