package com.code5.fw.security;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Aes_test extends TestCase {

	public void test_01() throws Exception {

		Aes aes = new Aes();

		String s = "abcd1111";

		String enc = aes.encrypt(s);
		System.out.println(enc);
		String dec = aes.decrypt(enc);
		System.out.println(dec);

		assertEquals(s, dec);

	}

}
