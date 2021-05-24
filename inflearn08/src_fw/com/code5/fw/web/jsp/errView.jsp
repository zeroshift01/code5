<%@page import="com.code5.fw.web.LoginException"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	response.setHeader("cache-control", "no-cache");
	response.setHeader("expires", "0");
	response.setHeader("pragma", "no-cache");

	Box box = BoxContext.getThread();

	Exception ex = (Exception) box.get(Box.KEY_EXCEPTION);

	String msg = "�븣�닔 �뾾�뒗 �삤瑜섍� 諛쒖깮�뻽�뒿�땲�떎.";

	if (ex instanceof LoginException) {
		msg = "濡쒓렇�씤�씠 �븘�슂�빀�땲�떎.";
	}
%>
<pre>
�궗�슜�옄�뿉寃� 蹂댁뿬二쇰뒗 �젙蹂�
<%=msg%>


媛쒕컻�옄瑜� �쐞�븳 �뵒踰꾧렇 �젙蹂�
<%=ex.getMessage()%>
</pre>
