package com.code5.fw.web;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author seuk
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

ArrayList<String> data2 = new ArrayList<String>();
data2.add("abcd1");
data2.add("abcd2");
	}

	/**
	 * @param key
	 * @return
	 */
	String getParameter(String key) {
		return (String) hm.get(key);
	}

	/**
	 *
	 */
	void setAttribute(String key, Object obj) {
		hm.put(key, obj);
	}

	/**
	 *
	 */
	Object getAttribute(String key) {
		return hm.get(key);
	}

}
