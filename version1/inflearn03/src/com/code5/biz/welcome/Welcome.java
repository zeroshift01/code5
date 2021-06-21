package com.code5.biz.welcome;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Welcome {

	/**
	 * @param request
	 * @return
	 */
	public String execute() {

		Box box = BoxContext.getThread();

		String name = (String) box.get("name");
		// String name = box.getString("name");
		// String name = box.s("name");

		String ret = "welcome (" + name + ")";

		box.put("ret", ret);

		return "/WEB-INF/classes/com/code5/biz/welcome/welcome.jsp";
	}

}
