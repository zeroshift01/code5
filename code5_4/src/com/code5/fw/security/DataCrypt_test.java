package com.code5.fw.security;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class DataCrypt_test extends TestCase {

	/**
	 * @throws Exception
	 */
	public void test_한단어테스트() throws Exception {

		DataCrypt dataCrypt = DataCrypt.getDataCrypt("S01");

		String plan = "abcd 1234 가나다라 !@#$";

		String enc = dataCrypt.encrypt(plan);

		String plan2 = dataCrypt.decrypt(enc);

		assertEquals(plan, plan2);

		dataCrypt = DataCrypt.getDataCrypt("S01");

		enc = dataCrypt.encrypt(plan);

		plan2 = dataCrypt.decrypt(enc);

		assertEquals(plan, plan2);

	}

	/**
	 * @throws Exception
	 */
	public void test_쓰레드안전성() throws Exception {

		DataCrypt_test$[] DataCrypt_test$ = new DataCrypt_test$[20];
		for (int i = 0; i < DataCrypt_test$.length; i++) {
			DataCrypt_test$[i] = new DataCrypt_test$();
			DataCrypt_test$[i].start();
		}

		for (int i = 0; i < DataCrypt_test$.length; i++) {
			DataCrypt_test$[i].join();
			assertEquals(DataCrypt_test$[i].isOK, true);
		}

	}
}

class DataCrypt_test$ extends Thread {

	boolean isOK = false;

	@Override
	public void run() {

		try {

			for (int i = 0; i < 100000; i++) {

				DataCrypt dataCrypt = DataCrypt.getDataCrypt("S02");

				String plan = i + "abcd 1234 가나다라 !@#$" + i;

				String enc = dataCrypt.encrypt(plan);

				String plan2 = dataCrypt.decrypt(enc);

				if (i % 1000 == 0) {
					System.out.println(i+" "+this.hashCode());
					System.out.println(plan);
					System.out.println(enc);
					System.out.println(plan2);
				}

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
