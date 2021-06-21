package com.code5.fw.trace;

/**
 * @author zero
 * 
 *         stub
 *
 */
public class Trace {

	/**
	 * @param cl
	 */
	public Trace(Object obj) {
	}

	/**
	 * @param log
	 */
	public void write(String log) {
		System.out.println(log);
	}

	/**
	 * @param log
	 */
	public void writeErr(String log) {
		System.out.println(log);
	}

	/**
	 * @param log
	 */
	public void writeErr(Exception ex) {
		System.out.println(ex.getMessage());
	}

	/**
	 * @param ex
	 */
	public void write(Exception ex) {
		System.out.println(ex.getMessage());
	}

}
