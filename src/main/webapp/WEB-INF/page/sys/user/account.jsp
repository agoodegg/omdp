<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><HTML>
<HEAD>
<TITLE>修改个人登录帐号</TITLE>
<META content=数码印刷，数码打样设计，制作广告,装饰包装 name=Keywords>
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
<script language="javascript">
function CheckForm()
{ 
	
	if($.trim($('#userAccount').val())=="")
	{ 
		alert("新登录帐号不能为空！");
		return (false);
	}
	
	return true;
}


$(document).ready(function(){
	$("#modify_account_btn")[0].onclick=function(){
		if(CheckForm()){
			$("body").mask("信息提交中...");
			$.ajax({
			    url: '<%=path%>/sys/user/updateUserAccount.htm',
			    type: 'POST',
			    dataType: 'json',
			    timeout: 2000,
			    data:{'newUserAccount':$.trim($('#userAccount').val())},
			    error: function(){
			    	$("body").unmask();
			        alert('服务器异常或超时!登录修改失败!');
			    },
			    success: function(json){
			    	$("body").unmask();
			    	if(json){
			    		if(json.success){
				    		$('#oldAccount')[0].innerHTML=json.account;
				    		$('#userAccount')[0].value="";
				    		alert(json.msg);
				    	}
				    	else{
				    		alert(json.msg);
				    	}
			    	}
			    	else{
			    		alert('服务器异常或超时!登录修改失败!');
			    	}
			    }
			});
		}
	}
});
</script>
<style type="text/css">
<!--
td{font-size:11pt;}
-->
</style>
</HEAD>

<BODY>
<div id="main" class="mainPanel wd txt_left">
<div class="titleInfo">							
	<h3 class="left">修改登录帐号</h3><span class="addrtitle b_size"></span>					
</div>

<div name="status" class="content b_size"><form name="p_user" id="p_user" action="<%=path%>/sys/user/updateUserAccount.htm" method="post" >
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;table-layout:fixed;">
	<tr>
		<td align="right">原登录帐号：</td>
		<td id="oldAccount"><c:out value="${user.userAccount}"/><td>
	<tr>
		<td align="right">请输入新的登录帐号：</td>
		<td><INPUT type="text" name="userAccount" id="userAccount" value=""></td>
	</tr>
	<tr><td></td><td><INPUT type="button" id="modify_account_btn" value="修  改"></td></tr>
</table>
</div></form>
</div>
</div>
</body>
</html>
