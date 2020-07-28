package com.code5.fw.security;

import kr.re.nsri.aria.ARIAEngine;

/**
 * @author seuk
 *
 */
public class Aria {

	private static final int BLOCK_LENGTH = 16;

	private byte[] iv = null;

	/**
	 * 
	 */
	private ARIAEngine ariaEngine = null;

	/**
	 * @param key
	 * @param iv
	 * @throws Exception
	 */
	public Aria(byte[] key, byte[] iv) throws Exception {

		ariaEngine = new ARIAEngine(key.length * 8);
		ariaEngine.setKey(key);

		this.iv = iv;
	}

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	byte[] encryptBlock(byte[] plan) throws Exception {
		return ariaEngine.encrypt(plan, 0);
	}

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	byte[] decryptBlock(byte[] enc) throws Exception {
		return ariaEngine.decrypt(enc, 0);
	}

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] plan) throws Exception {

		if (plan == null) {
			return null;
		}

		if (plan.length == 0) {
			return plan;
		}

		byte[] cbcBlock = new byte[BLOCK_LENGTH];

		int encLength = plan.length;

		if (plan.length % BLOCK_LENGTH != 0) {
			encLength = (plan.length / BLOCK_LENGTH) * BLOCK_LENGTH + BLOCK_LENGTH;
		}

		byte[] enc = new byte[encLength];

		int nPad = enc.length - plan.length;

		System.arraycopy(this.iv, 0, cbcBlock, 0, BLOCK_LENGTH);

		int forCnt = enc.length / BLOCK_LENGTH;

		int srcPos = 0;

		for (int i = 0; i < forCnt; i++) {

			byte[] encBlock = new byte[BLOCK_LENGTH];

			int length = BLOCK_LENGTH;
			if (plan.length < length + srcPos) {
				length = BLOCK_LENGTH - nPad;
			}

			System.arraycopy(plan, srcPos, encBlock, 0, length);

			for (int ii = length; ii < BLOCK_LENGTH; ii++) {
				encBlock[ii] = (byte) nPad;
			}

			xor16ToX(encBlock, cbcBlock);

			encBlock = ariaEngine.encrypt(encBlock, 0);

			System.arraycopy(encBlock, 0, enc, srcPos, BLOCK_LENGTH);

			cbcBlock = encBlock;

			srcPos = srcPos + encBlock.length;

		}

		return enc;
	}

	/**
	 * @param x
	 * @param y
	 */
	private void xor16ToX(byte[] x, byte[] y) {

		x[0] = (byte) (x[0] ^ y[0]);
		x[1] = (byte) (x[1] ^ y[1]);
		x[2] = (byte) (x[2] ^ y[2]);
		x[3] = (byte) (x[3] ^ y[3]);
		x[4] = (byte) (x[4] ^ y[4]);
		x[5] = (byte) (x[5] ^ y[5]);
		x[6] = (byte) (x[6] ^ y[6]);
		x[7] = (byte) (x[7] ^ y[7]);
		x[8] = (byte) (x[8] ^ y[8]);
		x[9] = (byte) (x[9] ^ y[9]);
		x[10] = (byte) (x[10] ^ y[10]);
		x[11] = (byte) (x[11] ^ y[11]);
		x[12] = (byte) (x[12] ^ y[12]);
		x[13] = (byte) (x[13] ^ y[13]);
		x[14] = (byte) (x[14] ^ y[14]);
		x[15] = (byte) (x[15] ^ y[15]);
	}

}