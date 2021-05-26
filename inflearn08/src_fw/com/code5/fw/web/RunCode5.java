package com.code5.fw.web;

import java.net.Socket;

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

		init.setAppName(RunCode5.class.getName());

		String baseDir = init.s("RUN_CODE5.BASE_DIR");
		int webPort = Integer.parseInt(init.s("RUN_CODE5.WEB_PORT"));

		if (!isWebPort(webPort)) {
			System.out.println(webPort + " 는 사용중입니다.");
			return;
		}

		String webappDir = init.s("RUN_CODE5.WEB_APP_DIR");

		Tomcat tomcat = new Tomcat();

		tomcat.setBaseDir(baseDir);
		tomcat.setPort(webPort);
		tomcat.addWebapp("", webappDir);

		tomcat.start();

		System.out.println("        (__) ");
		System.out.println("        (oo) ");
		System.out.println(" /-------\\/ ");
		System.out.println("/ |     ||   ");
		System.out.println("* ||----||   ");
		System.out.println("  ~~    ~~   ");
		System.out.println("  code5 start ");

		tomcat.getServer().await();

	}

	/**
	 * @param webPort
	 * @return
	 */
	private static boolean isWebPort(int webPort) throws Exception {

		Socket socket = null;
		try {
			socket = new Socket("localhost", webPort);
			socket.setSoTimeout(1);
			if (socket.isConnected()) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			return true;
		} finally {
			if (socket != null) {
				socket.close();
			}
		}

	}

}
