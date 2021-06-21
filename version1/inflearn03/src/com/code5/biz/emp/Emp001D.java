package com.code5.biz.emp;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.SqlRunner;

/**
 * @author zero
 *
 */
public class Emp001D {

	/**
	 * 
	 */
	private SqlRunner sql = SqlRunner.getSqlRunner();

	/**
	 * @return
	 * @throws SQLException
	 */
	Table emp00101() throws SQLException {
		return sql.getTable("EMP001D_01");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int emp00102() throws SQLException {
		return sql.executeSql("EMP001D_02");
	}

}
