<%@page import="com.code5.fw.web.MasterController"%>
<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();

	Table table = box.getTable("table");
	String[] cols = table.getCols();
	
	boolean is_emp00102 = MasterController.checkUrlAuth("emp00102");
%>
<html><head>
<script type="text/javascript">

function emp00102(){
	var form = document.form1;
	form.action = '/waf/emp00102';
	form.submit();
} 

</script>
<body>
<form name="form1" method="post">
<%
	for (int i = 0; i < table.size(); i++) {
%>

<input type="text" name ="EMP_N" value = "<%=table.s("EMP_N", i)%>" readonly="readonly">
<%=table.s("EMP_NM", i)%>
<input type="text" name ="HP_N" value = "<%=table.s("HP_N", i)%>">

<br>
<%
	}
%>

</form>

<%if(is_emp00102) { %>
<button onclick="emp00102()">emp00102</button>
<%} %>

</body>
</head></html>

