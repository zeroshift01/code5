package com.biz.login;

import com.biz.mng001.Mng001;
import com.code5.fw.web.BizController;

/**
 * 
 * 로그인 비즈니스 코드
 * 
 * @author seuk
 * 
 */
public class Login implements BizController {

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
			return loginView();
		}

		return (new Mng001()).mng00110();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		return loginView();
	}
}
