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
	 *         [1]
	 */
	public static String cryptPin(String data, String salt) {

		try {

			// [2]
			data = data + "-" + salt;

			// [3]
			byte[] b = data.getBytes();

			// [4]
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(b);
			byte[] enc = md.digest();

			// [5]
			return Hex.byteToHex(enc);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

}
