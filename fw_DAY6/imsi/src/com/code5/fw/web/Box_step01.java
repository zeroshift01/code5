package com.code5.fw.web;

import java.io.Serializable;

/**
 * @author seuk
 *
 */
public abstract class Box_step01 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param key
	 * @param object
	 */
	public abstract void setAttribute(String key, Object object);

	/**
	 * @param key
	 * @return
	 */
	public abstract Object getAttribute(String key);

	/**
	 * @param key
	 * @param object
	 * @return
	 */
	public abstract String getParameter(String key);

}
