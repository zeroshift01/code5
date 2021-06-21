package com.code5.fw.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author zero
 *
 */
public class TableRecodeBase extends Table {

	/**
	 * 
	 */
	private int limit = -1;

	/**
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 
	 */
	private HashMap<String, BigDecimal> colNameMap = new HashMap<String, BigDecimal>();;

	/**
	 * 
	 * 
	 * 
	 */
	private ArrayList<String[]> recodes = new ArrayList<String[]>();

	/**
	 * 
	 * 
	 * 
	 * 추가데이터를 관리하는 컬랙션 객체
	 */
	private HashMap<String, String> addDatas = null;

	/**
	 * 
	 * 
	 * 
	 * @param colNames
	 */
	public TableRecodeBase(String[] colNames) {

		if (colNames == null) {
			throw new RuntimeException();
		}

		for (int i = 0; i < colNames.length; i++) {
			this.colNameMap.put(colNames[i], new BigDecimal(i));
		}
	}

	/**
	 * 
	 * 
	 * 
	 */
	public TableRecodeBase() {
	}

	/**
	 * 
	 * 
	 * 
	 * @param data
	 * @return
	 */
	public boolean addRecode(String[] recode) {

		if (recode == null) {
			throw new RuntimeException();
		}

		if (colNameMap.size() != recode.length) {
			throw new RuntimeException();
		}

		if (this.isLimitRecode) {
			return false;
		}

		recodes.add(recode);

		if (size() > MAX_RECODE_COUNT) {
			this.isLimitRecode = true;
			return false;
		}

		if (this.limit > 0) {
			if (size() > this.limit) {
				this.isLimitRecode = true;
				return false;
			}
		}

		return true;

	}

	/**
	 *
	 */
	public boolean addRecode() {

		String[] data = new String[colNameMap.size()];

		if (this.isLimitRecode) {
			return false;
		}

		recodes.add(data);

		if (size() > MAX_RECODE_COUNT) {
			this.isLimitRecode = true;
			return false;
		}

		if (this.limit > 0) {
			if (size() > this.limit) {
				this.isLimitRecode = true;
				return false;
			}
		}

		return true;

	}

	/**
	 * 
	 * 
	 * @param colName
	 */
	public boolean addCol(String colName) {

		if (colNameMap.containsKey(colName)) {
			return false;
		}

		BigDecimal colPoint = new BigDecimal(colNameMap.size());
		colNameMap.put(colName, colPoint);

		return true;
	}

	/**
	 * 
	 * 
	 * @param colName
	 * @param row
	 * @return
	 */
	public String s(String colName, int row) {

		BigDecimal colB = colNameMap.get(colName);
		if (colB == null) {
			return "";
		}

		int colPosion = colB.intValue();

		String[] recode = recodes.get(row);
		if (recode == null) {
			return "";
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

		if (size() <= row) {
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
	 * @return
	 */
	public Box getBox() {

		if (size() != 1) {
			return new BoxLocal();
		}

		return getBox(0);

	}

	/**
	 * 
	 * @return
	 */
	public int size() {

		int size = recodes.size();

		if (this.isLimitRecode) {
			return size - 1;
		}

		return size;
	}

	/**
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
	 *
	 */
	private boolean isLimitRecode = false;

	/**
	 * @return
	 */
	public boolean isLimitRecode() {
		return isLimitRecode;
	}

	/**
	 * @return
	 */
	public Box getLimitBox() {

		if (!this.isLimitRecode) {
			return new BoxLocal();
		}

		return getBox(this.recodes.size() - 1);
	}

	/**
	 *
	 */
	private static int MAX_RECODE_COUNT = 10000;

	/**
	 * @return
	 */
	public int maxRecodeCount() {
		return MAX_RECODE_COUNT;
	}

	/**
	 *
	 */
	public boolean addCol(String colName, String[] data) {
		throw new RuntimeException();
	}

	/**
	 *
	 */
	public int colsSize() {
		return colNameMap.size();
	}

}
