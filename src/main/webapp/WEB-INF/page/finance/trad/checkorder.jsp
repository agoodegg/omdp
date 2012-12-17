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
	<title>核销结算-【${order.ordernum}】</title>
	<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
	
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	<link rel="stylesheet" href="<%=path%>/pub/resources/libs/jqueryui/themes/redmond/jquery.ui.all.css">
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.core.js"></script>
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.widget.js"></script>
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.datepicker.js"></script>
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	
	<script type='text/javascript' src='<%=path%>/pub/resources/libs/autocomplete/jquery.bgiframe.min.js'></script>
	<script type='text/javascript' src='<%=path%>/pub/resources/libs/autocomplete/jquery.ajaxQueue.js'></script>
	<script type='text/javascript' src='<%=path%>/pub/resources/libs/autocomplete/thickbox-compressed.js'></script>
	<script type='text/javascript' src='<%=path%>/pub/resources/libs/autocomplete/jquery.autocomplete.min.js'></script>
	<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/autocomplete/jquery.autocomplete.css" />
	<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/autocomplete/thickbox.css" />
	
	<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/default.css"/>
	<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
	<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
	<script src="<%=path%>/pub/resources/js/checkInput.js"></script>
<style type="text/css">
.busiTd{
	border:3px;
	cursor:hand;
}

.payInfoTab tr td{
	font-size:17px;
	color:#667760;
	font-weight:bold;
}

.payInfoTab input{
	font-size:14px;
}

.checkInfoTab tr td{
	font-size:17px;
	color:#000000;
	font-weight:bold;
}

.checkInfoTab input{
	font-size:14px;
}
</style>
<script type="text/javascript">
function formatItem(row) {
	if(!row[1]){
		return "<font color=\"#ff0000\">"+row[0] + "</font>";
	}
	else{
		return row[0] + " (<strong>" + row[1] + "</strong>)";
	}
}
function formatResult(row) {
	return row[0].replace(/(<.+?>)/gi, '');
}


function mover(obj){
	obj.style.backgroundColor="#bfb9b9";
}

function mout(obj){
	obj.style.backgroundColor="#cfcccc";
}

