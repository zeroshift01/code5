package com.biz.mng001;

import com.code5.fw.data.Box;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;

/**
 * 
 * FW 테이블 관리
 * 
 * @author seuk
 * 
 */
public class Mng001 implements BizController {

	/**
	 * 
	 * FW_SQL 조회
	 * 
	 * @param box
	 * @return
	 */
	public String mng00110() throws Exception {

		Box box = BoxContext.getThread();

		if ("".equals(box.s("KEY"))) {
			return "mng00110";
		}

		Mng001D dao = new Mng001D();
		Box thisBox = dao.mng00110();

		box.put("thisBox", thisBox);

		return "mng00110";
	}

	/**
	 * 
	 * FW_SQL 등록/수정
	 * 
	 * @return
	 * @throws Exception
	 */
	public String mng00111() throws Exception {

		Mng001D dao = new Mng001D();
		if (dao.mng00111_1() == 0) {
			dao.mng00111_2();
		}

		return MasterController.execute("mng00110");
	}

	/**
	 * 
	 * FW_CONTROLLER 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public String mng00120() throws Exception {

		Box box = BoxContext.getThread();

		if ("".equals(box.s("KEY"))) {
			return "mng00120";
		}

		Mng001D dao = new Mng001D();
		Box thisBox = dao.mng00120();

		box.put("thisBox", thisBox);

		return "mng00120";
	}

	/**
	 * 
	 * FW_CONTROLLER 등록,수정
	 * 
	 * @return
	 * @throws Exception
	 */
	public String mng00121() throws Exception {

		Mng001D dao = new Mng001D();
		if (dao.mng00121_1() == 0) {
			dao.mng00121_2();
		}

		return MasterController.execute("mng00120");
	}

	/**
	 * 
	 * FW_VIEW 조회
	 * 
	 * @return
	 */
	public String mng00130() throws Exception {

		Box box = BoxContext.getThread();

		if ("".equals(box.s("KEY"))) {
			return "mng00130";
		}

		Mng001D dao = new Mng001D();
		Box thisBox = dao.mng00130();

		box.put("thisBox", thisBox);

		return "mng00130";

	}

	/**
	 * 
	 * FW_VIEW 등록/수정
	 * 
	 * @return
	 * @throws Exception
	 */
	public String mng00131() throws Exception {

		Mng001D dao = new Mng001D();
		if (dao.mng00131_1() == 0) {
			dao.mng00131_2();
		}

		return MasterController.execute("mng00130");

	}
}
