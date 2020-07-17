// [1]
package com.code5.biz.comm003;

import com.code5.fw.web.Box;
import com.code5.fw.web.MasterController;

/**
 * 
 * 사용자 인증
 * 
 * @author seuk
 * 
 */
class Comm003 {

	/**
	 * 
	 * 로그인 화면 호출
	 * 
	 * @param box
	 * @return
	 */
	public String comm00301() throws Exception {
		return "comm00301";
	}

	/**
	 * 
	 * 사용자 인증, 로그인 후 화면 이동
	 * 
	 * @return
	 */
	public String comm00302() throws Exception {

		Box box = Box.getThread();

		// String ID = box.s("ID");
		String PIN = box.s("PIN");

		Comm003D dao = new Comm003D();
		Box user = dao.comm00302();

		if (!PIN.equals(user.s("PIN"))) {
			return MasterController.execute("comm00301");
		}

		return MasterController.execute("comm00202");

	}


}
