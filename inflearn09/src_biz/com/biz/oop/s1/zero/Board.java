package com.biz.oop.s1.zero;

/**
 * @author zero
 *
 */
public class Board {

	private String name = null;

	private Object data = null;

	public String getName() {
		return name;
	}

	public Board(String name) {
		this.name = name;
	}

	public Object list(Object in) {
		return data;
	}

	public Object write(Object in) {
		return list(in);
	}

	public Object delete(Object in) {
		return list(in);
	}

}

// 1. 데이터, 메소드, 클래스, 개발자의 코드