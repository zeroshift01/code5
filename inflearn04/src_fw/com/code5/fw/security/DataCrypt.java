package com.code5.fw.security;

/**
 * @author zero
 * 
 *         stub
 *
 */
public class DataCrypt {

	/**
	 * @return
	 * 
	 */
	public static DataCrypt getDataCrypt(String OPT) throws Exception {
		return new DataCrypt();

	}

	/**
	 * @param encStr
	 * @return
	 */
	public String decrypt(String encStr) throws Exception {

		return encStr;
	}

	/**
	 * @param planStr
	 * @return
	 */
	public String encrypt(String planStr) throws Exception {
		return planStr;
	}

	/**
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] b) throws Exception {
		return b;
	}

	/**
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] b) throws Exception {
		return b;
	}
}
