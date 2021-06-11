<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.code5.fw.web.MasterController"%>
<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@page import="com.code5.fw.data.Box"%>
<%
	Box box = BoxContext.getBox();
	Table list = box.getTable("list");
	
	boolean isLimitRecode = list.isLimitRecode();
	Box limitBox = list.getLimitBox();
	
	boolean is_brd02011 = MasterController.checkUrlAuth("brd02011");
	boolean is_brd02021 = MasterController.checkUrlAuth("brd02021");
	boolean is_brd02031 = MasterController.checkUrlAuth("brd02031");
%>

<script type="text/javascript">
function brd01010_next(NEXT_N){

	var form = document.form1;
	form1.NEXT_N.value = NEXT_N;
	form.action = '/waf/brd01010';
	
	form.submit();
	
}

function brd01030(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/brd01030';
	form.submit();
}

function brd01040(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/brd01040';
	form.submit();
}

function brd02031(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/brd02031';
	form.submit();
}

function brd02011(){
	
	<%if(!is_brd02011){%>
		alert('권한이 없습니다.');
		return;
	<%}%>
	
	var form = document.form1;
	form.action = '/waf/brd02011';
	form.submit();
}

function brd02021(){
	
	<%if(!is_brd02021){%>
		alert('권한이 없습니다.');
		return;
	<%}%>
	
	var form = document.form1;
	form.action = '/waf/brd02021';
	form.submit();
}



</script>


<form name="form1" method="post">
<hr>
<button onclick="brd01010()">brd01010</button>
<button onclick="brd02021()">brd02021</button>
<button onclick="brd02011()">brd02011</button>
<br>form1.NEXT_N : <input type="TEXT" name="NEXT_N" value="<%=box.s("NEXT_N")%>">
<br>form1.THIS_TOKEN_N : <input type="TEXT" name="THIS_TOKEN_N" value="<%=box.s("THIS_TOKEN_N")%>">
<br>form1.FIND_OPT : <input type="TEXT" name="FIND_OPT" value="<%=box.s("FIND_OPT")%>">
<br>form1.FIND_SRT : <input type="TEXT" name="FIND_SRT" value="<%=box.s("FIND_OPT")%>">
<hr>
	<%for (int i = 0; i < list.size(); i++) {%>
		N:<%=list.s("N", i)%>
		<button onclick="brd01030('<%=list.s("TOKEN_N", i)%>')">brd01030</button>
		<button onclick="brd01040('<%=list.s("TOKEN_N", i)%>')">brd01040</button>
		<%if(is_brd02031) { %>
		<button onclick="brd02030('<%=list.s("TOKEN_N", i)%>')">brd02030</button>
		<%}%>
		<br>form1.TITLE : <input type="TEXT" name="TITLE" value="<%=list.s("TITLE", i)%>">
		<br>form1.EM : <input type="TEXT" name="EM" value="<%=list.s("EM", i)%>">
		<br>form1.TOKEN_N : <input type="TEXT" readonly="readonly" name="TOKEN_N" value="<%=list.s("TOKEN_N", i)%>">
		<hr>
	<%}%>
	<% if(isLimitRecode) {%>
		<button onclick="brd01010_next('<%=limitBox.s("N")%>')">brd01010_next,<%=limitBox.s("N")%></button>
	<% }%>
</form>