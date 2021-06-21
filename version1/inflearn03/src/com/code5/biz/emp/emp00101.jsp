<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
	Table table = box.getTable("list");
%>
<html>
<head>
</head>
<body>
<%=table.toString()%>
</body>
</html>


