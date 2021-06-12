package com.biz.board;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zero
 *
 */
public class Board {

	/**
	 * @param request
	 * @return
	 */
	public String callList(HttpServletRequest request) {

		String findStr = request.getParameter("findStr");

		System.out.println(findStr);

		return "/WEB-INF/classes/com/biz/board/jsp/list.jsp";
	}

	/**
	 * @param request
	 * @param out
	 * @return
	 */
	public String callWrite(HttpServletRequest request) {

		return "/WEB-INF/classes/com/biz/board/jsp/write.jsp";

	}

	/**
	 * @param request
	 * @param response
	 */
	public String exeWrite(HttpServletRequest request) {

		request.setAttribute("exeWriteResult", "ok");

		return callList(request);
	}

}
