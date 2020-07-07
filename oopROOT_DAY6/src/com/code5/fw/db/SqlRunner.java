package com.code5.fw.db;

import java.util.List;
import java.util.Map;

/**
 * @author seuk
 *
 */
public interface SqlRunner {

	/**
	 * @return
	 */
	public List<Map<String, String>> getData();

	/**
	 * @return
	 */
	public int executeSql();

}
