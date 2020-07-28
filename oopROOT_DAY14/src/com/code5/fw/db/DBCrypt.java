package com.code5.fw.db;

import com.code5.fw.security.Aes;
import com.code5.fw.security.Aria;
import com.code5.fw.security.Crypt;
import com.code5.fw.trace.Trace;
import com.code5.fw.util.StringUtil;
import com.code5.fw.web.Box;
import com.code5.fw.web.TransactionContext;

/**
 * @author seuk
 *
 */
public class DBCrypt {

	/**
	 * 
	 */
	public static DBCrypt getDBCrypt() {
		return incDBCrypt;
	}

	/**
	 * 
	 */
	private static DBCrypt incDBCrypt = new DBCrypt();

	/**
	 * 
	 */
	private DBCrypt() {

	}

	/**
	 * 
	 */
	private static String CRYPT_KEY = "com.code5.fw.db.Crypt";

	/**
	 * @return
	 */
	private Crypt getCrypt() throws Exception {

		Box box = Box.getThread();

		Crypt crypt = (Crypt) box.get(CRYPT_KEY);
		if (crypt != null) {
			return crypt;
		}

		String defaultCrypt = TransactionContext.getThread().defaultCrypt();

		byte[] iv = StringUtil.hexToByte("1802E0A2B90E2F9C55E00E44D05829A5");
		byte[] key = StringUtil.hexToByte("D8D91CB33B6A77B43E92AF3D509152B2");

		if ("ARIA".equals(defaultCrypt)) {
			//crypt = new Aria(key, iv);
			//box.put(CRYPT_KEY, crypt);
			//return crypt;
			
			crypt = new Aes(key, iv);
			box.put(CRYPT_KEY, crypt);
			return crypt;
		}

		if ("AES".equals(defaultCrypt)) {
			crypt = new Aes(key, iv);
			box.put(CRYPT_KEY, crypt);
			return crypt;
		}

		throw new Exception();

	}

	/**
	 * @param encStr
	 * @return
	 */
	public String decrypt(String encStr) throws Exception {

		Crypt crypt = getCrypt();

		try {

			if (encStr == null) {
				return null;
			}

			encStr = encStr.trim();

			if ("".equals(encStr)) {
				return "";
			}

			byte[] enc = StringUtil.hexToByte(encStr);

			byte[] plan = crypt.decrypt_CBC_PKCS7(enc);

			return new String(plan, "UTF-8");

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

		Crypt crypt = getCrypt();

		try {

			if (planStr == null) {
				return null;
			}

			planStr = planStr.trim();

			if ("".equals(planStr)) {
				return "";
			}

			byte[] plan = planStr.getBytes("UTF-8");

			byte[] enc = crypt.decrypt_CBC_PKCS7(plan);

			String ret = StringUtil.byteToHex(enc);

			return ret;

		} catch (Exception ex) {
			Trace trace = new Trace(this);
			trace.write(planStr);
			trace.writeErr(ex);
			return "";
		}

	}

}
