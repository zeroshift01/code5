package com.code5.biz.board.web;

import com.code5.fw.web.Box;

/**
 * @author seuk
 *
 */
public class BoardC_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Box box = Box.getThread();
		box.put("pageNo", "1");

		BoardC boardC = new BoardC();
		boardC.listContents();

	}

}
