package com.biz.login;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.SqlRunner;

/**
 * 
 * FW 테이블 관리
 * 
 * @author seuk
 * 
 */
public class LoginD {

	private static String FORM_NO_01 = "LOGIND_01";
	private static String FORM_NO_02 = "LOGIND_02";
	private static String FORM_NO_03 = "LOGIND_03";

	/**
	 * 
	 */
	private SqlRunner sql = SqlRunner.getSqlRunner();

	/**
	 * @return
	 * 
	 *         사용자 정보를 가져온다.
	 */
	Table login_01() throws SQLException {
		return sql.getTable(FORM_NO_01);
	}

	/**
	 * @return
	 * @throws SQLException
	 * 
	 *                      로그인 실패 업데이트
	 */
	int login_02() throws SQLException {
		return sql.executeSql(FORM_NO_02);
	}

	/**
	 * @return
	 * @throws SQLException
	 * 
	 *                      로그인 성공 업데이트
	 */
	int login_03() throws SQLException {
		return sql.executeSql(FORM_NO_03);
	}

}
