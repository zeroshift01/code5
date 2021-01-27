package com.zero.topic01.p02;

class A {

	void method() {

		// 파급효과 종류
		// 1. 변경 : 오류 수정, 기능 개선
		// 2. 추가 : 새로운 기능의 추가, 오버라이딩, 새로운 메소드
		// 3. 교체 : 기존 기능과 새로운 기능을 동시에 사용

		B b = new B();
		b.method();

	}

}

class B {

	void method() {
		// 1. 변경 : 오류 수정, 기능 개선
	}

	void method(String p) {
		// 2. 추가 : 오버라이딩
	}

	void method2() {
		// 2. 추가 : 새로운 메소드
	}

	// void method() {
	// 3. 교체 : 기존 기능도 사용하고 새로운 기능도 같이 사용
	// }

}