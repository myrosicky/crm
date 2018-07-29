<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@include file="/WEB-INF/webpages/public/header.jsp"%>

 <s:form action="profile.action" >
	 <s:property value="content"/>,
	  -------<s:property value="author" /><br/>
	  </s:form>
	  
<%@include file="/WEB-INF/webpages/public/tail.jsp"%>
