package com.code5.biz.welcome;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Welcome_test_step01 extends TestCase {

	/**
	 * @throws Exception
	 */
	public void test_01() throws Exception {

		String NAME = "abcd";

		HttpServletRequest request = null;

		// request.setParameter("name", name);

		Welcome_step01 welcome = new Welcome_step01();
		welcome.execute(request);

		String MSG = (String) request.getAttribute("MSG");

		assertEquals("WELCOME = " + NAME, MSG);

	}

}
