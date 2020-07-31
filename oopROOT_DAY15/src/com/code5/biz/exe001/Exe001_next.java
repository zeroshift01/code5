package com.code5.biz.exe001;

import com.code5.fw.data.Table;
import com.code5.fw.security.DataCrypt;
import com.code5.fw.trace.Trace;
import com.code5.fw.web.Box;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.TransactionContext;
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
	 * 결제 화면 호출
	 * 
	 * @param box
	 * @return
	 */
	public String exe00110() throws Exception {

		// XSS 방어 프레임웍 기능으로 구현

		// 템플릿 사용
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

		String CRD_N = box.s("CRD_N");

		if ("".equals(CRD_N)) {
			box.put("MSG", "카드번호를 입력해주세요.");
			return MasterController.execute("exe00110");
		}

		Exe001D dao = new Exe001D();

		// DB 암호화 프레임웍 기능으로 구현
		dao.INSERT_BZ_ALNC();

		TransactionContext.getThread().commit();

		// 암호화 기능 사용 개선
		String ALNC_CRD_N = DataCrypt.encrypt("S03", CRD_N);

		String YYMM = box.s("YYMM");
		String AMT = box.s("AMT");

		String RET = null;

		try {

			RET = Alnc.execute(ALNC_CRD_N, YYMM, AMT);

		} catch (Exception ex) {

			Trace trace = new Trace(this);

			// 오류 횟수가 일정 수치까지 증가되면 보고
			trace.writeErrCount(ex);

			box.put("MSG", "결제요청중 오류가 발생했습니다.");
			return MasterController.execute("exe00110");

		}

		box.put("RET", RET);

		// update 결과 검증 프레임웍 기능으로 구현
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
	 * 결제 리스트 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exe00120() throws Exception {

		Box box = Box.getThread();

		Exe001D dao = new Exe001D();

		// 자동 복호화, SQL 분기 처리
		Table list = dao.SELECT_BZ_ALNC();
		
		box.put("list", list);

		// 템플릿 사용
		return "exe00120";
	}
}
