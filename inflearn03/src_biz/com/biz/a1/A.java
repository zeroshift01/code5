package com.biz.a1;

/**
 * @author zero
 *
 *         인터페이스는 교체비용을 줄이기 위해 사용합니다.
 */
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

		// 파급효과 제거 기준은 B

		B b = new B();

		b.execute1();

		b.execute2();
		b.execute2("p");

		boolean isDev = true;
		if (isDev) {
			b.execute3_dev();
			return;
		}

		b.execute3_pr();

	}

}

class B {

	// 1. 오류 / 타임아웃 / 복잡함
	void execute1() {
	}

	// 2. 기존기능
	void execute2() {
	}

	// 2. 기능추가, 오버로딩
	void execute2(String p) {

	}

	// 3. 교체
	void execute3_dev() {
		// stub
	}

	// 3. 교체
	void execute3_pr() {
		// api call
	}

}