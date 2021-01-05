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

		if (s instanceof String[]) {
			String[] ss = (String[]) s;
			return ss[0];
		}

		if (s instanceof String) {
			return (String) s;
		}

		return "";

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

	/**
	 * @param key
	 * @return
	 */
	public Table getTable(String key) {

		Object o = get(key);
		if (o == null) {
			return new Table();
		}

		if (o instanceof Table) {
			return (Table) o;
		}

		return new Table();

	}

	/**
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {

		String s = s(key);
		if ("true".equals(s)) {
			return true;
		}

		return false;

	}

	/**
	 *
	 */
	public static String KEY_SESSIONB = "com.code5.fw.web.KEY_SESSIONB";
	

	/**
	 * 
	 * @param sessionB
	 * 
	 *                 TODO [1-1]
	 */
	public abstract void setSessionB(SessionB sessionB);
	
	/**
	 * @return
	 * 
	 *         TODO [2]
	 */
	public SessionB getSessionB() {
		return (SessionB) get(KEY_SESSIONB);
	}
	

	/**
	 * 
	 */
	public static String KEY_REMOTE_ADDR = "com.code5.fw.web.KEY_REMOTE_ADDR";

	/**
	 * @return
	 * @throws Exception
	 */
	public String[] ss(String key) throws Exception {
		Object obj = get(key);

		if (obj instanceof String[]) {
			return (String[]) obj;
		}

		return new String[0];

	}
}
