package com.code5.fw.data;

import java.util.HashMap;

/**
 * @author seuk
 * 
 *         [1]
 */
public class BoxLocal extends Box {

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
	public BoxLocal() {

	}

	/**
	 * @param key
	 * @return
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
	 */
	Object getAttribute(String key) {
		return hm.get(key);
	}

	/**
	 * [1]
	 */
	public SessionB getSessionB() {
		return (SessionB) hm.get(KEY_SESSIONB);
	}

	/**
	 *
	 * [2]
	 *
	 */
	public void setSessionB(SessionB sessionB) {
		hm.put(KEY_SESSIONB, sessionB);
	}
}
