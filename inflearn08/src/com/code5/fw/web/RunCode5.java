package com.code5.fw.web;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

/**
 * @author zero
 *
 */
public class RunCode5 {

	public static void main(String[] args) throws Exception {

		String webappDir = new File("web/").getAbsolutePath();
		String baseDir = new File("temp/").getAbsolutePath();
		int webPort = 18080;
		String uriEncoding = "UTF-8";

		Tomcat tomcat = new Tomcat();

		
		
		tomcat.setBaseDir(baseDir);
		tomcat.addWebapp("", webappDir);
		tomcat.setPort(Integer.valueOf(webPort));

		Connector connector = tomcat.getConnector();
		connector.setURIEncoding(uriEncoding);

		tomcat.start();
		tomcat.getServer().await();

	}
}