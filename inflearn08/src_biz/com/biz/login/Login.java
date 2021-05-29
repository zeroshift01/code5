package com.biz.login;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.security.CryptPin;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.ServiceAnnotation;

/**
 * @author zero
 *
 */
public class Login implements BizController {

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(isLogin = false)
	public String exeLogin() throws Exception {

		Box box = BoxContext.getThread();

		String ID = box.s("ID");
		String PIN = box.s("PIN");

		PIN = CryptPin.cryptPin(PIN, ID);

		LoginD dao = LoginD.getLoginD();

		Table user = dao.login_01();

		if (user.size() != 1) {
			box.put("ret", "아이디가 없거나 패스워드가 일치하지 않습니다.");
			return MasterController.execute("callLogin");
		}

		Box thisUser = user.getBox();

		if (!PIN.equals(thisUser.s("PIN"))) {
			box.put("ret", "아이디가 없거나 패스워드가 일치하지 않습니다.");

			if (dao.login_02() != 1) {
				throw new Exception();
			}

			return MasterController.execute("callLogin");
		}

		int FAIL_CNT = thisUser.getInt("FAIL_CNT");
		if (FAIL_CNT > 5) {
			box.put("ret", "패스워드를 5회 이상 실패하였습니다.");
			return MasterController.execute("callLogin");
		}

		String AUTH = thisUser.s("AUTH");
		String IP = box.s(Box.KEY_REMOTE_ADDR);

		SessionB sessionB = box.getSessionB();
		sessionB.set(ID, AUTH, IP);
		box.setSessionB(sessionB);

		if (dao.login_03() != 1) {
			throw new Exception();
		}

		return MasterController.execute("callList");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(isLogin = false)
	public String exeLogout() throws Exception {
		BoxContext.getThread().setSessionB(null);
		return callLogin();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(isLogin = false)
	public String callLogin() throws Exception {
		return "login";
	}

}
