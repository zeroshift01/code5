package com.code5.biz.comm002;

import com.code5.biz.comm001.Comm001;
import com.code5.fw.web.Box;
import com.code5.fw.web.SubController;

/**
 * @author seuk
 * 
 *         중요공지사항
 *
 */
public class Comm002 extends SubController {

	/**
	 * @return
	 */
	public void start() {
		Box box = Box.getThread();
		box.put("NAME", "InformationC");
	}

	/**
	 * @return
	 * 
	 *         중요 공지사항 리스트 보기
	 */
	public String comm00201() {

		Comm001 comm001 = new Comm001();
		comm001.comm00101();

		return "comm00201";
	}

	/**
	 * @return
	 * 
	 *         중요 공지사항 게시물 보기
	 * 
	 */
	public String comm00202() {

		return "comm00202";
	}

	/**
	 * @return
	 * 
	 *         공지사항 저장
	 */
	public String comm00203() {

		// 저장 후 리스트로 이동
		return comm00201();
	}
}
