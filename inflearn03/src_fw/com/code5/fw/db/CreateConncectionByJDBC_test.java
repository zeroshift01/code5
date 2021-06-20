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

	// 1. sqlite : 서버리스, 관계형DB, 트랜잭션지원

	// 2. url : sqlite 실행에 필요한 데이터 파일 위치

	// 3. 객체지향관점 Connection 생성

	// 4. 개발자코드로 Connection 생성

	// 5. 우리는 개발자코드로 Connection 을 생성합니다.

	// jdbc:sqlite:/a/b/code5.db
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
