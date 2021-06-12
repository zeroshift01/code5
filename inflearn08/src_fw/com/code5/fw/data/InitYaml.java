package com.code5.fw.data;

import java.io.File;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
	private String webAppRoot = null;

	/**
	 * @return
	 */
	public String getWebAppRoot() {
		return webAppRoot;
	}

	/**
	 * 
	 */
	private String appName = null;

	/**
	 * @return
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @return
	 */
	public void setAppName(String appName) {

		if (this.appName != null) {
			return;
		}

		this.appName = appName;
	}

	/**
	 * 
	 */
	private String characterSet = null;

	/**
	 * @return
	 */
	public String getCharacterSet() {
		return characterSet;
	}

	/**
	 * 
	 */
	private boolean isProduct = false;

	/**
	 * @return
	 */
	public boolean isProduct() {
		return isProduct;
	}

	/**
	 * 
	 */
	private boolean isCache = false;

	/**
	 * @return
	 */
	public boolean isCache() {
		return isCache;
	}

	private String tempDir = null;
	private int webPort = 0;
	private String webAppDir = null;

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

		Properties properties = System.getProperties();
		this.appName = properties.getProperty("com.code5.app.name");

		this.isCache = is("CACHE");

		this.isProduct = is("PRODUCT");

		this.characterSet = s("CHARACTER_SET");

		String yamlUrl = s("THIS_YAML_URL");

		// webAppRoot/web/WEB-INF/classes/init.yaml
		File file = new File(yamlUrl);

		// webAppRoot/web/WEB-INF/classes
		file = file.getParentFile();

		// webAppRoot/web/WEB-INF
		file = file.getParentFile();
		
		// webAppRoot/WEB-INF
		file = file.getParentFile();

		// webAppRoot
		file = file.getParentFile();

		String webAppRoot = file.getAbsolutePath();

		this.webAppRoot = webAppRoot;

		this.webPort = Integer.parseInt(s("WEB_PORT"));

		this.webAppDir = new File(s("WEB_APP_DIR")).getAbsolutePath();
		this.tempDir = new File(s("TEMP_DIR")).getAbsolutePath();

		isRead = true;
	}

	/**
	 * @return
	 */
	public String getTempDir() {
		return tempDir;
	}

	/**
	 * @return
	 */
	public int getWebPort() {
		return webPort;
	}

	/**
	 * @return
	 */
	public String getWebAppDir() {
		return webAppDir;
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
	 * @param s
	 * @return
	 */
	private String convert$(String s) {
		if (this.webAppRoot != null) {
			s = s.replace("[WEB_APP_ROOT]", this.webAppRoot);
		}
		return s;
	}

	/**
	 * @param key
	 * @return
	 */
	public String s(String key) {
		Object obj = get(key);
		if (!(obj instanceof String)) {
			return "";

		}

		String s = (String) obj;
		s = convert$(s);
		return s;
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

			String s = list.get(i);
			s = convert$(s);
			ret[i] = s;
		}

		return ret;

	}

	/**
	 * @param key
	 * @return
	 */
	public boolean is(String key) {
		Object obj = get(key);
		if (obj instanceof String) {
			if ("true".equals((String) obj)) {
				return true;
			}
		}
		return false;
	}

}
