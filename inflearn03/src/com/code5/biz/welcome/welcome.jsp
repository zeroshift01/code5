<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
	String ret = box.s("ret");
%>
<html>
<head>
<body>
	<%=ret%>
</body>
</head>
</html>