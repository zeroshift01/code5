<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	// [3]
	Box box = Box.getThread();

	String ret = box.s("ret");

	out.println(ret);
%>

