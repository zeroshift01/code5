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

		String NAME = box.s("NAME");
		// String NAME = (String) box.get("NAME");

		String MSG = "WELCOME = " + NAME;

		box.put("MSG", MSG);

		return "/WEB-INF/classes/com/code5/biz/welcome/welcome.jsp";
	}

}
