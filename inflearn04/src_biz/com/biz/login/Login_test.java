package com.biz.login;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Login_test {

	/**
	 * @return
	 * @throws Exception
	 */
	@Test
	public void test_login_true() throws Exception {

		Box box = BoxContext.getThread();
		box.put("isLogin", true);

		Login login = new Login();
		login.login();

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_login_false() throws Exception {

		Box box = BoxContext.getThread();
		box.put("isLogin", false);

		Login login = new Login();
		login.login();
	}

}
