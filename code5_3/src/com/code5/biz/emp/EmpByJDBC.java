package com.code5.biz.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;

/**
 * @author zero
 *
 */
public class EmpByJDBC {

	/**
	 * @param x
	 * @throws Exception
	 */
	public static void main(String[] x) throws Exception {

		String EMP_N = "N001";
		String HP_N = "010-2222-3333";

		// TODO [1]
		SQLiteConfig config = new SQLiteConfig();

		Connection conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\public\\sqlite\\code5.db",
				config.toProperties());

		// Class.forName("org.sqlite.JDBC");
		// Connection conn =
		// DriverManager.getConnection("jdbc:sqlite:C:\\public\\sqlite\\code5.db");

		select(conn, EMP_N);
		update(conn, HP_N, EMP_N);

		select(conn, EMP_N);

		conn.close();
		// TODO [14] TX2 ³¡

	}

	/**
	 * @param conn
	 * @param EMP_N
	 * @throws Exception
	 */
	private static void select(Connection conn, String EMP_N) throws Exception {

		String SQL = "SELECT * FROM EMP ";

		if (!"".equals(EMP_N)) {
			SQL = SQL + " WHERE EMP_N = '" + EMP_N + "'";
		}

		System.out.println(SQL);

		Statement ps = conn.createStatement();

		ResultSet rs = ps.getResultSet();

		// TODO [4]
		List<List<String>> table = new ArrayList<List<String>>();

		// TODO [5]
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] cols = new String[columnCount];

		for (int i = 0; i < cols.length; i++) {
			List<String> colsName = new ArrayList<String>();
			colsName.add(metaData.getColumnName(i + 1));
		}

		// TODO [6]
		while (rs.next()) {

			// TODO [7]
			List<String> recode = new ArrayList<String>();
			for (int i = 1; i <= cols.length; i++) {
				recode.add(rs.getString(i));
			}
			table.add(recode);
		}

		// TODO [8]
		rs.close();
		ps.close();

		// TODO [9]
		for (int i = 0; i < table.size(); i++) {

			List<String> recode = table.get(i);

			for (int j = 0; j < cols.length; j++) {
				System.out.print(recode.get(j) + " ");
			}
			System.out.println();

		}

	}

	/**
	 * @throws Exception
	 */
	private static void update(Connection conn, String HP_N, String EMP_N) throws Exception {

		// TODO [10]
		conn.setAutoCommit(false);

		PreparedStatement ps = conn.prepareStatement("UPDATE EMP SET HP_N = ? WHERE EMP_N = ? ");
		ps.setString(1, HP_N);
		ps.setString(2, EMP_N);

		// TODO [11]
		int i = ps.executeUpdate();

		System.out.println("executeUpdate [" + i + "]");

		ps.close();

		// TODO [12]
		conn.commit();

	}

}
