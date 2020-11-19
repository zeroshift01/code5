<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	// [2]
	Box box = Box.getThread();

	String welcome = box.s("welcome");

	out.println(welcome);
%>

