<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><HTML>
<HEAD>
<TITLE>修改个人登录密码</TITLE>
<META content=数码印刷，数码打样设计，制作广告,装饰包装 name=Keywords>
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
<script language="javascript">
function CheckForm(){
	aa=document.p_user.newPassword.value;
   if(document.p_user.oldPassword.value=="") { 
     alert("原始密码不能为空！");
     return (false);
   }
   if(document.p_user.newPassword.value=="") { 
     alert("新设密码不能为空！");
     return (false);
   }
   if(aa.length<6){ 
     alert("密码长度最少六位！");
     return (false);
   }
   var Letter="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   var bb=0;
   for(i=0;i<=aa.length;i++){
	   var CheckChar=aa.charAt(i);
	   CheckChar=CheckChar.toUpperCase();
	   if(Letter.indexOf(CheckChar)!=-1){
		   bb=bb+1;
          
		  }

   }
   if(bb<=1){
	   //alert(bb);
	   alert("密码过于简单至少包含一个字母！");
       return (false);
   }
   if(document.p_user.newPasswordAgain.value=="")
   { alert("重复密码不能为空！");
     return (false);
   }
   if(document.p_user.newPassword.value!=document.p_user.newPasswordAgain.value)
   { alert("密码输入不一致！");
     return (false);
   }
   
   return true;
}

$(document).ready(function(){
	$("#modify_pwd_btn")[0].onclick=function(){
		if(CheckForm()){
			$("body").mask("信息提交中...");
			$.ajax({
			    url: '<%=path%>/sys/user/updatePassword.htm',
			    type: 'POST',
			    dataType: 'json',
			    timeout: 2000,
			    data:{'oldPassword':document.p_user.oldPassword.value,'newPassword':document.p_user.newPassword.value,'newPasswordAgain':document.p_user.newPasswordAgain.value},
			    error: function(){
			    	$("body").unmask();
			        alert('服务器异常或超时!密码修改失败!');
			    },
			    success: function(json){
			    	$("body").unmask();
			    	if(json){
			    		if(json.success){
			    			alert(json.msg);
			    			$('#p_user')[0].reset();
			    		}
			    		else{
			    			alert(json.msg);
			    		}
			    	}
			    	else{
			    		alert('服务器异常或超时!密码修改失败!');
			    	}
			    }
			});
		}
	}
});

</script>
<style type="text/css">
<!--
td{	font-size: 11pt;
}
-->
</style>
</HEAD>

<BODY>
<div id="main" class="mainPanel wd txt_left">
<div class="titleInfo">							
	<h3 class="left">修改密码（带*的必填）</h3><span class="addrtitle b_size"></span>					
</div>

<div name="status" class="content b_size"><form name="p_user" id="p_user" action="<%=path%>/sys/user/updatePassword.htm" method="post" >
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;table-layout:fixed;">
	<tr>
       <TD align="right" width="20%">*原始密码：<input type="hidden" name="UID" value="1"></TD>
       <TD><INPUT type="password" name="oldPassword" value="" ></TD>
		</TR>
    <TR>
    	<TD align="right">*新的密码：</TD>
      <TD><INPUT type="password" name="newPassword" value="" ></TD>
	</TR>
    <TR>
    	  <TD align="right">*确认输入：</TD>
		<TD><input type="password" name="newPasswordAgain" value=""></TD>
   	</TR>
   	<tr><td></td><td><INPUT type="button" value="修改密码" id="modify_pwd_btn"></td></tr>
</table>
</div></form>
</div>
</div>
</body>
</html>
