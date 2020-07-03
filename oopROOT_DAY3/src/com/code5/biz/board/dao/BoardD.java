package com.code5.biz.board.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seuk
 *
 */
public class BoardD {

	/**
	 * @param pageNo
	 * @return
	 */
	public List<Map<String, String>> listContents(String pageNo) {

		Map<String, String> hm = new HashMap<String, String>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		list.add(hm);

		return list;
	}
}
