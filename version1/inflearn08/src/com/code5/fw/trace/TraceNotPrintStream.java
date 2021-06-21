package com.code5.fw.trace;

import java.io.PrintStream;

/**
 * @author zero
 *
 */
class TraceNotPrintStream extends PrintStream {

	/**
	 * 
	 */
	public TraceNotPrintStream() {
		super(new TraceNotOutputStream());
	}
}
