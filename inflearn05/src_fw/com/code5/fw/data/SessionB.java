package com.code5.fw.data;

/**
 * @author zero
 * 
 *         stub
 */
public class SessionB {

	/**
	 * @param nextUrl
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String createToken(String nextUrl, String data) throws Exception {
		return data;
	}

	/**
	 * @param nextUrl
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String getDataByToken(String nextUrl, String token) throws Exception {
		return token;
	}

	public String getId() {
		return null;
	}

	/**
	 * @return
	 */
	public String getAuth() {
		return null;
	}

	/**
	 * @return
	 */
	public String getIp() {
		return null;
	}

}
