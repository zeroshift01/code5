package com.code5.fw.trace;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Trace_test extends TestCase {

	@Override
	protected void setUp() throws Exception {
		// TraceRunner traceRunner = TraceRunner.getTraceRunner();
		// traceRunner.init(Trace_test.class.getName());
	}

	public void xtest_01_지역객체() throws Exception {

		Trace trace01 = new Trace(this);

		trace01.write("가나다라");
		trace01.write("" + 123);
		trace01.write(trace01.toString());
	}

	Trace trace02 = new Trace(this);

	public void xtest_02_맴버객체() throws Exception {

		trace02.write("가나다라");
		trace02.write("" + 123);
		trace02.write(trace02.toString());
	}

	private static Trace TRACE_03 = new Trace(Trace_test.class);

	public void xtest_03_전역객체() throws Exception {

		TRACE_03.write("가나다라");
		TRACE_03.write("" + 123);
		TRACE_03.write(TRACE_03.toString());
	}

	/**
	 * 
	 */
	public void xtest_단일쓰레드_로그기록() throws Exception {

		Trace trace = new Trace(Trace_test.class);

		trace.write("가나다라");
		trace.write("" + 123);
		trace.write(trace.toString());
	}

	public void test_멀티쓰레드_로그기록() throws Exception {

		TraceRunner traceRunner = TraceRunner.getTraceRunner();
		traceRunner.init(Trace_test.class.getName(), true);

		Trace_test$[] t = new Trace_test$[10];
		for (int i = 0; i < t.length; i++) {
			t[i] = new Trace_test$(i);
			t[i].start();
		}

		for (int i = 0; i < t.length; i++) {
			t[i].join();
			System.err.println("fin " + i);
		}

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

		Trace trace = new Trace(this);

		String msg = null;
		for (int i = 0; i < 100000; i++) {
			msg = thisID + " 로그기록 가나다라 abcd" + i;
			trace.write(msg);
			System.out.println(msg);
			if (i % 1000 == 0) {
				trace.writeErr("err " + msg);
				System.err.println("err " + msg);
			}
		}

		System.err.println(msg);

	}
}