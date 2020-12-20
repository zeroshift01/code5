package com.code5.fw.web;

import java.util.HashMap;

/**
 * @author seuk
 * 
 * 
 */
public class BoxLocal extends Box {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * TODO [1]
	 */
	private HashMap<String, Object> hm = new HashMap<String, Object>();

	/**
	 * 
	 */
	public BoxLocal() {

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
