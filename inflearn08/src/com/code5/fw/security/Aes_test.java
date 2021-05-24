package com.code5.fw.security;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author zero
 *
 */
class Aes_test {

	/**
	 * @throws Exception
	 */
	@Test
	void test_01() throws Exception {

		Aes aes = new Aes();

		String s = "abcd1111";

		String enc = aes.encrypt(s);
		System.out.println(enc);
		String dec = aes.decrypt(enc);
		System.out.println(dec);

		assertEquals(s, dec);

	}

}
