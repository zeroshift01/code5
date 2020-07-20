// [1]
package com.code5.biz.comm003;

import com.code5.fw.data.Table;
import com.code5.fw.web.Box;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.SessionB;

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

		Table comm00302 = dao.comm00302();

		if (comm00302.size() != 1) {
			box.put("MSG", "아이디나 패스워드가 존재하지 않습니다.");
			return MasterController.execute("comm00301");
		}

		Box thisUser = comm00302.getBox();

		if (!PIN.equals(thisUser.s("PIN"))) {
			box.put("MSG", "아이디나 패스워드가 존재하지 않습니다.");

			if (dao.comm00302_2() != 1) {
				throw new Exception();
			}

			return MasterController.execute("comm00301");
		}

		String ID = thisUser.s("ID");
		String AUTH = thisUser.s("AUTH");
		// String ID = comm00302.s("ID");

		SessionB user = new SessionB(ID, AUTH);
		box.setSessionB(user);

		return MasterController.execute("comm00202");

	}

}
