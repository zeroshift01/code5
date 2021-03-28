package com.zero.topic01.p08;

class A {

	void method() {

		// 인터페이스와 구현클래스가 1:1 관계

		// A -> B <- B_Impl

		// 인터페이스를 사용한다고 해도 파급효과 제거하는 비용이 줄지 않는다!
		// 오히려 증가!

		// 파급효과가 발생했을때 제거하는 방법

		// 1. 변경(인터페이스 사용전 동일) : 오류 수정, 기능 개선
		// 2. 추가(어려움) : 새로운 기능의 추가, 오버라이딩, 새로운 메소드
		// 3. 교체(기회가 생김) : 기존 기능과 새로운 기능을 동시에 사용

		B b = B.createB();

		b.method();

		b.method2();
		b.method("");

		b.method3();

	}

}

interface B {

	public static B createB() {

		boolean 개발환경 = true;

		if (개발환경) {
			return new B_DV();
		}
		return new B_PR();
	}

	public void method();

	public void method(String p);

	public void method2();

	public void method3();
}

class B_DV implements B {

	public void method() {
		// 1. 변경 : 오류 수정, 기능 개선
		// 중복코드
	}

	public void method(String p) {
		// 2. 추가 : 오버라이딩
		// 중복코드
	}

	public void method2() {
		// 2. 추가 : 새로운 메소드
		// 중복코드
	}

	public void method3() {
		// test stub
	}

}

class B_PR implements B {

	public void method() {
		// 1. 변경 : 오류 수정, 기능 개선
		// 중복코드
	}

	public void method(String p) {
		// 2. 추가 : 오버라이딩
		// 중복코드
	}

	public void method2() {
		// 2. 추가 : 새로운 메소드
		// 중복코드
	}

	public void method3() {
		// 실제기능 수행
	}
}
