<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
Box box = BoxContext.getThread();
Box view = box.getBox(Box.KEY_FW_VIEW);
String JSP = view.s("JSP");
String TITLE = view.s("TITLE");
%>
<html>
<head>
<title><%=TITLE%></title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="/css/main.css" />
<script>
	function init() {
		var msg = document.main1.ALERT_MSG.value;
		if (msg != '') {
			alert(msg);
		}
	}
	
	function callUpdate(N){
		var form = document.form1;
		form.THIS_N.value = N; 
		form.action = '/waf/callUpdate';
		form.submit();
	} 

	function exeDelete(N){
		var form = document.form1;
		form.THIS_N.value = N; 
		form.action = '/waf/exeDelete';
		form.submit();
	}

	function callWrite(){
		var form = document.form1;
		form.action = '/waf/callWrite';
		form.submit();
	}
	
</script>
<body onload='init();'>
	<br>
	
	<button onclick="callWrite()">등록</button>
	<br>
	<jsp:include page="<%=JSP%>" flush="true"/>
	<br>
	<form name='main1'>
		<input name='ALERT_MSG' value='<%=box.getAlertMsg()%>' />
	</form>
</body>
</head>
</html>