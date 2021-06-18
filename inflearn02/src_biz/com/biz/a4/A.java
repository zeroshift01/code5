package com.biz.a4;

/**
 * @author zero
 *
 *         인터페이스는 교체비용을 줄이기 위해 사용합니다.
 */
class A {

	void execute() {

		// 인터페이스와 구현클래스가 1:1 관계일때는
		// 인터페이스를 제거

		// 그래도 나중을 생각한다면
		// 추상화클래스와 팩토리메소드패턴 사용

		// B b = new B();
		B b = B.createB();

		b.execute1();

		b.execute2();
		b.execute2("p");

		b.execute3();
	}

}

abstract class B {

	public static B createB() {
		// 기능의 정의
		boolean isDev = true;

		if (isDev) {
			return new BDevImpl();
		}

		return new BPrImpl();
	}

	public void execute1() {
		// 1. 오류
		// 2. 타임아웃
		// 3. 복잡함
	}

	public void execute2() {
	}

	public void execute2(String p) {
		// 4. 기능추가
	}

	// 5. 교체
	abstract void execute3();
}

class BDevImpl extends B {

	void execute3() {
		// stub
	}

}

class BPrImpl extends B {

	void execute3() {
		// api call
	}

}
