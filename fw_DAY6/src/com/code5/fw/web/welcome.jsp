<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	// [2]
	Box box = BoxContext.getThread();

	String welcome = box.s("welcome");

	out.println(welcome);
%>

