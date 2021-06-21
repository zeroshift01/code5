<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();

Table table = box.getTable("table");
String[] cols = table.getCols();

for (int i = 0; i < table.size(); i++) {
	for (int ii = 0; ii < cols.length; ii++) {
		out.println(table.s(cols[ii], i));

	}
}
%>