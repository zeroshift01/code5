<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
Table list = box.getTable("list");
%>

<script type="text/javascript">
</script>

<form name="form1" method="post">
<input type="TEXT" name="THIS_N">
	<%
		for (int i = 0; i < list.size(); i++) {
	%>
	<%=list.s("N", i)%>
	<%=list.s("TITLE", i)%>
	
	<button onclick="callUpdate('<%=list.s("N", i)%>')">수정화면호출</button>
	<button onclick="exeDelete('<%=list.s("N", i)%>')">삭제실행</button>
	
	<br>
	<%}%>
</form>

<button onclick="callWrite()">등록화면호출</button>