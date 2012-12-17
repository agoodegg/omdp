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
<title>OMAX|深圳欧美数码印务有限公司管理系统V1.0</title>
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
<script language="JavaScript">
var testWin = window.open("",'blankWin','left=0,top=0,width=0,height=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,location=no');
if(testWin){
	testWin.close()
	if(window.name=='omaxsys'){
		
		window.open("<%=basePath%>index.htm",'omaxsys', 'left=0,top=0,width='+ (screen.availWidth - 12) +',height='+ (screen.availHeight-66) +',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes,location=no')
		
		<c:forEach items="${bulletinList}" var="bulletin" varStatus="status">
		window.open("<%=path%>/bulletin/bulletinManage/showBulletin.htm?id=${bulletin.id}", 'newwindow_${status.count}', 'height=540,width=430,top=${status.count*20},left=${status.count*26},toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
		</c:forEach>
	}
	else{
		window.open("<%=basePath%>index.htm",'omaxsys', 'left=0,top=0,width='+ (screen.availWidth - 12) +',height='+ (screen.availHeight-66) +',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes,location=no')

		<c:forEach items="${bulletinList}" var="bulletin" varStatus="status">
		window.open("<%=path%>/bulletin/bulletinManage/showBulletin.htm?id=${bulletin.id}", 'newwindow_${status.count}', 'height=540,width=430,top=${status.count*20},left=${status.count*26},toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
		</c:forEach>

		window.opener = null;
		window.open('', '_self', ''); 
		window.close();
	}
}
else{
	alert("浏览器阻止当前站点弹出窗口！请将当前站点加入允许列表。");
	window.location.href="<%=basePath%>login.jsp#allowPopup";
}
</script>
</head>
<body bgcolor=#3366cc background="<%=path%>/pub/resources/images/back.gif">
跳转中...
</body>
</html>