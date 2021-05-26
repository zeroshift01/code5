package com.code5.fw.web;

import org.apache.catalina.startup.Tomcat;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class RunCode5 {

	public static void main(String[] args) throws Exception {

		InitYaml init = InitYaml.get();

		if (!init.isRead()) {
			return;
		}

		String baseDir = init.s("RUN_CODE5.BASE_DIR");
		int webPort = Integer.parseInt(init.s("RUN_CODE5.WEB_PORT"));

		String webappDir = init.s("RUN_CODE5.WEB_APP_DIR");

		Tomcat tomcat = new Tomcat();

		tomcat.setBaseDir(baseDir);
		tomcat.setPort(webPort);
		tomcat.addWebapp("", webappDir);

		tomcat.start();
		tomcat.getServer().await();

	}
}
