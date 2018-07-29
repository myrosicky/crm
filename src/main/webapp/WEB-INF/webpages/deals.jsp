<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@include file="/WEB-INF/webpages/public/jstlTag.jsp"%>
<script> document.title = '任务';</script>

<s:form action="profile.action" target="_blank" >
	<s:textfield name="content" label="enter content" />
    <s:textfield name="author" label="enter author" />
    <s:submit value="Submit" />
</s:form>


