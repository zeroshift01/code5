package com.code5.biz.welcome;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Welcome {

	/**
	 * @return
	 */
	public String execute() {

		Box box = BoxContext.getThread();

		// String NAME = request.getParameter("NAME");
		String NAME = box.getString("NAME");

		String MSG = "WELCOME = " + NAME;

		// request.setAttribute("MSG", MSG);
		box.put("MSG", MSG);

		return "/WEB-INF/classes/com/code5/biz/welcome/welcome.jsp";
	}

}
