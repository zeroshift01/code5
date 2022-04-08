package com.code5.fw.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author zero
 *
 */
public class TableColumnBase extends Table {

	/**
	 * 레코드 베이스와 달리 컬럼 베이스는 테이블의 추가 레코드가 없다. 따라서 처음 셋팅한 컬럼의 크기로 사이즈 고정
	 */
	private int size = -1;

	/**
	 * 
	 */
	private HashMap<String, BigDecimal> colNameMap = new HashMap<String, BigDecimal>();;

	/**
	 * 
	 * 
	 * 
	 */
	private ArrayList<String[]> cols = new ArrayList<String[]>();

	/**
	 *
	 */
	public boolean addCol(String colName) {
		return addCol(colName, new String[size()]);
	}

	/**
	 * 
	 * 
	 * @param colName
	 */
	public boolean addCol(String colName, String[] data) {

		if (colNameMap.containsKey(colName)) {
			return false;
		}

		if (size == -1) {
			size = data.length;
		}

		if (size != data.length) {
			return false;
		}

		BigDecimal colPoint = new BigDecimal(colNameMap.size());
		colNameMap.put(colName, colPoint);
		cols.add(data);

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
			throw new RuntimeException();
		}

		int colPosion = colB.intValue();

		String[] coldata = cols.get(colPosion);
		if (coldata == null) {
			throw new RuntimeException();
		}

		if (coldata.length == 0) {
			return "";
		}

		String thisData = coldata[row];
		if (thisData == null) {
			return "";
		}
		return thisData;

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

		String[] coldata = cols.get(colPosion);
		if (coldata == null) {
			throw new RuntimeException();
		}

		if (coldata.length == 0) {
			throw new RuntimeException();
		}

		coldata[row] = data;

	}

	/**
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 *
	 */
	public boolean addRecode(String[] recode) {
		throw new RuntimeException();
	}

	/**
	 *
	 */
	public boolean addRecode() {
		throw new RuntimeException();
	}

	/**
	 *
	 */
	public boolean isLimitRecode() {
		throw new RuntimeException();
	}

	/**
	 * @return
	 */
	public Box getLimitBox() {
		throw new RuntimeException();
	}

	/**
	 * 
	 */
	public void setLimit(int limit) {
		throw new RuntimeException();
	}

	/**
	 *
	 */
	public int maxRecodeCount() {
		return size;
	}

	@Override
	public int colsSize() {
		return colNameMap.size();
	}

	@Override
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

}
