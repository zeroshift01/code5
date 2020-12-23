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
	 * TODO [1-1]
	 */
	private HashMap<String, BigDecimal> colNameMap = new HashMap<String, BigDecimal>();;

	/**
	 * 
	 * TODO [1-2]
	 * 
	 */
	private ArrayList<String[]> recodes = new ArrayList<String[]>();

	/**
	 * 
	 * TODO [1-3]
	 * 
	 * 추가데이터를 관리하는 컬랙션 객체
	 */
	private HashMap<String, String> addDatas = null;

	/**
	 * 
	 * TODO [2-1]
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
	 * TODO [2-2]
	 * 
	 */
	public Table() {
	}

	/**
	 * 
	 * TODO [3-1]
	 * 
	 * @param data
	 * @return
	 */
	public boolean addRecode(String[] recode) {

		if (recodes.size() + 1 > MAX_RECODE_COUNT) {
			// TODO [3-2]
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
	 * TODO [3-3]
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
	 * 
	 * TODO [4]
	 * 
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
	 * TODO [5]
	 * 
	 * @return
	 */
	public int size() {
		return recodes.size();
	}

	/**
	 * 
	 * TODO [7]
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
	 * TODO [8-1]
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

		// TODO [8-2]
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
	 * TODO [9-1]
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
	 * TODO [9-2]
	 * 
	 * @return
	 */
	public Box getBox() {

		if (recodes.size() != 1) {
			throw new RuntimeException();
		}

		return getBox(0);

	}

	/**
	 * 
	 * TODO [10]
	 * 
	 * @return
	 * 
	 */
	public String[] getCols() {

		String[] cols = new String[colNameMap.size()];
		int i = 0;
		Iterator<String> iterator = colNameMap.keySet().iterator();
		while (iterator.hasNext()) {
			String colName = iterator.next();
			cols[i] = colName;
			i++;
		}

		return cols;
	}

	/**
	 * TODO [11]
	 */
	private boolean isNextRecode = false;

	/**
	 * @return
	 */
	public boolean isNextRecode() {
		return isNextRecode;
	}

	/**
	 * TODO [12]
	 */
	private static int MAX_RECODE_COUNT = 10000;

	/**
	 * @return
	 */
	public static int MAX_RECODE_COUNT() {
		return MAX_RECODE_COUNT;
	}

}
