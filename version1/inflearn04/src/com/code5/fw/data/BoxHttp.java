package com.code5.fw.data;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zero
 *
 */
public class BoxHttp extends Box {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private HttpServletRequest request = null;

	/**
	 * @param request
	 */
	public BoxHttp(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 
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
