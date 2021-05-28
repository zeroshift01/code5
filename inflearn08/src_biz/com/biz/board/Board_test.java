package com.biz.board;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;

/**
 * @author zero
 *
 */
public class Board_test {

	/**
	 * @throws Exception
	 */
	@Test
	public void test_list() {

		try {
			Box box = BoxContext.getThread();

			box.put("TITLE", "A");

			MasterController.execute("list");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_write() throws Exception {

		try {

			SessionB user = new SessionB("ID1", "U0", "1");
			Box box = BoxContext.getThread();
			box.put(Box.KEY_SESSIONB, user);

			box.put("TITLE", "TITLE_1");
			box.put("TXT", "TXT_1");
			box.put("EM", "aaa@bb.cc");

			MasterController.execute("write");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_update() throws Exception {

		try {

			Box box = BoxContext.getThread();
			
			box.put("TITLE", "");

			SessionB user = new SessionB("ID1", "U0", "1");
			box.put(Box.KEY_SESSIONB, user);

			MasterController.execute("list");

			Table list = box.getTable("list");
			String N = list.s("N", 0);

			box.put("N", N);

			box.put("TITLE", "u-TITLE_1");
			box.put("TXT", "u-TXT_1");
			box.put("EM", "u-aaa@bb.cc");

			MasterController.execute("update");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @throws Exception
	 */
	// @Test
	public void test_delete() throws Exception {
		MasterController.execute("list");
	}

	/**
	 * @throws Exception
	 */
	// @Test
	public void test_deleteAll() throws Exception {
		MasterController.execute("deleteall");
	}

}
