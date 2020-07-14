<%@ page contentType="text/html; charset=UTF-8"%>
<%

	// [3]
	Box box = (Box) request.getAttribute("com.code5.fw.web.Box");

	String ret = (String) request.getAttribute("ret");

	out.println(ret);
%>

