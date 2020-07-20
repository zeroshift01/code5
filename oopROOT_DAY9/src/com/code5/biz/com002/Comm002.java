// [1]
package com.code5.biz.com002;

import com.code5.fw.data.Table;
import com.code5.fw.web.Box;

/**
 * 
 * ë¡œê·¸?¸ ? „ ?™”ë©?, ë¡œê·¸?¸ ?›„ ?™”ë©?
 * 
 * @author seuk
 * 
 */
public class Comm002 {

	/**
	 * 
	 * ë¡œê·¸?¸ ? „ ?™”ë©?
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
	 * ë¡œê·¸?¸ ?›„ ?™”ë©?
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
