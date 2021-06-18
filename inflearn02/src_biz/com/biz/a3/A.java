package com.biz.a3;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 *         파급효과를 줄이기 위해 인터페이스를 쉽게 사용하지 마세요.
 */
class A {

	void execute() {

		// 인터페이스는 기능의 교체 요구사항이 있을때 사용

		// 기능의 교체 : 여러개의 기능이 준비되고 실행시점에 하나의 기능만 선택되어 실행됨

		// 여러개의 기능이 준비
		// BPrImpl.execute3(), BDvImpl.execute3()

		// 실행시점에 하나의 기능만 선택
		// 운영환경 : BPrImpl.execute3()
		// 개발환경 : BDvImpl.execute3()

		// 실행시점
		boolean isDev = InitYaml.get().is("isDev");

		B b = null;
		if (isDev) {
			b = new BDvImpl();
		} else {
			b = new BPrImpl();
		}

		b.execute3();

	}

}

interface B {
	public void execute3();
}

class BDvImpl implements B {

	public void execute3() {
		// 가상의 이체기능 실행
	}

}

class BPrImpl implements B {

	public void execute3() {
		// 이체기능 실행
	}

}
