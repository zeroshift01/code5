package com.zero.topic01.p03;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class A {

	void method() {

		// 파급효과 종류
		// 3. 교체 : 기존 기능과 새로운 기능을 동시에 사용

		// 교체할 필요가 있을때 A 코드 수정
		boolean 개발환경 = true;

		B b = new B_PR();
		if (개발환경) {
			b = new B_DV();
		}

		b.method3();

	}
}

interface B {
	public void method3();
}

class B_DV implements B {

	public void method3() {
		// test stub
	}

}

class B_PR implements B {

	public void method3() {
		// 실제기능 수행
	}

}

class C {

	void moethod() {

		// 잘못된 객체 생성, 쓸때없는 추상화 사용

		List<String> list = new ArrayList<String>();
		sort(list);

		((ArrayList<String>) list).addAll(null);

		// 일부러 인터페이스로 추상화 해 사용할 필요는 없다.
		// ArrayList LinkedList
		LinkedList<String> list2 = new LinkedList<String>();
		sort(list2);

		list2.addAll(null);

	}

	void sort(List<String> list) {

		// 추상화의 영역
		// OOP 리스코프 법칙

	}
}
