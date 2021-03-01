package com.code5.fw.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zero
 *
 */
public class BoxHttp_step01 extends Box_step01 {

	/**
	 * 
	 */
	private HttpServletRequest request = null;

	/**
	 * @param request
	 */
	public BoxHttp_step01(HttpServletRequest request) {
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
