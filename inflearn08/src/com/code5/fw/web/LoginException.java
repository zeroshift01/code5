package com.code5.fw.web;

/**
 * @author zero
 *
 */
public class LoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "로그인이 필요합니다.";
	}

}
