package com.code5.biz.board.web;

import com.code5.fw.web.Box;
import com.code5.fw.web.BoxLocal;

/**
 * @author seuk
 *
 */
public class BoardC_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BoardC boardC = new BoardC();
		Box box = new BoxLocal();
		box.put("pageNo", "1");

		boardC.listContents(box);

	}

}
