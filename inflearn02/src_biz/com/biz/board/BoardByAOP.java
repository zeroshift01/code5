package com.biz.board;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxLocal;

/**
 * @author zero
 * 
 *         공통기능을 제공하는 3가지 방법
 *
 */
class BoardByAOP {

	// 1. 파라메터
	void callList(Box box) {
		box.get("name");
	}

	// 2. 맴버객체 or 전역객체(static)

	private Box box1 = new BoxLocal();

	@Autowired
	private Box box2 = null;

	private static Box BOX3 = new BoxLocal();

	void callList() {
		box1.get("name");
		box2.get("name");
		BOX3.get("name");
	}

	// 3. ThreadLocal

	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	void setAOP() {
		Box box = new BoxLocal();
		TL.set(box);
	}

	void removeAOP() {
		TL.remove();
	}

	static void exeWrite() {
		Box box = TL.get();
		box.put("name","ABC");
	}

	void callWrite() {

		BoardByAOP.exeWrite();

		Box box = TL.get();
		box.get("name");
	}

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Autowired {

}
