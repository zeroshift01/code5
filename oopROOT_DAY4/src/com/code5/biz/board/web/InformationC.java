package com.code5.biz.board.web;

import com.code5.fw.web.Box;
import com.code5.fw.web.SubController;

/**
 * @author seuk
 *
 */
public class InformationC extends SubController {

	/**
	 * @return
	 */
	protected void start() {
		Box box = Box.getThread();
		box.put("NAME", "InformationC");
	}

	/**
	 * @param request
	 * @return
	 */
	public String infoListContents() {

		// infoListContents 구현

		return "/biz/board/listContents.jsp";
	}

	/**
	 * @return
	 */
	public String infoLoadContent() {

		start();

		// infoLoadContent 구현

		return "/biz/board/loadContent.jsp";
	}

}
