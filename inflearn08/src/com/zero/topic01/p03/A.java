package com.zero.topic01.p03;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class A {

	void method() {

		// 파급효과가 발생했을때 제거하는 방법
		// 3. 교체 : 기존 기능과 새로운 기능을 동시에 사용

		// 교체를 위해 A 코드 수정
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

		// new 로 생성한 객체를 일부러 추상화 해 사용할 필요가 없다.

		List<String> list = new ArrayList<String>();
		sort(list);
		((ArrayList<String>) list).addAll(null);

		ArrayList<String> list2 = new ArrayList<String>();
		sort(list2);
		list2.addAll(null);

		LinkedList<String> list3 = new LinkedList<String>();
		sort(list3);
		list3.getFirst();

	}

	void sort(List<String> list) {

		// 리스코프 치환 원칙
		// 추상화 범위

	}
}
