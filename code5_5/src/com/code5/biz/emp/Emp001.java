package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;

/**
 * @author seuk
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

		Emp001D dao = new Emp001D();
		if (dao.emp00102() != 1) {
			throw new Exception();
		}

		return MasterController.execute("emp00101");
	}

}
