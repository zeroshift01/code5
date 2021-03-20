package com.code5.fw.security;

import java.util.Base64;

/**
 * @author zero
 *
 */
public class Aria_ECB_ZERO_test {

	public static void main(String[] xxx) throws Exception {

		byte[] key = new byte[16];

		Aria_ECB_ZERO aria_ECB_ZERO = new Aria_ECB_ZERO(key);

		byte[] plan = new byte[16];

		System.out.println(Base64.getEncoder().encodeToString(aria_ECB_ZERO.encrypt(plan)));

		plan[15] = 1;

		System.out.println(Base64.getEncoder().encodeToString(aria_ECB_ZERO.encrypt(plan)));

		plan[15] = 2;

		System.out.println(Base64.getEncoder().encodeToString(aria_ECB_ZERO.encrypt(plan)));

		plan[15] = 3;

		System.out.println(Base64.getEncoder().encodeToString(aria_ECB_ZERO.encrypt(plan)));

		plan[15] = 4;

		System.out.println(Base64.getEncoder().encodeToString(aria_ECB_ZERO.encrypt(plan)));

		plan[15] = 5;

		System.out.println(Base64.getEncoder().encodeToString(aria_ECB_ZERO.encrypt(plan)));

	}

}
