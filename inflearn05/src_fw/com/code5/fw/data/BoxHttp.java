package com.code5.fw.data;

import java.util.ArrayList;
import java.util.Enumeration;

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

		obj = request.getParameter(key);

		if (obj != null) {
			return obj;
		}

		return null;
	}

	/**
	 * 
	 */
	public void put(String key, Object obj) {
		request.setAttribute(key, obj);
	}

	/**
	 * 
	 */
	public void setSessionB(SessionB sessionB) {
		request.setAttribute(KEY_SESSIONB, sessionB);
		request.getSession().setAttribute(KEY_SESSIONB, sessionB);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String[] ss(String key) throws Exception {
		Object obj = get(key);

		if (obj instanceof String[]) {
			return (String[]) obj;
		}

		obj = request.getParameterValues(key);

		if (obj instanceof String[]) {
			return (String[]) obj;
		}

		return new String[0];

	}

	/**
	 *
	 */
	public String[] getKeys() {

		Enumeration<String> em1 = request.getAttributeNames();
		Enumeration<String> em2 = request.getParameterNames();

		ArrayList<String> list = new ArrayList<String>();

		while (em1.hasMoreElements()) {
			String ss = em1.nextElement();
			list.add(ss);
		}

		while (em2.hasMoreElements()) {
			String ss = em2.nextElement();
			if (!list.contains(ss)) {
				list.add(ss);
			}
		}

		String[] keys = new String[list.size()];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = list.get(i);
		}

		return keys;

	}

}
