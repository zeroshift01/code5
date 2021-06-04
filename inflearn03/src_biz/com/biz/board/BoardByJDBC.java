package com.biz.board;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.code5.fw.data.Box;
import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC_CODE5_DEV;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class BoardByJDBC {

	/**
	 * @param x
	 * @throws Exception
	 */
	public static void main(String[] x) throws Exception {

		String FIND_STR = "A";

		BoardDTO input = new BoardDTO();
		input.setFindStr(FIND_STR);

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
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private static List<BoardDTO> select(Transaction transaction, BoardDTO input) throws Exception {

		String findStr = input.getFindStr();

		String SQL = "SELECT  ";
		SQL += "/n N ";
		SQL += "/n , TITLE ";
		SQL += "/n , EM ";
		SQL += "/n FROM ";
		SQL += "/n BZ_BOARD ";
		SQL += "/n WHERE 1 = 1 ";

		if (!"".equals(findStr)) {
			SQL += "/n AND TITLE LIKE '%'||" + findStr + "||'%' ";
		}

		SQL += "/n AND DEL_Y IS NULL ";
		SQL += "/n ORDER BY N DESC ";

		System.out.println(SQL);

		Statement ps = transaction.

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

class BoardDTO {

	// 엔티티에 속하지 않은 에트리뷰트
	private String findStr = null;

	private String n = null;

	private String title = null;

	private String txt = null;

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getTitle() {
		return title;
	}

	public String getFindStr() {
		return findStr;
	}

	public void setFindStr(String findStr) {
		this.findStr = findStr;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

}