<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	Box box = Box.getThread();

	String ret = box.s("ret");
	
	Box thisBox = box.getBox("thisBox");
	
%>
<html><head>
<script type="text/javascript">

function mng00130(){
	var form = document.form1;
	form.action = '/waf/mng00130';
	form.submit();
} 

function mng00131(){
	var form = document.form1;
	form.action = '/waf/mng00131'; 
	form.submit();
}

</script>
<body>

<form name="form1" method="post">

<br>KEY
<br><input type="text" name="KEY" value = "<%=thisBox.s("KEY")%>">

<br>JSP
<br><input type="text" name="JSP" value = "<%=thisBox.s("JSP")%>">

</form>

<button onclick="mng00130()">조회</button>
<button onclick="mng00131()">등록/수정</button>

</body>
</head></html>

