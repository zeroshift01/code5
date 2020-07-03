package com.code5.biz.comm001;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.code5.fw.web.Box;

/**
 * @author seuk
 * 
 *         공지사항 DAO
 *
 */
public class Comm001D {

	/**
	 * @param pageNo
	 * @return
	 * 
	 *         공지사항 리스트 조회
	 */
	List<Map<String, String>> comm00101() {

		Box box = Box.getThread();
		String pageNo = box.s("pageNo");

		// DAO 부분은 나중에 완성됩니다.
		pageNo.toString();

		Map<String, String> hm = new HashMap<String, String>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		list.add(hm);

		return list;
	}
}
