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
		random.nextBytes(iv);
		random.nextBytes(key);

		aria = new Aria_CBC_PKCS7(iv, key);
	}

	/**
	 * @param nextUrl
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String createToken(String nextUrl, String data) throws Exception {
		String key = nextUrl + "_" + data;
		return Hex.byteToHex(aria.encrypt(key.getBytes()));

	}

	/**
	 * @param nextUrl
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String getDataByToken(String nextUrl, String token) throws Exception {

		byte[] enc = Hex.hexToByte(token);

		String data = new String(aria.decrypt(enc));

		if (!data.startsWith(nextUrl)) {
			throw new Exception();
		}

		return data.substring(nextUrl.length() + 1);
	}
}
