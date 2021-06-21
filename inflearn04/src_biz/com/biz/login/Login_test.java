package com.biz.login;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;

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

		Box box = BoxContext.get();
		box.put("isLogin", true);

		MasterController.execute("exeLogin");
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_login_false() throws Exception {

		Box box = BoxContext.get();
		box.put("isLogin", false);

		MasterController.execute("exeLogin");
	}

}
