<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.web.Box"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Box box = Box.getThread();
	Table list = box.getTable("list");
%>
<html><head>
<script type="text/javascript">

function init(){
	var form = document.formMsg;
	if(form.MSG.value != '') {
		alert(form.MSG.value);
	}
}


function exe00120(){
	var form = document.form1;
	form.action = '/waf/exe00120';
	form.submit();
} 
</script>

<link rel="stylesheet" href="/css/code5.css">
<script src="/js/jquery-3.5.1.min.js"></script>


</head>
<body onload="init();">

<a href="/waf/exe00110">결제요청</a> <a href="/waf/exe00120">결제내역 조회</a>


<form name="form1" method="post">

<br>CRD_N
<br><input type="text" name="CRD_N">

<button onclick="exe00120()">검색</button>


</form>



<form name="formMsg"><input type="hidden" name = "MSG" value = "<%=box.s("MSG")%>"></form>


	<table border="1">
		<tr>
			<td>승인일시</td>
			<td>카드번호</td>
			<td>승인금액</td>
			<td>결과코드</td>
		</tr>

		<%
			for (int i = 0; i < list.size(); i++) {
			Box thisBox = list.getBox(i);
		%>
		<tr>
			<td><%=thisBox.s("ALNC_DTM")%></td>
			<td><%=thisBox.s("CRD_N")%></td>
			<td><%=thisBox.s("AMT")%></td>
			<td><%=thisBox.s("RET")%></td>
		</tr>
		<%
			}
		%>

	</table>

</body>
</head></html>

