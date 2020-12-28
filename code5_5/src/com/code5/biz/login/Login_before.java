package com.code5.biz.login;

import com.code5.biz.emp.Emp001;
import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author seuk
 *
 */
public class Login_before {

	/**
	 * @return
	 * @throws Exception
	 */
	public String loginView() throws Exception {
		return "loginView";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {

		Box box = BoxContext.getThread();

		// TODO [1]
		boolean isLogin = box.getBoolean("isLogin");

		if (isLogin) {
			// TODO [2]
			return loginView();
		}

		// TODO [3]
		Emp001 emp001 = new Emp001();
		return emp001.emp00101();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		return loginView();
	}
}
