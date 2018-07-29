<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/webpages/public/jstlTag.jsp"%>
<script> document.title = '任务';</script>

<!-- application  --> 
<link rel="stylesheet" type="text/css" href="${resourcePath}/style/task.css" >
<script>
	$("#myTask li a").click(function(){
		$("#myTask li a").css({"background-color":"white","color":"black"});
		$("#myTask li a").removeAttr("selected");
		$(this).css({"background-color":"rgb(21, 90, 21)","color":"white"});
		$(this).attr("selected","selected");
	});
	
	$("#myTask li a").hover(function(){
		$("#myTask li a[selected!=selected]").css({"background-color":"white","color":"black"});
		$(this).css({"background-color":"rgb(21, 90, 21)","color":"white"});
	});
</script>

<!-- page content  --> 
	<span name="title" class="thinBorder">任务</span>
	<div id="myTask">
		<ul name="category" data-bind="foreach: taskStatus">
			<li><a href="#" class="button" data-bind="click: show.bind($data,name), text: chsName" >-</a></li>
		</ul>
	</div>
	
	
	<div id="taskInfoList" >
		<ul>
		<li><p class="shadow" name="tip" data-bind="style: { display: tasks().length==0 ? 'block' : 'none' }" >没有符合条件的任务</p></li>
		<li>
			<ul name="clientInfo" data-bind="foreach:tasks">
					<li>
						<p>
							<input type="checkbox" data-bind="" />
							<span data-bind="text: category"></span>
						    <span data-bind="text: content"></span>
						    <span data-bind="text: '(' + $.datepicker.formatDate('yyyy-mm-dd', new Date(generateDate)) + ')' "></span>
						    <input type="hidden" data-bind="text: id" />
						</p>
					</li>
			</ul>
		</li>
		<li>
			<a class="button" name="btnLoadMore" href="#" data-bind="click: loadMoreTasks, style:{ display: tasks().length==0 || isLoadedAll()? 'none': 'block' } ">查看更多</a>
			<p name="noMoreTip" data-bind="style:{ display: tasks().length>0 && isLoadedAll()? 'block': 'none' }">没有更多任务</p>
		</li>
		</ul>
	</div>
	
	<div id="rightPanel" class="thinBorder">
		<div >
			<div >检索</div>
			<ul>
				<li>任务负责人<select data-bind="options: taskManagers, optionsCaption: '选择负责人', click: loadTaskManagers" ></select></li>
				<li>任务分类<select data-bind="options: taskCategorys, optionsCaption: '选择分类', click: loadTaskCategory"></select></li>
			</ul>
		</div>
	</div>
	

<script>
	$(function(){
		var taskPanelModel = new function(){
			var self = this;
			self.recordPerPage = 15;
			self.tasks = ko.observableArray([]);
			self.taskManagers = ko.observableArray([]);
			self.taskCategorys = ko.observableArray([]);
			self.taskStatus = ko.observableArray([]);
			self.isLoadedAll = ko.observable(false);
			self.onShowStatus = ko.observable();
			self.showTask = function(onShowStatus, fromIndex,toIndex,isIncrement){
				var params = "";
				params = params + "&taskStatus="+ (onShowStatus==undefined?"":onShowStatus)
					   + "&fromIndex=" + (fromIndex==undefined?0:fromIndex)
					   + "&toIndex="+ (toIndex==undefined?self.recordPerPage:toIndex)
				;
				params = params.substring(1);
				isIncrement = isIncrement==undefined?false: isIncrement;
				$.ajax({
					url:"${contextPath}/receiveTasks.do"
					,data: params
					,success:function(tasks){
						if(isIncrement)
							self.tasks().push(tasks);
						else
							self.tasks(tasks);
						self.isLoadedAll(tasks.length<self.recordPerPage?true:false);
					}
				})
			};
			self.show = function(onShowStatus,data){
				if(onShowStatus==self.onShowStatus()) return;
				self.isLoadedAll(false);
				self.tasks([]);
				self.onShowStatus(onShowStatus);
				self.showTask(onShowStatus);
			};
			self.loadMoreTasks = function(){
				var fromIndex = self.tasks().length;
				var  isIncrement = true;
				self.showTask(self.onShowStatus(),fromIndex,(fromIndex+self.recordPerPage),isIncrement);
			};
			
			self.loadTaskManagers = function(){
				$.ajax({
					url:"${contextPath}/retrieveTaskManagers.do"
					,success: function(taskManagers){
						self.taskManagers(taskManagers);
					}
				})
			};
			
			self.loadTaskCategory = function(){
				$.ajax({
					url:"${contextPath}/retrieveTaskCategorys.do"
					,success: function(taskCategorys){
						self.taskCategorys(taskCategorys);
					}
				})
			};
			self.loadDict = function(dicName){
				$.ajax({
					url:"${contextPath}/loadDict.do"
					,data:"dictName="+dicName
					,success: function(dicts){
						eval("self."+dicName)(dicts)
					}
				})
			};
			
			
		}
		
		try{
			ko.applyBindings(taskPanelModel, $("#myTask")[0]);
			ko.applyBindings(taskPanelModel, $("#taskInfoList")[0]);
			taskPanelModel.loadDict("taskStatus");
		}catch(e){}
	})

</script>

