package com.code5.biz.board.web;

import com.code5.fw.web.Box;
import com.code5.fw.web.BoxLocal;

/**
 * @author seuk
 *
 */
public class BoardC_test_step01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Box box = new BoxLocal();
		Box.setThreadLocal(box);

		BoardC boardC = new BoardC();

		box.put("pageNo", "1");

		boardC.listContents(box);

	}

}
