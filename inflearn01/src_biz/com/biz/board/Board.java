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

		String FIND_STR = request.getParameter("FIND_STR");
		String FIND_OPT = request.getParameter("FIND_OPT");

		String boardData = FIND_OPT + "=" + FIND_STR;

		request.setAttribute("BOARD_DATA", boardData);

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

		request.setAttribute("MSG", "exeWrite ok");

		return callList(request);
	}

}
