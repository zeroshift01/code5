package com.code5.fw.web;

import java.util.HashMap;

/**
 * @author seuk
 * 
 *         [1]
 */
public class BoxLocal_step01 extends Box_step01 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * [2]
	 */
	private HashMap<String, Object> hm = new HashMap<String, Object>();

	/**
	 * 
	 */
	public BoxLocal_step01() {

	}

	/**
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		return (String) hm.get(key);
	}

	/**
	 *
	 */
	public void setAttribute(String key, Object obj) {
		hm.put(key, obj);
	}

	/**
	 *
	 */
	public Object getAttribute(String key) {
		return hm.get(key);
	}

}
