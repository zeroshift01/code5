<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	Box box = Box.getThread();

	String ret = box.s("ret");
	
	Box thisBox = box.getBox("thisBox");
	
%>
<html><head>
<script type="text/javascript">

function mng00120(){
	var form = document.form1;
	form.action = '/waf/mng00120';
	form.submit();
} 

function mng00121(){
	var form = document.form1;
	form.action = '/waf/mng00121'; 
	form.submit();
}

</script>
<body>

<form name="form1" method="post">

<br>KEY
<br><input type="text" name="KEY" value = "<%=thisBox.s("KEY")%>">

<br>CLASS_NAME
<br><input type="text" name="CLASS_NAME" value = "<%=thisBox.s("CLASS_NAME")%>">

<br>METHOD_NAME
<br><input type="text" name="METHOD_NAME" value = "<%=thisBox.s("METHOD_NAME")%>">

</form>

<button onclick="mng00120()">조회</button>
<button onclick="mng00121()">등록/수정</button>

</body>
</head></html>

