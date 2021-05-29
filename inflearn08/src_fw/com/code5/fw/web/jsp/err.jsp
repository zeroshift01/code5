<%@page import="com.code5.fw.web.LoginException"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	response.setHeader("cache-control", "no-cache");
	response.setHeader("expires", "0");
	response.setHeader("pragma", "no-cache");

	Box box = BoxContext.getThread();

	Exception ex = (Exception) box.get(Box.KEY_EXCEPTION);

	String msg = "오류가 발생했습니다.";

	boolean isCallLogin = false;
	if (ex instanceof LoginException) {
		msg = "로그인이 필요합니다.";
		isCallLogin = true;
	}
%>
<pre>+
사용자에게 보여주는 에러 메세지

<%=msg%>

--------------------------------------------------
개발자에게 보여주는 에러 메세지

<%=ex.toString()%>
<%=ex.getMessage()%>
<%ex.printStackTrace();%>
</pre>

<button onclick="history.back(-1)">뒤로 가기</button>

<%if(isCallLogin) { %>
<br><br><br>
<a href ="/waf/callLogin">로그인화면 호출</a>
<%} %>
