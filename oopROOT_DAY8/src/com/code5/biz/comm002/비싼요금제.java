package com.code5.biz.comm002;

/**
 * @author skmpr
 *
 */
class 비싼요금제 implements 기본정책 {

	public long 요금() {
		return (new 일반요금제()).요금() * 10;
	}
}