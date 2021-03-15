<%@page import="com.code5.fw.web.MasterController"%>
<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();

	Table table = box.getTable("table");
	String[] cols = table.getCols();
	
	boolean is_emp00120 = MasterController.checkUrlAuth("emp00120");
%>
<html><head>
<script type="text/javascript">

function emp00120(){
	var form = document.form1;
	form.action = '/waf/emp00120';
	form.submit();
} 

</script>
<body>
<form name="form1" method="post">
<input type="text" name ="HP_N" value = "<%=box.s("HP_N")%>">
<%
	for (int i = 0; i < table.size(); i++) {
%>
<br>
<input type="text" name ="EMP_N" value = "<%=table.s("EMP_N", i)%>" readonly="readonly">
<input type="text" name ="EMP_NM" value = "<%=table.s("EMP_NM", i)%>" readonly="readonly">
<input type="text" name ="HP_N" value = "<%=table.s("HP_N", i)%>" readonly="readonly">
<br>
<%
	}
%>

</form>

<%if(is_emp00120) { %>
<button onclick="emp00120()">emp00120</button>
<%} %>

</body>
</head></html>

