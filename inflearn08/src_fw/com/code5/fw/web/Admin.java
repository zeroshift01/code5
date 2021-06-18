package com.code5.fw.web;

import java.util.ArrayList;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;

/**
 * @author zero
 *
 */
public class Admin implements BizController, BizControllerStartExecute {

	/**
	 *
	 */
	public String start() throws Exception {

		Box box = BoxContext.get();
		String IP = box.s(Box.KEY_REMOTE_ADDR);

		SessionB user = box.getSessionB();
		if (!IP.equals(user.getIp())) {
			throw new Exception();
		}

		boolean isAdmin = false;
		if (isAdmin) {
			box.setAlertMsg("관리자 등록을 해주세요.");
			return "nullView";
		}

		return null;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String admin001() throws Exception {
		Box box = BoxContext.get();

		for (int i = 0; i < RELOAD_LIST.size(); i++) {
			RELOAD_LIST.get(i).reload();
		}

		box.setAlertMsg("리로드가 수행되었습니다.");
		return "nullView";
	}

	/**
	 * 
	 */
	private static ArrayList<Reload> RELOAD_LIST = new ArrayList<Reload>();

	/**
	 * @param r
	 */
	public static void addReload(Reload r) {
		RELOAD_LIST.add(r);
	}

}
