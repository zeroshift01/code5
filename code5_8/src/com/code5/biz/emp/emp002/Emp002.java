package com.code5.biz.emp.emp002;

import com.code5.fw.data.Box;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;

/**
 * @author seuk
 *
 */
public class Emp002 implements BizController {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String emp00201() throws Exception {

		Box box = BoxContext.getThread();
		if (box.getBoolean("err")) {
			box.setAlertMsg("오류가 발생했습니다.");
			throw new Exception();
		}

		return "emp00201";
	}

}
