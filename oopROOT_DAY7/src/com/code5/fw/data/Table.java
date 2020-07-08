package com.code5.fw.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.code5.fw.web.Box;
import com.code5.fw.web.BoxLocal;

/**
 * @author seuk
 * 
 *         블럭단위 배열 관리를 하면 성능이 개선
 *
 */
public class Table {

	/**
	 * 
	 */
	private static int MAX_RECODE_COUNT = 10000;

	/**
	 * 
	 */
	private ArrayList<String[]> recodes = new ArrayList<String[]>();

	/**
	 * 
	 */
	private HashMap<String, BigDecimal> colNameMap = new HashMap<String, BigDecimal>();;

	/**
	 * 추가데이터를 관리하는 컬랙션 객체
	 */
	private HashMap<String, String> addDatas = null;

	/**
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
	 */
	public Table() {
	}

	/**
	 * @param data
	 * @return
	 */
	public void addRecode(String[] data) {

		if (recodes.size() + 1 > MAX_RECODE_COUNT) {
			throw new RuntimeException();
		}

		if (data == null) {
			throw new RuntimeException();
		}

		if (colNameMap.size() != data.length) {
			throw new RuntimeException();
		}

		recodes.add(data);

	}

	/**
	 * 
	 */
	public void addRecode() {

		if (recodes.size() + 1 > MAX_RECODE_COUNT) {
			throw new RuntimeException();
		}

		String[] data = new String[colNameMap.size()];
		recodes.add(data);

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
	 * @return
	 */
	public Box getBox() {

		if (recodes.size() != 1) {
			throw new RuntimeException();
		}

		return getBox(0);

	}

}
