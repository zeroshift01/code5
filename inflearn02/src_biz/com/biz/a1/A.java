package com.biz.a1;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
<<<<<<< HEAD
 *         인터페이스와 구현클래스가 1:1 관계일때 인터페이스를 제거하세요.
=======
 *         파급효과를 줄이기 위해 인터페이스를 사용하지 마세요.
>>>>>>> refs/remotes/origin/master
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
		// B 에서 발생한 문제는 A 로 전달, 파급효과 발생

		// A 가 B 의 기능을 사용하기 위해 준비
		B 객체 = new B();

		// 문제1
		// 예 : 오류, 지연, 어려움
		객체.문제가있음();

		// 문제2
		// 예 : 요구사항, 리펙토링
		객체.잘만들어짐();
		객체.잘만들어짐(0);

		// 문제3
		// 예 : 계좌이체, 이메일전송
		객체.실행시점에따라다름();

	}

}

class B {

	void 문제가있음() {
		// 문제1 해결 : 기능의 수정, 결함제거/리펙토링
	}

	private int 잘만들어진기능에서사용하는데이터 = 0;

	int 잘만들어짐() {
		return 잘만들어진기능에서사용하는데이터++;
	}

	int 잘만들어짐(int i) {

		// 문제2 해결 : 기능의 추가, 오버로딩/새로운메소드

		잘만들어진기능에서사용하는데이터 = i;
		잘만들어진기능에서사용하는데이터++;
		return 잘만들어진기능에서사용하는데이터;

	}

	void 실행시점에따라다름() {

		// 문제3 해결 : 기능의 교체, 실행시점에 기능이 결정

		boolean isDev = InitYaml.get().is("isDev");
		if (isDev) {
			// 가상의 기능
			// stub : 다른 모듈의 테스트를 위해 만들어진 가상의 기능
			return;
		}

		// 실제 기능
	}

}