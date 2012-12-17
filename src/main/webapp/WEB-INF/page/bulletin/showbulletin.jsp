<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>-公告-</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<style type="text/css">
<!--
body{
	scrollable:auto;
	font-size:15px;
}
.content{
	margin: 30 10 0 10;
	color: #477777;         /* 注释：颜色为#777777 */
	letter-spacing:2px;     /* 注释：字符左右间距1像素 */
	line-height:20px;       /* 注释：字符上下间距18像素 */
	font-size:12px;
	font-family: "Microsoft YaHei","微软雅黑","Microsoft JhengHei","华文细黑","STHeiti","MingLiu"
}
.footer{
	margin: 30 10 0 10;
	color: #477777;         /* 注释：颜色为#777777 */
	letter-spacing:2px;     /* 注释：字符左右间距1像素 */
	line-height:20px;       /* 注释：字符上下间距18像素 */
	font-size:12px;
	font-family: "Microsoft YaHei","微软雅黑","Microsoft JhengHei","华文细黑","STHeiti","MingLiu"
}
-->
</style>
</head>
<body background="<%=path%>/pub/resources/images/back.gif">
<h3>${bulletin.title}</h3>
<hr/>
<div class="content">${bulletin.content}</div>
<div class="footer">
<div style="width:100%;float:bottom;"><hr/></div>
<div style="float:right;">发布人:${bulletin.creator}&nbsp;&nbsp;&nbsp;
发布时间:<fmt:formatDate  value="${bulletin.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate ></div>
</div>
</body>
</html>