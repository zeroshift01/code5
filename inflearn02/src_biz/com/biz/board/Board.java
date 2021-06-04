package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Board {

	/**
	 * @param request
	 * @return
	 */
	public String callList() {

		Box box = BoxContext.getThread();

		String FIND_STR = (String) box.get("FIND_STR");
		String FIND_OPT = (String) box.get("FIND_OPT");

		String boardData = FIND_OPT + "=" + FIND_STR;

		box.put("BOARD_DATA", boardData);

		return "/WEB-INF/classes/com/biz/board/jsp/list.jsp";
	}

	/**
	 * @return
	 */
	public String callWrite() {

		return "/WEB-INF/classes/com/biz/board/jsp/write.jsp";

	}

	/**
	 * @return
	 */
	public String exeWrite() {

		Box box = BoxContext.getThread();

		box.put("MSG", "exeWrite ok");

		return callList();
	}

}
