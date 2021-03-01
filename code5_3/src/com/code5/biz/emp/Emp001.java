package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Emp001 {

	/**
	 * @return
	 * @throws Exception
	 */
	public String emp00101() throws Exception {

		Box box = BoxContext.getThread();

		Emp001D dao = new Emp001D();
		Table list = dao.emp00101();
		box.put("list", list);

		return "/WEB-INF/classes/com/code5/biz/emp/emp00101.jsp";

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String emp00102() throws Exception {

		Emp001D dao = new Emp001D();
		int cnt = dao.emp00102();

		if (cnt != 1) {
			throw new Exception();

		}

		return emp00101();
	}
}
