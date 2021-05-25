package com.code5.biz.login;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author zero
 *
 */
public class LoginD {

	/**
	 * 
	 */
	private Sql sql = new Sql(this);

	/**
	 * 
	 */
	public static LoginD getLoginD() {
		return new LoginD();
	}

	private static String FORM_NO_01 = "LOGIND_01";
	private static String FORM_NO_02 = "LOGIND_02";
	private static String FORM_NO_03 = "LOGIND_03";

	/**
	 * 
	 * ID 를 기준으로 BZ_ID 조회
	 * 
	 * @return
	 * @throws SQLException
	 */
	Table login_01() throws SQLException {
		return sql.getTable(FORM_NO_01);
	}

	/**
	 * 
	 * 실패회수 증가
	 * 
	 * @return
	 * @throws SQLException
	 */
	int login_02() throws SQLException {
		return sql.executeSql(FORM_NO_02);
	}

	/**
	 * 
	 * 로그인 성공 처리
	 * 
	 * @return
	 * @throws SQLException
	 */
	int login_03() throws SQLException {
		return sql.executeSql(FORM_NO_03);
	}
}
