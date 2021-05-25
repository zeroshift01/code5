package com.code5.fw.data;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

/**
 * @author zero
 *
 */
public class InitYaml {

	/**
	 * 
	 */
	private static InitYaml thisInc = new InitYaml();

	/**
	 * @return
	 */
	public static InitYaml get() {
		return thisInc;
	}

	/**
	 * 
	 */
	private Map<String, Object> map = null;

	/**
	 * 
	 */
	private boolean isRead = false;

	/**
	 * @return
	 */
	public boolean isRead() {
		return isRead;
	}

	/**
	 * 
	 */
	private String hostName = null;

	/**
	 * @return
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * 
	 */
	private InitYaml() {

		this.map = YamlReader.getMap("init");

		if (this.map == null) {
			return;
		}

		this.hostName = "DEFAULT";

		try {
			this.hostName = InetAddress.getLocalHost().getHostName();
			if (this.hostName.contains(".")) {
				this.hostName = "DEFAULT";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		isRead = true;
	}

	/**
	 * @param key
	 * @return
	 */
	private Object get(String key) {

		Object obj = _get(key + "." + this.hostName);
		if (obj != null) {
			return obj;
		}
		
		obj = _get(key + "." + "DEFAULT");
		if (obj != null) {
			return obj;
		}

		return _get(key);

	}

	/**
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object _get(String key) {

		if (this.map == null) {
			return "";
		}

		String[] keys = key.split("\\.");

		Object obj = this.map;

		for (int i = 0; i < keys.length; i++) {

			String thisKey = keys[i];

			if (!(obj instanceof Map)) {
				return null;
			}

			obj = ((Map<String, Object>) obj).get(thisKey);
		}

		return obj;
	}

	/**
	 * @param key
	 * @return
	 */
	public String s(String key) {
		Object obj = get(key);
		if (obj instanceof String) {
			return (String) obj;
		}
		return "";
	}

	/**
	 * @param key
	 * @return
	 */
	public String[] ss(String key) {
		Object obj = get(key);
		if (!(obj instanceof List)) {
			return new String[] { "" };
		}

		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) obj;

		String[] ret = new String[list.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = list.get(i);
		}

		return ret;

	}
}
