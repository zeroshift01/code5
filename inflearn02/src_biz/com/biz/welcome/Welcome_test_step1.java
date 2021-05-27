package com.biz.welcome;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

/**
 * @author zero
 *
 */
public class Welcome_test_step1 {

	/**
	 * @throws Exception
	 */
	@Test
	public void test_01() throws Exception {

		String NAME = "abcd";

		HttpServletRequest request = null;

		// request.getParameter("K");
		// request.getAttribute("K");
		// request.setAttribute("K","V");

		// request.setParameter("K", "V");

		Welcome_step1 welcome = new Welcome_step1();
		welcome.execute(request);

		// String MSG = request.getAttribute("MSG");
		String MSG = "?";

		assertEquals("WELCOME = " + NAME, MSG);

	}

}
