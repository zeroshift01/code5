package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Board {

	/**
	 * @return
	 */
	public String callList() {

		Box box = BoxContext.get();

		String findStr = (String) box.get("findStr");

		box.put("list", "list=[" + findStr + "]");

		return "/WEB-INF/classes/com/biz/board/jsp/list.jsp";
	}

	/**
	 * @param request
	 * @param out
	 * @return
	 */
	public String callWrite() {

		return "/WEB-INF/classes/com/biz/board/jsp/write.jsp";

	}

	/**
	 * @param request
	 * @param response
	 */
	public String exeWrite() {

		Box box = BoxContext.get();

		String txt = (String) box.get("txt");

		System.out.println("exeWrite [" + txt + "]");

		box.put("exeWriteResult", "ok");

		return callList();
	}

}
