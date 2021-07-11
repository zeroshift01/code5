package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class BoardNoIoc implements BizController {

	/**
	 * @return
	 * @throws Exception
	 */
	public String callList() throws Exception {

		Box box = BoxContext.get();

		BoardD dao = new BoardD();
		Table list = dao.list();
		box.put("list", list);

		return "/WEB-INF/classes/com/biz/board/jsp/list.jsp";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String callWrite() throws Exception {

		return "/WEB-INF/classes/com/biz/board/jsp/write.jsp";

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String exeWrite() throws Exception {

		BoardD dao = new BoardD();
		dao.write();

		TransactionContext.commit();

		return callList();
	}

}
