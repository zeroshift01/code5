package com.code5.fw.data;

/**
 * @author zero
 *
 */
public class InitProperty_test {

	public static void main(String[] xxx) {

		System.out.println(System.getProperty("java.class.path"));

		System.out.println(InitProperty.IS_WRITE_LOG());
		System.out.println(InitProperty.LOG_DIR_PATTERN());
		System.out.println(InitProperty.LOG_FILE_PATTERN());
		System.out.println(InitProperty.TRACE_CONFIG_URL());

	}
}
