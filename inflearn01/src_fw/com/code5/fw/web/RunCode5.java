package com.code5.fw.web;

import java.net.Socket;
import java.util.Properties;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class RunCode5 {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		InitYaml init = InitYaml.get();

		if (!init.isRead()) {
			return;
		}

		String tempDir = init.getTempDir();
		int webPort = init.getWebPort();
		String webappDir = init.getWebAppDir();

		System.out.println("hostName [" + init.getHostName() + "]");
		System.out.println("webappDir [" + webappDir + "]");
		System.out.println("webPort [" + webPort + "]");
		System.out.println("tempDir [" + tempDir + "]");

		if (!isWebPort(webPort)) {
			System.out.println(webPort + " 는 사용중입니다.");
			return;
		}

		Properties properties = System.getProperties();
		properties.setProperty("com.code5.app.name", "CODE5");

		Tomcat tomcat = new Tomcat();

		tomcat.setBaseDir(tempDir);
		tomcat.setPort(webPort);
		tomcat.addWebapp("", webappDir);

		String characterSet = init.getCharacterSet();
		System.out.println("uri characterSet [" + characterSet + "]");
		Connector conn = tomcat.getConnector();
		conn.setURIEncoding(characterSet);

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
