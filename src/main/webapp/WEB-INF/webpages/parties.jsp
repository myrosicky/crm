<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/webpages/public/jstlTag.jsp"%>
<script> document.title = '客户信息';</script>

<!-- application  --> 
<link rel="stylesheet" type="text/css" href="${resourcePath}/style/parties.css" >
<script>
	$("#myClient li a").click(function(){
		$("#myClient li a").css({"background-color":"white","color":"black"});
		$(this).css({"background-color":"rgb(21, 90, 21)","color":"white"});
	});
</script>

<!-- page content  --> 
	<span name="title" class="thinBorder">我的客户</span>
	<div id="myClient">
		<ul name="category">
			<li><a href="#" class="button" data-bind="click: showAllClient" >全部客户</a></li>
			<li><a href="#" class="button" data-bind="click: showTodayMissedClient" >所有未联系客户<i class="downTriangle">&#xe630;</i></a></li>
			<li><a href="#" class="button" >今天联系客户<i class="downTriangle">&#xe630;</i></a></li>
		</ul>
		<span><input id="searchBox" placeholder="输入关键字 搜索内容" class="hiddenBorder" /><i class="magnifier">&#xe652;</i><i class="downTriangle">&#xe630;</i></span>
	</div>
	
	
	<div id="clientInfoList" >
		<p class="shadow" data-bind="style: { display: parties().length==0 ? 'block' : 'none' }" >没有符合条件的客户信息</p>
		<ul name="buttonGroup" data-bind="visible: parties().length>0">
			<li><a href="#" name="selectAll" class="button" data-bind="click: function(){isSelectAll(!isSelectAll());} " >全选</a></li>
			<li><a href="#" data-bind="click: function(){isSelectAll(!isSelectAll());} " name="delete" class="button">删除</a></li>
		</ul>
		<ul name="clientInfo" data-bind="template: {name:'parties-template', foreach:parties}">
		</ul>
	</div>
	
	<div id="rightPanel" class="thinBorder">
		<div >
			<div >可以方便地导入/导出客户信息</div>
			<div>导入 或 导出 客户信息 <a data-bind="click: downloadForm" ></a>
				<ul data-bind="template: {name:'p-template', foreach: parties} " >
				</ul>
			</div>
 			<span>查看导出历史记录</span>
		</div>
		<div >
			<div>客户分类  </div>
			<ul data-bind="template: {name:'cc-template', foreach: clientCategory} " >
			</ul>
			
		</div>
		<div>
			<div>标签列表</div>
			<ul data-bind="template: {name:'labels-template', foreach: labels} " >
			</ul>
		</div>
	</div>
	
<script type="text/html"  id="parties-template">
	<li>
		<h3>
			<input type="checkbox" data-bind="attr: {checked: $parent.isSelectAll}" />
			<span data-bind="text: category"></span><span data-bind="text: name"></span>
		</h3>
		<p>
		    <label>创建时间：</label><span data-bind="text: $.datepicker.formatDate('yy-mm-dd', new Date(manageDate))"></span><i class="verticalString">&#xe658;</i>
		    <label>创建人：</label><span data-bind="text: manager.username"></span><i class="verticalString">&#xe658;</i>
		    <label>最后联系时间：</label><span data-bind="text: $.datepicker.formatDate('yy-mm-dd', new Date(manageDate))"></span><i class="verticalString">&#xe658;</i>
		    <label>最后联系人：</label><span data-bind="text: manager.username"></span>
		</p>
	</li>
</script>

<script type="text/html"  id="add-template">
<div class="">
	<form name="uploadForm" action="${contextPath}/file!upload.action" enctype="multipart/form-data" method="POST" >
		<li><label>name:</label><input name="partyName" type="text" /></li>
		<li><label>file:</label><input name="upload" type="file" /></li>
	</form>
</div>
</script>

<script>
	$(function(){
		
		var clientPanelModel = new function(){
			var self = this;
			self.parties = ko.observableArray([]);
			self.isSelectAll = ko.observable(false);
			self.showClient = function(params){
				params = params==undefined?"":params;
				$.ajax({
					url:"${contextPath}/receiveParties.do"
					,data: params
					,success:function(parties){
						self.parties(parties);
					}
				})
			};
			self.showAllClient = function(){
				self.showClient();
			};	
			self.showTodayMissedClient = function(){
				var params = "&missed=true";
				params = params.substring(1);
				self.showClient(params);
			};	
			self.showPartyAddPanel = function(){
				var flag = window.showModalDialog("${contextPath}/parties_m.htm",null ,"center=1&dialogHeight=300px&dialogWidth=300px");
				self.showClient();
			};
			self.downloadForm = function(){
				window.open("${contextPath}/party!downloadPartyForm.action","newWin");
			};
		}
		
		try{
			ko.applyBindings(clientPanelModel, $("#myClient")[0]);
			ko.applyBindings(clientPanelModel, $("#clientInfoList")[0]);
		}catch(e){}
	})

</script>


