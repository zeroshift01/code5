<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = (Box) request.getAttribute("box");
	String welcome = (String) box.get("welcome");

	out.println(welcome);
%>

