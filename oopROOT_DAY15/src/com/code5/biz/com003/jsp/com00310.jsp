<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	Box box = Box.getThread();

	String ret = box.s("ret");
%>
<html><head>
<script type="text/javascript">

function com00311(){
	var form = document.form1;
	form.action = '/waf/com00311';
	form.submit();
} 

</script>
<body>

<%=ret %>

<form name="form1" method="post">

<br>ID
<br><input type="text" name="ID">

<br>PIN
<br><input type="text" name="PIN">

</form>

<button onclick="com00311()">로그인</button>

</body>
</head></html>

