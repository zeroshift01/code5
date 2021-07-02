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
	// 단순함
	// 파라메터가 늘어 날 수 있고 사용방법이 복잡해짐
	void callList(Box box) {
		box.get("name");
	}

	// 2. 맴버객체 or 전역객체(static)
	// 객체지향적인 개발
	// 실행시점에 기능과 데이터를 결정할 수 있음
	// 다른 클래스와 공통기능 공유가 힘들어짐
	// 전역 객체는 멀티 쓰레드 환경에서 동작을 보장하지 못함

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
	// 우리가 만든 공통기능은 ThreadLocal 제공
	// 쓰레드의 시작과 종료부분에 setAOP, removeAOP 호출

	// 단순함
	// 다른 클래스와 공통기능 공유가 가능
	// 전역메소드(유틸리티)에서도 공통기능 공유 가능, 일관된 동작 보장

	// 프레임워크에서 명시적으로 기능을 구현해야 함

	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	void setAOP() {
		Box box = new BoxLocal();
		TL.set(box);
	}

	void removeAOP() {
		TL.remove();
	}

	static void setDefault() {
		Box box = TL.get();
		if (box.get("name") == null) {
			box.put("name", "ABC");
		}

	}

	void callWrite() {

		BoardByAOP.setDefault();

		Box box = TL.get();
		box.get("name");
	}

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Autowired {

}
