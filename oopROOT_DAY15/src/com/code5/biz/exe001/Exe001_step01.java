package com.code5.biz.exe001;

import com.code5.fw.data.SessionB;
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
public class Exe001_step01 {

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

		String ALNC_DTM = DateTime.getThisDTM();
		box.put("ALNC_DTM", ALNC_DTM);

		String CRD_N = box.s("CRD_N");

		// TODO [1]
		if ("".equals(CRD_N)) {
			box.put("MSG", "카드번호를 입력해주세요.");
			return MasterController.execute("exe00110");
		}

		DataCrypt s03DataCrypt = DataCrypt.getDataCrypt("S03");

		String ALNC_CRD_N = s03DataCrypt.encrypt(CRD_N);
		String YYMM = box.s("YYMM");
		String AMT = box.s("AMT");

		String RET = null;

		try {
			// TODO [2-1]
			// time out 10초
			RET = Alnc.execute(ALNC_CRD_N, YYMM, AMT);

		} catch (Exception ex) {

			// TODO [2-2]

			Trace trace = new Trace(this);
			trace.writeErr(ex);
			box.put("MSG", "결제요청중 오류가 발생했습니다.");
			return MasterController.execute("exe00110");

		}

		// TODO [3-1]

		DataCrypt dbDataCrypt = DataCrypt.getDataCrypt("SDB");
		String ENC_CRD_N = dbDataCrypt.encrypt(CRD_N);
		box.put("ENC_CRD_N", ENC_CRD_N);
		box.put("DTM", DateTime.getThisDTM());

		Exe001D dao = new Exe001D();
		dao.INSERT_BZ_ALNC();

		// TODO [3-2]
		if ("0000".equals(RET)) {
			box.put("MSG", "성공적으로 결제가 처리되었습니다.");
			return MasterController.execute("exe00120");
		}

		box.put("MSG", "결제가 실패했습니다. 에러코드[" + RET + "]");
		return MasterController.execute("exe00110");

	}

}
