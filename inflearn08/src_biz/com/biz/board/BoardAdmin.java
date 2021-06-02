package com.biz.board;

import com.code5.fw.data.Box;
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
public class BoardAdmin implements BizController, BizControllerStartExecute {

	/**
	 *
	 */
	public String start() throws Exception {

		Box box = BoxContext.getThread();

		String service = box.s(Box.KEY_SERVICE);

		if (!service.equals("allDelete")) {
			return null;
		}

		String remoteAddr = box.s(Box.KEY_REMOTE_ADDR);

		String adminIP = "0:0:0:0:0:0:0:1";

		if (!remoteAddr.equals(adminIP)) {
			box.setAlertMsg("알수없는 오류가 발생했습니다.");
			return "error";
		}

		return null;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(auth = "A0")
	public String forceDelete() throws Exception {

		return MasterController.execute("exeDelete");

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

		return MasterController.execute("callList");
	}

	@ServiceAnnotation(auth = "A0")
	public String allUpdate() throws Exception {

		Box box = BoxContext.getThread();
		Table input = box.createTableByKey("TOKEN_N");

		BoardD dao = new BoardD();
		for (int i = 0; i < input.size(); i++) {
			box.putFromTable(input, i);
			dao.updateAll();
		}

		TransactionContext.commit();

		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");

		return MasterController.execute("callList");
	}

}
