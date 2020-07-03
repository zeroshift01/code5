package com.code5.biz.board.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author seuk
 *
 */
/**
 * @author seuk
 *
 */
public class BoardC_step01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BoardC boardC = new BoardC();

		// HttpServletRequest 는 추상화 클래스로 객체로 생성이 불가능
		HttpServletRequest request = null;

		// HttpServletRequest 를 객체로 생성했다 하더라도 setParameter 메소드가 없음
		request.setParameter("pageNo", "1");

		// setParameter 대신 setAttribute 를 썻지만
		// 비즈니스를 구현한 코드에는 getParameter 를 사용한다.
		request.setAttribute("pateNo", "1");

		boardC.listContents(request);

	}

}
