<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>关于omdp v1.0</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/ext-core.js"></script>
<style type="text/css">
<!--
.warn_box{
	padding:20px;
	margin-top:20px;
	font-size:14px;
	color:#EE0000;
	background-color: ;
	border:double;
	border-color:#DDDD00;
	vertical-align:middle;
	text-align:center;
	line-height:25px;
}
.succ_box{
	padding:20px;
	margin-top:20px;
	font-size:14px;
	background-color: ;
	border:double;
	border-color:#00DD00;
	vertical-align:middle;
	text-align:center;
	line-height:25px;
}
-->
</style>
</head>
<body bgcolor=#3366cc background="<%=path%>/pub/resources/images/back.gif">
<h2>关于OMDP印务管理系统</h2>

</body>
</html>