package com.code5.biz.exe001;

import java.sql.SQLException;

import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.security.DataCrypt;
import com.code5.fw.trace.Trace;
import com.code5.fw.util.DateTime;
import com.code5.fw.web.Box;
import com.code5.fw.web.MasterController;
import com.other.system.Alnc;

/**
 * 
 * 결제 처리
 * 
 * @author seuk
 * 
 */
public class Exe001 {

	/**
	 * 
	 * 결제 화면 호출
	 * 
	 * @param box
	 * @return
	 */
	public String exe00110() throws Exception {
		return "exe00110";
	}

	/**
	 * 
	 * 카드 승인
	 * 
	 * @return
	 */
	public String exe00111() throws Exception {

		Box box = Box.getThread();

		SessionB user = box.getSessionB();
		String ID = user.getId();
		box.put("USER_ID", ID);
		box.put("USER_IP", "?");

		String ALNC_DTM = DateTime.getThisDTM();
		box.put("ALNC_DTM", ALNC_DTM);

		String CRD_N = box.s("CRD_N");

		if ("".equals(CRD_N)) {
			box.put("MSG", "카드번호를 입력해주세요.");
			return MasterController.execute("exe00110");
		}

		Exe001D dao = new Exe001D();

		DataCrypt dbDataCrypt = DataCrypt.getDataCrypt("SDB");
		String ENC_CRD_N = dbDataCrypt.encrypt(CRD_N);
		box.put("ENC_CRD_N", ENC_CRD_N);
		box.put("DTM", DateTime.getThisDTM());

		dao.exe00111_1();

		DataCrypt s03DataCrypt = DataCrypt.getDataCrypt("S03");

		String ALNC_CRD_N = s03DataCrypt.encrypt(CRD_N);
		String YYMM = box.s("YYMM");
		String AMT = box.s("AMT");

		String RET = null;

		try {

			RET = Alnc.execute(ALNC_CRD_N, YYMM, AMT);

		} catch (Exception ex) {

			Trace trace = new Trace(this);
			trace.writeErr(ex);
			box.put("MSG", "결제요청중 오류가 발생했습니다.");
			return MasterController.execute("exe00110");

		}

		box.put("RET", RET);

		box.put("DTM", DateTime.getThisDTM());
		int cnt = dao.exe00111_2();
		if (cnt != 1) {
			throw new SQLException();
		}

		if ("0000".equals(RET)) {
			box.put("MSG", "성공적으로 결제가 처리되었습니다.");
			return MasterController.execute("exe00120");
		}

		box.put("MSG", "결제가 실패했습니다. 에러코드[" + RET + "]");
		return MasterController.execute("exe00110");

	}

	/**
	 * 
	 * 결제 리스트 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exe00120() throws Exception {

		Box box = Box.getThread();

		SessionB user = box.getSessionB();
		String ID = user.getId();
		box.put("USER_ID", ID);

		Exe001D dao = new Exe001D();

		Table list = null;
		String CRD_N = box.s("CRD_N");
		DataCrypt dbDataCrypt = DataCrypt.getDataCrypt("SDB");

		if ("".equals(CRD_N)) {

			list = dao.exe00120_1();

		} else {

			String ENC_CRD_N = dbDataCrypt.encrypt(CRD_N);
			box.put("ENC_CRD_N", ENC_CRD_N);

			list = dao.exe00120_2();
		}

		for (int i = 0; i < list.size(); i++) {

			String THIS_CRD_N = list.s("CRD_N", i);
			THIS_CRD_N = dbDataCrypt.decrypt(THIS_CRD_N);
			list.setData("CRD_N", i, THIS_CRD_N);

		}

		box.put("list", list);

		return "exe00120";
	}
}
