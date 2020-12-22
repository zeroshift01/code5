package com.code5.fw.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author seuk
 *
 */
public class Welcome {

	/**
	 * @param request
	 * @return
	 */
	public String welcome(HttpServletRequest request) {

		String name = request.getParameter("name");

		String ret = "welcome:" + name;

		request.setAttribute("ret", ret);

		return "/biz/board/welcome.jsp";
	}

}