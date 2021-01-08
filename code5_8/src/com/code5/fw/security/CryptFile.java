package com.code5.fw.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.code5.fw.data.MakeRnd;

/**
 * @author seuk
 *
 */
public class CryptFile {

	/**
	 * 
	 */
	private static CryptFile incCryptFile = new CryptFile();

	/**
	 * 
	 */
	private CryptFile() {
	}

	/**
	 * @return
	 */
	public static CryptFile getCryptFile() {
		return incCryptFile;

	}

	private static final int BLOCK_SIZE = 64;
	private static final int META_SIZE = 16;

	public static void main(String[] xxx) throws Exception {

		e();

		DataCrypt dataCrypt = DataCrypt.getDataCrypt("S01");

		InputStream in = new FileInputStream(new File("C:/public/imsi.dat.enc"));
		FileOutputStream out = new FileOutputStream(new File("C:/public/imsi.dat"));

		byte[] encHead = new byte[BLOCK_SIZE + META_SIZE];
		in.read(encHead);

		byte[] head = dataCrypt.decrypt(encHead);

		byte[] nonce = new byte[BLOCK_SIZE];
		byte[] meta = new byte[META_SIZE];

		System.arraycopy(head, 0, nonce, 0, BLOCK_SIZE);
		System.arraycopy(head, BLOCK_SIZE, meta, 0, META_SIZE);

		String meta1 = new String(meta);
		String meta2 = meta1.substring(0, 7);
		String meta3 = meta1.substring(7);

		if (!"CODE5__".equals(meta2)) {
			throw new Exception();
		}
		int size = Integer.parseInt(meta3);

		byte[] iv = new byte[BLOCK_SIZE];
		System.arraycopy(head, 0, iv, 0, iv.length);
		print("iv", iv);

		byte[] block = new byte[BLOCK_SIZE];

		int cnt = size / BLOCK_SIZE;
		for (int i = 0; i <= cnt; i++) {

			in.read(block, 0, BLOCK_SIZE);

			xor32(block, iv);

			int len = BLOCK_SIZE;
			if (cnt == i) {
				len = size % BLOCK_SIZE;
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

	/**
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public void e(String from, String to) throws Exception {

		File fromFile = new File(from);

		if (!fromFile.isFile()) {
			throw new Exception("file not exists [" + from + "]");
		}

		File toFile = new File(to);

		if (toFile.isFile()) {
			throw new Exception("file exists [" + to + "]");
		}

		InputStream in = new FileInputStream(fromFile);
		OutputStream out = new FileOutputStream(toFile);

		// 첨부파일로 사용할 수 있는 int 범위
		int size = (int) fromFile.length();

		if (size == 0) {

		}

		DataCrypt dataCrypt = DataCrypt.getDataCrypt("S01");

		byte[] head = new byte[BLOCK_SIZE + META_SIZE];

		byte[] nonce = MakeRnd.createRnd(BLOCK_SIZE).getBytes();
		byte[] meta = ("CODE5__" + ("" + (1000000000 + size)).substring(1)).getBytes();

		System.arraycopy(nonce, 0, head, 0, nonce.length);
		System.arraycopy(meta, 0, head, nonce.length, meta.length);

		byte[] encHaed = dataCrypt.encrypt(head);
		print("encHaed", encHaed);
		out.write(encHaed);

		byte[] iv = nonce;
		System.out.println("iv");
		print("iv", iv);

		byte[] block = new byte[BLOCK_SIZE];

		int sp = 0;
		int cnt = size / BLOCK_SIZE;

		System.out.println();

		for (int i = 0; i <= cnt; i++) {

			int ep = sp + BLOCK_SIZE;
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
