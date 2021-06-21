package com.code5.fw.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.code5.fw.security.DataCrypt;

/**
 * @author zero
 *
 */
public class UploadFileB_test {

	static int blockSize = 64;
	static int metaSize = 16;

	public static void main(String[] xxx) throws Exception {

		e();

		DataCrypt dataCrypt = DataCrypt.getDataCrypt("S01");

		InputStream in = new FileInputStream(new File("C:/public/imsi.dat.enc"));
		FileOutputStream out = new FileOutputStream(new File("C:/public/imsi.dat"));

		byte[] encHead = new byte[blockSize + metaSize];
		in.read(encHead);

		byte[] head = dataCrypt.decrypt(encHead);

		byte[] nonce = new byte[blockSize];
		byte[] meta = new byte[metaSize];

		System.arraycopy(head, 0, nonce, 0, blockSize);
		System.arraycopy(head, blockSize, meta, 0, metaSize);

		String meta1 = new String(meta);
		String meta2 = meta1.substring(0, 7);
		String meta3 = meta1.substring(7);

		if (!"CODE5__".equals(meta2)) {
			throw new Exception();
		}
		int size = Integer.parseInt(meta3);

		byte[] iv = new byte[blockSize];
		System.arraycopy(head, 0, iv, 0, iv.length);
		print("iv", iv);

		byte[] block = new byte[blockSize];

		int cnt = size / blockSize;
		for (int i = 0; i <= cnt; i++) {

			in.read(block, 0, blockSize);

			xor32(block, iv);

			int len = blockSize;
			if (cnt == i) {
				len = size % blockSize;
			}

			out.write(block, 0, len);

		}

		in.close();
		out.flush();
		out.close();

	}

	/**
	 * @param x1
	 * @param x2
	 */
	private static void xor32(byte[] x1, byte[] x2) {

		x1[0] = (byte) (x1[0] ^ x2[0]);
		x1[1] = (byte) (x1[1] ^ x2[1]);
		x1[2] = (byte) (x1[2] ^ x2[2]);
		x1[3] = (byte) (x1[3] ^ x2[3]);
		x1[4] = (byte) (x1[4] ^ x2[4]);
		x1[5] = (byte) (x1[5] ^ x2[5]);
		x1[6] = (byte) (x1[6] ^ x2[6]);
		x1[7] = (byte) (x1[7] ^ x2[7]);
		x1[8] = (byte) (x1[8] ^ x2[8]);
		x1[9] = (byte) (x1[9] ^ x2[9]);
		x1[10] = (byte) (x1[10] ^ x2[10]);
		x1[11] = (byte) (x1[11] ^ x2[11]);
		x1[12] = (byte) (x1[12] ^ x2[12]);
		x1[13] = (byte) (x1[13] ^ x2[13]);
		x1[14] = (byte) (x1[14] ^ x2[14]);
		x1[15] = (byte) (x1[15] ^ x2[15]);
		x1[16] = (byte) (x1[16] ^ x2[16]);
		x1[17] = (byte) (x1[17] ^ x2[17]);
		x1[18] = (byte) (x1[18] ^ x2[18]);
		x1[19] = (byte) (x1[19] ^ x2[19]);
		x1[20] = (byte) (x1[20] ^ x2[20]);
		x1[21] = (byte) (x1[21] ^ x2[21]);
		x1[22] = (byte) (x1[22] ^ x2[22]);
		x1[23] = (byte) (x1[23] ^ x2[23]);
		x1[24] = (byte) (x1[24] ^ x2[24]);
		x1[25] = (byte) (x1[25] ^ x2[25]);
		x1[26] = (byte) (x1[26] ^ x2[26]);
		x1[27] = (byte) (x1[27] ^ x2[27]);
		x1[28] = (byte) (x1[28] ^ x2[28]);
		x1[29] = (byte) (x1[29] ^ x2[29]);
		x1[30] = (byte) (x1[30] ^ x2[30]);
		x1[31] = (byte) (x1[31] ^ x2[31]);
	}

	public static void e() throws Exception {

		File file = new File("C:/public/imsi.org.dat");

		// 허옹할 수 있는 파일 사이즈는 int 범위
		int size = (int) file.length();

		InputStream in = new FileInputStream(file);
		OutputStream out = new FileOutputStream(new File("C:/public/imsi.dat.enc"));

		DataCrypt dataCrypt = DataCrypt.getDataCrypt("S01");

		byte[] head = new byte[blockSize + metaSize];

		byte[] nonce = MakeRnd.createRnd(blockSize).getBytes();
		byte[] meta = ("CODE5__" + ("" + (1000000000 + size)).substring(1)).getBytes();

		System.arraycopy(nonce, 0, head, 0, nonce.length);
		System.arraycopy(meta, 0, head, nonce.length, meta.length);

		byte[] encHaed = dataCrypt.encrypt(head);
		print("encHaed", encHaed);
		out.write(encHaed);

		byte[] iv = nonce;
		System.out.println("iv");
		print("iv", iv);

		byte[] block = new byte[blockSize];

		int sp = 0;
		int cnt = size / blockSize;

		System.out.println();

		for (int i = 0; i <= cnt; i++) {

			int ep = sp + blockSize;
			if (ep > size) {
				ep = size;
			}

			in.read(block, 0, ep - sp);

			xor32(block, iv);

			out.write(block);

			sp = ep;

		}

		in.close();
		out.flush();
		out.close();

	}

	static void print(String s, byte[] xx) {
		System.out.print(s + "[" + xx.length + "]");
		for (int i = 0; i < xx.length; i++) {
			System.out.print(xx[i] + " ");
		}
		System.out.println();
	}
}
