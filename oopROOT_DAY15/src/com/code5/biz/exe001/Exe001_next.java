package com.code5.biz.exe001;

import com.code5.fw.data.Table;
import com.code5.fw.security.DataCrypt;
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
public class Exe001_next {

	/**
	 * 
	 * [1]
	 * 
	 * 카드승인 화면 호출
	 * 
	 * 
	 * @param box
	 * @return
	 */
	public String exe00110() throws Exception {

		return "exe00110";
	}

	/**
	 * 
	 * [2]
	 * 
	 * 카드승인 처리
	 * 
	 * @return
	 */
	public String exe00111() throws Exception {

		Box box = Box.getThread();

		String CRD_N = box.s("CRD_N");

		if ("".equals(CRD_N)) {
			box.put("MSG", "카드번호를 입력해주세요.");
			return MasterController.execute("exe00110");
		}

		Exe001D dao = new Exe001D();

		dao.INSERT_BZ_ALNC();

		String ALNC_CRD_N = DataCrypt.encrypt("S03", CRD_N);

		String YYMM = box.s("YYMM");
		String AMT = box.s("AMT");

		Box alncBox = Alnc.execute(ALNC_CRD_N, YYMM, AMT);
		box.put("ALNC_BOX", alncBox);

		String RET = box.s("ALNC_BOX.RET");

		dao.UPDATE_BZ_ALNC();

		if ("0000".equals(RET)) {
			box.put("MSG", "성공적으로 결제가 처리되었습니다.");
			return MasterController.execute("exe00120");
		}

		box.put("MSG", "결제가 실패했습니다. 에러코드[" + RET + "]");
		return MasterController.execute("exe00110");

	}

	/**
	 * 
	 * [3]
	 * 
	 * 카드승인 결과 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exe00120() throws Exception {

		Box box = Box.getThread();

		Exe001D dao = new Exe001D();

		Table list = dao.SELECT_BZ_ALNC();

		box.put("list", list);

		return "exe00120";
	}
}
