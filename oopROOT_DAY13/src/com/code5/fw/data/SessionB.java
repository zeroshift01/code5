package com.code5.fw.data;

import java.io.Serializable;

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
	 * @param id
	 * @param auth
	 * @param site
	 */
	public SessionB(String id, String auth) {

		this.id = id;
		this.auth = auth;
	}

}
