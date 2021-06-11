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
	
	boolean is_brd02010 = MasterController.checkUrlAuth("brd02010");
	boolean is_brd02020 = MasterController.checkUrlAuth("brd02020");
	boolean is_brd02030 = MasterController.checkUrlAuth("brd02030");
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

function brd02030(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/brd02030';
	form.submit();
}

function brd02010(){
	
	<%if(!is_brd02010){%>
		alert('권한이 없습니다.');
		return;
	<%}%>
	
	var form = document.form1;
	form.action = '/waf/brd02010';
	form.submit();
}

function brd02020(){
	
	<%if(!is_brd02020){%>
		alert('권한이 없습니다.');
		return;
	<%}%>
	
	var form = document.form1;
	form.action = '/waf/brd02020';
	form.submit();
}



</script>


<form name="form1" method="post">
<hr>
<button onclick="brd01010()">brd01010</button>
<button onclick="brd02020()">brd02020</button>
<button onclick="brd02010()">brd02010</button>
<br>form1.NEXT_N : <input type="TEXT" name="NEXT_N" value="<%=box.s("NEXT_N")%>">
<br>form1.THIS_TOKEN_N : <input type="TEXT" name="THIS_TOKEN_N" value="<%=box.s("THIS_TOKEN_N")%>">
<br>form1.FIND_OPT : <input type="TEXT" name="FIND_OPT" value="<%=box.s("FIND_OPT")%>">
<br>form1.FIND_SRT : <input type="TEXT" name="FIND_SRT" value="<%=box.s("FIND_OPT")%>">
<hr>
	<%for (int i = 0; i < list.size(); i++) {%>
		N:<%=list.s("N", i)%>
		<button onclick="brd01030('<%=list.s("TOKEN_N", i)%>')">brd01030</button>
		<button onclick="brd01040('<%=list.s("TOKEN_N", i)%>')">brd01040</button>
		<%if(is_brd02030) { %>
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