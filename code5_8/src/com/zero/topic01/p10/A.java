package com.zero.topic01.p10;

class A {

	void method() {

		// 파급효과가 발생했을때 제거하는 방법

		// 1. 변경 : 오류 수정, 기능 개선
		// 2. 추가 : 새로운 기능의 추가, 오버라이딩, 새로운 메소드
		// 3. 교체 : 기존 기능과 새로운 기능을 동시에 사용

		B b = B.createB();

		b.method();

		b.method2();
		b.method("");

		b.method3();

	}

}

// 1. 기존 클래스를 추상화 클래스로 변경
// 2. 교체의 기능을 위한 method3 선언
// 3. method3 를 구현한 B_DV 와 B_PR 구현
// B 가 리펙토링 되더라도 A 는 영향이 없다.
abstract class B {

	public static B createB() {

		boolean 개발환경 = true;

		if (개발환경) {
			return new B_DV();
		}
		return new B_PR();
	}

	public void method() {
		// 1. 변경 : 오류 수정, 기능 개선
	}

	public void method(String p) {
		// 2. 추가 : 오버라이딩
	}

	public void method2() {
		// 2. 추가 : 새로운 메소드
	}

	abstract public void method3();
}

class B_DV extends B {

	public void method3() {
		// test stub
	}

}

class B_PR extends B {

	public void method3() {
		// 실제기능 수행
	}
}
