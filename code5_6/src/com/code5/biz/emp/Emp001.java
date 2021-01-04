package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.security.DataCrypt;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.TransactionContext;

/**
 * @author seuk
 *
 */
public class Emp001 implements BizController {

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   EMP 데이터 조회
	 */
	public String emp00101() throws Exception {

		DataCrypt crypt = DataCrypt.getDataCrypt("SDB");

		Box box = BoxContext.getThread();

		Emp001D dao = Emp001D.getDao();
		Table table = dao.emp00101();

		for (int i = 0; i < table.size(); i++) {
			String HP_N = table.s("HP_N", i);
			HP_N = crypt.decrypt(HP_N);
			table.setData("HP_N", i, HP_N);
		}

		box.put("table", table);

		return "emp00101";
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   휴대폰 번호 업데이트
	 */
	public String emp00102() throws Exception {

		DataCrypt crypt = DataCrypt.getDataCrypt("SDB");

		Box box = BoxContext.getThread();

		String[] EMP_N_S = box.ss("EMP_N");
		String[] HP_N_S = box.ss("HP_N");

		Emp001D dao = Emp001D.getDao();

		for (int i = 0; i < HP_N_S.length; i++) {

			box.put("EMP_N", EMP_N_S[i]);
			box.put("HP_N", crypt.encrypt(HP_N_S[i]));

			if (dao.emp00102() != 1) {
				throw new Exception();
			}
		}

		TransactionContext.getThread().commit();

		return MasterController.execute("emp00101");
	}

}
