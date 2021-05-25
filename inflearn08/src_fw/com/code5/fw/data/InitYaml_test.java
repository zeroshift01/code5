package com.code5.fw.data;

/**
 * @author zero
 *
 */
public class InitYaml_test {

	/**
	 * @param xx
	 */
	public static void main(String[] xx) {

		String x1 = InitYaml.get().s("RUN_CODE5.WEB_APP_DIR");
		String x2 = InitYaml.get().s("LOG.LOG_DIR_PATTERN");
		String[] x3 = InitYaml.get().ss("NOLOG.SERVICE_KEY");
		String x4 = InitYaml.get().s("UPLOAD_FILE_DIR.TEMP_URL");

		System.out.println(x1);
		System.out.println(x2);
		System.out.println(x3[0]);

		System.out.println(x4);

	}
}
