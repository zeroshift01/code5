<%@page import="java.io.PrintWriter"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Exception exception = (Exception) request.getAttribute("exception");

	
	String message = "알수 없는 오류가 발생했습니다.";
	
	if (exception != null) {
		// [1]
		message = exception.getMessage();
	}
%>
아래는 사용자에게 보이는 정보입니다.
<br>[<%=message %>]

<pre>
아래는 개발자에게만 보이는 정보입니다.

<%
	if (exception != null) {
		// [2]
		exception.printStackTrace(new PrintWriter(out));	
	}
%>

</pre>
