package com.code5.biz.welcome;

import javax.servlet.http.HttpServletRequest;

import com.code5.fw.web.Box;
import com.code5.fw.web.BoxContext;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class Welcome_test extends TestCase {

	/**
	 * @throws Exception
	 */
	public void test_01() throws Exception {

		// TODO [1]
		HttpServletRequest request = null;

		String name = "abcd";
		request.setAttribute("name", name);

		Welcome w = new Welcome();
		String jsp = w.service(request);

		String welcome = (String) request.getAttribute("welcome");

		assertEquals("welcome " + name, welcome);

	}

	/**
	 * @throws Exception
	 */
	public void test_02() throws Exception {

		// TODO [1]
		Box box = BoxContext.getThread();

		// TODO [2]
		String name = "abcd";
		box.put("name", name);

		// TODO [3]
		Welcome w = new Welcome();
		w.service();

		String welcome = box.s("welcome");
		
		System.out.println(welcome);

		assertEquals("welcome " + name, welcome);

	}

}
