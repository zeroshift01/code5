package com.code5.biz.board.web;

import com.code5.fw.web.Box;

/**
 * @author seuk
 *
 */
public class BoardC_step01 {

	/**
	 * @return
	 */
	private void start() {
		Box box = Box.getThread();
		box.put("NAME", "BoardC");
	}

	/**
	 * @param request
	 * @return
	 */
	public String listContents() {

		start();

		// listContents 구현

		return "/biz/board/listContents.jsp";
	}

	/**
	 * @return
	 */
	public String infoContents() {

		start();

		// infoContents 구현

		return "/biz/board/loadContent.jsp";
	}
}
