<%@page import="com.code5.fw.data.SessionB"%>
<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

Box box = BoxContext.getThread();
Box view = box.getBox(Box.KEY_FW_VIEW);
String JSP = view.s("JSP");
String TITLE = view.s("TITLE");

SessionB user = box.getSessionB();

%>
<html>
<head>
<title><%=TITLE%></title>
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
	}
	
	function callList(){
		var form = document.mainform;
		form.action = '/waf/callList';
		form.submit();
	}
	
	function exeLogout(){
		var form = document.mainform;
		form.action = '/waf/exeLogout';
		form.submit();
	}
	
</script>
<body onload='init();'>
<br>로그인한 아이디 : <%=user.getId() %>, 권한코드 : <%=user.getAuth() %>
<br><button onclick="callWrite()">callWrite</button> <button onclick="callList()">callList</button> <button onclick="exeLogout()">logout</button>
<hr>
메뉴명:<%=TITLE%>
<jsp:include page="<%=JSP%>" flush="true"/>
<hr>
<form name='mainform' method="post">
mainform.ALERT_MSG : <input id='TEXT' name='ALERT_MSG' value='<%=box.getAlertMsg()%>' />
</form>
</body>
</head>
</html>