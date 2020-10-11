package com.code5.fw.web;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author seuk
 *
 */
public abstract class Box implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param key
	 * @param object
	 */
	public abstract void put(String key, Object object);

	/**
	 * @param key
	 * @return
	 */
	public abstract Object get(String key);

	/**
	 * @param key
	 * @return
	 * 
	 *         [1]
	 */
	public String getString(String key) {

		Object s = get(key);

		if (s == null) {
			return "";
		}

		if (!(s instanceof String)) {
			return "";

		}
		return (String) s;
	}

	/**
	 * @param key
	 * @return
	 * 
	 *         [2]
	 */
	public String s(String key) {
		return getString(key);
	}

	/**
	 * @param key
	 * @return
	 * 
	 *         [3]
	 */
	public int getInt(String key) {

		String s = s(key);
		if ("".equals(s)) {
			return 0;
		}

		try {

			return Integer.parseInt(s);

		} catch (Exception ex) {

			return 0;
		}

	}

	/**
	 * @param key
	 * @return
	 * 
	 *         [4]
	 */
	public BigDecimal getBigDecimal(String key) {

		String s = s(key);
		if ("".equals(s)) {
			return BigDecimal.ZERO;
		}

		try {

			return new BigDecimal(s);

		} catch (Exception ex) {

			return BigDecimal.ZERO;
		}

	}
}
