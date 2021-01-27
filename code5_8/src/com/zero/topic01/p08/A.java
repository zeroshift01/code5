package com.zero.topic01.p08;

class A {

	void method() {

		// 인터페이스와 구현클래스가 1:1 관계

		// 인터페이스를 사용한다고 해도 파급효과를 해결할 수 있는 비용이 줄어들지 않는다.
		// 추가로 발생하는 파급효과때문에 개발 비용이 오히려 더 증가!

		// A -> B <- B_Impl

		// 인터페이스와 구현클래스가 1:1 관계일때는 제거
		// new 를 사용해도 리펙토링 비용은 크지 않음
		// 그래도 의심이 가면 팩토리 패턴만 적용

		// 파급효과 종류
		// 1. 변경(O) : 오류 수정, 기능 개선
		// 2. 추가(O) : 새로운 기능의 추가, 오버라이딩, 새로운 메소드
		// 3. 교체(O) : 기존 기능과 새로운 기능을 동시에 사용

		B b = B.createB();
		b.method();

		b.method("p");
		b.method2();
	}

}

class B {

	public static B createB() {
		return new B();
	}

	public void method() {
		// 1. 변경 : 오류 수정, 기능 개선
	}

	void method(String p) {
		// 2. 추가 : 오버라이딩
	}

	void method2() {
		// 2. 추가 : 새로운 메소드
	}
}
