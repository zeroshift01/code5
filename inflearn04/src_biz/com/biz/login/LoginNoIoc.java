package com.biz.login;

import com.biz.board.BoardNoIoc;
import com.code5.fw.data.Box;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class LoginNoIoc implements BizController {

	/**
	 * @return
	 * @throws Exception
	 */
	public String exeLogin() throws Exception {

		Box box = BoxContext.get();
		boolean isLogin = box.getBoolean("isLogin");

		if (isLogin) {

			BoardNoIoc board = new BoardNoIoc();
			String key = board.callList();
			return key;

		}

		return callLogin();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String callLogin() throws Exception {
		// stub
		return "login";
	}

}
