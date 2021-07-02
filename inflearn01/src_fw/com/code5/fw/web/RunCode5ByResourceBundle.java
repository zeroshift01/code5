package com.code5.fw.web;

import java.util.ResourceBundle;

import org.apache.catalina.startup.Tomcat;

/**
 * @author zero
 *
 */
public class RunCode5ByResourceBundle {

	public static void main(String[] args) throws Exception {

		String classpath = System.getProperty("java.class.path");
		String[] classpaths = classpath.split(";");
		for (int i = 0; i < classpaths.length; i++) {
			System.out.println(classpaths[i]);
		}

		// classpaths[] + /com/code5/fw/init.properties
		ResourceBundle init = ResourceBundle.getBundle("com.code5.fw.init");

		String webappDir = init.getString("webappDir");
		String baseDir = init.getString("baseDir");
		int webPort = Integer.parseInt(init.getString("webPort"));

		Tomcat tomcat = new Tomcat();

		tomcat.addWebapp("", webappDir);

		// baseDir + /WEB-INF/web.xml
		tomcat.setBaseDir(baseDir);

		tomcat.setPort(webPort);

		tomcat.start();
		System.out.println("code5 start");
		tomcat.getServer().await();

	}
}
