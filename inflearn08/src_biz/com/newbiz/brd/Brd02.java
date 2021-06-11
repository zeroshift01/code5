package com.newbiz.brd;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BizControllerStartExecute;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.ServiceAnnotation;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class Brd02 implements BizController, BizControllerStartExecute {

	/**
	 *
	 */
	public String start() throws Exception {

		Box box = BoxContext.getBox();

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
	 * 
	 *                   forceDelete
	 */
	@ServiceAnnotation(auth = "A0")
	public String brd02030() throws Exception {

		execute("callUpdate");

		execute("delete");

		return execute("callList");

	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   allDelete
	 */
	@ServiceAnnotation(auth = "A0")
	public String brd02010() throws Exception {

		BrdD dao = new BrdD();
		dao.brd01031();

		TransactionContext.commit();

		Box box = BoxContext.getBox();
		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");

		return execute("callList");
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   allUpdate
	 */
	@ServiceAnnotation(auth = "A0")
	public String brd02020() throws Exception {

		Box box = BoxContext.getBox();
		Table input = box.createTableByKey("TOKEN_N");

		BrdD dao = new BrdD();
		for (int i = 0; i < input.size(); i++) {
			box.putFromTable(input, i);
			dao.brd02020();
		}

		TransactionContext.commit();

		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");

		return execute("callList");
	}
}
