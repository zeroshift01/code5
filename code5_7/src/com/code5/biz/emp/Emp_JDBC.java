package com.code5.biz.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

		SQLiteConfig config = new SQLiteConfig();

		Connection conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\public\\sqlite\\code5.db",
				config.toProperties());

		Emp_JDBC e = new Emp_JDBC();

		e.select(conn);

		e.update(conn);

		e.select(conn);

		conn.close();

	}

	/**
	 * @throws Exception
	 */
	private void select(Connection conn) throws Exception {

		PreparedStatement ps = conn.prepareStatement("SELECT EMP_N, EMP_NM, HP_N, DEPT_N FROM EMP WHERE EMP_NM = ? ");

		ps.setString(1, "ABC");

		ResultSet rs = ps.executeQuery();

		List<List<String>> table = new ArrayList<List<String>>();

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] cols = new String[columnCount];

		for (int i = 0; i < cols.length; i++) {
			List<String> colsName = new ArrayList<String>();
			colsName.add(metaData.getColumnName(i + 1));
		}

		while (rs.next()) {

			List<String> recode = new ArrayList<String>();
			for (int i = 1; i <= cols.length; i++) {
				recode.add(rs.getString(i));
			}
			table.add(recode);
		}

		rs.close();
		ps.close();

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

		conn.setAutoCommit(false);

		PreparedStatement ps = conn.prepareStatement("UPDATE EMP SET HP_N = ? WHERE EMP_N = ? ");
		ps.setString(1, "010-1111-2222");
		ps.setString(2, "N003");

		int i = ps.executeUpdate();

		System.out.println("executeUpdate [" + i + "]");

		ps.close();

		conn.commit();

	}

}
