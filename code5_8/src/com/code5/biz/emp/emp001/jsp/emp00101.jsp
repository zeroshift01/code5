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

function emp00101(){
	var form = document.form1;
	form.action = '/waf/emp00101';
	form.submit();
}

function emp00102(){
	var form = document.form2;
	form.action = '/multipart/emp00102';
	form.submit();
} 

</script>
<body>
<form name="form2" method="post" enctype="multipart/form-data">
<%
	for (int i = 0; i < table.size(); i++) {
		Box thisBox = table.getBox(i);
%>
<br>
<input type="text" name ="EMP_N" value = "<%=thisBox.s("EMP_N")%>" readonly="readonly">
<%=thisBox.s("EMP_NM")%>
<input type="text" name ="HP_N" value = "<%=thisBox.s("HP_N")%>">
<input type="file" name ="FILE_<%=i%>">

<input type="text" name ="FILE_ID_ORG" value = "<%=thisBox.s("FILE_ID")%>" readonly="readonly">

<a href="/waf/downloadfile?FILE_ID=<%=thisBox.s("FILE_ID")%>"><%=thisBox.s("FILE_NAME") %></a>
<br>
<%
	}
%>

</form>
<form name="form1" method="post">
<input type="text" name ="EMP_NM" value = "<%=box.s("EMP_NM")%>">
</form>

<button onclick="emp00101()">검색 emp00101</button>

<%if(is_emp00102) { %>
<button onclick="emp00102()">수정 emp00102</button>
<%} %>

</body>
</head></html>

