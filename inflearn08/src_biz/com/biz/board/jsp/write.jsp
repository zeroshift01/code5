<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">
function exeWrite(){
	var form = document.form1;
	form.action = '/multipart/exeWrite';
	form.submit();
}
</script>

<form name="form1" method="post">

TITLE : <input type="TEXT" name="TITLE">
<br>TXT : <input type="TEXT" name="TXT"> 
 <br>EM : <input type="TEXT" name="EM">
 <br>file1 : <input type="FILE" name="FILE_1">
 <br>file2 : <input type="FILE" name="FILE_2">
 <br>file3 : <input type="FILE" name="FILE_3">

</form>

<button onclick="exeWrite()">등록실행</button>