package com.code5.biz.comm001;

import java.util.List;
import java.util.Map;

import com.code5.fw.web.Box;
import com.code5.fw.web.SubController;

/**
 * @author seuk
 * 
 *         공지사항
 *
 */
public class Comm001 extends SubController {

	/**
	 * @return
	 */
	protected void start() {
		Box box = Box.getThread();
		box.put("NAME", "BoardC");
	}

	/**
	 * @return
	 * 
	 *         공지사항 리스트
	 */
	public String comm00101() {

		Box box = Box.getThread();

		Comm001D dao = new Comm001D();
		List<Map<String, String>> comm00101 = dao.comm00101();

		box.put("comm00101", comm00101);

		return "comm00101";
	}

	/**
	 * @return
	 * 
	 *         공지사항 게시물 보기
	 * 
	 */
	public String comm00102() {

		return "comm00102";
	}

	/**
	 * @return
	 * 
	 *         공지사항 저장
	 */
	public String comm00103() {

		// 공지사항 저장 후 공지사항 리스트로 이동
		return comm00101();
	}
}
