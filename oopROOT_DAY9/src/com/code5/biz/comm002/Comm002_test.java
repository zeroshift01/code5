// [1]
package com.code5.biz.comm002;

import com.code5.fw.db.Sql;
import com.code5.fw.web.Box;
import com.code5.fw.web.TransactionContext;

/**
 * 
 * BZ_BOARD 테스트 등록
 * 
 * @author seuk
 * 
 */
class Comm002_test {

	/**
	 * @param xx
	 * @throws Exception
	 */
	public static void main(String[] xx) throws Exception {

		Box box = Box.getThread();

		for (int i = 0; i < 1000; i++) {

			box.put("NUM", "" + i);
			box.put("HEAD", "제목[" + "" + i + "]");
			box.put("MAIN", "내용[" + "" + i + "]\n내용입니다.");

			Sql.getSql().executeSql("BOARD_x");

		}

		TransactionContext.getThread().commit();

	}

}
