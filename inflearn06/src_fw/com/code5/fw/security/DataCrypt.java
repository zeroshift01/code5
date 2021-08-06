package com.code5.fw.security;

import java.util.Base64;
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
	private String charcterSet = null;

	/**
	 * 
	 */
	private String encoding = null;

	/**
	 * 
	 */
	private DataCrypt(Crypt crypt, String charcterSet, String encoding) {
		this.crypt = crypt;
		this.charcterSet = charcterSet;
		this.encoding = encoding;
	}

	/**
	 * @param KEY
	 * @return
	 * @throws Exception
	 */
	private static byte[] makeKey(String KEY) throws Exception {

		String[] keyx = KEY.split(",");

		if (keyx.length == 1) {
			return new byte[0];
		}

		byte key[] = new byte[keyx.length];

		for (int i = 0; i < keyx.length; i++) {
			key[i] = Byte.parseByte(keyx[i].trim());
		}

		return key;

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
		String KEY = init.dec("SRT." + OPT + ".KEY");
		String IV = init.dec("SRT." + OPT + ".IV");

		byte key[] = makeKey(KEY);
		byte iv[] = makeKey(IV);

		Crypt crypt = null;

		if ("Aes_CBC_PKCS7".equals(MODE)) {

			crypt = new Aes_CBC_PKCS7(key, iv);

		} else if ("Aria_CBC_PKCS7".equals(MODE)) {

			crypt = new Aria_CBC_PKCS7(key, iv);

		} else if ("Aria_ECB_ZERO".equals(MODE)) {

			crypt = new Aria_ECB_ZERO(key);

		}

		if (crypt == null) {
			throw new Exception();
		}

		String charcterSet = init.s("SRT." + OPT + ".CHARACTER_SET");
		String encoding = init.s("SRT." + OPT + ".ENCODING");

		dataCrypt = new DataCrypt(crypt, charcterSet, encoding);
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

			byte[] enc = null;

			if ("".equals(encoding)) {
				enc = Hex.hexToByte(encStr);
			} else if ("BASE64".equals(encoding)) {
				enc = Base64.getDecoder().decode(encStr);
			}

			byte[] plan = crypt.decrypt(enc);

			String ret = null;

			if ("".equals(charcterSet)) {
				ret = new String(plan);
			} else {
				ret = new String(plan, charcterSet);
			}

			ret = ret.trim();

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

			byte[] plan = null;

			if ("".equals(charcterSet)) {
				plan = planStr.getBytes();
			} else {
				plan = planStr.getBytes(charcterSet);
			}

			byte[] enc = crypt.encrypt(plan);

			String ret = null;
			if ("".equals(encoding)) {
				ret = Hex.byteToHex(enc);
			} else if ("BASE64".equals(encoding)) {
				ret = new String(Base64.getEncoder().encode(enc));
			}

			return ret;

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}

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
