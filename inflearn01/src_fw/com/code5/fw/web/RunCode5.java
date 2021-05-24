package com.code5.fw.web;

import org.apache.catalina.startup.Tomcat;

/**
 * @author zero
 *
 */
public class RunCode5 {

	public static void main(String[] args) throws Exception {

		String webappDir = "C:\\public\\code5\\web";
		String baseDir = "C:\\public\\code5\\temp";
		int webPort = 18080;

		Tomcat tomcat = new Tomcat();

		tomcat.setBaseDir(baseDir);
		tomcat.setPort(webPort);
		tomcat.addWebapp("", webappDir);

		tomcat.start();
		tomcat.getServer().await();

	}
}
