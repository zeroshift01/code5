package com.biz.welcome;

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
		String NAME = (String) box.get("NAME");

		String MSG = "WELCOME = " + NAME;

		// request.setAttribute("MSG", MSG);
		box.put("MSG", MSG);

		return "/WEB-INF/classes/com/biz/welcome/jsp/welcome.jsp";
	}

}
