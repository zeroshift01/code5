package com.zero.topic01.p01;

class A {

	void method() {

		// B 문제는 A 로 전달, 파급효과

		B b = new B();
		b.method();
	}

}

class B {

	void method() {

	}

}