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
	
	boolean isAllDelete = MasterController.checkUrlAuth("allDelete");
	boolean isAllUpdate = MasterController.checkUrlAuth("allUpdate");
	boolean isForceDelete = MasterController.checkUrlAuth("forceDelete");
%>

<script type="text/javascript">
function callNextList(NEXT_N){

	var form = document.form1;
	form1.NEXT_N.value = NEXT_N;
	form.action = '/waf/callList';
	form.submit();
	return;
	
}

function callUpdate(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/callUpdate';
	form.submit();
	return;
}

function exeDelete(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/exeDelete';
	form.submit();
	return;
}

function forceDelete(TOKEN_N){
	var form = document.form1;
	form1.THIS_TOKEN_N.value = TOKEN_N;
	form.action = '/waf/forceDelete';
	form.submit();
	return;
}

function allDelete(){
	
	<%if(!isAllDelete){%>
		alert('권한이 없습니다.');
		return;
	<%}%>
	
	var form = document.form1;
	form.action = '/waf/allDelete';
	form.submit();
	return;
}

function allUpdate(){
	
	<%if(!isAllUpdate){%>
		alert('권한이 없습니다.');
		return;
	<%}%>
	
	var form = document.form1;
	form.action = '/waf/allUpdate';
	form.submit();
	return;
}



</script>


<form name="form1" method="post">
<hr>
	<table border="1">
		<tr>
			<td>
<button onclick="callList()">callList</button>
<button onclick="allUpdate()">allUpdate</button>
<button onclick="allDelete()">allDelete</button>
<br>form1.NEXT_N : <input type="TEXT" name="NEXT_N" value="<%=box.s("NEXT_N")%>">
<br>form1.THIS_TOKEN_N : <input type="TEXT" name="THIS_TOKEN_N" value="<%=box.s("THIS_TOKEN_N")%>">
<br>form1.FIND_OPT : <input type="TEXT" name="FIND_OPT" value="<%=box.s("FIND_OPT")%>">
<br>form1.FIND_SRT : <input type="TEXT" name="FIND_SRT" value="<%=box.s("FIND_OPT")%>">
			</td>
		</tr>
	</table>
<hr>
<table border="1">
		<tr>
			<td>N</td>
			<td>TITLE</td>
			<td>EM</td>
			<td>TOKEN_N</td>
			<td>service</td>
		</tr>
			
	<%for (int i = 0; i < list.size(); i++) {%>
	
	<tr>
		<td><%=list.s("N", i)%></td>
		<td><input type="TEXT" name="TITLE" value="<%=list.s("TITLE", i)%>"></td>
		<td><input type="TEXT" name="EM" value="<%=list.s("EM", i)%>"></td>
		<td><input type="TEXT" readonly="readonly" name="TOKEN_N" value="<%=list.s("TOKEN_N", i)%>"></td>
		<td>
		<button onclick="callUpdate('<%=list.s("TOKEN_N", i)%>')">callUpdate</button>
		<button onclick="exeDelete('<%=list.s("TOKEN_N", i)%>')">exeDelete</button>
		<%if(isForceDelete) { %>
		<button onclick="forceDelete('<%=list.s("TOKEN_N", i)%>')">forceDelete</button>
		<%}%>
		</td>
	</tr>
	<%}%>
	</table>
	<% if(isLimitRecode) {%>
		<button onclick="callNextList('<%=limitBox.s("N")%>')">callNextList,<%=limitBox.s("N")%></button>
	<% }%>
</form>