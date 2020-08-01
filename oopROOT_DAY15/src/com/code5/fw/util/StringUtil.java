package com.code5.fw.util;

import java.security.SecureRandom;

/**
 * @author seuk
 *
 */
public class StringUtil {

	/**
	 * @return
	 */
	public static String makePwd(int len) {

		StringBuffer temp = new StringBuffer();
		SecureRandom rnd = new SecureRandom();
		for (int i = 0; i < len; i++) {
			int rIndex = rnd.nextInt(3);
			switch (rIndex) {
			case 0:
				// a-z
				temp.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				temp.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				temp.append((rnd.nextInt(10)));
				break;
			}
		}
		return temp.toString();
	}

	/**
	 * @param s
	 * @return
	 */
	public static byte[] hexToByte(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * @param bytes
	 * @return
	 */
	public static String byteToHex(byte[] bytes) {

		StringBuilder sb = new StringBuilder();

		for (byte b : bytes) {

			sb.append(String.format("%02X", b & 0xff));
		}

		return sb.toString();
	}

	/**
	 * @param s
	 * @return
	 */
	public static String cleanXSS(String s) {

		s = s.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		s = s.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		s = s.replaceAll("'", "& #39;");
		s = s.replaceAll("eval\\((.*)\\)", "");
		s = s.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		s = s.replaceAll("script", "");
		return s;

	}
}
