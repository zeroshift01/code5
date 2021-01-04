package com.code5.fw.security;

/**
 * 
 * TODO [1]
 * 
 * @author seuk
 *
 */
public class Aria_CBC_PKCS7_test {

	public static void main(String[] xxx) throws Exception {

		byte[] key = new byte[16];

		Aria_CBC_PKCS7 Aria_CBC_PKCS7 = new Aria_CBC_PKCS7(key, key);

		byte[] plan = new byte[16];

		byte[] enc = Aria_CBC_PKCS7.encrypt(plan);

		byte[] dec = Aria_CBC_PKCS7.encrypt(plan);

		System.out.println(plan.length);
		System.out.println(enc.length);
		System.out.println(dec.length);

	}

}
