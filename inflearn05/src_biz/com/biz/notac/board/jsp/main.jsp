<%@page import="com.code5.fw.data.SessionB"%>
<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

response.setHeader("cache-control", "no-cache");
response.setHeader("expires", "0");
response.setHeader("pragma", "no-cache");


Box box = BoxContext.get();
Box fwView = box.getBox(Box.KEY_FW_VIEW);
String view = fwView.s("VIEW");
String title = fwView.s("TITLE");

%>
<html>
<head>
<title><%=title%></title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="/css/main.css" />
<script>
	function init() {
		var msg = document.mainform.ALERT_MSG.value;
		if (msg != '') {
			alert(msg);
		}
	}
	
	function callWrite(){
		var form = document.mainform;
		form.action = '/waf/callWrite';
		form.submit();
		return;
	}
	
	function callList(){
		var form = document.mainform;
		form.action = '/waf/callList';
		form.submit();
		return;
	}
	
	function exeLogout(){
		var form = document.mainform;
		form.action = '/waf/exeLogout';
		form.submit();
		return;
	}
	
</script>
<body onload='init();'>
<br>로그인한 아이디 : 없음, 권한코드 : 없음
<br><button onclick="callWrite()">callWrite</button> <button onclick="callList()">callList</button> <button onclick="exeLogout()">logout</button>
<hr>
메뉴명:<%=title%>
<jsp:include page="<%=view%>" flush="true"/>
<hr>
<form name='mainform' method="post">
mainform.ALERT_MSG : <input id='TEXT' name='ALERT_MSG' value='<%=box.getAlertMsg()%>' />
</form>
</body>
</head>
</html>