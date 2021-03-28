package com.code5.fw.security;

import java.security.MessageDigest;

import com.code5.fw.data.Hex;

/**
 * @author seuk
 *
 */
public class CryptPin {

	/**
	 * @param data
	 * @param salt
	 * @return
	 * 
	 */
	public static String cryptPin(String data, String salt) {

		try {

			data = data + "-" + salt;

			byte[] b = data.getBytes();

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(b);
			byte[] enc = md.digest();

			return Hex.byteToHex(enc);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

}
