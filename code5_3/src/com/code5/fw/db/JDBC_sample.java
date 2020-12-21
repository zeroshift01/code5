package com.code5.fw.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

/**
 * @author seuk
 *
 */
public class JDBC_sample {

	public static void main(String[] xxx) throws Exception {

		// TODO [1]
		SQLiteConfig config = new SQLiteConfig();

		Connection conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\public\\sqlite\\sqlitecode5.db",
				config.toProperties());

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("SELECT * FROM DEPT WHERE DNAME = 'ABCD'");

		rs.next();

		String DEPTNO = rs.getString("DEPTNO ");

		int cnt = st.executeUpdate("UPDATE EMP SET DEPTNO  = '" + DEPTNO + "'WHERE EMPNO = 'E1122'");
		System.out.println(cnt);

		rs.close();
		st.close();
		
		EMP
		EMP_NO
		EMP_NAME
		DEPT_NO
		
		conn.commit();

		conn.close();
	}

}
