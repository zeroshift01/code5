package com.code5.fw.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.code5.fw.data.Hex;

/**
 * 
 * @author seuk
 *
 */
public class Aes {

	// [1]
	private byte[] key = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	// [2]
	private SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
	private IvParameterSpec ivSpec = new IvParameterSpec(iv);

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String s) throws Exception {

		// [3]
		byte[] plan = s.getBytes();

		// [4]
		Cipher encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		encrypter.init(1, keySpec, ivSpec);
		byte[] enc = encrypter.doFinal(plan);

		// [5]
		return Hex.byteToHex(enc);

	}

	/**
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String s) throws Exception {

		// [6]
		byte[] enc = Hex.hexToByte(s);

		// [7]
		Cipher decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decrypter.init(2, keySpec, ivSpec);
		byte[] plan = decrypter.doFinal(enc);

		// [8]
		String plans = new String(plan);

		// [9]
		return plans.trim();

	}

}
