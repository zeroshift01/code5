package com.code5.fw.data;

import java.util.HashMap;

/**
 * @author zero
 *
 */
public class BoxLocal extends Box {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private HashMap<String, Object> hm = new HashMap<String, Object>();

	/**
	 * 
	 */
	public BoxLocal() {

	}

	/**
	 * 
	 */
	public Object get(String key) {
		return hm.get(key);
	}

	/**
	 * 
	 */
	public void put(String key, Object obj) {
		hm.put(key, obj);
	}

}
