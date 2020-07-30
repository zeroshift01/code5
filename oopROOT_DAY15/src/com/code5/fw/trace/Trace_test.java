package com.code5.fw.trace;

/**
 * @author seuk
 *
 */
public class Trace_test {

	public static void main(String[] xxx) {

		Trace trace = new Trace(Trace_test.class);
		trace.write("xxx");
	}

}
