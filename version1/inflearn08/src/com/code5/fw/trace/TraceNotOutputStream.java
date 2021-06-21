package com.code5.fw.trace;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zero
 *
 */
class TraceNotOutputStream extends OutputStream {

	/**
	 * @param b
	 * @throws IOException
	 */
	public void write(int b) throws IOException {

		try {
			throw new Exception();
		} catch (Exception exx) {
			exx.printStackTrace();
		}
	}

}
