<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	// [2]
	Box box = Box.getThread();

	String ret = (String) request.getAttribute("ret");

	out.println(ret);
%>

