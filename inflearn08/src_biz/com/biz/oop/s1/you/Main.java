package com.biz.oop.s1.you;

import com.biz.oop.s1.zero.Board;

/**
 * @author you
 *
 */
public class Main {

	public static void main(String[] x) {

		Board board = new Board("board1");

		Object in = new Object();

		Object out = board.list(in);

		out = board.write(in);

		out = board.delete(in);

		out.toString();

	}

}

// 1. 클래스 : 개발자의 코드
// 2. 객체 : 개발자의 코드로 만들어진 동작할 수 있는 단위

// 3. 절차지향적인 개발 : 요구사항을 개발자의 코드로 구현
// 4. 객체지향적인 개발 : 개발자의 코드를 실행 시점에 조합 요구사항을 실행

// A. 패키지 : 클래스를 묶을 수 있는 디렉토리
