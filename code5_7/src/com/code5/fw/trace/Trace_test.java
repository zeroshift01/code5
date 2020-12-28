package com.code5.fw.trace;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class Trace_test extends TestCase {

	/**
	 * 
	 */
	public void test_단일쓰레드_로그기록() {

		TraceRunner.getTraceRunner().init(Trace_test.class.getName());

		Trace trace = new Trace(Trace_test.class);
		trace.write("가나다라");
		trace.write("abcd");
		trace.write("1234");
	}

	public void test_멀티쓰레드_로그기록() {

	}

}

class Trace_test$ extends Thread {

	@Override
	public synchronized void start() {

		Trace trace = new Trace(this);

		int hash = this.hashCode();

		for (int i = 0; i < 10000; i++) {
			trace.write("로그기록 가나다라 " + hash + " " + i);
		}

	}
}