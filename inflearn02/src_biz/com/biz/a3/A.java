package com.biz.a3;

/**
 * @author zero
 *
 *         인터페이스는 교체비용을 줄이기 위해 사용합니다.
 */
class A {

	void execute() {

		// 인터페이스는 교체의 요구사항이 있을때 사용

		// 교체 : 2개이상의 기능이 동시에 사용되고
		// 실행시점에 그중 하나만 선택되어 사용되는 객체지향 기능

		// 대부분의 요구사항은
		// 인터페이스와 구현클래스가 1:1 관계
		// 교체의 요구사항이 아니다.

		// 기능의 정의
		boolean isDev = true;

		if (isDev) {
			B b = new BDevImpl();
			b.execute3();
			return;
		}

		B b = new BPrImpl();
		b.execute3();

	}

}

interface B {

	public void execute3();
}

class BDevImpl implements B {

	public void execute3() {
		// stub
	}

}

class BPrImpl implements B {

	public void execute3() {
		// api call
	}

}
