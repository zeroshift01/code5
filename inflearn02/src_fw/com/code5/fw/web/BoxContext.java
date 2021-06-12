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
	static void setBox(Box box) {
		TL.set(box);
	}

	/**
	 * 	
	 */
	static void removeBox() {
		TL.remove();
	}

	/**
	 * 
	 * 
	 * 
	 * @param box
	 */
	public static Box getBox() {

		Box box = TL.get();

		if (box != null) {
			return box;
		}
		box = new BoxLocal();
		setBox(box);
		return box;
	}

}
