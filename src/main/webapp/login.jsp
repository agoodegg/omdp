<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OMAX|深圳欧美数码印务有限公司管理系统V1.0</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<style type="text/css">
.login_body{
background:#026aa9;
text-align:center;
font-family: "Microsoft YaHei" ! important;
color:#ffffff;
}
.leftLabel{
	text-align:right;
}
.rightField{
	text-align:left;
}

input[type="text"]{
border: 1px solid; border-color: #CCC #EEE #EEE #CCC;
background: #F5F5F5;
}
input[type="password"]{
border: 1px solid; border-color: #CCC #EEE #EEE #CCC;
color: #000; background: #F5F5F5;
}
input[type="button"]{
border: 1px solid; border-color: #EEE #CCC #CCC #EEE;
color: #000; font-weight: bold; background: #F5F5F5;
}
input[type="reset"]{
border: 1px solid; border-color: #EEE #CCC #CCC #EEE;
color: #666; background: #F5F5F5;
}
.login_btn{
	cursor:hand;
	filter:alpha(opacity=50); /* IE */
    -moz-opacity:0.5; /* Moz + FF */
    opacity:0.5; /* 支持CSS3的浏览器（FF 1.5也支持）*/
}
.login_btn:hover{
	cursor:hand;
	 filter:alpha(opacity=100); /* IE */
     -moz-opacity:1; /* Moz + FF */
     opacity:1; /* 支持CSS3的浏览器（FF 1.5也支持）*/
}
.footer_info{
	text-align:center;
	color:#000000;
	font-family: "Microsoft YaHei" ! important;
	font-size:8.5px;
}

.black_overlay{  
    display: none;  
    position: absolute;  
    top: 0%;  
    left: 0%;  
    width: 100%;  
    height: 100%;  
    background-color: black;  
    z-index:1991;  
    -moz-opacity: 0.8;  
    opacity:.80;  
    filter: alpha(opacity=80);  
}  
.white_content {  
    display: none;  
    position: absolute;  
    top: 25%;  
    left: 25%;  
    width: 50%;  
    height: 50%;  
    padding: 16px;  
    border: 16px solid orange;  
    background-color: white;  
    z-index:1992;  
    overflow: auto;  
}

</style>
<script type="text/javascript">
//避免在框架页面中打开
var windowsArray=[];
if(window.top==window&&!(window.parentWindow)){
	
}
else{
	 var topWindow = window;
	 while(!!(topWindow.opener)){
	 	windowsArray.push(topWindow);
	 	topWindow=topWindow.opener;
	 }
	 for(var i=0;i<windowsArray.length;i++){
	 	windowsArray[i].close();
	 }
	 if(!!(topWindow.top)){
	 	topWindow.top.location.replace('<%=path%>/login.jsp');
	 }
	 else{
	 	topWindow.location.replace('<%=path%>/login.jsp');
	 }
}
</script>

<script type="text/javascript">
function login(){
	$("#userAccount")[0].value = $.trim($("#userAccount").val());
	$("#userPwd")[0].value = $.trim($("#userPwd").val());
	$("#vcode")[0].value = $.trim($("#vcode").val());
	
	var tipMsg = [];
	if($("#userAccount").val()==''){tipMsg.push("用户名不能为空！\n");}
	if($("#userPwd").val()==''){tipMsg.push("用户密码不能为空！\n");}
	if($("#vcode").val()==''){tipMsg.push("验证码不能为空！\n");}
	if(tipMsg.length>0){
		alert(tipMsg.join(""));
		return false;
	}
	//document.getElementById("loginForm").submit();
}

