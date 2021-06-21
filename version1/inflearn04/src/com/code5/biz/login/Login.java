package com.code5.biz.login;

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
	public String login() throws Exception {
		Box box = BoxContext.getThread();
		boolean isLogin = box.getBoolean("isLogin");

		if (isLogin) {

			return MasterController.execute("emp00110");

			// Emp001 emp001 = new Emp001();
			// return emp001.emp00110();

		}

		return loginView();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String loginView() throws Exception {
		return "loginView";
	}

}
