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
		Box thisBox = table.getBox(i);
%>

<input type="text" name ="EMP_N" value = "<%=thisBox.s("EMP_N")%>" readonly="readonly">
<%=thisBox.s("EMP_NM")%>
<input type="text" name ="HP_N" value = "<%=thisBox.s("HP_N")%>">

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

