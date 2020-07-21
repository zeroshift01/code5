<%@page import="com.code5.fw.data.SessionB"%>
<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = Box.getThread();
	SessionB user = box.getSessionB();
%>
<html><head>
<script type="text/javascript">
</script>
<body>

로그인 후 화면
<br>
<br>ID : <%=user.getId() %>
<br>AUTH : <%=user.getAuth() %>

</body>
</head></html>

