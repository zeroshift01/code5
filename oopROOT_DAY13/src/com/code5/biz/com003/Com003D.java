package com.code5.biz.com003;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author seuk
 * 
 */
public class Com003D {

	private static String FORM_NO_01 = "COM003D_01";
	private static String FORM_NO_02 = "COM003D_02";
	private static String FORM_NO_03 = "COM003D_03";

	/**
	 * 
	 * ID 를 기준으로 BZ_ID 조회
	 * 
	 * @return
	 * @throws SQLException
	 */
	Table com00311_1() throws SQLException {
		return Sql.getSql().getTable(FORM_NO_01);
	}

	/**
	 * 
	 * 실패회수 증가
	 * 
	 * @return
	 * @throws SQLException
	 */
	int com00311_2() throws SQLException {
		return Sql.getSql().executeSql(FORM_NO_02);
	}

	/**
	 * 
	 * 로그인 성공 처리
	 * 
	 * @return
	 * @throws SQLException
	 */
	int com00311_3() throws SQLException {
		return Sql.getSql().executeSql(FORM_NO_03);
	}

}
