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
	 *
	 */
	public String getMessage() {
		String message = this.exception.getMessage();
		if (message == null) {
			return "알수 없는 오류가 발생했습니다.";
		}

		return message;
	}

	/**
	 * @return
	 */
	public String getPrintStackTrace() {
		PrintWriter pw = null;
		StringWriter sw = null;
		try {

			sw = new StringWriter();
			pw = new PrintWriter(sw);

			this.exception.printStackTrace(pw);

			return sw.toString();

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		} finally {

			try {
				if (sw != null) {
					sw.close();
				}
			} catch (Exception exx) {
				exx.printStackTrace();
			}

			try {
				if (pw != null) {
					pw.close();
				}
			} catch (Exception exx) {
				exx.printStackTrace();
			}
		}

	}
}
