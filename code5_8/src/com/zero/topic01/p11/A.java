package com.zero.topic01.p11;

import com.code5.fw.data.InitProperty;

class A {

	void method() {

		B b = B.createB();

		b.method();

		b.method2();
		b.method("");

		b.method3();

	}

}

abstract class B {

	public static B createB() {

		// 자동화된 의존성 주입
		if (InitProperty.IS_PRODUCT()) {
			return new B_PR();
		}

		return new B_DV();
	}

	public void method() {
		// 1. 변경 : 오류 수정, 기능 개선
	}

	public void method(String p) {
		// 2. 추가 : 오버라이딩
	}

	public void method2() {
		// 2. 추가 : 새로운 메소드
	}

	abstract public void method3();
}

class B_DV extends B {

	public void method3() {
		// test stub
	}

}

class B_PR extends B {

	public void method3() {
		// 실제기능 수행
	}
}
