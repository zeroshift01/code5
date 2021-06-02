<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String boardData = (String) request.getAttribute("BOARD_DATA");
	String msg = (String) request.getAttribute("MSG");
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
