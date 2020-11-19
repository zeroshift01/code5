package com.code5.fw.web;

import com.code5.fw.data.Box;

/**
 * @author seuk
 *
 */
public class Welcome {

	/**
	 * @param box
	 * @return
	 */
	public String service() throws Exception {

		// [1]
		Box box = BoxContext.getThread();

		String name = box.s("name");
		String welcome = "welcome " + name;
		box.put("welcome", welcome);

		return "/WEB-INF/classes/com/code5/fw/web/welcome.jsp";
	}

}