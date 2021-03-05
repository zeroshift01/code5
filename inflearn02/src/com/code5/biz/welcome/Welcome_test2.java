package com.code5.biz.welcome;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Welcome_test2 extends TestCase {

	/**
	 * @throws Exception
	 */
	public void test_01() throws Exception {

		String name = "zero";

		Box box = BoxContext.getThread();

		box.put("name", name);

		Welcome w = new Welcome();
		w.execute();

		String ret = box.s("ret");

		assertEquals("welcome (" + name + ")", ret);

	}

}
