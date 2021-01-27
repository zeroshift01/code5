package com.zero.topic01.p05;

class A {

	void method() {

		// 인터페이스와 구현클래스가 1:1 관계

		// A -> B <- B_Impl

		B b = new B_Impl();
		b.method();
	}

}

interface B {
	public void method();
}

class B_Impl implements B {

	public void method() {
	}

}
