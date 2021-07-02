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

		// set() 메소드가 호출 안된 경우
		// 독립적 프로그램, 테스트 유닛
		// 개발자는 실행하는 환경에 의존하지 않는 코드를 만들 수 있음
		box = new BoxLocal();
		set(box);
		return box;
	}

}
