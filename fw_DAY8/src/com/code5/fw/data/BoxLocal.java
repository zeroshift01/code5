package com.code5.fw.data;

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
	 *
	 */
	public SessionB getSessionB() {
		return (SessionB) hm.get(KEY_SESSIONB);
	}

	/**
	 *
	 *
	 *
	 */
	public void setSessionB(SessionB sessionB) {
		hm.put(KEY_SESSIONB, sessionB);
	}
}
