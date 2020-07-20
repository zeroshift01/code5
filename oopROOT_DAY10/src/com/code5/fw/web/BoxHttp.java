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
	Object getAttribute(String key) {

		Object obj = request.getAttribute(key);
		if (obj != null) {
			return obj;
		}

		String s = request.getParameter(key);
		if (s != null) {
			return s;
		}

		String[] ss = request.getParameterValues(key);
		return ss;
	}

	/**
	 *
	 */
	void setAttribute(String key, Object obj) {
		request.setAttribute(key, obj);
	}

	/**
	 * 
	 */
	public void setSessionB(SessionB user) {

		request.getSession().setAttribute(Box.SESSIONB_KEY, user);
	}

	/**
	 *
	 */
	public SessionB getSessionB() {

		return (SessionB) request.getSession().getAttribute(Box.SESSIONB_KEY);

	}

}
