package com.code5.fw.security;

import kr.re.nsri.aria.ARIAEngine;

/**
 * 
 * @author seuk
 *
 */
public class Aria_CBC_PKCS7 implements Crypt {

	/**
	 *
	 */
	private static final int BLOCK_LENGTH = 16;

	/**
	 * 
	 */
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
	public Aria_CBC_PKCS7(byte[] key, byte[] iv) throws Exception {

		ariaEngine = new ARIAEngine(key.length * 8);
		ariaEngine.setKey(key);

		this.iv = iv;
	}

	/**
	 * 
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
	 * @param endBlock
	 * @return
	 */
	private static int getPaddingCntPKCS7(byte[] endBlock) {

		byte paddingCnt = endBlock[endBlock.length - 1];
		if (paddingCnt > 16) {
			return 0;
		}

		if (paddingCnt < 0) {
			return 0;
		}

		for (int i = (16 - paddingCnt); i < endBlock.length; i++) {
			if (endBlock[i] != paddingCnt) {
				return 0;
			}
		}

		return paddingCnt;

	}

	/**
	 * 
	 * @param enc
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] enc) throws Exception {

		if (enc == null) {
			return null;
		}

		if (enc.length == 0) {
			return enc;
		}

		byte[] cbcBlock = new byte[BLOCK_LENGTH];

		byte[] plan = new byte[enc.length];

		System.arraycopy(this.iv, 0, cbcBlock, 0, BLOCK_LENGTH);

		int forCnt = enc.length / BLOCK_LENGTH;

		int srcPos = 0;

		byte[] planBlock = null;

		for (int i = 0; i < forCnt; i++) {

			byte[] encBlock = new byte[BLOCK_LENGTH];
			System.arraycopy(enc, srcPos, encBlock, 0, BLOCK_LENGTH);

			planBlock = ariaEngine.decrypt(encBlock, 0);

			xor16ToX(planBlock, cbcBlock);

			System.arraycopy(planBlock, 0, plan, srcPos, BLOCK_LENGTH);

			cbcBlock = encBlock;

			srcPos = srcPos + encBlock.length;

		}

		int cntPKCS7 = getPaddingCntPKCS7(planBlock);
		if (cntPKCS7 == 0) {
			return plan;
		}

		byte[] newPlan = new byte[plan.length - cntPKCS7];
		System.arraycopy(plan, 0, newPlan, 0, newPlan.length);
		return newPlan;

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
