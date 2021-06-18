package com.biz.a1;

/**
 * @author zero
 *
 *         파급효과를 줄이기 위해 인터페이스를 사용하지 마세요.
 */
class A {

	void execute() {

		// A 가 B 의 기능을 사용한다.
		// A 가 B 에 의존한다.

		// Ripple Effect(파급효과), B의 오류 또는 기능의 문제가 A 실행 결과를 이상하게 만듬
		// 낮은결합도가 필요

		// Side Effect(부작용), B의 오류 또는 기능을 개선했지만 여전히 A 실행 결과가 이상함
		// 회귀테스트가 필요

		// A 가 B 의 기능을 사용할 때
		// B 에서 발생한 파급효과는 A 로 전달

		// A 가 B 의 기능을 사용하기 위해 준비
		B b = new B();

		// 파급효과 1
		// 오류, 지연, 난해함 이 있는 기능
		b.execute1();

		// 파급효과 2
		// 잘 만들어진 기능
		b.execute2();
		// 추가기능
		b.execute2("P");

		// 파급효과 3
		// 이체기능이 실행되지만 개발환경에선 가상으로 이체기능이 실행되어야 함
		b.execute3();
		// b.execute3Dv();

	}

}

class B {

	void execute1() {
		// 파급효과1 해결 : 기능의 수정, 결함제거/리펙토링
	}

	void execute2() {
	}

	void execute2(String p) {
		// 파급효과2 해결 : 기능의 추가, 오버로딩/새로운메소드
	}

	void execute3() {
		// 이체기능
	}

	void execute3Dv() {
		// 파급효과3 해결 : 기능의 교체, 실행시점에 기능이 결정
	}

}