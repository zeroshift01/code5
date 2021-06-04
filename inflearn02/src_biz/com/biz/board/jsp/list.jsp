<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
	String boardData = (String) box.get("BOARD_DATA");
	String msg = (String) box.get("MSG");
%>
<html>
<head>
<body>
<pre>

list.jsp

BOARD_DATA : <%=boardData%>

MSG : <%=msg%>

</pre>
</body>
</head>
</html>
