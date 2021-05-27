package com.code5.fw.data;

/**
 * @author zero
 *
 */
class A {

	// 1. 파라메터

	/**
	 * @param box
	 */
	void execute_01(Box box) {
		box.get("name");
	}

	// 2. 맴버객체 or 전역객체(static)

	private Box box = new BoxLocal();
	private static Box BOX = new BoxLocal();

	/**
	 * 
	 */
	void execute_02_1() {
		box.get("name");
	}

	/**
	 * 
	 */
	void execute_02_2() {
		BOX.get("name");
	}

	// 3. ThreadLocal

	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	/**
	 * 
	 */
	void execute_03_set() {
		Box box = new BoxLocal();
		TL.set(box);
	}

	/**
	 * 
	 */
	void execute_03_1() {
		Box box = TL.get();
		box.get("name");
	}

	/**
	 * 
	 */
	static void execute_03_2() {
		Box box = TL.get();
		box.get("name");
	}

	/**
	 * 
	 */
	void execute_03_remove() {
		TL.remove();
	}
}
