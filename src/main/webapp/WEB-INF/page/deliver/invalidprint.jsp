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
<title>打印送货单</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
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
<table width="100%" height="100%">
	<tr><td align="center" valign="middle">
		<div class="warn_box">
		送货单金额为0，请查看对应工单信息及细项填写是否无误！
		</div>
		</td>
	</tr>
</table>
</body>
</html>