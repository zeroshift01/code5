package com.code5.biz.board.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.code5.biz.board.dao.BoardD;

/**
 * @author seuk
 *
 */
public class BoardC {

	/**
	 * @param request
	 * @return
	 */
	public String listContents(HttpServletRequest request) {

		String pageNo = request.getParameter("pageNo");

		BoardD dao = new BoardD();
		List<Map<String, String>> listContents = dao.listContents(pageNo);
		request.setAttribute("listContents", listContents);

		return "/board/listContents.jsp";
	}

	/**
	 * @param request
	 * @return
	 */
	public String loadContent(HttpServletRequest request) {

		return "/board/loadContent.jsp";
	}

	/**
	 * @param request
	 * @return
	 */
	public String saveContent(HttpServletRequest request) {

		return "/board/saveContent.jsp";
	}

}
