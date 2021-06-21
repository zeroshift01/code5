package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.TransactionContext;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Emp001_test extends TestCase {

	@Override
	protected void setUp() throws Exception {
		SessionB user = new SessionB("id_U0", "U0", "10.1.1.1");
		Box box = BoxContext.getThread();
		box.setSessionB(user);
	}

	@Override
	protected void tearDown() throws Exception {
		TransactionContext.commit();
	}

	/**
	 * @throws Exception
	 */
	public void test_emp00110() throws Exception {

		MasterController.execute("emp00110");

	}

	/**
	 * @throws Exception
	 */
	public void test_emp00120() throws Exception {

		MasterController.execute("emp00120");
	}

}
