package com.code5.fw.trace;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author seuk
 *
 */
class TraceNotOutputStream extends OutputStream {

	/**
	 * @param b
	 * @throws IOException
	 */
	public void write(int b) throws IOException {

		try {

			// TODO [1]
			throw new Exception();
		} catch (Exception exx) {
			exx.printStackTrace();
		}
	}

}
