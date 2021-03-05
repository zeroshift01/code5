package com.code5.fw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.code5.biz.emp.EmpDTO;
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
		pEmpDTO.setEmpNm(EMP_NM);
		pEmpDTO.setHpN(HP_N);

		Transaction transaction = new Transaction_SQLITE_JDBC_OLD();

		List<EmpDTO> list = select(transaction, pEmpDTO);

		transaction.close();

		update(transaction, list, pEmpDTO);

		transaction.commit();

		Box box = BoxContext.getThread();
		box.put("EMP_NM", EMP_NM);

		List<String[]> list2 = selectForCollection(transaction);

		System.out.println(list2.get(1)[0]);
		System.out.println(list2.get(1)[2]);

		transaction.closeConnection();

	}

	/**
	 * @param transaction
	 * @param empDTO
	 * @return
	 * @throws Exception
	 */
	private static List<EmpDTO> select(Transaction transaction, EmpDTO empDTO) throws Exception {

		String EMP_NM = empDTO.getEmpNm();

		String SQL = "SELECT EMP_N, EMP_NM, HP_N, DEPT_N FROM EMP ";

		if (!"".equals(EMP_NM)) {
			SQL = SQL + " WHERE EMP_NM = '" + EMP_NM + "'";
		}

		System.out.println(SQL);

		Statement ps = transaction.createStatement();

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

		return list;

	}

	/**
	 * @param transaction
	 * @return
	 * @throws Exception
	 */
	private static List<String[]> selectForCollection(Transaction transaction) throws Exception {

		Box box = BoxContext.getThread();

		String EMP_NM = box.s("EMP_NM");

		String SQL = "SELECT EMP_N, EMP_NM, HP_N, DEPT_N FROM EMP ";

		if (!"".equals(EMP_NM)) {
			SQL = SQL + " WHERE EMP_NM = '" + EMP_NM + "'";
		}

		System.out.println(SQL);

		Statement ps = transaction.createStatement();

		ResultSet rs = ps.executeQuery(SQL);

		ArrayList<String[]> table = new ArrayList<String[]>();

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		String[] column = new String[columnCount];

		for (int i = 0; i < column.length; i++) {
			column[i] = metaData.getColumnName(i + 1);
		}
		table.add(column);

		while (rs.next()) {
			String[] recode = new String[columnCount];

			for (int i = 0; i < column.length; i++) {
				recode[i] = rs.getString(column[i]);
			}
			table.add(recode);
		}

		return table;

	}

	/**
	 * @param transaction
	 * @param list
	 * @param pEmpDTO
	 * @throws Exception
	 */
	private static void update(Transaction transaction, List<EmpDTO> list, EmpDTO pEmpDTO) throws Exception {

		for (int i = 0; i < list.size(); i++) {

			EmpDTO thisEmpDTO = list.get(i);
			String EMP_N = thisEmpDTO.getEmpN();
			String HP_N = pEmpDTO.getHpN();

			String SQL = "UPDATE EMP SET HP_N = ? WHERE EMP_N = ? ";
			PreparedStatement ps = transaction.prepareStatement(SQL);
			ps.setString(1, HP_N);
			ps.setString(2, EMP_N);

			System.out.println(SQL);

			int updateCnt = ps.executeUpdate();
			if (updateCnt != 1) {
				throw new Exception();
			}

		}

	}

}