function closeCheckWin(){
	if(confirm("确定关闭当前窗口?")){
		window.close();
	}
}
function docheck(){
	if(Validator.Validate($('#checkForm')[0], 2)){
		if(!(confirm("确认核销？"))){
			return false;
		}
		$("body").mask("信息提交中...");
		$.ajax({
		    url: '<%=path %>/finance/trad/docheckorder.htm',
		    type: 'POST',
		    timeout: 2000,
		    data:$('#checkForm').serialize(), 
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
}
</script>
</head>
<body topmargin="0">
<table id="mytable"  width="100%" bgcolor="#ddeeff" border="0" bordercolor="#CCCCCC" align="center" cellpadding="0" cellspacing="0" hspace="0" vspace="0" style="border-collapse:collapse;" class="small">
<th colspan="6"><span style="float:left;">工单状态:【<o:ostatus value="${order.orderStatus}"></o:ostatus>】</span><span style="float:right;" title="工单编号"><span class="numclass">工单编号No:</span><span class="numclasscontent">${order.ordernum}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></th>
<tr>
		<td width="10%" align="center" nowrap>客户名称：<input name="id" type="hidden" value="${order.id}"/></td>
		<td width="20%"><input name="ordernum" type="hidden" value="${order.ordernum}"/>${order.custName}
		</td>
		<td width="10%" align="center">客户编号：</td>
		<td width="20%">${order.custId}
		</td>
		<td width="10%" align="center">支付类型：</td>
		<td><o:dict type="payType" value="${order.payType}"></o:dict>-<b><o:dict type="payCycleType" value="${order.ext1}"></o:dict></b>&nbsp;</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>联系人：</td>
		<td width="20%">${order.linkMan}</td>
		<td width="10%" align="center" nowrap>联系电话：</td>
		<td width="20%">${order.tel}
		</td>
		<td align="center" nowrap>客户地址:</td><td>${order.deliverAddress}
		</td>
	</tr>
	<c:choose><c:when test="${order.dispatchOther=='1'}">
	<tr>
		<td width="10%" align="center" nowrap>是否外派单：</td>
		<td width="20%">外派单</td>
		<td width="10%" align="center" nowrap>供货商名称：</td>
		<td width="20%">${order.dispatchName}
		</td>
		<td align="center" nowrap>供货商电话:</td><td>${order.agentMobile}
		</td>
	</tr>
	</c:when><c:otherwise>
	</c:otherwise></c:choose>
	<tr>
		<td width="10%" align="center" nowrap>下单时间：</td>
		<td width="20%" nowrap><fmt:formatDate  value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate >&nbsp;
		</td>
		<td width="10%" align="center" nowrap>交货时间：</td>
		<td width="20%" nowrap><fmt:formatDate  value="${order.deliverTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate >&nbsp;</td>
		<td width="10%" align="center" nowrap>业务名称：</td>
		<td width="20%">${order.orderTitle}
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>印刷要求(详细描述)：</td>
		<td colspan="5" height="40"><textarea name="printMemo" width="100%" height="100%" style="width:100%;height:100%;" disabled>${order.printMemo}</textarea></td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>排版人员：<input type="hidden" name="typesetOpr" id="typesetOpr" value="${order.typesetOpr}"/></td>
		<td width="20%" nowrap>${typeNames}</td>
		<td width="10%" align="center" nowrap>装订人员：<input type="hidden" name="bindingOpr" id="bindingOpr" value="${order.bindingOpr}"/></td>
		<td width="20%" nowrap>${bindNames}</td>
		<td width="10%" align="center" nowrap>交货方式：</td>
		<td width="20%">
		<o:dict type="deliverType" value="${order.deliverMethod}"></o:dict>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>文件路径：</td>
		<td width="20%">${order.filepos}</td>
		<td align="center" nowrap>软件版本：</td><td>${order.software}</td>
		<td align="center" nowrap>开单员：</td><td>${order.creatorName}</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>备注信息：</td>
		<td colspan="5" height="40"><textarea name="orderMemo" width="100%" height="100%" disabled style="width:100%;height:100%;">${order.orderMemo}</textarea></td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>明细列表：</td>
		<td colspan="5">
		<div style="background:#ddeeff" align="left" width="100%" height="100%">
		<table width="100%" border="1" align="center" style="border-collapse:collapse;border-top-style:none" bordercolor="#000000" >
			<tr align="center" bgcolor="#00CCCC">
				<td width="4%" style="font-size:14px;">No.</td>
				<td width="10%" style="font-size:13px;">业务类型</td>
				<td width="21%" style="font-size:13px;">品名</td>
				<td width="8%" align="center" style="font-size:13px;">数量</td>
				<td width="8%" align="center" style="font-size:13px;">单价</td>
				<td width="8%" align="center" style="font-size:13px;">金额</td>
				<td style="font-size:13px;">制作要求</td>
			</tr>
			<c:forEach items="${details}" var="detail" varStatus="status">
			<tr align="left" valign="top">
				<td align="center" style="font-size:13px;">${status.count}</td>
				<td style="font-size:13px;"><o:dict type="BUSI_TYPE" value="${detail.itemType}"></o:dict></td>
				<td style="font-size:13px;">${detail.itemName}</td>
				<td align="center" nowrap style="font-size:13px;"><fmt:formatNumber value="${detail.amount}" pattern="0.##"/>${detail.unit}</td>
				<td align="center" style="font-size:13px;"><fmt:formatNumber value="${detail.unitPrice}" pattern="0.00"/></td>
				<td align="right" style="font-size:13px;font-weight:bold;color:#ff0000;"><fmt:formatNumber value="${detail.total}" pattern="0.00"/></td>
				<td style="font-size:13px;">${detail.request}</td>
			</tr>
			</c:forEach>
		</span>
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td></td>
		<td height="30" style="font-size:16px;color:#ff0000;font-weight:bold;" align="right" colspan="5">合计:&nbsp;<fmt:formatNumber value="${order.price}" pattern="0.00"/> 元  
		</td>
	</tr>
</table>
<div style="background:#ddeeff">
&nbsp;
</div>

<div style="margin:10 0 0 0;background:#efae41;font-size:27;font-weight:bold;">★☆结算信息</div>
<table  width="100%" bgcolor="#ddeeff" border="0" bordercolor="#CCCCCC" align="center" cellpadding="0" cellspacing="0" hspace="0" vspace="0" style="border-collapse:collapse;" class="payInfoTab">
<tr>
		<td width="15%" align="center" nowrap>总金额：</td>
		<td width="17%"><fmt:formatNumber value="${order.price}" pattern="0.00"/> 元<input type="hidden" id="totalPrice" value="${order.price}"/>
		</td>
		<td width="15%" align="center">客户编号：</td>
		<td width="17%">${order.custId}<input type="hidden" name="custCd" value="${order.custId}"/>
		</td>
		<td width="15%" align="center">客户名称：</td>
		<td>${order.custName}<input type="hidden" id="custName" name="custName" value="${order.custName}"/><input type="hidden" id="ordernum" name="ordernum" value="${order.ordernum}"/></td>
	</tr>
	<tr>
		<td width="15%" align="center" nowrap style="color:#0000ff;">结算方式：</td>
		<td width="17%"><o:dict type="payType" value="${trad.feeType}"></o:dict>
		</td>
		<td width="15%" align="center" style="color:#0000ff;">应收：</td>
		<td width="17%"><fmt:formatNumber value="${trad.yfee}" pattern="0.00"/>元
		</td>
		<td width="15%" align="center" style="color:#0000ff;" >实收：</td>
		<td><fmt:formatNumber value="${trad.acFee}" pattern="0.00"/>元</td>
	</tr>
	<tr>
		<td width="15%" align="center">支票号：</td>
		<td>${trad.checkNum}</td>
		<td width="15%" align="center" nowrap>是否已开发票：</td>
		<td width="17%"><o:dict type="sendBill" value="${trad.checkStatus}"></o:dict>
		</td>
		<td width="15%" align="center">发票类型：</td>
		<td><o:dict type="billType" value="${trad.checkType}"></o:dict></td>
	</tr>
	<tr>
		<td width="15%" align="center">结算备注：</td>
		<td colspan="5" height="50">
		<textarea name="tradComm" style="width:100%;height:100%;" disabled>${trad.tradComm}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="center">结算人：</td>
		<td width="17%">${trad.tradOprName}
		</td>
		<td width="15%" align="center">结算时间：</td>
		<td width="17%"><fmt:formatDate  value="${trad.tradTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate >
		</td>
		<td colspan="2">
		&nbsp;
		</td>
	</tr>
</table>

<div style="margin:10 0 0 0;background:#acf37c;font-size:27;font-weight:bold;color:ff0000;">★★☆核销</div>
<form id="checkForm" action="<%=path%>/finance/trad/docheckorder.htm" method="POST">
<table  width="100%" bgcolor="#ddeeff" border="0" bordercolor="#CCCCCC" align="center" cellpadding="0" cellspacing="0" hspace="0" vspace="0" style="border-collapse:collapse;" class="checkInfoTab">
<tr>
		<td width="15%" align="center">核销备注：<input type="hidden" id="ordernum" name="ordernum" value="${order.ordernum}"/></td>
		<td colspan="5" height="50">
		<textarea name="validComm" style="width:100%;height:100%;"  dataType="LimitB"   max="100" msg="核销备注(最大长度100)"></textarea>
		</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="5" align="right" >
		
		<input style="font-size:17px;color:#0000ff;font-weight:bold;" type="button" onclick="closeCheckWin()" value="取 消">&nbsp;&nbsp;&nbsp;&nbsp;<input style="font-size:17px;color:#0000ff;font-weight:bold;" type="button" value="确认核销"  onclick="docheck()">
		</td>
	</tr>
</table>
</form>
</body>
</html>
