package com.code5.fw.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author seuk
 *
 */
public class BizException extends Exception {

	/**
	 * 
	 */
	private Exception exception = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param exception
	 */
	public BizException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * @return
	 */
	public String printError() {
		PrintWriter pw = new PrintWriter(new StringWriter());
		try {
			this.exception.printStackTrace(pw);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			pw.close();
		}
		return pw.toString();
	}
}
