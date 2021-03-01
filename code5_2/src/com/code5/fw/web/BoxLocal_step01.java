package com.code5.fw.web;

import java.util.HashMap;

/**
 * @author zero
 *
 */
public class BoxLocal_step01 extends Box_step01 {

	/**
	 * 
	 */
	private HashMap<String, Object> hm = new HashMap<String, Object>();

	/**
	 * 
	 */
	public BoxLocal_step01() {

	}

	/**
	 * TODO [2-1]
	 */
	public Object get(String key) {
		return (String) hm.get(key);
	}

	/**
	 * TODO [2-2]
	 */
	public void put(String key, Object obj) {
		hm.put(key, obj);
	}

}
