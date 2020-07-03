package com.code5.biz.board.web;

import com.code5.fw.web.Box;
import com.code5.fw.web.SubController;

/**
 * @author seuk
 *
 */
public class BoardC extends SubController {

	/**
	 * @return
	 */
	public void start() {
		Box box = Box.getThread();
		box.put("NAME", "BoardC");
	}

	/**
	 * @param request
	 * @return
	 */
	public String listContents() {

		// listContents 구현

		return "/biz/board/listContents.jsp";
	}

	/**
	 * @return
	 */
	public String infoContents() {

		// infoContents 구현

		return "/biz/board/loadContent.jsp";
	}
}
