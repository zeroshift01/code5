package com.code5.fw.web;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

/**
 * @author zero
 *
 */
public class Code5Run {

	public static void main(String[] args) throws Exception {

		String webappDirLocation = "web/";

		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir("C:\\public\\temp");

		String webPort = "8080";

		tomcat.setPort(Integer.valueOf(webPort));
		Connector connector = tomcat.getConnector();
		connector.setURIEncoding("UTF-8");
		tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

		try {
			tomcat.start();
		} catch (LifecycleException ex) {
			System.exit(-1);
		}
		tomcat.getServer().await();

	}
}