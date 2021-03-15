package com.code5.fw.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zero
 *
 */
public class Aes_CBC_PKCS7 implements Crypt {

	private SecretKeySpec keySpec = null;
	private IvParameterSpec ivSpec = null;

	/**
	 * @param key
	 */
	public Aes_CBC_PKCS7(byte[] key, byte[] iv) throws Exception {

		this.keySpec = new SecretKeySpec(key, "AES");
		this.ivSpec = new IvParameterSpec(iv);
	}

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] plan) throws Exception {

		Cipher encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		encrypter.init(1, keySpec, ivSpec);

		byte[] enc = encrypter.doFinal(plan);

		return enc;

	}

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] enc) throws Exception {

		Cipher decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decrypter.init(2, keySpec, ivSpec);
		byte[] plan = decrypter.doFinal(enc);

		return plan;

	}

}
