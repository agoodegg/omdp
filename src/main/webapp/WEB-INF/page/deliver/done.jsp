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
<title>打印送货单</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
	
<link rel="stylesheet" href="<%=path%>/pub/resources/libs/jqueryui/themes/redmond/jquery.ui.all.css">
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.widget.js"></script>
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.datepicker.js"></script>
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
<script type="text/javascript">

function doneSendOrder(){

	var val = $.trim($('#sendOpr').val());
	if(val==''){
		alert('送货人必填');
		return;
	}

	$("body").mask("信息提交中...");
	$.ajax({
	    url: '<%=path %>/deliver/deliverQuery/doneDeliver.htm',
	    type: 'POST',
	    timeout: 2000,
	    data:{'id':'${deliver.id}','sendOpr':val}, 
	    error: function(){
	    	$("body").unmask();
	        alert('服务器异常或超时!新增失败!');
	    },
	    success: function(json){
	    	var jsondata = {};
	    	$("body").unmask();
	    	if(json){
	    		try{
	    			jsondata = $.parseJSON(json);
	    			if(jsondata.success===true){
		    			alert(jsondata.msg);
		    			window.opener.refreshGrid();
		    			window.close();
			    		
			    	}
			    	else if(jsondata.success===false){
			    		alert(jsondata.msg);
			    	}
			    	else{
			    		alert('服务器异常或超时!');
			    	}
	    		}catch(e){
	    			alert('服务器异常或超时!');
	    		}
	    	}
	    	else{
	    		alert('服务器异常或超时!');
	    	}
	    }
	});
}
</script>
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
<body topmargin="0" onLoad="window.focus()">

<center>
<table border="0" width="95%" style="border-collapse:collapse;" align="center" >
	<tr height="30pt">
		<td align="center" style="font-size:22px;font-family:黑体;font-weight:bold;" colspan="10">
			<font color='#000000'>深圳市欧美数码印务有限公司</font>
		</td>
	</tr>
	<tr>
		<td width="38%" align="left" style="font-size:13px;">客户名称:${deliver.custName}&nbsp;</td>
		<td align="left" style="font-size:13px;font-family:宋体" width="20%">No.<font color="#ff0000">${deliver.sendNo}</font></td>
		<td align="right" width="18%" style="font-size:13px;font-family:宋体" nowrap>制单时间:<fmt:formatDate  value="${now}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate ></td>
	</tr>
</table>
<div class="makebill">
<table align="center" width="100%" cellpadding="2" cellspacing="0" class="tabp">
<tr valign="top"><td>
	<table width="100%" border="1" align="center" style="border-collapse:collapse;border-top-style:none" bordercolor="#000000" >
		<tr align="center" bgcolor="#00CCCC"  style="font-size:13px;">
			<td width="8%" style="font-size:13px;">编号</td>
			<td width="27%" style="font-size:13px;">品名</td>
			<td width="8%" align="center" style="font-size:13px;">数量</td>
			<td width="8%" align="center" style="font-size:13px;">单价</td>
			<td  width="8%" style="font-size:13px;">金额</td>
			<td style="font-size:13px;">备注</td>
		</tr>
		<c:forEach items="${details}" var="detail" varStatus="status">
		<tr align="left" valign="top">
			<td align="center" style="font-size:13px;">${status.count}</td>
			<!-- <td><o:dict type="BUSI_TYPE" value="${detail.itemType}"></o:dict></td> -->
			<td style="font-size:13px;">${detail.itemName}</td>
			<td style="font-size:13px;" align="center" nowrap>${detail.amount}${detail.unit}</td>
			<td style="font-size:13px;" align="center">${detail.unitPrice}</td>
			<td style="font-size:13px;" align="center">${detail.total}</td>
			<td style="font-size:13px;"></td>
		</tr>
		</c:forEach>
	</span>
	</table>	
</td></tr>

<tr height="20px"><td style="border-bottom-style:none;" valign="bottom">
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style="border-collapse:collapse; border-top-style:none; border-bottom-style:none; border-left-style:none;border-right-style:none" >
		<tr align="center" height="20px" style="">
			<td colspan="2" style="font-size:13px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;text-align:right;">
			取货方式:&nbsp;<o:dict type="deliverType" value="${order.deliverMethod}"></o:dict>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户地址:${order.deliverAddress}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系方式:${order.tel}
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td> 
		</tr>
		<tr align="center" height='20px'>
			<td width="8%" style="font-size:13px;">合计:</td>
			<td width="58%" style="font-size:14.5px;font-family:黑体;font-weight:bold;border-left-style:none;">人民币贰仟贰佰伍拾元整</td>
		</tr>
	</table>
</td></tr>
</table>
</div>
</center>
<form method="POST" name="form1" id="form1">
<table border="0" style="margin:0 0 0 0;" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td width="35%" style="font-family:黑体;font-size:13px;"><font color="#ff0000">客户签收</font><input type="text"/></td>
<td width="20%" style="font-family:黑体;font-size:13px;"></td>
<td style="font-family:黑体;font-size:13px;"><font color="#ff0000">送货人签名</font><input type="text" id="sendOpr"/>&nbsp;&nbsp;&nbsp;<input type="button" value="确认送达" onclick="doneSendOrder()"/></td>
</tr>
</table>
</form>
</body>

</html>
