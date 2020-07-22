<%@page import="com.code5.fw.exception.BizException"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	BizException bizException = (BizException) request.getAttribute("bizException");
	
	String message = "알수 없는 오류가 발생했습니다.";
	String printError = "";
	
	if (bizException != null) {
		message = bizException.getMessage();
		printError = bizException.getPrintStackTrace();
	}
%>
<br>서비스 실행중 오류가 발생했습니다.
<br><%=message %>

<pre>
아래는 개발자에게만 보이도록 할 얘정입니다.
<%=printError %>
</pre>