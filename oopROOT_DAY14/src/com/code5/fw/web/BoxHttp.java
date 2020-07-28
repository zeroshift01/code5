package com.code5.fw.web;

import javax.servlet.http.HttpServletRequest;

import com.code5.fw.data.SessionB;

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
	 *
	 */
	public SessionB getSessionB() {
		return (SessionB) request.getSession().getAttribute(KEY_SESSIONB);
	}

	/**
	 * 
	 *
	 */
	public void setSessionB(SessionB sessionB) {
		request.getSession().setAttribute(KEY_SESSIONB, sessionB);
	}

}
