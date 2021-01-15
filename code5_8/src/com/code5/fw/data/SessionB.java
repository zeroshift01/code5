package com.code5.fw.data;

import java.io.Serializable;
import java.security.SecureRandom;

import com.code5.fw.security.Aria_CBC_PKCS7;

/**
 * 
 * 
 * @author seuk
 */
public class SessionB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id = null;

	/**
	 * 
	 */
	private String auth = null;

	/**
	 * 
	 */
	private String ip = null;

	/**
	 * 
	 */
	private Aria_CBC_PKCS7 aria = null;

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param id
	 * @param auth
	 * @param ip
	 * @throws Exception
	 */
	public SessionB(String id, String auth, String ip) throws Exception {

		this.id = id;
		this.auth = auth;
		this.ip = ip;

		SecureRandom random = new SecureRandom();

		byte[] iv = new byte[16];
		byte[] key = new byte[16];

		if (InitProperty.IS_PRODUCT()) {
			random.nextBytes(iv);
			random.nextBytes(key);
		}

		aria = new Aria_CBC_PKCS7(iv, key);
	}

	/**
	 * @param nextUrl
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String createToken(String nextUrl, String data) throws Exception {
		if (data == null) {
			return "";
		}

		if ("".equals(data)) {
			return "";
		}

		long nonce = System.currentTimeMillis();
		String key = nonce + "_" + nextUrl + "_" + data;
		return Hex.byteToHex(aria.encrypt(key.getBytes()));

	}

	/**
	 * @param nextUrl
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String getDataByToken(String nextUrl, String token) throws Exception {

		if (token == null) {
			return "";
		}

		if ("".equals(token)) {
			return "";
		}

		byte[] enc = Hex.hexToByte(token);

		String xx = new String(aria.decrypt(enc));
		long nonce = Long.parseLong(xx.substring(0, 11));
		String checkUrl = xx.substring(12, nextUrl.length());
		String data = xx.substring(12 + nextUrl.length() + 1);

		long t = System.currentTimeMillis();
		if (t - nonce > 1000 * 60 * 10) {
			throw new Exception();
		}

		if (!checkUrl.equals(nextUrl)) {
			throw new Exception();
		}

		return data;
	}
}
