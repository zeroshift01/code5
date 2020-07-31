package com.code5.biz.exe001;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * 
 * 결제 처리
 * 
 * @author seuk
 * 
 */
public class Exe001D {

	private static String FROM_NO_01 = "EXE001D_01";
	private static String FROM_NO_02 = "EXE001D_02";
	private static String FROM_NO_03 = "EXE001D_03";
	private static String FROM_NO_04 = "EXE001D_04";

	/**
	 * 
	 */
	private Sql sql = Sql.getSql();

	/**
	 * 
	 * 승인 요청 정보를 입력한다.
	 * 
	 * @throws SQLException
	 */
	void INSERT_BZ_ALNC() throws SQLException {
		sql.executeSql(FROM_NO_01);
	}

	/**
	 * 
	 * 승인 결과를 업데이트 한다.
	 * 
	 * @throws SQLException
	 */
	int UPDATE_BZ_ALNC() throws SQLException {
		return sql.executeSql(FROM_NO_02);
	}

	/**
	 * 
	 * 결제 리스트 조회
	 * 
	 * @return
	 * @throws SQLException
	 */
	Table SELECT_BZ_ALNC() throws SQLException {
		return sql.getTable(FROM_NO_03);
	}

	/**
	 * 
	 * 
	 * 카드번호를 검색어로 결제 리스트 조회
	 * 
	 * @return
	 * @throws SQLException
	 */
	Table SELECT_BZ_ALNC_WHERE_CRD_N() throws SQLException {
		return sql.getTable(FROM_NO_04);
	}

}
