<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/webpages/public/jstlTag.jsp"%>

<div id="partyInfo">
<form name="saveForm" method="POST" action="${contextPath }/party!upload.action">
<li><label>name</label><input name="name" data-bind="text: name" /></li>
<li><label>category</label><input name="category" data-bind="text: category" /></li>
<li><label>remark</label><input name="remark" data-bind="text: remark" /></li>
<li><label>file:</label><input name="upload" type="file" /></li>
<a href="#" data-bind="click: save" >保存</a>
<a href="#" data-bind="click: upload" >upload</a>
</form>
</div>
<script>
	$(function(){
		
		var piModel = new function(){
			var partyObj = {
					var name = ko.observable("");
					var category = ko.observable("");
					var remark = ko.observable("");	
			};
			var self = this;
			
			self.save = function(){
				$.ajax({
					url:"${contextPath}/savePartyInfo.do"
					,data: $("[name=saveForm]").serialize()
					,success: function(){
						alert("success");
					}
				});
				
			};
			self.upload = function(){
				$.ajax({
					url:"${contextPath}/file!upload.action"
					,data: {serviceName:"partyOpsService",parser:"excel",}
					,type: "json"
					,success: function(){
						alert("success");
					}
				});
				
			};
			
		}
		
		try{
			ko.applyBindings(piModel,$("#partyInfo")[0]);
		}catch(e){}
	})

	
</script>
