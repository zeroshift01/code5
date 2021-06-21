package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.security.DataCrypt;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Emp001 implements BizController {

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   사원정보 조회
	 */
	public String emp00110() throws Exception {

		Box box = BoxContext.getThread();

		Emp001D dao = new Emp001D();
		Table table = dao.emp00110();

		DataCrypt dataCrypt = DataCrypt.getDataCrypt("SDB");

		for (int i = 0; i < table.size(); i++) {
			String THIS_HP_N = table.s("HP_N", i);
			String DEC_HP_N = dataCrypt.decrypt(THIS_HP_N);
			table.setData("HP_N", i, DEC_HP_N);
		}

		box.put("table", table);

		return "emp00110";
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   사원정보 중 휴대폰 번호 수정
	 */
	public String emp00120() throws Exception {

		Box box = BoxContext.getThread();

		DataCrypt dataCrypt = DataCrypt.getDataCrypt("SDB");

		String HP_N = box.s("HP_N");
		String ENC_HP_N = dataCrypt.encrypt(HP_N);
		box.put("ENC_HP_N", ENC_HP_N);

		Emp001D dao = new Emp001D();
		Table table = dao.emp00110();
		for (int i = 0; i < table.size(); i++) {

			String THIS_EMP_N = table.s("EMP_N", i);

			box.put("EMP_N", THIS_EMP_N);
			if (dao.emp00120() != 1) {
				throw new Exception();
			}
		}

		return emp00110();
	}
}
