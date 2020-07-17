package com.code5.fw.web;

import java.io.Serializable;

/**
 * @author seuk
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
	private String site = null;

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
	public String getsite() {
		return site;
	}

	/**
	 * @param id
	 * @param auth
	 * @param site
	 */
	public SessionB(String id, String auth, String site) {

		this.id = id;
		this.auth = auth;
		this.site = site;
	}

}
