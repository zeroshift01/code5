package com.code5.biz.emp.emp001;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.data.UploadFileB;
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

		SessionB user = box.getSessionB();

		Emp001D dao = Emp001D.getDao();
		Table table = dao.emp00101();

		for (int i = 0; i < table.size(); i++) {
			String HP_N = table.s("HP_N", i);
			HP_N = crypt.decrypt(HP_N);
			table.setData("HP_N", i, HP_N);

			String FILE_ID = table.s("FILE_ID", i);
			FILE_ID = user.createToken("downloadfile", FILE_ID);
			table.setData("FILE_ID", i, FILE_ID);

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
		SessionB user = box.getSessionB();

		Table table = box.createTableByKey("EMP_N");

		Emp001D dao = Emp001D.getDao();

		for (int i = 0; i < table.size(); i++) {

			String HP_N = table.s("HP_N", i);
			table.setData("HP_N", i, crypt.encrypt(HP_N));

			String FILE_ID_ORG = table.s("FILE_ID_ORG", i);
			FILE_ID_ORG = user.getDataByToken("downloadfile", FILE_ID_ORG);
			box.put("FILE_ID", FILE_ID_ORG);

			UploadFileB file = box.getUploadFileB("FILE_" + i);
			if (file.getSize() != 0) {

				UploadFileB orgFile = new UploadFileB(FILE_ID_ORG);
				orgFile.delete();

				String FILE_ID = file.getFileId();
				box.put("FILE_ID", FILE_ID);
				file.save();
			}

			box.putFromTable(table, i);
			if (dao.emp00102() != 1) {
				throw new Exception();
			}
		}

		TransactionContext.getThread().commit();

		return MasterController.execute("emp00101");
	}

}
