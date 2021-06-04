package com.code5.fw.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zero
 * 
 *         공통기능을 제공하는 3가지 방법
 *
 */
class A {

	// 1. 파라메터

	void execute_01(Box box) {
		box.get("name");
	}

	// 2. 맴버객체 or 전역객체(static)

	private Box box1 = new BoxLocal();

	@Autowired
	private Box box2 = null;

	private static Box BOX3 = new BoxLocal();

	void execute_02() {
		box1.get("name");
		box2.get("name");
		BOX3.get("name");
	}

	// 3. ThreadLocal

	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	void set() {
		Box box = new BoxLocal();
		TL.set(box);
	}

	void remove() {
		TL.remove();
	}

	void execute_03_1() {
		Box box = TL.get();
		box.get("name");
	}

	static void execute_03_2() {
		Box box = TL.get();
		box.get("name");
	}

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Autowired {

}
