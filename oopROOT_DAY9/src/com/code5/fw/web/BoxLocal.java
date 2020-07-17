package com.code5.fw.web;

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

	/**
	 *
	 */
	public void setSessionB(SessionB user) {

		hm.put(Box.SESSIONB_KEY, user);
	}

	/**
	 *
	 */
	public SessionB getSessionB() {

		return (SessionB) hm.get(Box.SESSIONB_KEY);

	}

}
