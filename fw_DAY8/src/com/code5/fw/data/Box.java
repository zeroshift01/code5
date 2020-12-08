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
	public static final String KEY_SESSIONB = "com.code5.fw.data.KEY_SESSIONB";

	public static final String KEY_ = "com.code5.fw.data.IP";

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
	 */
	public String s(String key) {
		return getString(key);
	}

	/**
	 * @param key
	 * @return
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

	/**
	 * @param key
	 * @param i
	 * @return
	 */
	public String ss(String key, int i) {
		return getString(key, i);
	}

	/**
	 * @param key
	 * @param i
	 * @return
	 */
	public String getString(String key, int i) {

		Object obj = get(key);

		if (obj instanceof String[]) {
			String[] strs = (String[]) obj;
			if (strs.length <= i) {
				return "";
			}

			return strs[i];
		}

		return "";

	}

	/**
	 * @param key
	 * @return
	 */
	public Box getBox(String key) {
		Object obj = get(key);
		if (obj == null) {
			return new BoxLocal();
		}

		if (obj instanceof Box) {
			return (Box) obj;
		}

		return new BoxLocal();
	}

	/**
	 * 
	 * @return
	 */
	public abstract SessionB getSessionB();

	/**
	 * 
	 * @param sessionB
	 */
	public abstract void setSessionB(SessionB sessionB);

}
