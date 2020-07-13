package com.code5.biz.board.web;

import com.code5.fw.web.Box;
import com.code5.fw.web.BoxLocal;

/**
 * @author seuk
 *
 */
public class BoardC_test_step02 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Box box = new BoxLocal();

		// [1]
		box.setAttribute("name", "hello world!");

		BoardC boardC = new BoardC();

		boardC.welcome(box);

	}

}
