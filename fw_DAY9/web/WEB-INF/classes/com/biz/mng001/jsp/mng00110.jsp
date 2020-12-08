<%@page import="com.code5.fw.data.SessionB"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	Box box = BoxContext.getThread();

	String ret = box.s("ret");
	
	Box thisBox = box.getBox("thisBox");
	
	SessionB user = box.getSessionB();
	
%>
<html><head>
<script type="text/javascript">

function mng00110(){
	var form = document.form1;
	form.action = '/waf/mng00110';
	form.submit();
} 

function mng00111(){
	var form = document.form1;
	form.action = '/waf/mng00111'; 
	form.submit();
}

</script>
<body>

<br>
<br>ID : <%=user.getId() %>
<br>AUTH : <%=user.getAuth() %>

<form name="form1" method="post">

<br>KEY
<br><input type="text" name="KEY" value = "<%=thisBox.s("KEY")%>">

<br>SQL
<br><textarea rows="10" cols="90%" name="SQL"><%=thisBox.s("SQL")%></textarea>
</form>

<button onclick="mng00110()">조회</button>
<button onclick="mng00111()">등록/수정</button>

</body>
</head></html>

