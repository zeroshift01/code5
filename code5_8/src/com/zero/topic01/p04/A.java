package com.zero.topic01.p04;

class A {

	void method() {

		// 파급효과 종류
		// 3. 교체 : 기존 기능과 새로운 기능을 동시에 사용

		B b = B.createB();
		b.method();
	}

}

interface B {

	public static B createB() {

		// 팩토리패턴 으로 객체 생성 방법을 B 로 이동
		// A 는 코드 수정이 필요 없음

		// return new B1();
		return new B2();
	}

	public void method();
}

class B1 implements B {

	public void method() {
	}

}

class B2 implements B {

	public void method() {
	}

}