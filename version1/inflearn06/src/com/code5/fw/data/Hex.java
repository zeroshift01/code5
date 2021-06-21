package com.code5.fw.data;

/**
 * @author seuk
 *
 */
public class Hex {

	/**
	 * @param b
	 * @return
	 */
	public static String byteToHex(byte[] b) {

		try {
			StringBuilder sb = new StringBuilder();
			for (final byte x : b) {
				sb.append(String.format("%02x", x));
			}
			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * @param s
	 * @return
	 */
	public static byte[] hexToByte(String s) {

		try {

			int len = s.length();
			byte[] data = new byte[len / 2];
			for (int i = 0; i < len; i += 2) {
				data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
