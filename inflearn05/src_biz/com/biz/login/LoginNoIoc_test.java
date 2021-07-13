package com.biz.login;

import static org.junit.Assert.assertEquals;

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
		String JSP = login.exeLogin();

		assertEquals("/WEB-INF/classes/com/biz/board/jsp/list.jsp", JSP);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_login_false() throws Exception {

		Box box = BoxContext.get();
		box.put("isLogin", false);

		LoginNoIoc login = new LoginNoIoc();
		String JSP = login.exeLogin();

		assertEquals("/WEB-INF/classes/com/biz/login/jsp/login.jsp", JSP);
	}

}
