package com.zero.topic01.p01;

class A {

	void method() {

		// A 는 B 의 기능을 사용한다.
		// A 는 B 에 의존한다.
		// A 와 B 는 결합되어 있다.
		// 파급효과 : B 에 발생한 문제가 결합된 A 로 전달
		// 낮은 결합도는 파급효과가 발생했을때 낮은 비용으로 제거

		B b = new B();
		b.method();
	}

}

class B {

	void method() {

		// 파급효과 종류 : 오류, 성능문제, 복잡함
	}

}