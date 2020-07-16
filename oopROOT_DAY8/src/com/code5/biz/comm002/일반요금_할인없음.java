package com.code5.biz.comm002;

/**
 * @author skmpr
 *
 */
class 일반요금_할인없음 extends 핸드폰요금계산 {

	기본정책 기본정책() {
		return new 일반요금제();
	}

	부가정책 부가정책() {
		return new 할인50();
	}

	/**
	 * @param xxx
	 */
	public static void main(String[] xxx) {

		// 템플릿 패턴
		핸드폰요금계산 a = new 일반요금_할인없음();
		System.out.println(a.지불할금액());
	}

}
