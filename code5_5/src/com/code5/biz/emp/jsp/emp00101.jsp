<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	// 뷰는 코드최적화가 안되어 있습니다.
	
	Box box = BoxContext.getThread();

	Table table = box.getTable("welcome");
	String[] cols = table.getCols();
%>

<%
	for (int i = 0; i < table.size(); i++) {
%>
<%
	for (int ii = 0; ii < cols.length; ii++) {
%>

<%=table.s(cols[ii], i)%>

<%
	}
%>

<br>
<%
	}
%>

