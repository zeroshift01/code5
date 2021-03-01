<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
	String ret = box.s("ret");

	out.println(ret);
%>


