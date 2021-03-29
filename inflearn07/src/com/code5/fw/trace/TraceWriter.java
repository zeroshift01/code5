package com.code5.fw.trace;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @author seuk
 *
 */
class TraceWriter {

	String logKey = null;
	String logFileUrl = null;

	boolean isMulti = false;

	int initCnt = 0;
	int errCnt = 0;

	private PrintWriter out = null;

	/**
	 * @param logKey
	 * @param logFileUrl
	 * @param isMulti
	 */
	TraceWriter(String logKey, String logFileUrl, boolean isMulti) {

		this.logKey = logKey;
		this.logFileUrl = logFileUrl;
		this.isMulti = isMulti;

		try {

			this.out = new PrintWriter(new FileWriter(this.logFileUrl), true);

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * @param log
	 */
	void println(String log) {

		if (this.out == null) {
			return;
		}

		try {

			this.out.println(log);

			if (!isMulti) {
				this.out.flush();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	void close() {

		if (this.out == null) {
			return;
		}

		try {
			this.out.flush();
			this.out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 
	 */
	void flush() {

		if (this.out == null) {
			return;
		}

		try {
			this.out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
