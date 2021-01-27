package com.zero.topic01.p02;

class A {

	void method() {

		B b = new B();

		// 파급효과 종류

		// 1. 변경 : 오류 수정, 기능 개선
		b.method();

		// 2. 추가 : 새로운 기능의 추가, 오버라이딩, 새로운 메소드
		b.method();
		b.method();

		// 3. 교체 : 기존 기능과 새로운 기능을 동시에 사용
		b.method3();

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

	// 3. 교체 : 기존 기능도 사용하고 새로운 기능도 같이 사용
	void method3() {
		// 개발할때는 stub
	}

	void method3() {
		// 운영환경에선 실제 기능 실행
	}

}