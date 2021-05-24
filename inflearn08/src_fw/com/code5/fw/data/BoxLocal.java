package com.code5.fw.data;

import java.util.HashMap;
import java.util.Iterator;

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
	 * @param isXssConvert
	 */
	public BoxLocal(boolean isXssConvert) {
		this.setXssConvert(isXssConvert);
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

	/**
	 *
	 */
	public void setSessionB(SessionB sessionB) {
		hm.put(KEY_SESSIONB, sessionB);
	}

	/**
	 *
	 */
	public String[] getKeys() {
		String[] keys = new String[hm.size()];
		Iterator<String> iterator = hm.keySet().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			keys[i] = iterator.next();
			i++;
		}

		return keys;

	}
}
