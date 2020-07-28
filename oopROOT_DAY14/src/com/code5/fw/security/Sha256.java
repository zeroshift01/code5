package com.code5.fw.security;

import java.security.MessageDigest;

import com.code5.fw.util.StringUtil;

/**
 * @author seuk
 *
 */
public class Sha256 {

	/**
	 * @param id
	 * @param pin
	 * @return
	 * @throws Exception
	 */
	public static String makePIN(String id, String pin) throws Exception {

		byte[] b = (id + "-" + pin).getBytes();

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(b);
		byte[] digest = md.digest();

		String ret = StringUtil.byteToHex(digest);
		return ret;

	}

}
