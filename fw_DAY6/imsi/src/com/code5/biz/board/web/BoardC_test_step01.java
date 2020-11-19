package com.code5.biz.board.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author seuk
 *
 */
public class BoardC_test_step01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BoardC boardC = new BoardC();

		// [1]
		HttpServletRequest request = null;

		// [2]
		request.setParameter("name", "hello world!");

		// [3]
		request.setAttribute("name", "hello world!");

		boardC.welcome(request);

	}

}
