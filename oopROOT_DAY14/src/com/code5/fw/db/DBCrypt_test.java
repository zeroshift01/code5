package com.code5.fw.db;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class DBCrypt_test extends TestCase {

	/**
	 * @throws Exception
	 */
	public void test_한단어테스트() throws Exception {

		DBCrypt dbCrypt = DBCrypt.getDBCrypt();

		String plan = "abcd 1234 가나다라 !@#$";

		String enc = dbCrypt.encrypt(plan);

		String plan2 = dbCrypt.decrypt(enc);

		assertEquals(plan, plan2);

	}

	/**
	 * @throws Exception
	 */
	public void test_스트레스테스트() throws Exception {

		DBCrypt dbCrypt = DBCrypt.getDBCrypt();

		for (int i = 0; i < 100000; i++) {

			String plan = i + "abcd 1234 가나다라 !@#$" + i;

			String enc = dbCrypt.encrypt(plan);

			String plan2 = dbCrypt.decrypt(enc);

			assertEquals(plan, plan2);

		}

	}

	/**
	 * @throws Exception
	 */
	public void test_쓰레드안전성() throws Exception {

		DBCrypt_test$[] dbCrypt_test$ = new DBCrypt_test$[20];
		for (int i = 0; i < dbCrypt_test$.length; i++) {
			dbCrypt_test$[i] = new DBCrypt_test$();
			dbCrypt_test$[i].start();
		}

		for (int i = 0; i < dbCrypt_test$.length; i++) {
			dbCrypt_test$[i].join();
			assertEquals(dbCrypt_test$[i].isOK, true);
		}

	}
}

class DBCrypt_test$ extends Thread {

	boolean isOK = false;

	@Override
	public void run() {

		DBCrypt dbCrypt = DBCrypt.getDBCrypt();

		try {

			for (int i = 0; i < 100000; i++) {

				String plan = i + "abcd 1234 가나다라 !@#$" + i;

				String enc = dbCrypt.encrypt(plan);

				String plan2 = dbCrypt.decrypt(enc);

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
