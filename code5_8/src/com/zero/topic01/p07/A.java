package com.zero.topic01.p07;

class A {

	void method() {

		// 인터페이스와 구현클래스가 1:1 관계

		// A -> B <- B_Impl

		// 파급효과 종류
		// 1. 변경(O) : 오류 수정, 기능 개선
		// 2. 추가(X) : 새로운 기능의 추가, 오버라이딩, 새로운 메소드
		// 3. 교체(X) : 기존 기능과 새로운 기능을 동시에 사용

		// 인터페이스를 사용한다고 해도 파급효과를 해결할 수 있는 비용이 줄어들지 않는다.
		// 오히려 더 증가!

		B b = new B_Impl();
		b.method();

		b.method("p");
		b.method2();
	}

}

interface B {
	public void method();

	public void method(String p);

	public void method2();
}

class B_Impl implements B {

	public void method() {
		// 1. 변경 : 오류 수정, 기능 개선
	}

	public void method(String p) {
		// 2. 추가 : 오버라이딩
	}

	public void method2() {
		// 2. 추가 : 새로운 메소드
	}

}
