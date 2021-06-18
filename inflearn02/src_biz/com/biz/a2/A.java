package com.biz.a2;

/**
 * @author zero
 *
 *         파급효과를 줄이기 위해 인터페이스를 사용하지 마세요.
 */
class A {

	void execute() {

		// 결합도
		// 2개 이상의 모듈간의 관계를 측정하는 척도

		// 내용결합 : 코드를 사용 기능이 교환
		// 공통결합
		// 외부결합
		// 제어결합
		// 스템프결합 : 구조체로 통해 기능이 교환, 인터페이스/추상화클래스
		// 자료결합 : 데이터를 통해 기능이 교환

		// 자료결합은 가장높은 개발비용 가장낮은 결합도
		// 내용결합은 가장낮은 개발비용 가장높은 결합도

		// 가장낮은 결합도가 좋지만 가장낮은 개발비용을 사용하는 것도 좋음

		// 낮은 결합도를 위해
		// 구현클래스와 인터페이스를 1:1 관계로 구성
		B b = new BImpl();

		b.execute1();

		b.execute2();

		b.execute2("P");

		b.execute3();

		// 추상화된 인터페이스에는 내용결합이 필요함

		// 따라서 인터페이스를 사용한다고
		// 내용결합에 있던 파급효과가 없어지는 건 아님

		// 오히려 인터페이스 때문에 코드 복잡도가 높아지고
		// 정보은닉을 하지 못함. (public, protected)

	}

}

interface B {

	public void execute1();

	public void execute2();

	public void execute2(String p);

	public void execute3();
}

class BImpl implements B {

	// 파급효과 1
	// 오류, 지연, 난해함 이 있는 기능
	public void execute1() {
	}

	// 파급효과 2
	// 잘 만들어진 기능이지만 추가기능이 필요
	public void execute2() {
	}

	public void execute2(String p) {
	}

	// 파급효과 3
	// 이체기능이 실행되지만 개발환경에선 가상의 이체기능이 실행되어야 함
	public void execute3() {
	}

}
