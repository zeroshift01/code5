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

<form name="form1" method="post" enctype = "multipart/form-data">
<input type ="TEXT" NAME = "TOKEN_N" VALUE = "<%=box.s("TOKEN_N")%>">
<br>
<br>TITLE : <input type="TEXT" name="TITLE" value="<%=board.s("TITLE")%>">
<br>TXT : <input type="TEXT" name="TXT" value="<%=board.s("TXT")%>">
<br>EM : <input type="TEXT" name="EM" value="<%=board.s("EM")%>">
 
<br>FILE_NM_1 : <%=board.s("FILE_NM_1")%> <a href="/waf/file001?FILE_ID=<%=board.s("T_FILE_ID_1")%>">[파일다운로드]</a>
<br><%=board.s("T_FILE_ID_1")%>
<br>
<br>FILE_NM_2 : <%=board.s("FILE_NM_2")%> <a href="/waf/file001?FILE_ID=<%=board.s("T_FILE_ID_2")%>">[파일다운로드]</a>
<br><%=board.s("T_FILE_ID_2")%>
<br>
<br>
<br> 
<br>file1 : <input type="FILE" name="FILE_1">
<br>file2 : <input type="FILE" name="FILE_2">

</form>

<button onclick="exeUpdate()">수정실행</button>