<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
	String MSG = box.s("MSG");
%>
<html>
<head>
<body>
	<%=MSG%>
</body>
</head>
</html>