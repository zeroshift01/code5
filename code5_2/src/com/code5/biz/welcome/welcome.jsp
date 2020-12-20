<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();

	String welcome = box.s("welcome");

	out.println(welcome);
%>

