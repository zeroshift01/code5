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
public class BoardByJDBC {

	// InitCode5DBByDev 실행

	// SQL 의존적인 java 코드

	/**
	 * @param x
	 * @throws Exception
	 */
	public static void main(String[] x) throws Exception {

		String findStr = "ABCD";
		String em = "aa@bb.com";

		BoardDTO input = new BoardDTO();
		input.setFindStr(findStr);

		Transaction transaction = TransactionContext.get();

		List<BoardDTO> list = selectForDto(transaction, input);

		BoardDTO dto = list.get(0);

		System.out.println();
		System.out.println("BoardDTO---");
		System.out.println(dto.getN());
		System.out.println(dto.getTitle());
		System.out.println(dto.getTxt());
		System.out.println(dto.getEm());
		System.out.println("BoardDTO---");
		System.out.println();

		input.setEm(em);

		update(transaction, list, input);

		list = selectForDto(transaction, input);

		dto = list.get(0);
		System.out.println();
		System.out.println("BoardDTO---");
		System.out.println(dto.getN());
		System.out.println(dto.getTitle());
		System.out.println(dto.getTxt());
		System.out.println(dto.getEm());
		System.out.println("BoardDTO---");
		System.out.println();

		transaction.commit();

		List<String[]> list2 = selectForCollection(transaction);

		String[] recode = list2.get(1);

		System.out.println();
		System.out.println("String[]---");
		System.out.println(recode[0]);
		System.out.println(recode[1]);

		// 가독성 문제
		System.out.println(recode[3]);

		// null 문제
		System.out.println(recode[2]);

		// IndexOutOfBound
		// System.out.println(recode[4]);
		System.out.println("String[]---");
		System.out.println();

		transaction.closeConnection();

	}

	/**
	 * @param transaction
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private static List<BoardDTO> selectForDto(Transaction transaction, BoardDTO input) throws Exception {

		String findStr = input.getFindStr();

		String SQL = "SELECT  ";
		SQL += "\n N ";
		SQL += "\n , TITLE ";
		SQL += "\n , TXT ";
		SQL += "\n , EM ";
		SQL += "\n FROM ";
		SQL += "\n BZ_BOARD ";
		SQL += "\n WHERE 1 = 1 ";

		if (!"".equals(findStr)) {
			SQL += "\n AND TXT LIKE '%'||'" + findStr + "'||'%' ";
		}

		SQL += "\n AND DEL_Y IS NULL ";
		SQL += "\n ORDER BY N DESC ";

		System.out.println(SQL);

		Statement ps = transaction.createStatement();

		ResultSet rs = ps.executeQuery(SQL);

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		while (rs.next()) {

			BoardDTO dto = new BoardDTO();

			dto.setN(rs.getString("N"));
			dto.setTitle(rs.getString("TITLE"));
			dto.setEm(rs.getString("EM"));

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

		Box box = BoxContext.get();

		String findStr = box.s("FIND_STR");

		String SQL = "SELECT  ";
		SQL += "\n N ";
		SQL += "\n , TITLE ";
		SQL += "\n , TXT ";
		SQL += "\n , EM ";
		SQL += "\n FROM ";
		SQL += "\n BZ_BOARD ";
		SQL += "\n WHERE 1 = 1 ";

		if (!"".equals(findStr)) {
			SQL += "\n AND TITLE LIKE '%'||'" + findStr + "'||'%' ";
		}

		SQL += "\n AND DEL_Y IS NULL ";
		SQL += "\n ORDER BY N DESC ";

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
	 * @param input
	 * @throws Exception
	 */
	private static void update(Transaction transaction, List<BoardDTO> list, BoardDTO input) throws Exception {

		transaction.setAutoCommitFalse();

		try {

			String em = input.getEm();

			for (int i = 0; i < list.size(); i++) {

				BoardDTO thisEmpDTO = list.get(i);

				String n = thisEmpDTO.getN();

				String SQL = "UPDATE BZ_BOARD SET EM = ? WHERE N = ? ";

				PreparedStatement ps = transaction.prepareStatement(SQL);
				ps.setString(1, em);
				ps.setString(2, n);

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

	private String em = null;

	public String getEm() {
		return em;
	}

	public void setEm(String em) {
		this.em = em;
	}

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