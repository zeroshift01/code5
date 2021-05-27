package com.code5.fw.security;

import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.data.Hex;
import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class DataCrypt {

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, DataCrypt> dataCryptMap = new ConcurrentHashMap<String, DataCrypt>();

	/**
	 * 
	 */
	private Crypt crypt = null;

	/**
	 * 
	 */
	private DataCrypt(Crypt crypt) {
		this.crypt = crypt;
	}

	/**
	 * @return
	 * 
	 */
	public static DataCrypt getDataCrypt(String OPT) throws Exception {

		DataCrypt dataCrypt = dataCryptMap.get(OPT);
		if (dataCrypt != null) {
			return dataCrypt;
		}

		InitYaml init = InitYaml.get();

		String MODE = init.s("SRT." + OPT + ".MODE");
		String KEY = init.s("SRT." + OPT + ".KEY");
		String IV = init.s("SRT." + OPT + ".IV");

		byte[] keys = decryptKEY(KEY);
		byte[] ivs = decryptKEY(IV);

		Crypt crypt = null;

		if ("Aes_CBC_PKCS7".equals(MODE)) {

			crypt = new Aes_CBC_PKCS7(keys, ivs);

		} else if ("Aria_CBC_PKCS7".equals(MODE)) {

			crypt = new Aria_CBC_PKCS7(keys, ivs);

		} else if ("Aria_ECB_ZERO".equals(MODE)) {

			crypt = new Aria_ECB_ZERO(keys);

		}

		if (crypt == null) {
			throw new Exception();
		}

		dataCrypt = new DataCrypt(crypt);
		dataCryptMap.put(OPT, dataCrypt);

		return dataCrypt;

	}

	/**
	 * @param encStr
	 * @return
	 */
	public String decrypt(String encStr) throws Exception {

		try {

			if (encStr == null) {
				return null;
			}

			encStr = encStr.trim();

			if ("".equals(encStr)) {
				return "";
			}

			byte[] enc = Hex.hexToByte(encStr);

			byte[] plan = crypt.decrypt(enc);

			String ret = new String(plan);

			return ret;

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}

	}

	/**
	 * @param planStr
	 * @return
	 */
	public String encrypt(String planStr) throws Exception {

		try {

			if (planStr == null) {
				return null;
			}

			planStr = planStr.trim();

			if ("".equals(planStr)) {
				return "";
			}

			byte[] plan = planStr.getBytes();

			byte[] enc = crypt.encrypt(plan);

			String ret = Hex.byteToHex(enc);

			return ret;

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}

	}

	/**
	 * 
	 * 
	 * @param enc
	 */
	private static byte[] decryptKEY(String key) throws Exception {

		if ("".equals(key)) {
			return new byte[16];
		}

		byte[] thisKey = new byte[16];
		thisKey[15] = 5;

		Aria_ECB_ZERO x = new Aria_ECB_ZERO(thisKey);

		byte[] enc = Hex.hexToByte(key);
		return x.decrypt(enc);
	}

	/**
	 * 
	 */
	public byte[] encrypt(byte[] b) throws Exception {
		byte[] enc = crypt.encrypt(b);
		return enc;
	}

	/**
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] b) throws Exception {
		byte[] enc = crypt.decrypt(b);
		return enc;
	}
}
