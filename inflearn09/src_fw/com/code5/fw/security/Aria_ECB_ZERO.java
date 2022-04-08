package com.code5.fw.security;

import kr.re.nsri.aria.ARIAEngine;

/**
 * @author zero
 *
 */
public class Aria_ECB_ZERO implements Crypt {

	/**
	 * 
	 */
	private static final int BLOCK_LENGTH = 16;

	/**
	 * 
	 */
	private ARIAEngine ariaEngine = null;

	/**
	 * @param key
	 * @param iv
	 * @throws Exception
	 */
	public Aria_ECB_ZERO(byte[] key) throws Exception {

		ariaEngine = new ARIAEngine(key.length * 8);
		ariaEngine.setKey(key);
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

		int encLength = plan.length;

		if (plan.length % BLOCK_LENGTH != 0) {
			encLength = (plan.length / BLOCK_LENGTH) * BLOCK_LENGTH + BLOCK_LENGTH;
		}

		byte[] enc = new byte[encLength];

		int nPad = enc.length - plan.length;

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
				encBlock[ii] = (byte) 0;
			}

			encBlock = ariaEngine.encrypt(encBlock, 0);

			System.arraycopy(encBlock, 0, enc, srcPos, BLOCK_LENGTH);

			srcPos = srcPos + encBlock.length;

		}

		return enc;
	}

	/**
	 * @param endBlock
	 * @return
	 */
	private static int getPaddingCntZERO(byte[] endBlock) {

		int paddingCnt = 0;
		for (int i = endBlock.length - 1; i >= 0; i--) {
			if (endBlock[i] != 0) {
				break;
			}
			paddingCnt++;
		}

		return paddingCnt;

	}

	/**
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

		byte[] plan = new byte[enc.length];

		int forCnt = enc.length / BLOCK_LENGTH;

		int srcPos = 0;

		byte[] planBlock = null;

		for (int i = 0; i < forCnt; i++) {

			byte[] encBlock = new byte[BLOCK_LENGTH];
			System.arraycopy(enc, srcPos, encBlock, 0, BLOCK_LENGTH);

			planBlock = ariaEngine.decrypt(encBlock, 0);

			System.arraycopy(planBlock, 0, plan, srcPos, BLOCK_LENGTH);

			srcPos = srcPos + encBlock.length;

		}

		int cntPKCS7 = getPaddingCntZERO(planBlock);
		if (cntPKCS7 == 0) {
			return plan;
		}

		byte[] newPlan = new byte[plan.length - cntPKCS7];
		System.arraycopy(plan, 0, newPlan, 0, newPlan.length);
		return newPlan;

	}

}
