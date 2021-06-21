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

	String msg = "알수 없는 오류가 발생했습니다.";

	if (ex instanceof LoginException) {
		msg = "로그인이 필요합니다.";
	}
%>
<pre>
사용자에게 보여주는 정보
<%=msg%>


개발자를 위한 디버그 정보
<%=ex.getMessage()%>
</pre>