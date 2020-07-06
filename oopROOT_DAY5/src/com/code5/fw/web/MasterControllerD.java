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

		if ("comm00101".equals(pathInfo)) {

			return new String[] { "com.code5.biz.comm001.Comm001", "comm00101" };

		}

		if ("comm00102".equals(pathInfo)) {

			return new String[] { "com.code5.biz.comm001.Comm001", "comm00102" };
		}

		if ("comm00201".equals(pathInfo)) {

			return new String[] { "com.code5.biz.comm002.comm002", "comm00201" };
		}

		throw new Exception();
	}

}
