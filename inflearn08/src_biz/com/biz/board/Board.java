package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BizControllerStartExecute;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.ServiceAnnotation;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class Board implements BizController, BizControllerStartExecute {

	/**
	 *
	 */
	public String start() throws Exception {

		Box box = BoxContext.getThread();

		String service = box.s(Box.KEY_SERVICE);

		if (!service.equals("allDelete")) {
			return null;
		}

		SessionB user = box.getSessionB();

		String ip = user.getIp();
		String remoteAddr = box.s(Box.KEY_REMOTE_ADDR);

		if (!remoteAddr.equals(ip)) {
			box.setAlertMsg("알수없는 오류가 발생했습니다.");
			return "error";
		}

		return null;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(isLogin = false)
	public String list() throws Exception {

		Box box = BoxContext.getThread();

		BoardD dao = new BoardD();
		Table list = dao.list();
		box.put("list", list);

		return "list";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String write() throws Exception {

		BoardD dao = new BoardD();
		dao.write();

		return MasterController.execute("list");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {

		BoardD dao = new BoardD();
		if (dao.update() != 1) {
			throw new Exception();
		}

		return MasterController.execute("list");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {

		Box box = BoxContext.getThread();
		box.put("CHECK_ID", "OK");

		BoardD dao = new BoardD();
		if (dao.delete() != 1) {
			throw new Exception();
		}

		return MasterController.execute("list");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(auth = "A0,C0")
	public String forceDelete() throws Exception {

		BoardD dao = new BoardD();
		if (dao.delete() != 1) {
			throw new Exception();
		}

		return MasterController.execute("list");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(auth = "A0")
	public String allDelete() throws Exception {

		BoardD dao = new BoardD();
		dao.deleteAll();

		TransactionContext.commit();

		Box box = BoxContext.getThread();
		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");

		return MasterController.execute("list");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(isLogin = false)
	public String listJson() throws Exception {

		MasterController.execute("list");
		return "listjson";

	}

}
