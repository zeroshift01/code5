package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Emp001_test extends TestCase {

	/**
	 * @throws Exception
	 */
	public void xtest_emp00110() throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_NM", "ABC");

		Emp001 emp001 = new Emp001();
		emp001.emp00110();

	}

	/**
	 * @throws Exception
	 */
	public void test_emp00120() throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_NM", "ABC");
		box.put("HP_N", "010-2222-3333");

		Emp001 emp001 = new Emp001();
		emp001.emp00120();

		TransactionContext.commit();

	}

}
