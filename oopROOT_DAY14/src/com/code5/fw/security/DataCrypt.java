package com.code5.fw.security;

import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.db.Sql;
import com.code5.fw.trace.Trace;
import com.code5.fw.util.StringUtil;
import com.code5.fw.web.Box;
import com.code5.fw.web.BoxLocal;

/**
 * 
 * TODO [1]
 * 
 * @author seuk
 *
 */
public class DataCrypt {

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, DataCrypt> dataCryptMap = new ConcurrentHashMap<String, DataCrypt>();

	/**
	 * TODO [2]
	 */
	private Crypt crypt = null;

	/**
	 * 
	 */
	private DataCrypt(Crypt crypt) {
		this.crypt = crypt;
	}

	/**
	 * 
	 * TODO [3]
	 * 
	 * @return
	 */
	public static DataCrypt getDataCrypt(String OPT) throws Exception {

		DataCrypt dataCrypt = dataCryptMap.get(OPT);
		if (dataCrypt != null) {
			return dataCrypt;
		}

		// TODO [4]
		Box thisBox = new BoxLocal();
		thisBox.put("OPT", OPT);
		Box cryptInfo = Sql.getSql().getTableByCache(thisBox, "DATACRYPT_01").getBox();

		String MODE = cryptInfo.s("MODE");
		String KEY = cryptInfo.s("KEY");
		String IV = cryptInfo.s("IV");

		byte[] keys = decryptKEY(KEY);
		byte[] ivs = decryptKEY(IV);

		Crypt crypt = null;

		// TODO [5]
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

		// TODO [6]
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

			// TODO [7]
			byte[] enc = StringUtil.hexToByte(encStr);

			byte[] plan = crypt.decrypt(enc);

			String ret = new String(plan);

			return ret;

		} catch (Exception ex) {
			Trace trace = new Trace(this);
			trace.writeErr(encStr);
			trace.writeErr(ex);
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

			// TODO [8]
			byte[] plan = planStr.getBytes();

			byte[] enc = crypt.encrypt(plan);

			// TODO [9]
			String ret = StringUtil.byteToHex(enc);

			return ret;

		} catch (Exception ex) {
			Trace trace = new Trace(this);
			trace.writeErr(planStr);
			trace.writeErr(ex);
			return "";
		}

	}

	/**
	 * TODO [11]
	 * 
	 * @param enc
	 */
	private static byte[] decryptKEY(String key) throws Exception {

		byte[] thisKey = new byte[16];
		thisKey[15] = 5;

		Aria_ECB_ZERO x = new Aria_ECB_ZERO(thisKey);

		byte[] enc = StringUtil.hexToByte(key);
		return x.decrypt(enc);
	}

}
