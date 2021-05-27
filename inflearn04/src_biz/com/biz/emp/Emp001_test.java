package com.biz.emp;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class Emp001_test {

	/**
	 * @throws Exception
	 */
	@Test
	public void test_emp00110() throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_NM", "ABC");

		Emp001 emp001 = new Emp001();
		emp001.emp00110();

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_emp00120() throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_NM", "ABC");
		box.put("HP_N", "010-2222-3333");

		Emp001 emp001 = new Emp001();
		emp001.emp00120();

		TransactionContext.commit();

	}

}
