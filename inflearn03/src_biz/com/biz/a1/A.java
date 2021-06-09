package com.biz.a1;

class A {

	void execute() {

		// A 가 B 의 기능을 사용한다.
		// A 가 B 에 의존한다.

		// B 에서 발생한 파급효과는 A 로 전달.

		// 파급효과 종류
		// 1. 오류/타임아웃/복잡함
		// 2. 기능추가
		// 3. 교체
		// 파급효과는 제거할 수 없다.

		// 낮은 결합도는 파급효과를 제거하는 비용이 적다.

		B b = new B();

		b.execute1();

		b.execute2();
		b.execute2("p");

		b.execute3();

	}

}

class B {

	void execute1() {
		// 1. 오류 / 타임아웃 복잡함
	}

	void execute2() {
		// 2. 기존기능
	}

	void execute2(String p) {
		// 2. 기능추가, 오버로딩
	}

	void execute3() {

		// 3. 교체

		boolean isDev = true;
		if (isDev) {
			// stub
			return;
		}

		// api call
	}

}