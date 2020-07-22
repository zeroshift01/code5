<%@page import="com.code5.fw.data.SessionB"%>
<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = Box.getThread();
	SessionB user = box.getSessionB();
	
	boolean is_mng00110 = box.getBoolean("is_mng00110");
%>
<html><head>
<script type="text/javascript">
</script>
<body>

로그인 후 화면
<br>
<br>ID : <%=user.getId() %>
<br>AUTH : <%=user.getAuth() %>

<br> 관리자화면 이동 가능 <%=is_mng00110 %>

</body>
</head></html>

