package com.code5.fw.data;

import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
abstract public class Table {

	abstract public boolean isLimitRecode();

	abstract public Box getLimitBox();

	abstract public void setLimit(int limit);

	abstract public boolean addRecode(String[] recode);

	abstract public boolean addRecode();

	abstract public boolean addCol(String colName);

	abstract public boolean addCol(String colName, String[] data);

	abstract public void setData(String colName, int row, String data);

	abstract public String s(String colName, int row);

	abstract public int size();

	abstract public int maxRecodeCount();

	abstract public int colsSize();

	abstract public String[] getCols();

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
	 * @return
	 */
	public Box getBox() {

		if (size() != 1) {
			throw new RuntimeException();
		}

		return getBox(0);

	}

	/**
	 * 
	 * @param row
	 * @return
	 */
	public Box getBox(int row) {

		Box box = new BoxLocal(BoxContext.get().isXssConvert());

		String[] cols = getCols();
		for (int i = 0; i < cols.length; i++) {
			String colName = cols[i];
			String data = s(colName, row);
			box.put(colName, data);
		}
		return box;
	}

	/**
	 *
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String[] cols = getCols();

		for (int i = 0; i < cols.length; i++) {
			sb.append(cols[i] + "\t");
		}
		sb.append("\n");

		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < cols.length; j++) {

				sb.append(s(cols[j], i) + "\t");
			}
			sb.append("\n");
		}

		return sb.toString();

	}
}
