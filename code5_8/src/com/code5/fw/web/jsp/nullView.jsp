<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
%>
<html>
<head>
<title></title>
<script>
	function init() {
		var msg = document.main1.ALERT_MSG.value;
		if (msg != '') {
			alert(msg);
		}
	}
</script>
<body onload='init();'>

	<form name='main1'>
		<input name='ALERT_MSG' value='<%=box.getAlertMsg()%>' />
	</form>
</body>
</head>
</html>