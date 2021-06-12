<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getBox();
	String list = (String) box.get("list");
	String exeWriteResult = (String) box.get("exeWriteResult");
%>
<html>
<head>
<body>
<pre>

list.jsp

list : <%=list%>

exeWriteResult : <%=exeWriteResult%>

</pre>
</body>
</head>
</html>
