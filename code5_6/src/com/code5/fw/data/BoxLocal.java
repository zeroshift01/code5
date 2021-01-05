package com.code5.fw.data;

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
		return (String) hm.get(key);
	}

	/**
	 * 
	 */
	public void put(String key, Object obj) {
		hm.put(key, obj);
	}

	/**
	 *
	 * TODO [1-3]
	 */
	public void setSessionB(SessionB sessionB) {
		hm.put(KEY_SESSIONB, sessionB);
	}
}
