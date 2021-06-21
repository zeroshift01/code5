package com.code5.biz.welcome;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Welcome_test extends TestCase {

	/**
	 * @throws Exception
	 */
	public void test_01() throws Exception {

		String NAME = "abcd";

		Box box = BoxContext.getThread();
		box.put("NAME", NAME);

		Welcome welcome = new Welcome();
		welcome.execute();

		String MSG = box.s("MSG");

		assertEquals("WELCOME = " + NAME, MSG);

	}

}
