package com.code5.biz.mng001;

import java.sql.SQLException;

import com.code5.fw.data.Box;
import com.code5.fw.db.SqlRunner;

/**
 * 
 * FW 테이블 관리
 * 
 * @author seuk
 * 
 */
public class Mng001D {

	/**
	 * [1]
	 */
	private SqlRunner sql = SqlRunner.getSqlRunner();

	// [2]
	private static String FORM_NO_01 = "MNG001D_01";
	private static String FORM_NO_02 = "MNG001D_02";
	private static String FORM_NO_03 = "MNG001D_03";
	private static String FORM_NO_04 = "MNG001D_04";
	private static String FORM_NO_05 = "MNG001D_05";
	private static String FORM_NO_06 = "MNG001D_06";
	private static String FORM_NO_07 = "MNG001D_07";
	private static String FORM_NO_08 = "MNG001D_08";
	private static String FORM_NO_09 = "MNG001D_09";

	/**
	 * @return
	 * @throws SQLException
	 */
	Box mng00110() throws SQLException {
		return sql.getTable(FORM_NO_01).getBox();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int mng00111_1() throws SQLException {
		return sql.executeSql(FORM_NO_02);
	}

	/**
	 * @throws SQLException
	 */
	void mng00111_2() throws SQLException {
		sql.executeSql(FORM_NO_03);
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	Box mng00120() throws SQLException {
		return sql.getTable(FORM_NO_04).getBox();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int mng00121_1() throws SQLException {
		return sql.executeSql(FORM_NO_05);
	}

	/**
	 * @throws SQLException
	 */
	void mng00121_2() throws SQLException {
		sql.executeSql(FORM_NO_06);
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	Box mng00130() throws SQLException {
		return sql.getTable(FORM_NO_07).getBox();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int mng00131_1() throws SQLException {
		return sql.executeSql(FORM_NO_08);
	}

	/**
	 * @throws SQLException
	 */
	void mng00131_2() throws SQLException {
		sql.executeSql(FORM_NO_09);
	}

}
