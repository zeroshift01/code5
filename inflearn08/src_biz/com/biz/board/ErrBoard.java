package com.biz.board;

import java.sql.SQLException;

import com.code5.fw.data.Box;
import com.code5.fw.web.AuthException;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.LoginException;

/**
 * @author zero
 *
 */
public class ErrBoard implements BizController {
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String errStub1() throws Exception {

		Box box = BoxContext.getThread();
		if ("1".equals(box.s("err"))) {
			box.setAlertMsg("오류가 발생했습니다.");
			throw new Exception();
		}

		return "nullView";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String errStub2() throws Exception {

		Box box = BoxContext.getThread();
		
		if ("1".equals(box.s("err"))) {
			box.setAlertMsg("오류가 발생했습니다.");
			throw new Exception();
		}

		if ("2".equals(box.s("err"))) {
			throw new Exception("일반적인오류");
		}

		if ("3".equals(box.s("err"))) {
			throw new SQLException("SQL 오류");
		}

		if ("4".equals(box.s("err"))) {
			throw new AuthException();
		}

		if ("5".equals(box.s("err"))) {
			throw new LoginException();
		}

		return "nullView";
	}
}