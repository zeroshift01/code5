package com.code5.fw.web;

import org.apache.catalina.startup.Tomcat;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class RunCode5 {

	public static void main(String[] args) throws Exception {

		if (!InitYaml.IS_INIT()) {
			return;
		}

		String webappDir = InitYaml.WEB_APP_DIR();
		String baseDir = InitYaml.BASE_DIR();
		int webPort = InitYaml.WEB_PORT();

		Tomcat tomcat = new Tomcat();

		tomcat.setBaseDir(baseDir);
		tomcat.setPort(webPort);
		tomcat.addWebapp("", webappDir);

		tomcat.start();
		tomcat.getServer().await();

	}
}
