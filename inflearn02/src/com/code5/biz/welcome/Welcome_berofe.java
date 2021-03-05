package com.code5.biz.welcome;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zero
 *
 */
public class Welcome_berofe {

	/**
	 * @param request
	 * @return
	 */
	public String execute(HttpServletRequest request) {

		String name = request.getParameter("name");

		String ret = "welcome (" + name + ")";

		request.setAttribute("ret", ret);

		return "/WEB-INF/classes/com/code5/biz/welcome/welcome.jsp";
	}

}
