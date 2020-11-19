package com.code5.fw.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author seuk
 * 
 */
public class BoxHttp extends Box {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * [1]
	 */
	private HttpServletRequest request = null;

	/**
	 * @param request
	 */
	public BoxHttp(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * [2]
	 */
	public Object get(String key) {

		Object obj = request.getAttribute(key);
		if (obj != null) {
			return obj;
		}

		return request.getParameter(key);
	}

	/**
	 *
	 */
	public void put(String key, Object obj) {
		request.setAttribute(key, obj);
	}

}
