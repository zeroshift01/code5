package com.code5.biz.welcome;

import com.code5.fw.web.Box;
import com.code5.fw.web.BoxContext;

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

		// TODO [1]
		Box box = BoxContext.getThread();

		String name = box.s("name");
		String welcome = "welcome " + name;
		box.put("welcome", welcome);

		return "/WEB-INF/classes/com/code5/biz/welcome/welcome.jsp";
	}

}
