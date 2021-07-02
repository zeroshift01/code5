package com.code5.fw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class CreateConncectionByJDBC_test {

	// sqlite 소개 : 서버리스, 관계형DB, 트랜잭션기능 제공, 영속성에 제한이 있음
	// jdbc:sqlite:/a/b/code5.db, InitYaml 에서 제공

	// 개발은 sqlite, 운영은 상용 DBMS 사용

	// 객체지향 Connection 생성과 클래스지향 Connection 생성 비교

	// 실행 시점에 특정 클래스를 선택해 사용하면 되기 때문에
	// 클래스지향 Connection 생성 코드를 사용

	private String url = InitYaml.get().s("Transaction_SQLITE_JDBC_CODE5_DEV.URL");

	/**
	 * @throws Exception
	 */
	@Test
	public void createConnectionByDriverManager() throws Exception {

		String className = "org.sqlite.JDBC";

		// org.sqlite.JDBC static 생성자 호출
		// DriverInfo 자료구조에 저장
		Class.forName(className);

		// DriverInfo 자료구조 사용
		Connection conn = DriverManager.getConnection(url);
		conn.setAutoCommit(true);

	}

	/**
	 * @return
	 * @throws SQLException
	 */
	@Test
	public void createConnectionBySQLite() throws Exception {

		org.sqlite.SQLiteConfig config = new org.sqlite.SQLiteConfig();
		Properties properties = config.toProperties();

		Connection conn = org.sqlite.JDBC.createConnection(url, properties);
		conn.setAutoCommit(true);

	}

}
