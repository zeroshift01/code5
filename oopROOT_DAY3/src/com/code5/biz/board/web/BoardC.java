package com.code5.biz.board.web;

import java.util.List;
import java.util.Map;

import com.code5.biz.board.dao.BoardD;
import com.code5.fw.web.Box;

/**
 * @author seuk
 *
 */
public class BoardC {

	/**
	 * @param request
	 * @return
	 */
	public String listContents() {

		// http://localhost:11118/waf/listContents?pageNo=2

		Box box = Box.getThread();

		String pageNo = box.s("pageNo");

		BoardD dao = new BoardD();
		List<Map<String, String>> listContents = dao.listContents(pageNo);
		box.put("listContents", listContents);

		return "/biz/board/listContents.jsp";
	}

}
