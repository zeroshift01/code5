// [1]
package com.code5.biz.comm999;

import com.code5.fw.web.Box;

/**
 * @author seuk
 * 
 */
public class Comm002 {

	/**
	 * @param box
	 * @return
	 */
	public String comm00201() {

		Box box = Box.getThread();

		String name = box.getString("name");

		String ret = "welcome:" + name;

		box.put("ret", ret);

		// [1]
		return "comm00201";
	}

}
