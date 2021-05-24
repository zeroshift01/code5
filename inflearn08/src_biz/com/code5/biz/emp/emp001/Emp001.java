package com.code5.biz.emp.emp001;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.data.UploadFileB;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class Emp001 implements BizController {

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   EMP 데이터 조회
	 */
	public String emp00110() throws Exception {

		Box box = BoxContext.getThread();

		Emp001D dao = Emp001D.getDao();
		Table table = dao.emp00101();

		box.put("table", table);

		return "emp00110";
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   휴대폰 번호 업데이트
	 */
	public String emp00120() throws Exception {

		Box box = BoxContext.getThread();

		Table table = box.createTableByKey("EMP_N");

		Emp001D dao = Emp001D.getDao();

		for (int i = 0; i < table.size(); i++) {

			box.putFromTable(table, i);

			fileUpdate(box, table, i);

			if (dao.emp00102() != 1) {
				throw new Exception();
			}
		}

		TransactionContext.getThread().commit();

		return MasterController.execute("emp00110");
	}

	/**
	 * @param box
	 * @param table
	 * @param i
	 */
	private void fileUpdate(Box box, Table table, int i) throws Exception {

		SessionB user = BoxContext.getThread().getSessionB();

		String FILE_ID_ORG = table.s("FILE_ID_ORG", i);
		FILE_ID_ORG = user.getDataByToken("downloadfile", FILE_ID_ORG);
		box.put("FILE_ID", FILE_ID_ORG);

		UploadFileB file = box.getUploadFileB("FILE_" + i);
		if (file.getSize() == 0) {
			return;
		}

		UploadFileB orgFile = new UploadFileB(FILE_ID_ORG);
		orgFile.delete();

		String FILE_ID = file.getFileId();
		box.put("FILE_ID", FILE_ID);
		file.save();

	}
}
