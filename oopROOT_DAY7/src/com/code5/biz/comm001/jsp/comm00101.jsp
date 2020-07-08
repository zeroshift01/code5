<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = Box.getThread();
Table comm00101 = box.getTable("comm00101");
%>
<%
	for (int i = 0; i < comm00101.length(); i++) {
	Box thisRecode = comm00101.getBox(i);
%>

	<%=thisRecode.s("번호")%>
	<%=thisRecode.s("제목")%>
	<br>
<%
	}
%>
