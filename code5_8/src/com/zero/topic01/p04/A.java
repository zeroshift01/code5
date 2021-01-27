package com.zero.topic01.p04;

class A {

	void method() {

		// 파급효과 종류
		// 3. 교체 : 기존 기능과 새로운 기능을 동시에 사용

		B b = B.createB();
		b.method3();
	}

}

interface B {

	public static B createB() {

		// 팩토리패턴 으로 객체 생성 방법을 B 로 이동
		// A 는 코드 수정이 필요 없음

		// return new B_PR();
		return new B_DV();
	}

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
