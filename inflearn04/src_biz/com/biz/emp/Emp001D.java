package com.biz.emp;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;

/**
 * @author zero
 *
 */
public class Emp001D {

	/**
	 * 
	 */
	private Sql sql = new Sql(this);

	/**
	 * @return
	 * @throws SQLException
	 */
	Table emp00110() throws SQLException {
		return sql.getTable("EMP001D_01");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	int emp00120() throws SQLException {
		return sql.executeSql("EMP001D_02");
	}

}
