package com.code5.fw.web;

import java.util.HashMap;

import com.code5.fw.data.SessionB;

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
	public SessionB getSessionB() {
		return (SessionB) hm.get(KEY_SESSIONB);
	}

	/**
	 *
	 *
	 */
	public void setSessionB(SessionB sessionB) {
		hm.put(KEY_SESSIONB, sessionB);
	}

}
