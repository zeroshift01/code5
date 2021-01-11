<%@ page contentType="application/x-msdownload;"%><%@page
	import="com.code5.fw.security.CryptFile"%><%@page
	import="com.code5.fw.data.Box"%><%@page
	import="com.code5.fw.web.BoxContext"%><%
	
Box box = BoxContext.getThread();
Box fileBox = box.getBox("fileBox");
String REAL_FILE_URL = fileBox.s("REAL_FILE_URL");
String REAL_FILE_NAME = fileBox.s("REAL_FILE_NAME");

response.setHeader("Content-Disposition", "attachment;filename=" + REAL_FILE_NAME + ";");

long timeOut = 1000 * 60 * 10;

CryptFile.getCryptFile().decrypt(fileBox.s("FILE_REL"), out, timeOut);
%>