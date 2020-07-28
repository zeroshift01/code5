package com.code5.fw.security;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author seuk
 *
 */
public class Aes {

	Cipher encrypter = null;
	Cipher decrypter = null;
	SecretKeySpec keySpec = null;
	IvParameterSpec ivSpec = null;

	/**
	 * @param key
	 */
	public Aes(byte[] key, byte[] iv) throws Exception {

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

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String plan) throws Exception {

		if (plan == null) {
			return "";
		}

		plan = plan.trim();
		if ("".equals(plan)) {
			return "";
		}

		byte[] planB = plan.getBytes("UTF-8");

		byte[] encB = encrypt(planB);

		String enc = Base64.getEncoder().encodeToString(encB);
		return enc;
	}

	/**
	 * @param enc
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String enc) throws Exception {

		if (enc == null) {
			return "";
		}

		enc = enc.trim();
		if ("".equals(enc)) {
			return "";
		}

		byte[] encB = Base64.getDecoder().decode(enc);

		byte[] planB = decrypt(encB);

		String plan = new String(planB, "UTF-8");

		return plan;
	}
}
