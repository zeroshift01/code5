package com.code5.fw.trace;

import org.junit.Test;

/**
 * @author zero
 *
 */
public class Trace_test {

	/**
	 * @throws Exception
	 */
	public void test_01_지역객체() throws Exception {

		Trace trace01 = new Trace(this);

		trace01.write("가나다라");
		trace01.write("" + 123);
		trace01.write(trace01.toString());
	}

	Trace trace02 = new Trace(this);

	/**
	 * @throws Exception
	 */
	public void test_02_맴버객체() throws Exception {

		trace02.write("가나다라");
		trace02.write("" + 123);
		trace02.write(trace02.toString());
	}

	private static Trace TRACE_03 = new Trace(Trace_test.class);

	/**
	 * @throws Exception
	 */
	public void test_03_전역객체() throws Exception {

		TRACE_03.write("가나다라");
		TRACE_03.write("" + 123);
		TRACE_03.write(TRACE_03.toString());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_멀티쓰레드_로그기록() throws Exception {

		Trace_test$[] t = new Trace_test$[2];
		for (int i = 0; i < t.length; i++) {
			t[i] = new Trace_test$(i);
			t[i].start();
		}

		for (int i = 0; i < t.length; i++) {
			t[i].join();
			System.err.println("fin " + i);
		}

		TraceRunner traceRunner = TraceRunner.getTraceRunner();
		traceRunner.flush();
	}

}

class Trace_test$ extends Thread {

	int thisID = -1;

	public Trace_test$(int i) {
		thisID = i;
	}

	@Override
	public void run() {

		String name = getName();

		Trace trace = new Trace(this);

		for (int i = 0; i < 10000000; i++) {
			String msg = "[" + name + "][" + i + "] log write";
			if (thisID == 0) {
				trace.write("out" + msg);
				trace.writeErr("err" + msg);
			} else {
				trace.writeErr("err" + msg);
				trace.write("out" + msg);
			}
		}

	}
}
