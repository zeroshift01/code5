package com.biz.welcome;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Welcome_test {

	/**
	 * @throws Exception
	 */
	@Test
	public void test_fin() throws Exception {

		String NAME = "abcd";

		Box box = BoxContext.getThread();
		box.put("NAME", NAME);

		Welcome welcome = new Welcome();
		welcome.execute();

		String MSG = (String) box.get("MSG");

		assertEquals("WELCOME = " + NAME, MSG);

	}

}
