<%@page import="com.code5.fw.web.MasterController"%>
<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	response.setHeader("cache-control", "no-cache");
	response.setHeader("expires", "0");
	response.setHeader("pragma", "no-cache");

	Box box = BoxContext.getThread();

	Table table = box.getTable("table");
	String[] cols = table.getCols();
	
	boolean isEmp00120 = MasterController.checkUrlAuth("emp00120");
%>
<script type="text/javascript">

function emp00110(){
	var form = document.form1;
	form.action = '/waf/emp00110';
	form.submit();
}

function emp00120(){
	var form = document.form2;
	form.action = '/multipart/emp00120';
	form.submit();
} 

</script>

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

<br><br>
처리후 삭제
<br><input type="file" name ="TEMP_FILE_1">
<br><input type="file" name ="TEMP_FILE_2">


</form>
<form name="form1" method="post">
<input type="text" name ="EMP_NM" value = "<%=box.s("EMP_NM")%>">
</form>

<button onclick="emp00110()">검색emp00110</button>

<%if(isEmp00120) { %>
<button onclick="emp00120()">수정 emp00120</button>
<%} %>
