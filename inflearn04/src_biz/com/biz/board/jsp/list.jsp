<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.get();
Table list = box.getTable("list");
%>
<html>
<head>
<body>
	<pre>
	
<%
		for (int i = 0; i < list.size(); i++) {
%>
	<%=list.s("N",i) %>  <%=list.s("TITLE",i) %> <%=list.s("TXT",i) %> <%=list.s("EM",i) %>
<%
	}
%>

</pre>
</body>
</head>
</html>
