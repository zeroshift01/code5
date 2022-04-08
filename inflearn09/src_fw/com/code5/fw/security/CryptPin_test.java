package com.code5.fw.security;

import org.junit.Test;

/**
 * @author zero
 *
 */
public class CryptPin_test {

	@Test
	public void test_01() {

		String admin = CryptPin.cryptPin("1", "admin");
		String user1 = CryptPin.cryptPin("1", "user1");
		String user2 = CryptPin.cryptPin("1", "user2");

		System.out.println(admin);
		System.out.println(user1);
		System.out.println(user2);

	}

}
