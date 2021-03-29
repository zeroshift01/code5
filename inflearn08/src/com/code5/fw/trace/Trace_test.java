package com.code5.fw.trace;

import com.code5.fw.data.InitProperty;

import junit.framework.TestCase;

/**
 * @author zero
 *
 */
public class Trace_test extends TestCase {

	/**
	 * 
	 */
	public void test_단일쓰레드_로그기록() throws Exception {

		InitProperty.init(Trace_test.class.getName());

		Trace trace = new Trace(Trace_test.class);

		trace.write("가나다라");
		trace.write("" + 123);
		trace.write(trace.toString());
	}

	public void test_멀티쓰레드_로그기록() throws Exception {

		InitProperty.init(Trace_test.class.getName(), true);

		Trace_test$[] t = new Trace_test$[10];
		for (int i = 0; i < t.length; i++) {
			t[i] = new Trace_test$();
			t[i].start();
		}

		for (int i = 0; i < t.length; i++) {
			t[i].join();
		}

	}

}

class Trace_test$ extends Thread {

	public void run() {

		Trace trace = new Trace(this);

		int hash = this.hashCode();

		for (int i = 0; i < 10000; i++) {
			trace.write("로그기록 가나다라 " + hash + " " + i);
		}

	}
}