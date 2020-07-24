package com.code5.fw.trace;

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
	 * 
	 */
	private String classNameShort = null;

	/**
	 * 
	 */
	private void setClassNameShort() {

		this.classNameShort = this.className;

		int x = this.className.lastIndexOf(".");
		if (x >= 1) {
			this.classNameShort = this.className.substring(x + 1);
		}
	}

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

		setClassNameShort();

	}

	/**
	 * @param log
	 */
	public void write(String log) {
		traceRunner.write("out", this.classNameShort, log);
	}

	/**
	 * @param log
	 */
	public void writeErr(String log) {
		traceRunner.write("err", this.classNameShort, log);
	}

}
