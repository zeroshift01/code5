package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class Emp001 {

	// SQL 의존문제를 해결하기 위한 방법
	// 1. SQL 과 JAVA 코드 분리 -> KEY
	// 2. 정적 SQL, 동적 SQL 장점을 흡수한 쉬운 사용 -> getTable, executeSql
	// 3. 컬랙션의 단점을 해결 -> Table

	/**
	 * @return
	 * @throws Exception
	 */
	public String emp00101() throws Exception {

		Box box = BoxContext.getThread();

		Emp001D dao = new Emp001D();
		Table table = dao.emp00101();
		box.put("table", table);

		return "/WEB-INF/classes/com/code5/biz/emp/emp00101.jsp";

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String emp00102() throws Exception {

		emp00101();

		Box box = BoxContext.getThread();
		Table table = box.getTable("table");

		Emp001D dao = new Emp001D();

		for (int i = 0; i < table.size(); i++) {

			box.put("EMP_N", table.getData("EMP_N", i));

			int updateCnt = dao.emp00102();
			if (updateCnt != 1) {
				throw new Exception();
			}
		}

		TransactionContext.commit();

		return emp00101();
	}
}
