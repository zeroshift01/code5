package com.code5.biz.board.web;

import com.code5.fw.web.Box;

/**
 * @author seuk
 *
 */
public class BoardC {

	/**
	 * @param box
	 * @return
	 * 
	 *         [1]
	 */
	public String welcome() {

		Box box = Box.getThread();

		String name = box.getString("name");

		String ret = "welcome:" + name;

		box.put("ret", ret);

		return "/biz/board/welcome.jsp";
	}

}
