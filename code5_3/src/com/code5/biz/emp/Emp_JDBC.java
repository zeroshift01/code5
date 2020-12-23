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
 * @author seuk
 *
 */
public class Emp_JDBC {

	/**
	 * @param x
	 * @throws Exception
	 */
	public static void main(String[] x) throws Exception {

		// TODO [1]
		SQLiteConfig config = new SQLiteConfig();

		Connection conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\public\\sqlite\\code5.db",
				config.toProperties());

		Emp_JDBC e = new Emp_JDBC();

		// TODO [13] TX1 시작
		e.select(conn);

		e.update(conn);
		// TODO [13] TX1 끝

		// TODO [14] TX2 시작
		e.select(conn);
		// TODO [14] TX2 끝

		conn.close();

	}

	/**
	 * @throws Exception
	 */
	private void select(Connection conn) throws Exception {

		// TODO [2]
		PreparedStatement ps = conn.prepareStatement("SELECT EMP_N, EMP_NM, HP_N, DEPT_N FROM EMP WHERE EMP_NM = ? ");

		// TODO [3]
		ps.setString(1, "ABC");

		ResultSet rs = ps.executeQuery();

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
	private void update(Connection conn) throws Exception {

		// TODO [10]
		conn.setAutoCommit(false);

		PreparedStatement ps = conn.prepareStatement("UPDATE EMP SET HP_N = ? WHERE EMP_N = ? ");
		ps.setString(1, "010-1111-2222");
		ps.setString(2, "N003");

		// TODO [11]
		int i = ps.executeUpdate();

		System.out.println("executeUpdate [" + i + "]");

		ps.close();

		// TODO [12]
		conn.commit();

	}

	/**
	 * @param conn
	 * @throws Exception
	 */
	void selectStatement(Connection conn, String EMP_NM) throws Exception {

		String SQL = "";

		SQL = SQL + "SELECT EMP_N, EMP_NM, HP_N, DEPT_N FROM EMP ";

		if (!"".equals(EMP_NM)) {
			SQL = SQL + " WHERE EMP_NM = '" + EMP_NM + "'";
		}

		System.out.println(SQL);

		Statement ps = conn.createStatement();

		ResultSet rs = ps.getResultSet();

		rs.next();

	}

}
