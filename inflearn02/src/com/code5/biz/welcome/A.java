package com.code5.biz.welcome;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxLocal;

/**
 * @author zero
 *
 */
public class A {

	/**
	 * @param a
	 * 
	 *          1. ÆÄ¶ó¸ÞÅÍ
	 */
	public void execute_01(Box box) {
		box.s("name");
	}

	//

	/**
	 * 2. ¸É¹ö°´Ã¼ or static
	 */
	public void execute_02() {
		box.s("name");
		BOX.s("name");
	}

	private Box box = new BoxLocal();
	private static Box BOX = new BoxLocal();

	/**
	 * 3. ThreadLocal
	 */
	public void execute_03() {
		Box box = th.get();
		box.s("name");
	}

	ThreadLocal<Box> th = new ThreadLocal<Box>();
}