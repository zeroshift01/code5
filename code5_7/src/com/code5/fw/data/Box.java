package com.code5.fw.data;

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
	public static String SERVICE_KEY = "com.code5.fw.web.Box.SERVICE_KEY";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param key
	 * @param object
	 * 
	 *
	 */
	public abstract void put(String key, Object object);

	/**
	 * @param key
	 * @return
	 * 
	 * 
	 */
	public abstract Object get(String key);

	/**
	 * @param key
	 * @return
	 * 
	 * 
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
	 * @param defaultx
	 * @return
	 * 
	 * 
	 */
	public String getString(String key, String defaultx) {

		String s = getString(key);

		if ("".equals(s)) {
			return defaultx;
		}

		return s;

	}

	/**
	 * @param key
	 * @return
	 * 
	 * 
	 */
	public String s(String key) {
		return getString(key);
	}

	/**
	 * @param key
	 * @param defualtx
	 * @return
	 * 
	 * 
	 */
	public String s(String key, String defualtx) {
		return getString(key, defualtx);
	}

	/**
	 * @param key
	 * @return
	 * 
	 * 
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
	 * 
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
