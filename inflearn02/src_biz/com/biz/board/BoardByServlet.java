package com.biz.board;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zero
 * 
 *         WAS 의 기능이 있어야지만 실행할 수 있는 코드
 * 
 */
public class BoardByServlet {

	/**
	 * @param request
	 * @return
	 */
	public String callList(HttpServletRequest request) {

		String findStr = request.getParameter("findStr");

		request.setAttribute("list", "list=[" + findStr + "]");

		/*
		 * 
		 * <% request.getAttribute("list") %>
		 * 
		 */

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
