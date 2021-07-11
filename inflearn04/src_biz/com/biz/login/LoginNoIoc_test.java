package com.biz.login;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class LoginNoIoc_test {

	/**
	 * @return
	 * @throws Exception
	 */
	@Test
	public void test_login_true() throws Exception {

		Box box = BoxContext.get();
		box.put("isLogin", true);

		LoginNoIoc login = new LoginNoIoc();
		String key = login.exeLogin();

		System.out.println(key);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_login_false() throws Exception {

		Box box = BoxContext.get();
		box.put("isLogin", false);

		LoginNoIoc login = new LoginNoIoc();
		String key = login.exeLogin();

		System.out.println(key);
	}

}
