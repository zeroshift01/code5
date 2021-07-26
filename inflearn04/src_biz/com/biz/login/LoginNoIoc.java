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

			// 기다려야 하고
			// 변경이 있을때 같이 수정해야 하고
			// 컴파일이 필요함

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
		return "/WEB-INF/classes/com/biz/login/jsp/login.jsp";
	}

}
