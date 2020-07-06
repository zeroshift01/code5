package com.code5.fw.data;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author seuk
 *
 */
public class Table_step01 {

	/**
	 * 
	 */
	private String[][] data = null;

	/**
	 * 
	 */
	private HashMap<String, BigDecimal> colsNameMap = null;

	/**
	 * @param cols
	 * @param data
	 * @throws Exception
	 */
	public Table_step01(String[] cols, String[][] data) throws Exception {

		if (data.length != cols.length) {
			throw new Exception();
		}

		this.data = data;

		for (int i = 0; i < cols.length; i++) {
			colsNameMap.put(cols[i], new BigDecimal(i));
		}
	}

	/**
	 * @param col
	 * @param row
	 * @return
	 */
	public String s(String col, int row) {

		int cos = colsNameMap.get(col).intValue();
		return data[cos][row];

	}

}
