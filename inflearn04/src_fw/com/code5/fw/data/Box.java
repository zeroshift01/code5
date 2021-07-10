package com.code5.fw.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zero
 *
 */
public abstract class Box implements Serializable {

	public static String KEY_REMOTE_ADDR = "com.code5.fw.web.KEY_REMOTE_ADDR";
	public static String KEY_SERVICE = "com.code5.fw.web.KEY_SERVICE";
	public static String KEY_SESSIONB = "com.code5.fw.web.KEY_SESSIONB";
	public static String KEY_CONTENT_TYPE = "com.code5.fw.web.KEY_CONTENT_TYPE";

	public static String KEY_FW_VIEW = "com.code5.fw.web.KEY_FW_VIEW";
	public static String KEY_FW_CONTROLLER = "com.code5.fw.web.KEY_FW_CONTROLLER";

	public static String KEY_ALERT_MSG = "com.code5.fw.web.KEY_ALERT_MSG";

	public static String KEY_EXCEPTION = "com.code5.fw.web.KEY_EXCEPTION";

	private boolean isXssConvert = false;

	/**
	 * @param isXssConvert
	 */
	public void setXssConvert(boolean isXssConvert) {
		this.isXssConvert = isXssConvert;
	}

	/**
	 * @return
	 */
	public boolean isXssConvert() {
		return this.isXssConvert;
	}

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
			return new TableRecodeBase();
		}

		if (o instanceof Table) {
			return (Table) o;
		}

		return new TableRecodeBase();

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
	 * @param sessionB
	 */
	public abstract void setSessionB(SessionB sessionB);

	/**
	 * @return
	 * 
	 */
	public SessionB getSessionB() {

		try {
			// return new SessionB("A0", "A0", "1");
			return (SessionB) get(KEY_SESSIONB);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}

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

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Table createTable(String[] key) throws Exception {

		Table table = new TableColumnBase();
		for (int i = 0; i < key.length; i++) {
			table.addCol(key[i], ss(key[i]));
		}

		return table;

	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Table createTableByKey(String key) throws Exception {

		Table table = new TableColumnBase();

		String[] data = ss(key);
		table.addCol(key, data);

		int x = data.length;

		String[] keys = getKeys();
		for (int i = 0; i < keys.length; i++) {
			data = ss(keys[i]);
			if (x == data.length) {
				table.addCol(keys[i], data);
			}

		}

		return table;

	}

	/**
	 * @param table
	 * @param row
	 * @throws Exception
	 */
	public void putFromTable(Table table, int row) throws Exception {
		String[] cols = table.getCols();
		for (int i = 0; i < cols.length; i++) {
			put(cols[i], table.s(cols[i], row));
		}

	}

	/**
	 * @param key
	 * @return
	 * 
	 * 
	 */
	private String _getString(String key) {

		Object s = get(key);

		if (s == null) {
			return null;
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
	 */
	private String _getString(String key, String defaultx) {

		String s = _getString(key);

		if (s == null) {
			return defaultx;
		}

		return s;
	}

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key, String d) {

		String data = _getString(key, d);

		if (data == null) {
			data = "";
		}

		if (!isXssConvert) {
			return data;
		}

		String keyx = "$$XSS_CONVERT_" + data.hashCode();

		String datax = _getString(keyx);
		if (datax != null) {
			return datax;
		}

		data = XssConvert.xssConvert(data);

		put(keyx, data);

		return data;

	}

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return getString(key, "");
	}

	/**
	 * @param key
	 * @return
	 * 
	 * 
	 */
	public String s(String key) {
		return getString(key, "");
	}

	/**
	 * @param key
	 * @param d
	 * @return
	 */
	public String s(String key, String d) {
		return getString(key, d);
	}

	/**
	 * @return
	 */
	public abstract String[] getKeys();

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
	 * @param key
	 * @return
	 */
	public UploadFileB getUploadFileB(String key) {
		Object obj = get(key);
		if (obj == null) {
			return new UploadFileB();
		}

		if (obj instanceof UploadFileB) {
			return (UploadFileB) obj;

		}

		return new UploadFileB();
	}

	/**
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		try {
			String s = s(key);
			return Long.parseLong(s);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * @param msg
	 */
	public void setAlertMsg(String msg) {
		put(KEY_ALERT_MSG, msg);
	}

	/**
	 * @param msg
	 * @return
	 */
	public String getAlertMsg() {
		return s(KEY_ALERT_MSG);
	}

	/**
	 * @param key
	 * @param b
	 */
	public void put(String key, boolean b) {
		put(key, "" + b);
	}

}
