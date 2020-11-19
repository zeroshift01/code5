package com.code5.biz.board.web;

import com.code5.fw.web.Box;

/**
 * @author seuk
 *
 */
public class BoardC_step01 {

	/**
	 * @param box
	 * @return
	 * 
	 *         [2]
	 */
	public String welcome(Box box) {

		String name = box.getParameter("name");

		String ret = "welcome:" + name;

		box.setAttribute("ret", ret);

		return "/biz/board/welcome.jsp";
	}

}
