package com.code5.biz.com003;

import com.code5.fw.data.Table;
import com.code5.fw.web.Box;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.SessionB;

/**
 * 
 * 로그인
 * 
 * @author seuk
 * 
 */
public class Com003 {

	/**
	 * 
	 * 로그인 화면
	 * 
	 * @param box
	 * @return
	 */
	public String com00310() throws Exception {
		return "com00310";
	}

	/**
	 * 로그인 처리
	 * 
	 * @return
	 */
	public String com00311() throws Exception {

		Box box = Box.getThread();

		// String ID = box.s("ID");
		String PIN = box.s("PIN");

		com003D dao = new com003D();

		Table com00302 = dao.com00302();

		if (com00302.size() != 1) {
			box.put("MSG", "아이디가 없거나 패스워드가 일치하지 않습니다.");
			return MasterController.execute("com00301");
		}

		Box thisUser = com00302.getBox();

		if (!PIN.equals(thisUser.s("PIN"))) {
			box.put("MSG", "아이디가 없거나 패스워드가 일치하지 않습니다.");

			if (dao.com00302_2() != 1) {
				throw new Exception();
			}

			return MasterController.execute("com00301");
		}

		String ID = thisUser.s("ID");
		String AUTH = thisUser.s("AUTH");
		// String ID = com00302.s("ID");

		SessionB user = new SessionB(ID, AUTH);
		box.setSessionB(user);

		return MasterController.execute("com00202");

	}

	/**
	 * 
	 * 로그인 후 화면
	 * 
	 * @return
	 * @throws Exception
	 */
	public String com00320() throws Exception {
		return "com00320";
	}
}
