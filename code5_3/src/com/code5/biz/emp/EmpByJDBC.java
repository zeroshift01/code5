package com.code5.biz.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.sqlite.SQLiteConfig;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

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

		String EMP_NM = "ABC";
		String HP_N = "010-2222-3333";

		EmpDTO pEmpDTO = new EmpDTO();
		pEmpDTO.setEmpN(EMP_NM);

		SQLiteConfig config = new SQLiteConfig();
		Properties properties = config.toProperties();

		Connection conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\public\\sqlite\\code5.db", properties);

		/*
		 * 
		 * Class.forName("org.sqlite.JDBC"); conn =
		 * DriverManager.getConnection("jdbc:sqlite:C:\\public\\sqlite\\code5.db");
		 * 
		 */

		List<EmpDTO> list = selectForDTO(conn, pEmpDTO);

		conn.setAutoCommit(false);

		for (int i = 0; i < x.length; i++) {

			EmpDTO thisEmpDTO = list.get(i);
			thisEmpDTO.setHpN(HP_N);

			update(conn, thisEmpDTO);
		}

		conn.commit();

		Box box = BoxContext.getThread();
		box.put("EMP_NM", EMP_NM);

		List<List<String>> table = selectForCollection(conn);

		// TODO [9]
		for (int i = 0; i < table.size(); i++) {

			List<String> recode = table.get(i);

			for (int j = 0; j < recode.size(); j++) {
				System.out.print(recode.get(j) + "\t");
			}
			System.out.println();

		}

		conn.close();

	}

	/**
	 * @param conn
	 * @param empDTO
	 * @return
	 * @throws Exception
	 */
	private static List<EmpDTO> selectForDTO(Connection conn, EmpDTO empDTO) throws Exception {

		String EMP_NM = empDTO.getEmpNm();

		String SQL = "SELECT EMP_N, EMP_NM, HP_N, DEPT_N FROM EMP ";

		if (!"".equals(EMP_NM)) {
			SQL = SQL + " WHERE EMP_NM = '" + EMP_NM + "'";
		}

		System.out.println(SQL);

		Statement ps = conn.createStatement();

		ResultSet rs = ps.executeQuery(SQL);

		ArrayList<EmpDTO> list = new ArrayList<EmpDTO>();
		while (rs.next()) {

			EmpDTO dto = new EmpDTO();

			dto.setEmpN(rs.getString("EMP_N"));
			dto.setEmpNm(rs.getString("EMP_NM"));
			dto.setHpN(rs.getString("HP_N"));
			dto.setDeptN(rs.getString("DEPT_N"));

			list.add(dto);
		}

		// TODO [8]
		rs.close();
		ps.close();

		return list;

	}

	/**
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private static List<List<String>> selectForCollection(Connection conn) throws Exception {

		Box box = BoxContext.getThread();

		String EMP_NM = box.s("EMP_NM");

		String SQL = "SELECT EMP_N, EMP_NM, HP_N, DEPT_N, XXX, XXX FROM EMP ";

		if (!"".equals(EMP_NM)) {
			SQL = SQL + " WHERE EMP_NM = '" + EMP_NM + "'";
		}

		System.out.println(SQL);

		Statement ps = conn.createStatement();

		ResultSet rs = ps.executeQuery(SQL);

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

		return table;

	}

	/**
	 * @throws Exception
	 */
	private static void update(Connection conn, EmpDTO empDTO) throws Exception {

		PreparedStatement ps = conn.prepareStatement("UPDATE EMP SET HP_N = ? WHERE EMP_N = ? ");
		ps.setString(1, empDTO.getHpN());
		ps.setString(2, empDTO.getEmpN());

		// TODO [11]
		int i = ps.executeUpdate();

		System.out.println("executeUpdate [" + i + "]");

		ps.close();

	}

}
