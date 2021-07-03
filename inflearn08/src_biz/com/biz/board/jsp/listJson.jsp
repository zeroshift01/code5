<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.get();
	Table list = box.getTable("list");
%>
[
<%
	for (int i = 0; i < list.size(); i++) {
%>
<%=list.s("N", i)%>,<%=list.s("TITLE", i)%>
	
<%}%>
]