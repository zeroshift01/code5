package com.code5.fw.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeoutException;

import com.code5.fw.data.MakeRnd;

/**
 * @author zero
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

	/**
	 * 
	 */
	private static final int BLOCK_SIZE = 64;

	/**
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public void decrypt(String from, String to) throws Exception {

		OutputStream out = null;

		try {

			out = new FileOutputStream(to);

			decrypt(from, out, -1);

		} catch (Exception ex) {
			throw ex;
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * @param x1
	 * @param x2
	 */
	private static void xor64(byte[] x1, byte[] x2) {

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
		x1[32] = (byte) (x1[32] ^ x2[32]);
		x1[33] = (byte) (x1[33] ^ x2[33]);
		x1[34] = (byte) (x1[34] ^ x2[34]);
		x1[35] = (byte) (x1[35] ^ x2[35]);
		x1[36] = (byte) (x1[36] ^ x2[36]);
		x1[37] = (byte) (x1[37] ^ x2[37]);
		x1[38] = (byte) (x1[38] ^ x2[38]);
		x1[39] = (byte) (x1[39] ^ x2[39]);
		x1[40] = (byte) (x1[40] ^ x2[40]);
		x1[41] = (byte) (x1[41] ^ x2[41]);
		x1[42] = (byte) (x1[42] ^ x2[42]);
		x1[43] = (byte) (x1[43] ^ x2[43]);
		x1[44] = (byte) (x1[44] ^ x2[44]);
		x1[45] = (byte) (x1[45] ^ x2[45]);
		x1[46] = (byte) (x1[46] ^ x2[46]);
		x1[47] = (byte) (x1[47] ^ x2[47]);
		x1[48] = (byte) (x1[48] ^ x2[48]);
		x1[49] = (byte) (x1[49] ^ x2[49]);
		x1[50] = (byte) (x1[50] ^ x2[50]);
		x1[51] = (byte) (x1[51] ^ x2[51]);
		x1[52] = (byte) (x1[52] ^ x2[52]);
		x1[53] = (byte) (x1[53] ^ x2[53]);
		x1[54] = (byte) (x1[54] ^ x2[54]);
		x1[55] = (byte) (x1[55] ^ x2[55]);
		x1[56] = (byte) (x1[56] ^ x2[56]);
		x1[57] = (byte) (x1[57] ^ x2[57]);
		x1[58] = (byte) (x1[58] ^ x2[58]);
		x1[59] = (byte) (x1[59] ^ x2[59]);
		x1[60] = (byte) (x1[60] ^ x2[60]);
		x1[61] = (byte) (x1[61] ^ x2[61]);
		x1[62] = (byte) (x1[62] ^ x2[62]);
		x1[63] = (byte) (x1[63] ^ x2[63]);

	}

	/**
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public void encrypt(String from, String to) throws Exception {

		InputStream in = null;
		OutputStream out = null;

		try {

			File fromFile = new File(from);

			in = new FileInputStream(fromFile);
			out = new FileOutputStream(to);

			long fileSize = fromFile.length();
			if (fileSize == 0) {
				throw new Exception("file size 0 [" + from + "]");
			}

			DataCrypt dataCrypt = DataCrypt.getDataCrypt("S01");

			byte[] nonce = MakeRnd.createRnd(BLOCK_SIZE).getBytes();
			out.write(dataCrypt.encrypt(nonce));

			byte[] block = new byte[BLOCK_SIZE];

			long cnt = fileSize / BLOCK_SIZE;

			for (int i = 0; i <= cnt; i++) {

				long sp = BLOCK_SIZE * i;

				int len = BLOCK_SIZE;
				if (sp > fileSize) {
					len = (int) fileSize % BLOCK_SIZE;
				}

				in.read(block, 0, len);

				xor64(nonce, block);

				out.write(nonce);

			}

			byte[] meta = ("CODE5__" + ("" + (100000000000000l + fileSize)).substring(1)).getBytes();
			block = new byte[BLOCK_SIZE];

			System.arraycopy(meta, 0, block, 0, meta.length);

			xor64(nonce, block);

			out.write(nonce);

			out.flush();

		} catch (Exception ex) {
			throw ex;
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * @param from
	 * @param out
	 * @param timeOut
	 * @throws Exception
	 */
	public void decrypt(String from, OutputStream out, long timeOut) throws Exception {

		InputStream in = null;

		try {

			long st = System.currentTimeMillis();

			File fromFile = new File(from);

			long fileSize = fromFile.length();

			if (fileSize == 0) {
				throw new Exception("file size 0 [" + from + "]");
			}

			in = new FileInputStream(fromFile);

			byte[] nonce = new byte[BLOCK_SIZE];
			in.read(nonce);

			DataCrypt dataCrypt = DataCrypt.getDataCrypt("S01");
			nonce = dataCrypt.decrypt(nonce);

			byte[] block = new byte[BLOCK_SIZE];

			// nonce, endBlock, meta 를 빼고 출력
			long cnt = (fileSize / BLOCK_SIZE) - 3;

			for (int i = 0; i < cnt; i++) {

				in.read(block);

				xor64(nonce, block);

				out.write(nonce);

				System.arraycopy(block, 0, nonce, 0, block.length);

				long downloadTime = System.currentTimeMillis() - st;
				if (downloadTime > timeOut) {
					throw new TimeoutException("time out [" + downloadTime + "]");
				}

			}

			byte[] endBlock = new byte[BLOCK_SIZE];

			in.read(block);
			xor64(nonce, block);

			System.arraycopy(nonce, 0, endBlock, 0, nonce.length);

			System.arraycopy(block, 0, nonce, 0, block.length);

			in.read(block);

			xor64(nonce, block);

			String meta = new String(nonce).trim();

			if (!meta.startsWith("CODE5_")) {
				throw new Exception();
			}

			long outFileSize = Long.parseLong(meta.substring(7));

			int end = (int) (outFileSize - cnt * BLOCK_SIZE);

			out.write(endBlock, 0, end);
			out.flush();

		} catch (Exception ex) {
			throw ex;
		} finally {
			if (in != null) {
				in.close();
			}
		}

	}

}
