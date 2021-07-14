package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.ServiceAnnotation;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class BoardAdmin implements BizController {

	/**
	 *
	 */
	private String start() throws Exception {

		Box box = BoxContext.get();

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

		execute("callUpdate");

		execute("delete");

		return execute("callList");

	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(auth = "A0")
	public String allDelete() throws Exception {

		start();

		BoardD dao = new BoardD();
		dao.deleteAll();

		TransactionContext.commit();

		Box box = BoxContext.get();
		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");

		return execute("callList");
	}

	@ServiceAnnotation(auth = "A0")
	public String allUpdate() throws Exception {

		Box box = BoxContext.get();
		Table input = box.createTableByKey("TOKEN_N");

		BoardD dao = new BoardD();
		for (int i = 0; i < input.size(); i++) {
			box.putFromTable(input, i);
			dao.updateAll();
		}

		TransactionContext.commit();

		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");

		return execute("callList");
	}

}
