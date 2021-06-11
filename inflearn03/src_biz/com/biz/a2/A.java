package com.biz.a2;

/**
 * @author zero
 *
 *         인터페이스는 교체비용을 줄이기 위해 사용합니다.
 */
class A {

	void execute() {

		B b = new BImpl();

		b.execute1();

		b.execute2();
		b.execute2("p");

		// 인터페이스를 사용한다고 해서 결합도가 낮아지는건 아님

		// 인터페이스 때문에
		// 코드 복잡도가 높아지고
		// 정보은닉을 하지 못함. (public, protected)

	}

}

interface B {

	// 3. 교체
	// public void execute3();

	public void execute1();

	public void execute2();

	public void execute2(String p);

}

class BImpl implements B {

	// 1. 오류 / 타임아웃 / 복잡함
	public void execute1() {
	}

	// 2. 기존기능
	public void execute2() {
	}

	// 2. 기능추가, 오버로딩
	public void execute2(String p) {

	}

}
