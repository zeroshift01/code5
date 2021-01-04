package com.code5.fw.security;

/**
 * 
 * @author seuk
 *
 */
public class Aes_CBC_PKCS7_test {

	public static void main(String[] xxx) throws Exception {

		byte[] key = new byte[16];

		Aes_CBC_PKCS7 Aes_CBC_PKCS7 = new Aes_CBC_PKCS7(key, key);

		byte[] plan = new byte[16];

		byte[] enc = Aes_CBC_PKCS7.encrypt(plan);

		byte[] dec = Aes_CBC_PKCS7.decrypt(enc);

		System.out.println(plan.length);
		System.out.println(enc.length);
		System.out.println(dec.length);

	}
}
