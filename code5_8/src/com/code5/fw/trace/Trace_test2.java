package com.code5.fw.trace;

import com.code5.fw.db.SqlRunner;
import com.code5.fw.db.Transaction;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class Trace_test2 extends TestCase {

	/**
	 * 
	 */
	public void test_단일쓰레드_로그기록() throws Exception {

		Trace trace = new Trace(this);
		trace.write("xxx");
		
		SqlRunner.getSqlRunner().executeSql("xxx");

	}

}