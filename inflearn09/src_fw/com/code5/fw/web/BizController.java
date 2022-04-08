package com.code5.fw.web;

/**
 * @author zero
 *
 */
public interface BizController {

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 */
	default String execute(String key) throws Exception {
		return MasterController.executeService(key);
	}

}
