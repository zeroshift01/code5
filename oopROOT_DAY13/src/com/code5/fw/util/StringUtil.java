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

}
