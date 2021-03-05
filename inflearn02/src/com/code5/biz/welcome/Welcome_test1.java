package com.code5.biz.welcome;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Welcome_test1 extends TestCase {

	/**
	 * @throws Exception
	 */
	public void test_01() throws Exception {

		String name = "abcd";

		HttpServletRequest request = null;

		// request.setParameter
		// request.getParameter

		// request.setAttribute
		// request.getAttribute

		request.setParameter("name", name);

		Welcome_berofe w = new Welcome_berofe();
		w.execute(request);

		String ret = (String) request.getAttribute("ret");

		assertEquals("welcome (" + name + ")", ret);

	}

}
