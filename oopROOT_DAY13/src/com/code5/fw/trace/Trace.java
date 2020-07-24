package com.code5.fw.trace;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author seuk
 *
 */
public class Trace {

	/**
	 * 
	 */
	private TraceRunner traceRunner = TraceRunner.getTraceRunner();

	/**
	 * 
	 */
	private String className = null;

	/**
	 * @param cl
	 */
	@SuppressWarnings("rawtypes")
	public Trace(Object obj) {

		if (obj instanceof String) {
			this.className = (String) obj;
		} else if (obj instanceof Class) {
			this.className = ((Class) obj).getName();
		} else {
			this.className = obj.getClass().getName();
		}

	}

	/**
	 * @param log
	 */
	public void write(String log) {
		traceRunner.write("out", this.className, log);
	}

	/**
	 * @param log
	 */
	public void writeErr(String log) {
		traceRunner.write("err", this.className, log);
	}

	/**
	 * @param log
	 */
	public void writeErr(Exception ex) {
		if (ex == null) {
			return;
		}

		StringWriter sw = null;
		PrintWriter pw = null;

		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			ex.printStackTrace(pw);

			traceRunner.write("err", this.className, pw.toString());

		} catch (Exception exx) {
			try {

				if (sw != null) {
					sw.close();
				}
			} catch (Exception exxx) {
				exxx.printStackTrace();
			}

			try {
				if (pw != null) {
					pw.close();
				}
			} catch (Exception exxx) {
				exxx.printStackTrace();
			}

			exx.printStackTrace();
		}

	}

}
