package com.code5.fw.data;

import java.io.Serializable;

/**
 * @author zero
 *
 */
public class SessionB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private boolean isLogin = false;

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
	 * @throws Exception
	 * 
	 */
	public SessionB(String ip) throws Exception {
		this.ip = ip;
	}

	/**
	 * @param id
	 * @param auth
	 * @param ip
	 * @throws Exception
	 */
	public SessionB(String id, String auth, String ip) throws Exception {
		this(ip);
		set(id, auth, ip);
	}

	/**
	 * @param id
	 * @param auth
	 * @param ip
	 * @throws Exception
	 */
	public void set(String id, String auth, String ip) throws Exception {

		this.id = id;
		this.auth = auth;
		this.ip = ip;

		this.isLogin = true;

	}

	/**
	 * @param nextUrl
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String createToken(String nextUrl, String data) throws Exception {
		// stub;
		return data;
	}

	/**
	 * @param nextUrl
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String getDataByToken(String nextUrl, String token) throws Exception {
		// stub
		return token;
	}

	/**
	 * @return
	 */
	public boolean isLogin() {
		return this.isLogin;
	}
}
