package com.code5.fw.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author seuk
 * 
 *         [1]
 *
 */
public class BoxHttp_step01 extends Box_step01 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * [2]
	 */
	private HttpServletRequest request = null;

	/**
	 * @param request
	 */
	public BoxHttp_step01(HttpServletRequest request) {
		request = this.request;
	}

	/**
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		return request.getParameter(key);
	}

	/**
	 *
	 */
	public void setAttribute(String key, Object obj) {
		request.setAttribute(key, obj);
	}

	/**
	 *
	 */
	public Object getAttribute(String key) {
		return request.getAttribute(key);
	}

}
