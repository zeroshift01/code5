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

		String x111 = "1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";
		String x222 = "2,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";
		String x333 = "3,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";

		System.out.println(InitYaml.get().enc(x111));
		System.out.println(InitYaml.get().enc(x222));
		System.out.println(InitYaml.get().enc(x333));
		
		System.out.println(InitYaml.get().enc("dev jdbc url + id + pw"));
		System.out.println(InitYaml.get().enc("pr jdbc url + id + pw"));
		System.out.println(InitYaml.get().enc("jdbc:mysql://localhost/code5,user,1"));
		
		

	}
}
