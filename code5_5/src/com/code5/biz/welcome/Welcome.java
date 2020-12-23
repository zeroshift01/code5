package com.code5.biz.welcome;

import com.code5.fw.web.BizController;

/**
 * @author seuk
 *
 */
public class Welcome implements BizController{

	/**
	 * @return
	 * @throws Exception
	 */
	public String service() throws Exception {
		return "welcome";
	}

}
