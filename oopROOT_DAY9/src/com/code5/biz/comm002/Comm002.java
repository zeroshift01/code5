// [1]
package com.code5.biz.comm002;

import com.code5.fw.data.Table;
import com.code5.fw.web.Box;

/**
 * 
 * 로그인 전 화면, 로그인 후 화면
 * 
 * @author seuk
 * 
 */
class Comm002 {

	/**
	 * 
	 * 로그인 전 화면
	 * 
	 * @param box
	 * @return
	 */
	public String comm00201() throws Exception {

		setBoard();

		return "comm00201";
	}

	/**
	 * 
	 * 로그인 후 화면
	 * 
	 * @return
	 */
	public String comm00202() throws Exception {

		setBoard();

		return "comm00202";

	}

	/**
	 * @throws Exception
	 */
	private void setBoard() throws Exception {

		Comm002D dao = new Comm002D();

		Table list = dao.getBoard();

		Box box = Box.getThread();

		box.put("list", list);

	}
}
