package com.code5.fw.security;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class Aes_test extends TestCase {

	/**
	 * @throws Exception
	 */
	public void test_한단어테스트() throws Exception {

		byte[] key = new byte[16];
		byte[] iv = new byte[16];

		Aes aes = new Aes(key, iv);

		String plan = "abcd 1234 가나다라 !@#$";

		String enc = aes.encrypt(plan);

		String plan2 = aes.decrypt(enc);

		assertEquals(plan, plan2);

	}

	/**
	 * @throws Exception
	 */
	public void test_스트레스테스트() throws Exception {

		byte[] key = new byte[16];
		byte[] iv = new byte[16];

		Aes aes = new Aes(key, iv);

		for (int i = 0; i < 100000; i++) {

			String plan = i + "abcd 1234 가나다라 !@#$" + i;

			String enc = aes.encrypt(plan);

			String plan2 = aes.decrypt(enc);

			assertEquals(plan, plan2);

		}

	}

	/**
	 * @throws Exception
	 */
	public void test_쓰레드안전성() throws Exception {

		byte[] key = new byte[16];
		byte[] iv = new byte[16];

		Aes aes = new Aes(key, iv);

		Aes$[] aes$ = new Aes$[20];
		for (int i = 0; i < aes$.length; i++) {
			aes$[i] = new Aes$(aes);
			aes$[i].start();
		}

		for (int i = 0; i < aes$.length; i++) {
			aes$[i].join();
			assertEquals(aes$[i].isOK, true);
		}

	}
}

class Aes$ extends Thread {

	private Aes aes = null;

	boolean isOK = false;

	Aes$(Aes aes) {
		this.aes = aes;
	}

	@Override
	public void run() {

		try {

			for (int i = 0; i < 100000; i++) {

				String plan = i + "abcd 1234 가나다라 !@#$" + i;

				String enc = aes.encrypt(plan);

				String plan2 = aes.decrypt(enc);

				if (!plan.equals(plan2)) {
					isOK = false;
					break;
				}

			}

			isOK = true;

		} catch (Exception ex) {
			isOK = false;
			ex.printStackTrace();
		}

	}

}
