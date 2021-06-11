<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">
function brd01021(){
	var form = document.form1;
	form.action = '/multipart/brd01021';
	form.submit();
}
</script>
<hr>
<form name="form1" method="post" enctype = "multipart/form-data">
post, multipart/form-data
<br>
<br>form1.TITLE : <input type="TEXT" name="TITLE">
<br>form1.TXT : <input type="TEXT" name="TXT"> 
<br>form1.EM : <input type="TEXT" name="EM">
<br>form1.file1 : <input type="FILE" name="FILE_1">
<br>form1.file2 : <input type="FILE" name="FILE_2">
</form>
<button onclick="brd01021()">brd01021</button>
