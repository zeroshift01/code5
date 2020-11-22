package com.biz.login;

import com.code5.fw.web.BizController;
import com.code5.fw.web.MasterController;

/**
 * 
 * 로그인 비즈니스 코드
 * 
 * @author seuk
 * 
 */
public class Login_1 implements BizController {

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

		boolean failLogin = true;

		if (failLogin) {
			// return loginView();
			return MasterController.execute("loginView");
		}

		// (new Mng001()).mng00110()
		return MasterController.execute("loginmng00110");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		return MasterController.execute("loginView");
	}
}
