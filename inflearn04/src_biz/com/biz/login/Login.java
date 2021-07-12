package com.biz.login;

import com.code5.fw.data.Box;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;

/**
 * @author zero
 *
 */
public class Login implements BizController {

	/**
	 * @return
	 * @throws Exception
	 */
	public String exeLogin() throws Exception {
		Box box = BoxContext.get();
		boolean isLogin = box.getBoolean("isLogin");

		if (isLogin) {

			return MasterController.execute("callList");

		}

		return callLogin();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String callLogin() throws Exception {
		return "/WEB-INF/classes/com/biz/login/jsp/login.jsp";
	}

}
