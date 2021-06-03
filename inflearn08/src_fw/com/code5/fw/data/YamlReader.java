package com.code5.fw.data;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * @author zero
 *
 */
public class YamlReader {

	public static void main(String[] xxx) throws Exception {

		ClassLoader cl = YamlReader.class.getClassLoader();

		String yamlName = "init.yaml";

		String path = cl.getResource(yamlName).getPath();

		// init.yaml
		File file = new File(path);

		// classes/init.yaml
		file = file.getParentFile();

		// WEB-INF/classes/init.yaml
		file = file.getParentFile();

		// web/WEB-INF/classes/init.yaml
		file = file.getParentFile();

		// root/web/WEB-INF/classes/init.yaml
		file = file.getParentFile();

		System.out.println(file.getAbsolutePath());

	}

	/**
	 * @param n
	 * @throws Exception
	 */
	public static Map<String, Object> getMap(String yamlName) {

		InputStream inputStream = null;

		try {

			ClassLoader cl = YamlReader.class.getClassLoader();

			yamlName = yamlName.replaceAll("\\.", "\\/");

			yamlName = yamlName + ".yaml";

			inputStream = cl.getResourceAsStream(yamlName);

			Yaml yaml = new Yaml();
			Map<String, Object> map = yaml.load(inputStream);

			String path = cl.getResource(yamlName).getPath();

			// init.yaml
			File file = new File(path);
			// classes/init.yaml
			file = file.getParentFile();
			// WEB-INF/classes/init.yaml
			file = file.getParentFile();
			// web/WEB-INF/classes/init.yaml
			file = file.getParentFile();
			// root/web/WEB-INF/classes/init.yaml
			file = file.getParentFile();

			String appRootUrl = file.getAbsolutePath();
			map.put("APP_ROOT_URL", appRootUrl);

			return map;

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

	}
}
