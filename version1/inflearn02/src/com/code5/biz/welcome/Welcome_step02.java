package com.code5.biz.welcome;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zero
 *
 */
public class Welcome_step02 {

	/**
	 * @param request
	 * @return
	 */
	public String execute(HttpServletRequest request) {

		String NAME = request.getParameter("NAME");

		String MSG = "WELCOME = " + NAME;

		request.setAttribute("MSG", MSG);

		return "/WEB-INF/classes/com/code5/biz/welcome/welcome.jsp";
	}

}
