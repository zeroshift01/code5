package com.code5.fw.security;

/**
 * @author zero
 *
 */
public interface Crypt {

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] plan) throws Exception;

	/**
	 * @param enc
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] enc) throws Exception;

}
