<%@page import="com.code5.fw.util.StringUtil"%>
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

<a href="/waf/exe00110">카드승인요청 exe00110</a> <a href="/waf/exe00120">카드승인내역 조회 exe00120</a>

<form name="form1" method="post">

<br>ALNC_DTM
<br><input type="text" name="CRD_N" value="<%=StringUtil.cleanXSS(box.s("CRD_N"))%>">


<br>CRD_N
<br><input type="text" name="CRD_N" value="<%=StringUtil.cleanXSS(box.s("CRD_N"))%>">

<br>YYMM
<br><input type="text" name="YYMM" value="<%=StringUtil.cleanXSS(box.s("YYMM"))%>">


<br>AMT
<br><input type="text" name="AMT" value="<%=StringUtil.cleanXSS(box.s("AMT"))%>">


</form>

<button onclick="exe00111()">카드승인요청 exe00111</button>

</body>
</head></html>

