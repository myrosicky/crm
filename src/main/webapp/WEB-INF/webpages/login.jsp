<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/webpages/public/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}/style/login.css" >
<div class="fullScream" name="fullScream"   >
	<div id="login" class="centerPosition"   >
	<img src="http://2d.zol-img.com.cn/product/56_450x337/13/cem7etE2kLTGo.jpg" >
		<table>
			<tbody>
				<tr><td colSpan=2 >
					<img/>
				</td></tr>
				
				<tr><td colSpan=2 >
					<p name="loginErrMsg" data-bind="text: loginErrMsg"></p>
					<input name="username" placeholder="用户名"  />
				</td></tr>
				
				<tr><td colSpan=2 >
					<input name="password" type="password"  placeholder="密码" />
				</td></tr>
				
				<tr><td>
					<img/>
				</td><td>
					<input value="登陆" type="button" data-bind="click: login"  />
					<input value="注册" type="button" data-bind="click: register"  />
				</td></tr>
			</tbody>
		</table>
	</div>
</div>


<script type="text/javascript" >
	$(function(){
		var loginModel = new function(){
			 var self = this;
		        self.username= ko.observable("");
		        self.password= ko.observable("");
		        self.loginErrMsg= ko.observable("");
		        
		        self.login = function(){
		        	self.username($("#login :input[name=username]").val());
		          	self.password($("#login :input[name=password]").val());
					$.ajax({
						url:"${contextPath}/login.do",
						type:"GET",
						data: {"username":self.username(),"password":self.password()},
						dataType: "json",
						cache: false,
						success: function(result){
							if(!result.success){
								alert(0);
								self.loginErrMsg(result.errMsg);
								return true;
							}
							self.loginErrMsg("");
							window.location.href = "${contextPath}/main.htm?"+Math.random();
						},fail: function(){
						},complete:function(){
						}
					});
				};
				
				self.register = function(){
		        	self.username($("#login :input[name=username]").val());
		          	self.password($("#login :input[name=password]").val());
					$.ajax({
						url:"${contextPath}/register.do",
						type:"POST",
						data: "username="+self.username()+ "&password="+self.password(),
						cache: false,
						success: function(result){
							if(!result.success){
								self.loginErrMsg(result.errMsg);
								return true;
							}
							self.loginErrMsg("");
							self.login();
						}
					});
				};
		}
		
		ko.applyBindings(loginModel,$("#login")[0]);
	});
</script>
<%@include file="/WEB-INF/webpages/public/tail.jsp"%>