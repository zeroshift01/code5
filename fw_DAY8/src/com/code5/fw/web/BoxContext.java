package com.code5.fw.web;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxLocal;

/**
 * @author seuk
 *
 */
public class BoxContext {

	/**
	 * [1]
	 */
	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	/**
	 * [2]
	 * 
	 * @param box
	 */
	static void setThread(Box box) {
		TL.set(box);
	}

	/**
	 * [2]
	 */
	static void removeThread() {
		TL.remove();
	}

	/**
	 * [3]
	 * 
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

}
