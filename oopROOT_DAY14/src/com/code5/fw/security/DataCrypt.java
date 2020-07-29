package com.code5.fw.security;

import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.db.Sql;
import com.code5.fw.trace.Trace;
import com.code5.fw.util.StringUtil;
import com.code5.fw.web.Box;
import com.code5.fw.web.BoxLocal;

/**
 * @author seuk
 *
 */
public class DataCrypt {

	/**
	 * 
	 */
	private static String KEY_CRYPT_REUSE = "com.code5.fw.security.DataCrypt";

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
	 */
	public static DataCrypt getDataCrypt(String OPT) throws Exception {

		DataCrypt dataCrypt = dataCryptMap.get(OPT);
		if (dataCrypt != null) {
			return dataCrypt;
		}

		Box thisBox = new BoxLocal();
		thisBox.put("OPT", OPT);
		Box cryptInfo = Sql.getSql().getTableByCache(thisBox, "DATACRYPT_01").getBox();

		String MODE = cryptInfo.s("MODE");
		String KEY = cryptInfo.s("KEY");
		String IV = cryptInfo.s("IV");

		byte[] keys = decryptKEY(KEY);
		byte[] ivs = decryptKEY(IV);

		Crypt crypt = null;

		if ("Aes_CBC_PKCS7".equals(MODE)) {

			crypt = new Aria_CBC_PKCS7(keys, ivs);

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

			Box box = Box.getThread();
			String s = box.s(KEY_CRYPT_REUSE + "_" + encStr);
			if (!"".equals(s)) {
				return s;
			}

			byte[] enc = StringUtil.hexToByte(encStr);

			byte[] plan = crypt.decrypt(enc);

			String ret = new String(plan);

			box.put(KEY_CRYPT_REUSE + "_" + encStr, ret);
			return ret;

		} catch (Exception ex) {
			Trace trace = new Trace(this);
			trace.write(encStr);
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

			Box box = Box.getThread();
			String s = box.s(KEY_CRYPT_REUSE + "_" + planStr);
			if (!"".equals(s)) {
				return s;
			}

			byte[] plan = planStr.getBytes();

			byte[] enc = crypt.encrypt(plan);

			String ret = StringUtil.byteToHex(enc);

			box.put(KEY_CRYPT_REUSE + "_" + planStr, ret);
			return ret;

		} catch (Exception ex) {
			Trace trace = new Trace(this);
			trace.write(planStr);
			trace.writeErr(ex);
			return "";
		}

	}

	/**
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
