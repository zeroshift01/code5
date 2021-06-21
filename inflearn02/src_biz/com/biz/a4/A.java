package com.biz.a4;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 *         인터페이스와 구현클래스가 1:1 관계일때는 인터페이스를 제거합니다.
 */
class A {

	void execute() {

		// 기능의 교체 요구사항

		// JDBC 드라이버, 컬랙션객체
		// - 이미 잘 만들어진 틀에서 사용만 하면 됨
		// - 인터페이스를 만들 기회가 없음

		// 이체 : A은행이체, B은행이체, C은행이체
		// - 인터페이스 결합 대상이 아님
		// - 인터페이스 사용보다 디자인패턴으로 해결 : 퍼사드패턴(구조), 미디에이터패턴(행위)
		// - 기능의 호출 MasterController.execute("A은행이체");

		// 커피 : 카푸치노, 라떼, 아메리카노
		// - 아메리카노를 먼저 잘 만들고 카푸치노, 라떼를 고민

		// DAO, Service
		// 인터페이스와 구현클래스가 1:1 관계일 경우 인터페이스는 필요 없음

		// 현실적으로 기능의 교체 요구사항은 당장 없습니다.

		// 아메리카노를 먼저 잘 만들고 카푸치노 라떼를 고민

		// 실행시점에 따라 달라지는 기능을 구현할때 기준이 되는 기능을 잘 만들고 나서 고민

		// 대안
		// 인터페이스 없이 구현크래스 구현에 집중하고
		// 교체 요구사항이 발생했을때 추상화클래스와 팩토리메소드로 해결

		// B 객체 = new B();
		B 객체 = B.createB();

		객체.문제가있음();

		객체.잘만들어짐();
		객체.잘만들어짐(0);

		객체.실행시점에따라다름();
	}

}

abstract class B {

	// 실제 기능
	abstract void 실행시점에따라다름();

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

	//////////////////////////////////////

	// 팩토리메소드(생성패턴)
	public static B createB() {

		boolean isDev = InitYaml.get().is("isDev");

		if (isDev) {
			return new BDvImpl();
		}

		return new BPrImpl();
	}

}

class BDvImpl extends B {

	void 실행시점에따라다름() {
		// 가상의 기능
		// stub : 다른 모듈의 테스트를 위해 만들어진 가상의 기능
	}

}

class BPrImpl extends B {

	public void 실행시점에따라다름() {
		// 실제 기능
	}
}