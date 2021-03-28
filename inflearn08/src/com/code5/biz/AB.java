package com.code5.biz;

public class AB implements A, B {

	public static void main(String[] xx) {
		AB AB = new AB();
		AB.printlnAB();
	}

	void printlnAB() {

		A.println();
		B.println();

	}
}
