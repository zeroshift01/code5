package com.code5.biz.exe001;

import java.sql.SQLException;

import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.security.DataCrypt;
import com.code5.fw.trace.Trace;
import com.code5.fw.util.DateTime;
import com.code5.fw.web.Box;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.TransactionContext;
import com.other.system.Alnc;

/**
 * 
 * 카드승인
 * 
 * @author seuk
 * 
 */
public class Exe001 {

	/**
	 * 
	 */
	private Trace trace = new Trace(this);
	
	/**
	 * 
	 * [1]
	 * 
	 * 카드승인 화면 호출
	 * 
	 * @param box
	 * @return
	 */
	public String exe00110() throws Exception {

		Box box = Box.getThread();

		// TODO [장애] 이중 카드승인을 방지하기 위한 키
		String KEY = DateTime.getThisDTM() + "_exe00111";
		box.put("KEY", KEY);

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

		// TODO [성능] 모니터링을 위한 시작시간 종료시간 기록
		trace.write(DateTime.getThisDTM() + " 시작");

		SessionB user = box.getSessionB();
		
		// TODO [유지보수] 로그인한 사용자 정보를 매번 Box 객체에 전달함이 불편
		String ID = user.getId();
		box.put("USER_ID", ID);

		String CRD_N = box.s("CRD_N");

		if ("".equals(CRD_N)) {
			box.put("MSG", "카드번호를 입력해주세요.");
			return MasterController.execute("exe00110");
		}

		Exe001D dao = new Exe001D();

		// TODO [보안] 카드번호 암호화, DB
		DataCrypt dbDataCrypt = DataCrypt.getDataCrypt("SDB");
		String ENC_CRD_N = dbDataCrypt.encrypt(CRD_N);
		box.put("ENC_CRD_N", ENC_CRD_N);
		box.put("DTM", DateTime.getThisDTM());

		try {
			dao.INSERT_BZ_ALNC();
		} catch (Exception ex) {
			// TODO [장애] 이중 카드승인을 방지하기 위한 키
			trace.writeErr(ex);
			box.put("MSG", "화면을 새로 고침 하였거나 이미 승인이 된 화면입니다.");
			return MasterController.execute("exe00110");
		}

		// TODO [장애] 승인 요청할 데이터를 확정
		TransactionContext.getThread().commit();

		try {

			// TODO [보안] 카드번호 암호화, 외부시스템
			DataCrypt s03DataCrypt = DataCrypt.getDataCrypt("S03");

			String ALNC_CRD_N = s03DataCrypt.encrypt(CRD_N);
			String YYMM = box.s("YYMM");
			String AMT = box.s("AMT");

			Box alncBox = Alnc.execute(ALNC_CRD_N, YYMM, AMT);

			String ALNC_DTM = alncBox.s("ALNC_DTM");
			String ALNC_N = alncBox.s("ALNC_N");
			String RET = alncBox.s("RET");

			box.put("ALNC_DTM", ALNC_DTM);
			box.put("ALNC_N", ALNC_N);
			box.put("RET", RET);

		} catch (Exception ex) {

			// TODO [장애] 타임아웃등 외부 시스템 문제 발생시 처리

			Trace trace = new Trace(this);
			trace.writeErr(ex);
			box.put("MSG", "카드승인중 요청중 오류가 발생했습니다.");
			return MasterController.execute("exe00110");

		}

		String RET = box.s("RET");

		box.put("DTM", DateTime.getThisDTM());
		
		int cnt = dao.UPDATE_BZ_ALNC();
		// TODO [장애] 실수로 작성된 SQL 대량 업데이트를 방지
		if (cnt != 1) {
			throw new SQLException();
		}

		trace.write(DateTime.getThisDTM() + " 종료");

		if ("0000".equals(RET)) {
			box.put("MSG", "성공적으로 카드승인이 되었습니다.");
			return MasterController.execute("exe00120");
		}

		box.put("MSG", "카드승인이 실패했습니다. 에러코드[" + RET + "]");
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

		SessionB user = box.getSessionB();
		String ID = user.getId();
		box.put("USER_ID", ID);

		Exe001D dao = new Exe001D();

		Table list = null;
		String CRD_N = box.s("CRD_N");
		DataCrypt dbDataCrypt = DataCrypt.getDataCrypt("SDB");

		// TODO [유지보수] 검색조건이 있을때와 없을때를 고려 SQL 을 만들어야 함 

		if ("".equals(CRD_N)) {

			list = dao.SELECT_BZ_ALNC();

		} else {

			String ENC_CRD_N = dbDataCrypt.encrypt(CRD_N);
			box.put("ENC_CRD_N", ENC_CRD_N);

			list = dao.SELECT_BZ_ALNC_WHERE_CRD_N();
		}

		for (int i = 0; i < list.size(); i++) {

			// TODO [보안] 카드번호 복호화
			String THIS_CRD_N = list.s("CRD_N", i);
			THIS_CRD_N = dbDataCrypt.decrypt(THIS_CRD_N);

			list.setData("CRD_N", i, THIS_CRD_N);

		}

		box.put("list", list);

		return "exe00120";
	}
}
