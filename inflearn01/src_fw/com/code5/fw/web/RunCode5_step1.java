package com.code5.fw.web;

import org.apache.catalina.startup.Tomcat;

/**
 * @author zero
 *
 */
public class RunCode5_step1 {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String webappDir = "C:/public/code5/web";
		String baseDir = "C:/public/code5/temp";
		int webPort = 18080;

		Tomcat tomcat = new Tomcat();
		tomcat.addWebapp("", webappDir);

		tomcat.setBaseDir(baseDir);
		tomcat.setPort(webPort);

		tomcat.start();

		System.out.println("code5 start");

		tomcat.getServer().await();

	}
}
