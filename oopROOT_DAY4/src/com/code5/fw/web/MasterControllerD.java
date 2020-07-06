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

		if ("infoContents".equals(pathInfo)) {

			return new String[] { "com.code5.biz.board.web.BoardC", "infoContents" };

		}

		throw new Exception();
	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 */
	String getJspByKey(String key) throws Exception {

		if ("listContents".equals(key)) {
			return "/biz/board/listContents.jsp";
		}

		throw new Exception();
	}

}