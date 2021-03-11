package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class Emp001 implements BizController {

	/**
	 * @return
	 * @throws Exception
	 */
	public String emp00101() throws Exception {

		Box box = BoxContext.getThread();

		Emp001D dao = new Emp001D();
		Table table = dao.emp00101();
		box.put("table", table);

		return "emp00101";

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String emp00102() throws Exception {

		emp00101();

		Box box = BoxContext.getThread();
		Table table = box.getTable("table");

		Emp001D dao = new Emp001D();

		for (int i = 0; i < table.size(); i++) {

			box.put("EMP_N", table.getData("EMP_N", i));

			int updateCnt = dao.emp00102();
			if (updateCnt != 1) {
				throw new Exception();
			}
		}

		TransactionContext.commit();

		return emp00101();
	}
}
