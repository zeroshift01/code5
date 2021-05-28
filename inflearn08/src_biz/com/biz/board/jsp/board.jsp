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
</script>
<body onload='init();'>
	<br>
	<a href="/waf/listview">listview</a>
	<a href="/waf/writeview">writeview</a>
	<br>
	<jsp:include page="<%=JSP%>" flush="true"/>
		<br>
		<form name='main1'>
			<input name='ALERT_MSG' value='<%=box.getAlertMsg()%>' />
		</form></body>
</head>
</html>