<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.code5.fw.web.MasterController"%>
<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%
	Box box = BoxContext.getThread();
	Table list = box.getTable("list");
	
	boolean isLimitRecode = list.isLimitRecode();
	Box limitBox = list.getLimitBox();
	
	boolean isAllDelete = MasterController.checkUrlAuth("allDelete");
	boolean isAllUpdate = MasterController.checkUrlAuth("allUpdate");
	boolean isForceDelete = MasterController.checkUrlAuth("forceDelete");
%>

<script type="text/javascript">
function callNextList(TOKEN_N){

	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/callList';
	
	form.submit();
	
}

function callUpdate(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/callUpdate';
	form.submit();
}

function exeDelete(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/exeDelete';
	form.submit();
}

function forceDelete(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/forceDelete';
	form.submit();
}

function allDelete(){
	
	<%if(!isAllDelete){%>
		alert('권한이 없습니다.');
		return;
	<%}%>
	
	var form = document.form1;
	form.action = '/waf/allDelete';
	form.submit();
}

function allUpdate(){
	
	<%if(!isAllUpdate){%>
		alert('권한이 없습니다.');
		return;
	<%}%>
	
	var form = document.form1;
	form.action = '/waf/allUpdate';
	form.submit();
}



</script>


<form name="form1" method="post">

<br>form1.THIS_TOKEN_N : <input type="TEXT" name="THIS_TOKEN_N">
<br>
<br>
<br>- 게시물 리스트
<br>--------------------------------------------
	<%
		for (int i = 0; i < list.size(); i++) {
	%>
	
	
	<br>N : <%=list.s("N", i)%>
	
	<br>TITLE : <input type="TEXT" name="TITLE" value="<%=list.s("TITLE", i)%>">
	<br>EM : <input type="TEXT" name="EM" value="<%=list.s("EM", i)%>">
	<br>TOKEN_N : <input type="TEXT" readonly="readonly" name="TOKEN_N" value="<%=list.s("TOKEN_N", i)%>">
	
	<br><button onclick="callUpdate('<%=list.s("TOKEN_N", i)%>')">수정 화면호출</button>
	<br><button onclick="exeDelete('<%=list.s("TOKEN_N", i)%>')">한건삭제 실행</button>
	
	<%if(isForceDelete) { %>
	<br><button onclick="forceDelete('<%=list.s("TOKEN_N", i)%>')">관리자 한건삭제 실행</button>
	<%}%>
	
	
	<br>--------------------------------------------
	<%}%>
</form>

<% if(isLimitRecode) {%>
<br>다음 페이지가 있습니다.
<br><button onclick="callNextList('<%=limitBox.s("TOKEN_N")%>')">다음페이지 화면호출</button> <%=limitBox.s("N")%>, <%=limitBox.s("TOKEN_N")%>
<br>--------------------------------------------
<% }%>

<br>
<br>
<br><button onclick="allUpdate()">관리자 게시판제목수정 실행</button> allUpdate
<br> 
<br><button onclick="allDelete()">관리자 게시판 전체삭제</button> allDelete