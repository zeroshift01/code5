// [1]
package com.code5.biz.comm002;

import com.code5.fw.web.Box;

/**
 * @author seuk
 * 
 *         [2]
 */
public class Comm002 {

	/**
	 * @param box
	 * @return
	 * 
	 *         [3]
	 */
	public String comm00201() {

		Box box = Box.getThread();

		String name = box.getString("name");

		String ret = "welcome:" + name;

		box.put("ret", ret);

		// [4]
		return "/WEB-INF/classes/com/code5/biz/comm002/jsp/comm00201.jsp";
	}

}
