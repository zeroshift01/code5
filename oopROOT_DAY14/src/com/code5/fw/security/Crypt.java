package com.code5.fw.security;

/**
 * @author seuk
 *
 */
public interface Crypt {

	/**
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt_CBC_PKCS7(byte[] plan) throws Exception;

	/**
	 * @param enc
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt_CBC_PKCS7(byte[] enc) throws Exception;

}
