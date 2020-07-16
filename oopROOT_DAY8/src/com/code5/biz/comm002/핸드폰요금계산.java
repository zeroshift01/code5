package com.code5.biz.comm002;

/**
 * @author skmpr
 *
 */
abstract class 핸드폰요금계산 {

	abstract 기본정책 기본정책();

	abstract 부가정책 부가정책();

	/**
	 * @return
	 */
	public long 지불할금액() {

		long 월요금 = 기본정책().요금();
		long 지불할금액 = 부가정책().요금계산(월요금);
		return 지불할금액;
	}

}
