<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = BoxContext.getThread();
	Table list = box.getTable("list");
	
	boolean isLimitRecode = list.isLimitRecode();
	Box limitBox = list.getLimitBox();
%>

<script type="text/javascript">
</script>


<form name="form1" method="post">

<br>form1.TOKEN_N : <input type="TEXT" name="TOKEN_N">
<br>
<br>
<br>- 게시물 리스트
<br>--------------------------------------------
	<%
		for (int i = 0; i < list.size(); i++) {
	%>
	
	
	<br>N : <%=list.s("N", i)%>
	<br>TITLE : <%=list.s("TITLE", i)%>
	<br>TOKEN_N : <%=list.s("TOKEN_N", i)%>
	
	<br><button onclick="callUpdate('<%=list.s("TOKEN_N", i)%>')">수정 화면호출</button>
	<br><button onclick="exeDelete('<%=list.s("TOKEN_N", i)%>')">한건삭제 실행</button>
	
	<br>--------------------------------------------
	<%}%>
</form>

<% if(isLimitRecode) {%>
다음글이 있습니다.
<%=limitBox.s("TOKEN_N") %>
<br>--------------------------------------------
<% }%>

<br>
<br>
<br><button onclick="allDelete()">관리자 게시판제목수정 실행</button>
<br> 
<br><button onclick="allDelete()">관리자 게시판 전체삭제</button>