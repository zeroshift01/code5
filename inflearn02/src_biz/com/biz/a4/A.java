package com.biz.a4;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 *         파급효과를 줄이기 위해 인터페이스를 쉽게 사용하지 마세요.
 */
class A {

	void execute() {

		// 기능의 교체는 거의 없음

		// JDBC 드라이버, 컬랙션객체
		// - 이미 잘 만들어진 틀에서 확장만 하면 됨
		// - 인터페이스를 만들 기회가 없음

		// 이체 : A은행이체, B은행이체, C은행이체
		// - 인터페이스 결합 대상이 아님
		// - 기능의 호출 MasterController.execute("A은행이체");

		// 커피 : 카푸치노, 라떼, 아메리카노
		// - 아메리카노를 먼저 잘 만들고 카푸치노, 라떼를 고민

		// DAO, Service
		// 인터페이스와 구현클래스가 1:1 관계일 경우 인터페이스는 필요 없음

		// 아메리카노를 먼저 잘 만들고 카푸치노 라떼를 고민
		// 계좌이체 기능을 잘 만들고 개발환경을 고민
		// 추상화클래스와 팩토리메소드패턴 사용

		B b = new B();

		b.execute1();

		// b.execute2();
		b.execute2("p");

		b.execute3();
	}

}

class B {

	void execute1() {
	}

	void execute2() {
	}

	void execute2(String p) {
	}

	void execute3() {
	}

	///////////////////////////////////////////

	// 팩토리메소드(생성패턴)
	public static B createB() {

		boolean isDev = InitYaml.get().is("isDev");

		if (isDev) {
			return new BDvImpl();
		}

		return new BPrImpl();
	}

	// 기능의 교체
	// abstract void execute3();
}

class BDvImpl extends B {
	void execute3() {
	}

}

class BPrImpl extends B {
	void execute3() {
	}
}
