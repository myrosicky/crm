<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/webpages/public/header.jsp"%>
<script>document.title = '主面板';</script>

<!-- application  --> 
<link rel="stylesheet" type="text/css" href="${resourcePath}/style/main.css" >
<script type="text/javascript">
	$(document).ready(function(){
		$("div[name=taskInfo]").position({
		my: "top"
		,at: "bottom"
		,of: "[name=btnCreateTask]"
		});
	
		$("div[name=taskInfo] :input[name=deadLine]").datetimepicker({
			timeFormat: 'HH:mm:ss'
		});
		
	});
</script>


<div id="wholePage"   class="fullScream">

	<%-- 左边竖菜单 --%>
	<div id="menu" collapse=false >
		<span style="background-color: rgb(131, 131, 224); width: 100%; display: inline-block; height: 70px; line-height: 70px; ">LL CRM</span>
		<ul class="verticalMenu" ">
			<li><a href="#" data-bind="click: show.bind($data,'dashboard')"><i class="icon-user"></i>工作台</a></li>
			<li><a href="#" data-bind="click: show.bind($data,'parties')"><i class="icon-envelope-alt"></i>客户信息</a></li>
			<li><a href="#" data-bind="click: show.bind($data,'task')"><i class="icon-cog"></i>任务</a></li>
			<li><a href="#" data-bind="click: show.bind($data,'deals')"><i class="icon-signout"></i>销售机会</a></li>
		</ul>
		
		<a href="#" onclick="toggleVerMenu()"><img id="colExp" src="http://www.iconpng.com/png/ios7-premium-fill-2/arrowhead3.png"  style="width: 50px;height: 50px;margin-top: 100%;"></a>
	</div>
	
	<%-- 顶端工具栏 --%>
	<div id="topTool" class="thinBorder" >
		<div style=" width: 40%; " class="thinBorder">		
			<i class="magnifier">&#xe652;</i>
			<input id="searchBox" placeholder="输入关键字 搜索内容" style="min-height: 90%; margin-top: 0px; width: 80%;" class="hiddenBorder" />
		</div>		
		<div ><a href="#" class="button" onclick="" style="background-color: rgb(58, 58, 156);" >呼叫</a></div>
		<div ><a href="#" name="btnCreateTask" class="button" data-bind="click: function(data,event){$('div[name=taskInfo]').show();}" style="background-color: rgb(223, 25, 33);" >创建任务</a></div>
		<div ><a href="#" class="button" data-bind="click: show.bind($data,'parties_m')" style="background-color: rgb(8, 146, 41);" >创建客户</a></div>
		<div style=" margin-right:25px; float: right; "><img src="http://www.iconpng.com/png/hand-drawn-wedding/bell53.png" style=" height: 40px; margin-top: 40%;"> </div>
	</div>
		<%-- 任务div --%>
		<div name="taskInfo" >
			<ul>
			<li><label>内容</label><input name="content" /></li>
			<li><label>类别</label><select name="category" data-bind="options:taskCategory , optionsCaption: '请选择', click: function(data,event){if(taskCategory().length>0) loadTaskCategory();}"  /></select></li>
			<li><label>完成时间</label><input name="deadLine" /></li>
			<li>
			<a href="#" name="btnSave" class="button" data-bind="click: saveTask"   >保存</a>
			<a href="#" name="btnCancel" data-bind="click: function(){$('div[name=taskInfo]').hide();}"  >取消</a>
			</li>
			</ul>
		</div>
		
	<%-- 内容面板 --%>
	<div id="content" style="height: 990px;"  data-bind=" html: contentHtml" >
	</div>
</div>


<script type="text/javascript" >

	function toggleVerMenu(){
		var isCollapse = eval($("#menu").attr("collapse"));
		var menuWidth = isCollapse?15:5;
		$("#colExp").rotate({animateTo: (isCollapse?0:180)});
		$("#menu").animate({width:menuWidth+"%"});
		$("#topTool").animate({width:(100-menuWidth)+"%"});
		$("#content").animate({width:(100-menuWidth)+"%"});
		$("#menu").attr("collapse",!isCollapse);
	}


	$(function(){
	
            	
		var availableTags = [
      "ActionScript",
      "AppleScript",
      "Asp",
      "BASIC",
      "C",
      "C++",
      "Clojure",
      "COBOL",
      "ColdFusion",
      "Erlang",
      "Fortran",
      "Groovy",
      "Haskell",
      "Java",
      "JavaScript",
      "Lisp",
      "Perl",
      "PHP",
      "Python",
      "Ruby",
      "Scala",
      "Scheme"
    ];
	function split( val ) {
      return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
	
	$( "#searchBox" )
      // don't navigate away from the field on tab when selecting an item
      .bind( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).autocomplete( "instance" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
       	minLength: 0,
        source: function( request, response ) {
          	$.getJSON( "${contextPath}/receivePopwords.do", {
            	term: extractLast( request.term )
          	}, response );
        },
        focus: function() {
          return false;
        },
        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          terms.push( ui.item.value );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( ", " );
          return false;
        }
      });
      
      
		var mainModel = new function(){
			var self = this;
			self.onShow = ko.observable("");
			self.contentHtml = ko.observable("");
			self.taskCategory = ko.observableArray([]);
			self.show = function(name, $data){
				if(self.onShow()==name) return;
				self.onShow(name);
					$.ajax({
					url:"${contextPath}/"+name+".htm"
					,type:"POST"
					,success:function(text){
						self.contentHtml(text);
					}
					,error:function(){
						alert('error');
					}
					});
			};
			self.saveTask = function(){
				$.ajax({
					url:'${contextPath}/saveTask.do'
					,data: $("div[name=taskInfo] :input").serialize()
					,success: function(){
						alert('success');
					}	
				});
			};
			
			self.loadTaskCategory = function(){
				$.ajax({
					url: '${contextPath}/loadDict.do'
					,data: "dict=taskCategory"
					,success: function(taskCategory){
						self.taskCategory(taskCategory);
					}
				});
			};
		}
		
		try{
			ko.applyBindings(mainModel,$("#wholePage")[0]);
		}catch(e){}
		$("#menu").find("a:first").click();
	});

</script>


<%@include file="/WEB-INF/webpages/public/tail.jsp"%>