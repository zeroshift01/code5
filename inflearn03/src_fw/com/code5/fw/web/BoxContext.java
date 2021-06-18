package com.code5.fw.web;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxLocal;

/**
 * @author zero
 *
 */
public class BoxContext {

	/**
	 * 
	 */
	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	/**
	 * 
	 * 
	 * 
	 * @param box
	 */
	static void set(Box box) {
		TL.set(box);
	}

	/**
	 * 	
	 */
	static void remove() {
		TL.remove();
	}

	/**
	 * 
	 * 
	 * 
	 * @param box
	 */
	public static Box get() {

		Box box = TL.get();

		if (box != null) {
			return box;
		}
		box = new BoxLocal();
		set(box);
		return box;
	}

}
