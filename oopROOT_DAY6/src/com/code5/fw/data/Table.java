package com.code5.fw.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

import com.code5.fw.web.Box;
import com.code5.fw.web.BoxLocal;

/**
 * @author seuk
 *
 */
public class Table {

	/**
	 * 
	 */
	private String[][] data = null;

	/**
	 * 
	 */
	private HashMap<String, BigDecimal> colsNameMap = null;

	/**
	 * 
	 */
	private HashMap<String, String> addData = null;

	/**
	 * 
	 */
	private int maxLength = -1;

	/**
	 * @param cols
	 * @param data
	 */
	public Table(String[] cols, String[][] data) {

		if (cols == null) {
			throw new RuntimeException();
		}

		if (data == null) {
			throw new RuntimeException();
		}

		if (data.length != cols.length) {
			throw new RuntimeException();
		}

		this.data = data;
		this.colsNameMap = new HashMap<String, BigDecimal>();
		this.maxLength = data.length;

		for (int i = 0; i < cols.length; i++) {
			this.colsNameMap.put(cols[i], new BigDecimal(i));
		}
	}

	/**
	 * 
	 */
	public Table() {
		this.data = new String[][] { {} };
		this.maxLength = 0;
		colsNameMap = new HashMap<String, BigDecimal>();
	}

	/**
	 * @return
	 */
	public int length() {
		if (maxLength == -1) {
			throw new RuntimeException();
		}
		return maxLength;
	}

	/**
	 * @param col
	 * @param row
	 * @return
	 */
	public String s(String colName, int row) {

		BigDecimal colB = colsNameMap.get(colName);
		if (colB == null) {
			throw new RuntimeException();
		}

		int col = colB.intValue();

		if (data.length > col) {

			if (data[col].length > row) {

				String thisData = data[col][row];
				if (thisData == null) {
					return "";
				}

				// 초기데이터집합에 있을경우 리턴
				return thisData;
			}

		}

		// 추가된 데이터가 리턴
		String key = colName + "_" + row;
		String data = addData.get(key);
		if (data == null) {
			return "";
		}

		return data;

	}

	/**
	 * @param colName
	 * @param row
	 * @param data
	 */
	public void addData(String colName, int row, String data) {

		BigDecimal colB = colsNameMap.get(colName);
		if (colB == null) {
			colsNameMap.put(colName, new BigDecimal(colsNameMap.size() + 1));
		}

		String key = colName + "_" + row;

		if (addData == null) {
			addData = new HashMap<String, String>();
		}
		addData.put(key, data);

		if (row > maxLength - 1) {
			maxLength = row;
		}
	}

	/**
	 * @param row
	 * @return
	 */
	public Box getBox(int row) {

		Box box = new BoxLocal();
		Iterator<String> iterator = colsNameMap.keySet().iterator();
		while (iterator.hasNext()) {
			String colName = iterator.next();
			String data = s(colName, row);
			box.put(colName, data);
		}

		return box;

	}

}
