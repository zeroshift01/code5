package com.code5.fw.web;

import com.code5.fw.data.Table;

/**
 * @author seuk
 *
 */
public class MasterControllerD {

	private Table getSubControllerTable() {
		String[][] data = { { "comm00101", "com.code5.biz.comm001.Comm001", "comm00101" },
				{ "comm00102", "com.code5.biz.comm001.Comm001", "comm00102" },
				{ "comm00103", "com.code5.biz.comm002.comm002", "comm00201" } };
		String[] cols = { "KEY", "CLASS_NAME", "METHOD_NAME" };
		return new Table(cols, data);

	}

	/**
	 * @return
	 */
	private Table getJspTable() {
		String[][] data = { { "comm00101", "/WEB-INF/classes/com/code5/biz/comm001/jsp/comm00101.jsp" },
				{ "comm00102", "/WEB-INF/classes/com/code5/biz/comm001/jsp/comm00102.jsp" } };
		String[] cols = { "KEY", "JSP_URL" };
		return new Table(cols, data);

	}

	/**
	 * @return
	 */
	Box getSubController() throws Exception {

		Table table = getSubControllerTable();

		Box box = Box.getThread();
		String KEY = box.s("pathInfo");

		for (int i = 0; i < table.length(); i++) {
			if (KEY.equals(table.s("KEY", i))) {
				return table.getBox(i);
			}
		}

		throw new Exception();
	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Box getJspByKey(String JSP_KEY) throws Exception {

		Table table = getJspTable();

		for (int i = 0; i < table.length(); i++) {
			if (JSP_KEY.equals(table.s("KEY", i))) {
				return table.getBox(i);
			}
		}

		throw new Exception();
	}

}
