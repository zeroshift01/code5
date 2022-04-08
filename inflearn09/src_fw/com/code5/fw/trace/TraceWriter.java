package com.code5.fw.trace;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author zero
 *
 */
class TraceWriter {

	String logKey = null;
	String logFileUrl = null;

	boolean isBuffer = false;

	int initCnt = 0;
	int errCnt = 0;

	private BufferedWriter out = null;

	
	/**
	 * @param logKey
	 * @param logFileUrl
	 * @param useBuffer
	 */
	TraceWriter(String logKey, String logFileUrl, boolean useBuffer) {

		this.logKey = logKey;
		this.logFileUrl = logFileUrl;
		this.isBuffer = useBuffer;

		try {

			this.out = new BufferedWriter(new FileWriter(this.logFileUrl, true));

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
			this.out.write(log);
			this.out.newLine();

			if (!isBuffer) {
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
