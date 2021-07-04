package com.code5.fw.data;

import java.io.Serializable;

/**
 * @author zero
 *
 */
public abstract class Box implements Serializable { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param key
	 * @param object
	 * 
	 * 
	 */
	public abstract void put(String key, Object object);

	/**
	 * @param key
	 * @return
	 * 
	 * 
	 */
	public abstract Object get(String key);

}
