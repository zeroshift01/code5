<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
	Box board = box.getBox("board");
%>

<script type="text/javascript">
function exeUpdate(){
	var form = document.form1;
	form.action = '/multipart/exeUpdate';
	form.submit();
}
</script>

<form name="form1" method="post">

TITLE : <input type="TEXT" name="TITLE" value="<%=board.s("TITLE")%>">
<br>TXT : <input type="TEXT" name="TXT" value="<%=board.s("TXT")%>">
<br>EM : <input type="TEXT" name="EM" value="<%=board.s("EM")%>">
 
<br><%=board.s("FILE_NM_1")%>
<br><%=board.s("FILE_NM_2")%>
<br><%=board.s("FILE_NM_3")%>
 
<br>file1 : <input type="FILE" name="FILE_1">
<br>file2 : <input type="FILE" name="FILE_2">
<br>file3 : <input type="FILE" name="FILE_3">

</form>

<button onclick="exeUpdate()">수정실행</button>