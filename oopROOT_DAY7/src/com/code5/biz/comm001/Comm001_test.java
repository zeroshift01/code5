package com.code5.biz.comm001;

import com.code5.fw.data.Table;
import com.code5.fw.web.Box;
import com.code5.fw.web.TransactionContext;

import junit.framework.TestCase;

/**
 * @author seuk
 * 
 *         게시판
 *
 */
public class Comm001_test extends TestCase {

	/**
	 * @return
	 * 
	 *         게시판 리스트
	 */
	public void test_comm00101() throws Exception {

		Box box = Box.getThread();

		box.put("OFFSET", "1");

		Comm001D dao = new Comm001D();

		Table comm00101 = dao.comm00101();
		box.put("comm00101", comm00101);
	}

	/**
	 * @throws Exception
	 */
	public void test_comm00102() throws Exception {

		Box box = Box.getThread();

		Comm001D dao = new Comm001D();

		box.put("NUM", "1");
		box.put("HEAD", "제목");
		box.put("MAIN", "내용");

		dao.comm00103();

		box.put("NUM", "2");
		box.put("HEAD", "제목");
		box.put("MAIN", "내용");

		dao.comm00103();

		box.put("NUM", "3");
		box.put("HEAD", "제목");
		box.put("MAIN", "내용");

		dao.comm00103();

		TransactionContext.getThread().rollback();

	}

	/**
	 * @throws Exception
	 */
	public void test_comm00103() throws Exception {

		Box box = Box.getThread();

		Comm001D dao = new Comm001D();

		box.put("NUM", "1");
		box.put("HEAD", "제목");
		box.put("MAIN", "내용");

		dao.comm00103();

		box.put("NUM", "2");
		box.put("HEAD", "제목");
		box.put("MAIN", "내용");

		dao.comm00103();

		box.put("NUM", "3");
		box.put("HEAD", "제목");
		box.put("MAIN", "내용");

		dao.comm00103();

		TransactionContext.getThread().commit();

	}
}
