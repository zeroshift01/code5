package com.other.system;

import java.util.concurrent.TimeoutException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.code5.fw.trace.Trace;
import com.code5.fw.util.DateTime;
import com.code5.fw.web.Box;
import com.code5.fw.web.BoxLocal;

/**
 * 
 * 결제 요청에 대한 처리를 수행하는 테스트 stub
 * 
 * @author seuk
 */
public class Alnc {

	/**
	 * 
	 * 암호화된 카드번호가 맞게 복호화 되는지 확인하고
	 * 
	 * 임의의 결과를 반환하는 테스트 stub
	 * 
	 * @param CRD_N
	 * @param YYMM
	 * @param AMT
	 * @return
	 * @throws Exception
	 */
	public static Box execute(String CRD_N, String YYMM, String AMT) throws Exception {

		byte[] enc = hexToByte(CRD_N);

		byte[] key = { -74, 57, 19, -96, 54, -39, -49, -48, 32, 23, -15, -51, 100, -12, -76, 3 };
		byte[] iv = { 119, -77, -92, 26, -117, -22, -36, 13, 80, -61, 6, 90, -56, -1, -70, 120 };

		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		Cipher decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decrypter.init(2, keySpec, ivSpec);
		byte[] plan = decrypter.doFinal(enc);

		CRD_N = new String(plan);

		Trace trace = new Trace(Alnc.class);
		trace.write("복호화된 카드번호 [" + CRD_N + "]");

		long x = System.currentTimeMillis() % 9;

		if (x >= 0) {
			if (x < 6) {

				// 성공

				Box thisBox = new BoxLocal();
				String ALNC_N = "" + (System.currentTimeMillis() % 10000000);
				thisBox.put("ALNC_DTM", DateTime.getThisDTM());
				thisBox.put("ALNC_N", ALNC_N);
				thisBox.put("RET", "0000");
				return thisBox;
			}
		}

		if (x >= 7) {
			if (x < 9) {

				// 실패

				Box thisBox = new BoxLocal();
				thisBox.put("RET", "999" + x);
				return thisBox;
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
