<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@include file="/WEB-INF/webpages/public/jstlTag.jsp"%>
<form name="uploadForm" method="POST" action="${contextPath}/file!upload.action" enctype="multipart/form-data" >
	<input type="file" name="upload" /> 
	<input type="submit" value="submit" /> 
</form>

<form name="downloadForm" method="GET" action="${contextPath}/file!download.action">
	<input name="fileid" type="hidden" value="1" />
	<input type="submit" value="submit" /> 
</form>

<%@include file="/WEB-INF/webpages/public/tail.jsp"%>