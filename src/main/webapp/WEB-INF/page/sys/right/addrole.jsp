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
<TITLE>添加角色</TITLE>
<META content=数码印刷，数码打样设计，制作广告,装饰包装 name=Keywords>
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/pub/resources/js/checkInput.js"></script>
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
<script language="javascript">
$(document).ready(function(){
	$("#modify_account_btn")[0].onclick=function(){
		if(Validator.Validate($('#edituserForm')[0], 2)){
			$("body").mask("信息提交中...");
			$.ajax({
			    url: '<%=path%>/sys/roleManage/saveNew.htm',
			    type: 'POST',
			    dataType: 'json',
			    timeout: 2000,
			    data:$('#edituserForm').serialize(),
			    error: function(){
			    	$("body").unmask();
			        alert('服务器异常或超时!新增失败!');
			    },
			    success: function(json){
			    	$("body").unmask();
			    	if(json){
			    		if(json.success){
			    			window.opener.query();
				    		alert(json.msg);
				    		window.close();
				    	}
				    	else{
				    		alert(json.msg);
				    	}
			    	}
			    	else{
			    		alert('服务器异常或超时!新增失败!');
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
	<h3 class="left">添加角色</h3><span class="addrtitle b_size">(添加新的角色)</span>					
</div>

<div name="status" class="content b_size"><form name="edituserForm" id="edituserForm" action="<%=path%>/sys/roleManage/saveNew.htm" method="post" >
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;">
	<tr>
		<td align="right">角色名称：</td>
		<td>
		<input name="roleName" id="roleName" type="text" dataType="LimitB" min="1" max="40" msg="角色名称(非空,最大40位)"/>
		</td>
	</tr>
	<tr>
		<td align="right">角色描述：</td>
		<td id="oldAccount"><textarea rows="4" cols="30" name="roleDesc" id="roleDesc" dataType="LimitB" max="40" msg="角色描述(最大80位)"></textarea> </td>
	</tr>
	<tr><td></td><td><INPUT type="button" id="modify_account_btn" value="新  增"></td></tr>
</table>
</div></form>
</div>
</div>
</body>
</html>
