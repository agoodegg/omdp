<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="o" uri="/WEB-INF/omdp.tld"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title>查看送货单</title>
<!--media=print 这个属性可以在打印时有效--> 
<style media=print> 
.Noprint{display:none;} 
.PageNext{page-break-after: always;} 
</style>


<style> 
.tdp 
{ 
border-bottom: 1 solid #000000; 
border-left: 1 solid #000000; 
border-right: 0 solid #ffffff; 
border-top: 0 solid #ffffff; 
} 
.tabp 
{ 
border-color: #000000 #000000 #000000 #000000; 
border-style: solid; 
border-top-width: 2px; 
border-right-width: 2px; 
border-bottom-width: 1px; 
border-left-width: 1px; 
} 
.NOPRINT { 
font-family: "宋体"; 
font-size: 9pt; 
}
</style>
</head>
<body topmargin="0" style="padding:0 0 0 5;" onLoad="window.focus()">
<span class="noprint" style="font-size=16px">
<OBJECT	classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" width="0" height="0" name="myprint"></OBJECT>
</span>


<form name="form1" action="" style="margin-top:9px;margin-left:2px;width:100%">
<center>
<table border="0" width="95%" style="border-collapse:collapse;" align="center" >
	<tr height="30pt">
		<td align="center" style="font-size:22px;font-family:黑体;font-weight:bold;" colspan="10">
			<font color='#000000'>深圳市欧美数码印务有限公司</font>
		</td>
	</tr>
	<tr>
		<td width="38%" align="left" style="font-size:14px; text-decoration:none;">客户名称:${deliver.custName}&nbsp;</td>
		<td align="left" style="font-size:14px;font-family:宋体" width="20%">No.<font color="#ff0000">${deliver.sendNo}</font></td>
		<td align="right" width="18%" style="font-size:14px;font-family:宋体" nowrap>制单时间:<fmt:formatDate  value="${now}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate ></td>
	</tr>
</table>
<div class="makebill">
<table align="center" width="100%" cellpadding="0" style="border-top-style:none;border-bottom-style:none;border-right-style:none;" cellspacing="0" class="tabp" height="305">

<tr valign="top"><td style="border-left-style:none;">
	<table width="100%"  border="1" align="center" style="border-collapse:collapse;border-top-style:none;border-left-style:none;border-right-style:none;" bordercolor="#000000" >
		<tr align="center" bgcolor="#00CCCC"  style="font-size:14px;">
			<td width="8%" style="font-size:14px;border-left-style:none;">编号</td>
			<td width="27%" style="font-size:14px;">品名</td>
			<td width="8%" align="center" style="font-size:14px;">数量</td>
			<td width="8%" align="center" style="font-size:14px;">单价</td>
			<td  width="8%" style="font-size:14px;">金额</td>
			<td style="font-size:14px;border-right-style:none;">备注</td>
		</tr>
		<c:forEach items="${details}" var="detail" varStatus="status">
		<tr align="left" valign="middle" style="font-size:14px;line-height:24px;">
			<td align="center" style="font-size:14px;border-left-style:none;">${status.count}</td>
			<!-- <td><o:dict type="BUSI_TYPE" value="${detail.itemType}"></o:dict></td> -->
			<td style="font-size:14px;" align="center">${detail.itemName}</td>
			<td style="font-size:14px;" align="center" nowrap><fmt:formatNumber value="${detail.amount}" pattern="0.##"/>${detail.unit}</td>
			<td style="font-size:14px;" align="right"><fmt:formatNumber value="${detail.unitPrice}" pattern="0.00"/></td>
			<td style="font-size:14px;" align="right"><fmt:formatNumber value="${detail.total}" pattern="0.00"/></td>
			<td style="font-size:14px;border-right-style:none;"></td>
		</tr>
		</c:forEach>
	</span>
	</table>	
</td>
<td rowspan="2" style="width:1px;" style="border:1px solid;border-top-style:none;border-bottom-style:none;border-right-style:none;">&nbsp;</td>
<td style="border:1 solid;vertical-align:middle;text-align:center;font-style:bold;font-weight:bold;"  rowspan="2" width="18">
①存根<img src="<%=path%>/pub/resources/images/up.png" style="width:16px" width="16">白<img src="<%=path%>/pub/resources/images/down.png" style="width:16px" width="16"><br/>②财务<img src="<%=path%>/pub/resources/images/up.png" style="width:16px" width="16">红<img src="<%=path%>/pub/resources/images/down.png" style="width:16px" width="16"><br/>③客户<img src="<%=path%>/pub/resources/images/up.png" style="width:16px" width="16">黄<img src="<%=path%>/pub/resources/images/down.png" style="width:16px" width="16">
</td>
</tr>

<tr height="20px"><td style="border-bottom-style:none;border-left-style:none;" valign="bottom">
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style="border-collapse:collapse; border-top-style:none; border-bottom-style:none; border-left-style:none;border-right-style:none" >
		<tr align="center" height="20px" style="">
			<td colspan="2" style="font-size:14px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;text-align:right;">
			取货方式:&nbsp;<o:dict type="deliverType" value="${order.deliverMethod}"></o:dict>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户地址:${order.deliverAddress}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系方式:${order.tel}
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td> 
		</tr>
		<tr align="center" height='20px'>
			<td width="8%" style="font-size:14px;border-left-style:none;">合计:</td>
			<td width="58%" style="font-size:14.5px;font-family:黑体;font-weight:bold;border-right-style:none;"><fmt:formatNumber value="${order.price}" pattern="0.00"/>元【${RMB}】</td>
		</tr>
	</table>
</td></tr>

</table>
</div>
</center>
<table border="0" style="margin:0 0 0 0;" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td width="35%" style="font-family:黑体;font-size:14px;">&nbsp;&nbsp;&nbsp;客户签收:</td>
<td width="35%" style="font-family:黑体;font-size:14px;"></td>
<td style="font-family:黑体;font-size:14px;">送货人签名:</td>
</tr>
</table>

<table border="0" style="margin:15 0 0 0;" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td align="center" style="font-size:23px;font-family:宋体;font-weight:bold;">数码印刷&nbsp;&nbsp;&nbsp;&nbsp;装订专家</td>
</tr>
<tr height="30">
<td align="center" style="font-size:14px;font-family:宋体;">24小时服务热线：0755-26551959、26551973、26551978、26551987</td>
</tr>
<tr>
<td align="center" style="font-size:14px;font-family:宋体;">Email：ypvv@163.net&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：深圳市科技园南区综合服务楼一楼(肯德基西侧)</td>
</tr>
</table>
</FORM>


<div class="Noprint" > 
<table width="100%"  height="50" border=0 class="noprint" align="center" style="background:#FFFFFF">
	<TR>
		<TD width="50%" align="center"><input type="hidden" name="countPrint" id="countPrint" value="0" />
			<input type="button" onClick="javascript:self.close();" value="关  闭">
		</TD>
	</tr>
	</table>
</div>
</body>

</html>
