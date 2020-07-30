<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = Box.getThread();
%>
<html><head>
<script type="text/javascript">

function init(){
	var form = document.formMsg;
	if(form.MSG.value != '') {
		alert(form.MSG.value);
	}
}

function exe00111(){
	var form = document.form1;
	form.action = '/waf/exe00111';
	form.submit();
} 
</script>

<link rel="stylesheet" href="/css/code5.css">
<script src="/js/jquery-3.5.1.min.js"></script>

</head>
<body onload="init();">

<form name="formMsg"><input type="hidden" name = "MSG" value = "<%=box.s("MSG")%>"></form>

<a href="/waf/exe00110">결제요청</a> <a href="/waf/exe00120">결제내역 조회</a>

<form name="form1" method="post">

<br>CRD_N
<br><input type="text" name="CRD_N">

<br>YYMM
<br><input type="text" name="YYMM">


<br>AMT
<br><input type="text" name="AMT">


</form>

<button onclick="exe00111()">승인요청 exe00111</button>

</body>
</head></html>

