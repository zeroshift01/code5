package com.code5.fw.db;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

/**
 * @author seuk
 *
 */
public class Transaction_MYSQL_JDBC extends Transaction {

	/**
	 * 
	 */
	private Connection conn = null;

	/**
	 *
	 */
	protected Connection getConnection() throws Exception {

		if (this.conn != null) {
			return this.conn;
		}

		// 실무에서는 아이디/패스워드/url 을 코드에서 알아 볼수 없도록 처리합니다.
		Properties properties = new Properties();
		properties.put("user", "DB이름");
		properties.put("password", "패스워드");
		properties.put("url", "DBURL");

		Driver driver = (Driver) Class.forName("oracal.jdbc.driver.OracleDriver").getConstructor().newInstance();
		this.conn = driver.connect(properties.getProperty("url"), properties);

		return this.conn;

	}

}