$(document).ready(function(){
	$('#userAccount')[0].focus();
});
</script>
</head>
<body class="login_body">
<div id="fade" class="black_overlay"></div> 
<!--[if lt IE 7]>
  <div style='border: 1px solid #F7941D; background: #FEEFDA; text-align: center; clear: both; height: 75px; position: relative;'>
    <div style='position: absolute; right: 3px; top: 3px; font-family: courier new; font-weight: bold;'>
    <a href='#' onclick='javascript:this.parentNode.parentNode.style.display="none"; return false;'>
    <img src='<%=path %>/pub/resources/images/ie6no/ie6nomore-cornerx.jpg' style='border: none;' alt='Close this notice'/></a></div>
    <div style='width: 640px; margin: 0 auto; text-align: left; padding: 0; overflow: hidden; color: black;'>
      <div style='width: 75px; float: left;'><img src='<%=path %>/pub/resources/images/ie6no/ie6nomore-warning.jpg' alt='Warning!'/></div>
      <div style='width: 275px; float: left; font-family: Arial, sans-serif;'>
        <div style='font-size: 14px; font-weight: bold; margin-top: 12px;'>当前浏览器版本过低！本系统某些功能无法正常使用</div>
        <div style='font-size: 12px; margin-top: 6px; line-height: 12px;'>还在用IE6和基于IE6内核的浏览器吗？ 亲！ Out啦。快升级获得更好的浏览体验！</div>
      </div>
      <div style='width: 75px; float: left;'><a href='http://www.firefox.com' target='_blank'>
      <img src='<%=path %>/pub/resources/images/ie6no/ie6nomore-firefox.jpg' style='border: none;' alt='Get Firefox 3.5'/></a></div>
      <div style='width: 75px; float: left;'><a href='http://www.browserforthebetter.com/download.html' target='_blank'>
      <img src='<%=path %>/pub/resources/images/ie6no/ie6nomore-ie8.jpg' style='border: none;' alt='Get Internet Explorer 8'/></a></div>
      <div style='width: 73px; float: left;'><a href='http://www.apple.com/safari/download/' target='_blank'>
      <img src='<%=path %>/pub/resources/images/ie6no/ie6nomore-safari.jpg' style='border: none;' alt='Get Safari 4'/></a></div>
      <div style='float: left;'><a href='http://www.google.com/chrome' target='_blank'>
      <img src='<%=path %>/pub/resources/images/ie6no/ie6nomore-chrome.jpg' style='border: none;' alt='Get Google Chrome'/></a></div>
    </div>
  </div>  
  <![endif]-->
  
<br/>
<br/>
<br/>
<br/><a href="#allowPopup" onclick="alert("test")"></a>
<form method="POST" action="<%=path%>/j_spring_security_check" method="POST"" id="loginForm">
<table width="750" height="468" border="0" style="margin:auto;" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="3">
			<img src="<%=path %>/pub/resources/images/login/2_03.gif" width="750" height="156" alt=""></td>
	</tr>
	<tr>
		<td width="306" height="126">
			<img src="<%=path %>/pub/resources/images/login/2_05.gif" width="306" height="126" alt=""></td>
		<td  width="318" height="126" style="background:url(<%=path %>/pub/resources/images/login/2_06.gif)">
			<table style="width:100%;height:100%;" border="0">
			<tr><td width="20%" class="leftLabel">用户名:</td>
			<td class="rightField"><input type="text" id="userAccount" name="j_username" /></td></tr>
			
			<tr><td width="20%" class="leftLabel">密&nbsp;&nbsp;&nbsp;码:</td>
			<td class="rightField"><input id="userPwd" name="j_password" type="password" /></td></tr>
			
			<tr><td width="20%" class="leftLabel">验证码:</td>
			<td class="rightField"><input id="vcode" type="text" name="j_code" style="width:50px;"/>
			<img style="vertical-align:middle;" id="vcodeImg" src="<%=path %>/vcode.jsp" >
			<input type="image" class="login_btn" style="vertical-align:middle;" onclick="javascript:login();" src="<%=path %>/pub/resources/images/login/l_btn.gif" /></td></tr>
			</table>
		</td>
		<td width="127" height="126" style="background:url(<%=path %>/pub/resources/images/login/2_07.gif)">
		
		</td>
	</tr>
	<tr>
		<td colspan="3" valign="top" width="750" height="186" style="background:url(<%=path %>/pub/resources/images/login/2_08.gif)">
		<br/><br/><br/>
		<p class="footer_info">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;©2011 OMAX|深圳欧美数码印务有限公司. All rights reserved.  当前版本v1.0</p>
		</td>
	</tr>
</table></form>
  
</body>
</html>
