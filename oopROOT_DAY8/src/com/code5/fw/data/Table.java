package com.code5.fw.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author seuk
 * 
 *
 */
public class Table {

	/**
	 * [1]
	 */
	private static int MAX_RECODE_COUNT = 10000;

	/**
	 * 
	 */
	private boolean isNextRecode = false;

	/**
	 * [2]
	 * 
	 * @return
	 */
	public boolean isNextRecode() {
		return isNextRecode;
	}

	/**
	 * @return
	 */
	public static int MAX_RECODE_COUNT() {
		return MAX_RECODE_COUNT;
	}

	/**
	 * 
	 * [3]
	 * 
	 */
	private ArrayList<String[]> recodes = new ArrayList<String[]>();

	/**
	 * 
	 */
	private HashMap<String, BigDecimal> colNameMap = new HashMap<String, BigDecimal>();;

	/**
	 * 
	 * [4]
	 * 
	 * 추가데이터를 관리하는 컬랙션 객체
	 */
	private HashMap<String, String> addDatas = null;

	/**
	 * 
	 * [5]
	 * 
	 * @param colNames
	 */
	public Table(String[] colNames) {

		if (colNames == null) {
			throw new RuntimeException();
		}

		for (int i = 0; i < colNames.length; i++) {
			this.colNameMap.put(colNames[i], new BigDecimal(i));
		}
	}

	/**
	 * 
	 * [6]
	 * 
	 */
	public Table() {
	}

	/**
	 * 
	 * [8]
	 * 
	 * @param data
	 * @return
	 */
	public boolean addRecode(String[] recode) {

		if (recodes.size() + 1 > MAX_RECODE_COUNT) {
			this.isNextRecode = true;
			return false;
		}

		if (recode == null) {
			throw new RuntimeException();
		}

		if (colNameMap.size() != recode.length) {
			throw new RuntimeException();
		}

		recodes.add(recode);
		return true;

	}

	/**
	 * 
	 */
	public boolean addRecode() {

		if (recodes.size() + 1 > MAX_RECODE_COUNT) {
			this.isNextRecode = true;
			return false;
		}

		String[] data = new String[colNameMap.size()];
		recodes.add(data);

		return true;

	}

	/**
	 * @param colName
	 */
	public void addCol(String colName) {

		if (colNameMap.containsKey(colName)) {
			return;
		}

		BigDecimal colPoint = new BigDecimal(colNameMap.size());
		colNameMap.put(colName, colPoint);
	}

	/**
	 * @return
	 */
	public int length() {
		return recodes.size();
	}

	/**
	 * 
	 * [7]
	 * 
	 * @param colName
	 * @param row
	 * @return
	 */
	public String s(String colName, int row) {

		BigDecimal colB = colNameMap.get(colName);
		if (colB == null) {
			throw new RuntimeException();
		}

		int colPosion = colB.intValue();

		String[] recode = recodes.get(row);
		if (recode == null) {
			throw new RuntimeException();
		}

		if (recode.length == 0) {
			return "";
		}

		if (recode.length - 1 < colPosion) {

			if (addDatas == null) {
				return "";
			}

			String key = colPosion + "_" + row;
			String thisData = addDatas.get(key);
			if (thisData == null) {
				return "";
			}
			return thisData;
		}

		String thisData = recode[colPosion];
		if (thisData == null) {
			return "";
		}
		return thisData;

	}

	/**
	 * @param colName
	 * @param row
	 * @return
	 */
	public String getData(String colName, int row) {
		return s(colName, row);
	}

	/**
	 * 
	 * [9]
	 * 
	 * @param colName
	 * @param row
	 * @param data
	 */
	public void setData(String colName, int row, String data) {

		BigDecimal colB = colNameMap.get(colName);
		if (colB == null) {
			throw new RuntimeException();
		}

		int colPosion = colB.intValue();

		if (recodes.size() <= row) {
			throw new RuntimeException();
		}

		String[] recode = recodes.get(row);
		if (recode == null) {
			throw new RuntimeException();
		}

		if (recode.length - 1 < colPosion) {

			if (addDatas == null) {
				addDatas = new HashMap<String, String>();
			}

			String key = colPosion + "_" + row;
			addDatas.put(key, data);

			return;
		}

		recode[colPosion] = data;
	}

	/**
	 * 
	 * [10]
	 * 
	 * @param row
	 * @return
	 */
	public Box getBox(int row) {

		Box box = new BoxLocal();
		Iterator<String> iterator = colNameMap.keySet().iterator();
		while (iterator.hasNext()) {
			String colName = iterator.next();
			String data = s(colName, row);
			box.put(colName, data);
		}

		return box;

	}

	/**
	 * 
	 * [11]
	 * 
	 * @return
	 */
	public Box getBox() {

		if (recodes.size() != 1) {
			throw new RuntimeException();
		}

		return getBox(0);

	}

}
