package com.code5.fw.web;

/**
 * @author seuk
 *
 */
public class MasterControllerD {

	/**
	 * @return
	 */
	String[] getSubController() throws Exception {

		Box box = Box.getThread();
		String pathInfo = box.s("pathInfo");

		if ("listContents".equals(pathInfo)) {

			return new String[] { "com.code5.biz.board.web.BoardC", "listContents" };

		}

		if ("loadContent".equals(pathInfo)) {

			return new String[] { "com.code5.biz.board.web.BoardC", "loadContent" };
		}

		throw new Exception();
	}

}
