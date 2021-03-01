package com.code5.fw.web;

/**
 * @author zero
 *
 */
public class BoxContext {

	/**
	 * TODO [1]
	 */
	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	/**
	 * 
	 * TODO [2]
	 * 
	 * @param box
	 */
	static void setThread(Box box) {
		TL.set(box);
	}

	/**
	 * TODO [3]
	 */
	static void removeThread() {
		TL.remove();
	}

	/**
	 * 
	 * TODO [4]
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
