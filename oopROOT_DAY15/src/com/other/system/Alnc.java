package com.other.system;

import java.util.concurrent.TimeoutException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * 결제 요청에 대한 처리를 수행하는 테스트 stub
 * 
 * @author seuk
 */
public class Alnc {

	/**
	 * @param CRD_N
	 * @param YYMM
	 * @param AMT
	 * @return
	 * @throws Exception
	 */
	public static String execute(String CRD_N, String YYMM, String AMT) throws Exception {

		byte[] enc = hexToByte(CRD_N);

		byte[] key = { -74, 57, 19, -96, 54, -39, -49, -48, 32, 23, -15, -51, 100, -12, -76, 3 };
		byte[] iv = { 119, -77, -92, 26, -117, -22, -36, 13, 80, -61, 6, 90, -56, -1, -70, 120 };

		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		Cipher decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decrypter.init(2, keySpec, ivSpec);
		byte[] plan = decrypter.doFinal(enc);

		CRD_N = new String(plan);

		System.out.println("복호화된 카드번호 [" + CRD_N + "]");

		long x = System.currentTimeMillis() % 9;

		if (x >= 0) {
			if (x < 6) {
				// 성공
				return "0000";
			}
		}

		if (x >= 7) {
			if (x < 9) {
				// 실패
				return "999" + x;
			}
		}

		// 타임아웃 발생
		throw new TimeoutException();

	}

	/**
	 * @param s
	 * @return
	 */
	private static byte[] hexToByte(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
