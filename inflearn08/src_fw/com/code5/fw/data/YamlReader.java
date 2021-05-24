package com.code5.fw.data;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * @author zero
 *
 */
public class YamlReader {

	/**
	 * @param n
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String yamlName, String key) {

		InputStream inputStream = null;
		Map<String, Object> map = null;
		try {

			ClassLoader cl = YamlReader.class.getClassLoader();

			yamlName = yamlName + ".yaml";

			inputStream = cl.getResourceAsStream(yamlName);

			Yaml yaml = new Yaml();
			map = yaml.load(inputStream);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		String[] keys = key.split("\\.");

		for (int i = 0; i < keys.length; i++) {
			map = (Map<String, Object>) map.get(keys[i]);
			if (map == null) {
				return null;
			}
		}

		return map;

	}
}
