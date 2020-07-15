<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = Box.getThread();
	Box comm00102 = box.getBox("comm00102");
%>

<%=comm00102.s("번호")%>
<%=comm00102.s("제목")%>
