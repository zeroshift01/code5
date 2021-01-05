package com.code5.biz.login;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.security.CryptPin;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;

/**
 * @author seuk
 *
 */
public class Login implements BizController {

	/**
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {

		Box box = BoxContext.getThread();

		String ID = box.s("ID");
		String PIN = box.s("PIN");

		// TODO [1]
		PIN = CryptPin.cryptPin(PIN, ID);

		LoginD dao = LoginD.getLoginD();

		Table user = dao.login_01();

		if (user.size() != 1) {
			// TODO [2-1]
			box.put("ret", "아이디가 없거나 패스워드가 일치하지 않습니다.");
			return MasterController.execute("loginView");
		}

		Box thisUser = user.getBox();

		if (!PIN.equals(thisUser.s("PIN"))) {
			// TODO [2-2]
			box.put("ret", "아이디가 없거나 패스워드가 일치하지 않습니다.");

			if (dao.login_02() != 1) {
				throw new Exception();
			}

			return MasterController.execute("loginView");
		}

		int FAIL_CNT = thisUser.getInt("FAIL_CNT");
		if (FAIL_CNT > 5) {
			// TODO [3]
			box.put("ret", "패스워드를 5회 이상 실패하였습니다.");
			return MasterController.execute("loginView");
		}

		String AUTH = thisUser.s("AUTH");
		String IP = box.s(Box.KEY_REMOTE_ADDR);

		// TODO [4]
		SessionB sessionB = new SessionB(ID, AUTH, IP);
		box.setSessionB(sessionB);

		// TODO [5]
		if (dao.login_03() != 1) {
			throw new Exception();
		}

		return MasterController.execute("emp00101");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {

		BoxContext.getThread().setSessionB(null);
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
