package com.code5.fw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class EmpByJDBC_test {

	// 잘 만든 Transaction 을 사용한 JDBC 프로그래밍

	// select, SELECT SQL, 동적 SQL, VO 를 사용 결과 저장

	// update, UPDATE SQL, 정적 SQL, 1개 이상의 SQL 이 논리적으로 묶인 트랜잭션 단위

	// selectForCollection, VO 단점인 낮은 유연성을 해결하기 위해 컬랙션을 사용했지만 문제가 있음

	// SQL 의존문제를 해결하기 위한 방법
	// 1. SQL 과 JAVA 코드 분리
	// 2. 정적 SQL, 동적 SQL 장점을 흡수한 쉬운 사용
	// 3. 컬랙션의 단점을 해결

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

		Transaction transaction = TransactionContext.getThread();

		List<EmpDTO> list = select(transaction, pEmpDTO);

		update(transaction, list, pEmpDTO);

		transaction.closeConnection();

		transaction = new Transaction_SQLITE_JDBC_CODE5_DEV();

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

		transaction.setAutoCommitFalse();

		try {

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

			transaction.commit();

		} catch (Exception ex) {

			transaction.rollback();
		}

	}

}

class EmpDTO {

	private String empN = null;

	private String empNm = null;

	private String hpN = null;

	private String deptN = null;

	public String getEmpN() {
		return empN;
	}

	public void setEmpN(String empN) {
		this.empN = empN;
	}

	public String getEmpNm() {
		return empNm;
	}

	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}

	public String getHpN() {
		return hpN;
	}

	public void setHpN(String hpN) {
		this.hpN = hpN;
	}

	public String getDeptN() {
		return deptN;
	}

	public void setDeptN(String deptN) {
		this.deptN = deptN;
	}

}