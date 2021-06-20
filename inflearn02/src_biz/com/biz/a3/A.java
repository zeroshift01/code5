package com.biz.a3;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 *         인터페이스와 구현클래스가 1:1 관계일때는 인터페이스를 제거합니다.
 */
class A {

	void execute() {

		// 기능의 교체 : 여러개의 기능이 준비되고 실행시점에 하나의 기능만 선택되어 실행됨

		// 인터페이스는 기능의 교체가 필요할때 유용함
		// 1) 여러개의 기능을 잘 만들어 두고 2) 실행시점에 한개의 기능을 선택

		// 1) 여러개의 기능을 잘 만들어 두고
		// BPrImpl.실행시점에따라다름()
		// BDvImpl.실행시점에따라다름()

		// 2) 실행시점에 한개의 기능을 선택
		B 객체 = B.createB();

		객체.문제가있음();

		객체.잘만들어짐();
		객체.잘만들어짐(0);

		객체.실행시점에따라다름();

		// 인터페이스의 가장 큰 문제 : 중복코드

		// 중복코드를 제거하기 위해 사용되는

		// 인터페이스에서 제공하는 default는 객체지향적이지 않고 (데이터가 없고 기능만 있는)
		// 공통기능을 위한 추가 클래스는 코드가 복잡해짐

		// 더욱 큰 문제는
		// B가 가지고 있는 문제3 만 해결됨

	}

}

interface B {

	public static B createB() {

		// 문제3 해결 : 기능의 교체, 실행시점에 기능이 결정

		boolean isDev = InitYaml.get().is("isDev");

		B 객체 = null;
		if (isDev) {
			객체 = new BDvImpl();
		} else {
			객체 = new BPrImpl();
		}

		return 객체;

	}

	public void 실행시점에따라다름();

	public void 문제가있음();

	public int 잘만들어짐();

	public int 잘만들어짐(int i);
}

class BDvImpl implements B {

	public void 실행시점에따라다름() {
		// 가상의 기능
		// stub : 다른 모듈의 테스트를 위해 만들어진 가상의 기능
	}

	public void 문제가있음() {
		// 문제1 해결 : 기능의 수정, 결함제거/리펙토링
	}

	private int 잘만들어진기능에서사용하는데이터 = 0;

	public int 잘만들어짐() {
		return 잘만들어진기능에서사용하는데이터++;
	}

	public int 잘만들어짐(int i) {

		// 문제2 해결 : 기능의 추가, 오버로딩/새로운메소드

		잘만들어진기능에서사용하는데이터 = i;
		잘만들어진기능에서사용하는데이터++;
		return 잘만들어진기능에서사용하는데이터;

	}

}

class BPrImpl implements B {

	public void 실행시점에따라다름() {
		// 실제 기능
	}

	public void 문제가있음() {
		// 문제1 해결 : 기능의 수정, 결함제거/리펙토링
	}

	private int 잘만들어진기능에서사용하는데이터 = 0;

	public int 잘만들어짐() {
		return 잘만들어진기능에서사용하는데이터++;
	}

	public int 잘만들어짐(int i) {

		// 문제2 해결 : 기능의 추가, 오버로딩/새로운메소드

		잘만들어진기능에서사용하는데이터 = i;
		잘만들어진기능에서사용하는데이터++;
		return 잘만들어진기능에서사용하는데이터;

	}

}
