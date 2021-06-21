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
	 * @param id
	 * @param auth
	 * @param ip
	 */
	public SessionB(String id, String auth, String ip) {

		this.id = id;
		this.auth = auth;
		this.ip = ip;
	}

}
