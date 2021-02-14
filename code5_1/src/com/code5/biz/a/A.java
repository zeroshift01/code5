package com.code5.biz.a;

import com.code5.biz.b.B;

/**
 * @author zero
 *
 */
public class A {

	public static void main(String[] args) {

		B b = new B();
		String msg = b.makeMsg(args[0]);
		System.out.println(msg);

	}
 
}
