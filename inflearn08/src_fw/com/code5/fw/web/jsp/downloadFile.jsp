<%@page import="java.net.URLEncoder"%><%@page import="java.net.URL"%><%@page
	import="java.io.OutputStream"%><%@page
	import="com.code5.fw.data.UploadFileB"%><%@page
	import="com.code5.fw.data.DateTime"%><%@page
	import="com.code5.fw.db.SqlRunner"%><%@ page
	contentType="application/x-msdownload;"%><%@page
	import="com.code5.fw.security.CryptFile"%><%@page
	import="com.code5.fw.data.Box"%><%@page
	import="com.code5.fw.web.BoxContext"%><%
	OutputStream outputStream = response.getOutputStream();

try {

	Box box = BoxContext.getThread();
	UploadFileB file = box.getUploadFileB("file");
	String fileUrl = file.getFileUrl();
	String fileName = file.getFileName();
	String contentType = file.getContentType();
	

	long timeOut = 1000 * 60 * 10;

	box.put("ST_DTM", DateTime.getThisDTM());

	fileName = new String(fileName.getBytes(),"ISO-8859-1");
	response.setContentType(contentType);
	response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";");
	
	CryptFile.getCryptFile().decrypt(fileUrl, outputStream, timeOut);

} catch (Exception ex) {
	ex.printStackTrace();
} finally {
	if (outputStream != null) {
		outputStream.close();
	}
}
%>
