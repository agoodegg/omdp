<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="o" uri="/WEB-INF/omdp.tld"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><html>
<HEAD>
	<title>打印OT单</title>
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
</HEAD>
<body topmargin="0" onLoad="window.focus()">
<span class="noprint" style="font-size=16px">
<OBJECT	classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" width="0" height="0" name="myprint"></OBJECT>
</span>


<form name="form1" action="" style="margin-top:9px;margin-left:2px;width:100%">
<center>
<table border="0" width="95%" style="border-collapse:collapse;" align="center" >
	<tr height="30pt">
		<td align="center" style="font-size:16px;font-weight:bold;font-family:宋体" colspan="10">
		<font color='#0000FF'>欧美数码印务<c:choose><c:when test="${order.dispatchOther=='1'}"></c:when><c:otherwise>生产工单</c:otherwise></c:choose></font>
		</td>
	</tr>
	<tr>
		<td align="left" style="font-size:14px;font-family:宋体" width="15%">No.<font color="#ff0000">${order.ordernum}</font></td>
		<td width="55%"style="font-size:14px;">&nbsp;</td>
		
		<c:choose><c:when test="${order.dispatchOther=='1'}">
		<td align="right" width="18%" style="font-size:13px;text-decoration:none;">供货商:${order.dispatchName}&nbsp;</td>
		</c:when><c:otherwise><td align="right" width="18%" style="font-size:14px;font-family:宋体" nowrap>
		制单时间:<fmt:formatDate  value="${now}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate ></td></c:otherwise></c:choose>
	</tr>
</table>
<div class="makebill">
<table align="center" width="99%" cellpadding="2" cellspacing="0" style="margin:0 0 0 6;" class="tabp" height="419">
<tr valign="top"><td>
	<table border="1" bordercolor="#000000" width="100%" align="center" style="border-collapse:collapse;border-bottom-style:none;">
		<tr>
			<td width="11%" style="font-size:14px;">文件路径</td><td style="font-size:14px;">${order.filepos}</td>
			<td width="11%" style="font-size:14px;">软件格式</td><td width="17%" style="font-size:14px;">${order.software}</td>
			<td width="11%" align="center" style="font-size:14px;">交货时间</td><td width="20%" align="left" style="font-size:14px;"><fmt:formatDate  value="${order.deliverTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate ></td>
		</tr>
		<tr>
			<td style="border-bottom-style:none " style="font-size:14px;">&nbsp;印刷要求</td>
			<td colspan="5" style="border-bottom-style:none;font-size:14px;">${order.printMemo}</td>
		</tr>
	</table>
	<table width="100%" border="1" align="center" style="border-collapse:collapse;border-top-style:none" bordercolor="#000000" >
		<tr align="center" bgcolor="#00CCCC">
			<td width="4%" style="font-size:14px;">No.</td>
			<td width="10%" style="font-size:14px;" align="center">业务类型</td>
			<td width="27%" style="font-size:14px;" align="center">品名</td>
			<td width="8%" align="center" style="font-size:14px;">数量</td>
			<td style="font-size:14px;">制作要求</td>
		</tr>
		<c:forEach items="${details}" var="detail" varStatus="status">
		<tr align="left" valign="top" style="line-height:28px;">
			<td align="center" style="font-size:14px;">${status.count}</td>
			<td style="font-size:14px;"  align="center"><o:dict type="BUSI_TYPE" value="${detail.itemType}"></o:dict></td>
			<td style="font-size:14px;"  align="center">${detail.itemName}</td>
			<td align="center" nowrap style="font-size:14px;"><fmt:formatNumber value="${detail.amount}" pattern="0.##"/>${detail.unit}</td>
			<td style="font-size:14px;">${detail.request}</td>
		</tr>
		</c:forEach>
	</span>
	</table>	
</td></tr>

<tr height="20px"><td style="border-bottom-style:none;" valign="bottom">
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" 
		style="border-collapse:collapse; border-top-style:none; border-bottom-style:none; border-left-style:none;border-right-style:none" >
		<c:choose><c:when test="${order.dispatchOther=='1'}">

		</c:when><c:otherwise>
		<tr align="center" height="20px" style="">
			<td colspan="10" style="font-size:14px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;text-align:right;">
			取货方式:&nbsp;<o:dict type="deliverType" value="${order.deliverMethod}"></o:dict>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户地址:${order.deliverAddress}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系方式:${order.tel}
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td> 
		</tr>
		</c:otherwise></c:choose>
		<tr align="center" height='20px'>
			<td width="6%" style="font-size:14px;">开单</td> <td width="10%" style="font-size:14px;">${order.creatorName}</td>
			<td width="6%" style="font-size:14px;">跟单</td> <td width="11%" style="font-size:14px;"></td>
			<td width="6%" style="font-size:14px;">排版</td> <td width="14%" style="font-size:14px;">${order.typeOprNames}&nbsp;</td>
			<td width="6%" style="font-size:14px;">装订</td> <td width="17%" style="font-size:14px;">${order.bindOprNames}&nbsp;</td>
  			<td width="12%" style="font-size:14px;">客户签字</td> <td width="14%" style="font-size:14px;">&nbsp;</td>
		</tr>
	</table>
</td></tr>
</table>
</div>
</center>
</FORM>


<div class="Noprint" > 
<table width="100%"  height="50" border=0 class="noprint" align="center"  style="background:#9191a1;">
	<TR>
		<TD width="50%" align="center"><input type="hidden" name="countPrint" id="countPrint" value="0" />
			<input type="button" onClick="javascript:self.close();" value="关  闭">
		</TD>
		<td align="left"><input type="button" name="print1" id="print1" onClick="printit();" value="打    印" class="groove">
				<input type="button" name="print2" id="print2" onClick="printpreview()" value="打印预览" class="groove">
				<input type="button" name="print3" id="print3" onClick="printsetup()" value="打印设置" class="groove">
		</td>		
	</tr>
	</table>
</div>
</BODY>

<script language="javascript">
	
function printsetup(){ 
	myprint.execwb(8,1); 
} 

// 打印页面预览
function printpreview(){
	myprint.execwb(7,1);
} 

function printit(){
	var printCountObj = document.getElementById('countPrint');
	var printCountVal = printCountObj.value;
	if (printCountVal >= 1) {
		if (!confirm('本单已经打印过一次，要再打印一次吗？')) {
			window.close();
			return;
		}
	}else{
		if (!confirm('确定打印吗？')) {
			window.close();
			return;
		}
	}
	
	// 禁止在本界面连续点击打印
	document.getElementById('print1').disabled = true;
	printCountObj.value = parseInt(printCountObj.value)+1;
	
	myprint.execwb(6,1);  // 提示用户选择打印机
	
	window.close();
}
</script>
