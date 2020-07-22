package com.code5.fw.web;

import java.io.Serializable;

import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;

/**
 * @author seuk
 *
 */
public abstract class Box implements Serializable {

	/**
	 * 
	 */
	public static String KEY_SESSIONB = "com.code5.fw.web.KEY_SESSIONB";

	/**
	 * 
	 */
	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	/**
	 * @param box
	 */
	public static Box getThread() {
		Box box = TL.get();
		if (box != null) {
			return box;
		}
		box = new BoxLocal();
		setThread(box);
		return box;
	}

	/**
	 * @param box
	 */
	static void setThread(Box box) {
		TL.set(box);
	}

	/**
	 * 
	 */
	static void removeThread() {
		TL.remove();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param key
	 * @param object
	 */
	abstract void setAttribute(String key, Object object);

	/**
	 * @param key
	 * @return
	 */
	abstract Object getAttribute(String key);

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key) {

		Object s = getAttribute(key);

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
	 * @param obj
	 */
	public void put(String key, Object obj) {
		setAttribute(key, obj);
	}

	/**
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return getAttribute(key);
	}

	/**
	 * @param key
	 * @return
	 */
	public String s(String key) {
		return getString(key);
	}

	/**
	 * @return
	 */
	public Table getTable(String key) {

		Object obj = get(key);

		if (obj instanceof Table) {
			return (Table) obj;
		}

		// 빈 객체가 아닌 디폴트 리턴
		return new Table();

	}

	/**
	 * @param key
	 * @return
	 */
	public Box getBox(String key) {

		Object obj = get(key);

		if (obj instanceof Box) {
			return (Box) obj;
		}

		// 빈 객체가 아닌 디폴트 리턴
		return new BoxLocal();

	}

	/**
	 * @return
	 */
	public abstract SessionB getSessionB();

	/**
	 * @param sessionB
	 */
	public abstract void setSessionB(SessionB sessionB);

	/**
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		try {
			return Integer.parseInt(getString(key));
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * @param KEY
	 * @param x
	 */
	public void put(String KEY, int x) {
		put(KEY, "" + x);
	}

	/**
	 * @param KEY
	 * @param x
	 */
	public void put(String KEY, boolean x) {
		put(KEY, "" + x);

	}

	/**
	 * @param KEY
	 * @return
	 */
	public boolean getBoolean(String KEY) {
		if ("true".equals(s(KEY))) {
			return true;
		}
		return false;

	}
}
