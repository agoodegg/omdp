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
	<title>修改OT单--</title>
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

function CheckForm(){

	return true;
}

function dirty(){
	window.onbeforeunload = function(){ return '订单数据未提交！确认离开该页面吗？'; } 
}

function noDirty(){
	window.onbeforeunload = null;
}
	
$(function() {

	$("#hour").val("${deliverTimeHours}");

	$("#orderTimePicker").datepicker({changeYear: true,  
        showOn: 'button',   
        buttonImage: '<%=path%>/pub/resources/images/calendar.gif',   
        buttonImageOnly: true,  
        duration: 0,  
        showTime: true,  
        constrainInput: false,
        minDate: '-5y', maxDate: '+5y'});
	$("#deliverTimePicker").datepicker({changeYear: true,  
        showOn: 'button',   
        buttonImage: '<%=path%>/pub/resources/images/calendar.gif',   
        buttonImageOnly: true,  
        duration: 0,  
        showTime: true,  
        constrainInput: false,
        minDate: '-5y', maxDate: '+5y'});
    
    $('#orderForm').change(function(){
        dirty();
    })
    
	$("#custName").autocomplete("<%=path %>/order/ot-newOrder/queryCustomer.htm",{
		width: 180,
		selectFirst: false,
		formatItem: formatItem,
		formatResult: formatResult,
		matchSubset: false
	});
	
	
	
	var newCustUrl = "<%=path %>/customer/customerManage/toAddCust.htm";
	var custListUrl = "<%=path %>/customer/customerManage/queryCustList.htm";
	
	function openNewCustWin(url,data){
		var result = window.showModalDialog(url,data,"dialogWidth=730px;dialogHeight=390px");
		if(result){
			$("#custName").val(result.shortName);
			$("#custId").val(result.custId);
			$("#deliverAddress").val(result.address);
			$("#tel").val(result.mobileNo);
		}
	}
	
	$("#custName").result(function(event, data, formatted,oldValue) {
		if(!data[1]){
			$("#custName").val(oldValue);
			openNewCustWin(newCustUrl,{'custName':oldValue},"dialogWidth=730px;dialogHeight=390px");
		}
		else{
			$("#custName").val(data[0]);
			$("#custId").val(data[1]);
		}
	});
	
	$("#searchBtn").bind('click',function(){
		var win = window.showModalDialog(custListUrl,'',"dialogWidth=730px;dialogHeight=390px");
	});
	
	$("#newBtn").bind('click',function(){
		var oldValue=$("#custName").val();
		openNewCustWin(newCustUrl,{'custName':oldValue},"dialogWidth=730px;dialogHeight=390px");
	});
	
	$("#btnSave").bind('click',function(){
		if(CheckForm()){
			$("body").mask("信息提交中...");
			$.ajax({
			    url: '<%=path %>/order/ot-newOrder/updateOrder.htm',
			    type: 'POST',
			    timeout: 2000,
			    data:$('#orderForm').serialize(), 
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
				    			noDirty();
				    			alert("修改成功过!");
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
	});
});

function selectBusiType(busiCd){
	document.getElementById('frm_business').src="<%=path %>/order/ot-newOrder/toNewOrderDetail.htm?sec=${sec}&busiType="+busiCd;
	
	var types = $(".busiTd");
	for(var i=0;i<types.length;i++){
		if('BUSI_'+busiCd==types[i].id){
			types[i].style.backgroundColor="#FFA2CA";
		}
		else{
			types[i].style.backgroundColor="#ccccFF";
		}
	}
}


function format(num){
	var numStr = ""+num;
	if(numStr.indexOf('.')<0){
		numStr = numStr+".00";
	}
	else if(numStr.indexOf('.')==numStr.length()-2){
		numStr = numStr+"0";
	}
	return numStr;
}

function refreshTotal(num,total,busiType){
	if(num==0||num=='0'){
		$("#busi_num_"+busiType)[0].innerHTML="";
		$("#busi_price_"+busiType)[0].innerHTML="";
		
		$("#tPrice"+busiType).val(total);
		
		var allTotal = 0.0;
		$("input[id^='tPrice']").each(function(){
			var temp = 0;
			if($(this).val()){
				temp = parseInt($(this).val());
			}
			allTotal += temp;
		});
		
		$("#tempTotal")[0].innerHTML = "合计:"+format(allTotal)+"元"
		
	}
	else{
		$("#busi_num_"+busiType)[0].innerHTML="共"+num+"项";
		$("#busi_price_"+busiType)[0].innerHTML=" "+total+"元";
		
		$("#tPrice"+busiType).val(total);
		
		var allTotal = 0.0;
		$("input[id^='tPrice']").each(function(){
			var temp = 0;
			if($(this).val()){
				temp = parseInt($(this).val());
			}
			allTotal += temp;
		});
		
		$("#tempTotal")[0].innerHTML = "合计:"+format(allTotal)+"元"
	}
}


function mover(obj){
	obj.style.backgroundColor="#bfb9b9";
}

function mout(obj){
	obj.style.backgroundColor="#cfcccc";
}

function selOpr(obj,id){
	var ids = document.getElementById(id).value;
	var result = window.showModalDialog("<%=path%>/sys/employeeManage/selEmployee.htm?mode=multi&f=id",ids,"dialogWidth=470px;dialogHeight=640px");
	if(result){
		$('#'+id).val(result.ids);
		$('#'+id+"Name").val(result.names);
	}
}

function addTdHeight(h){
	$("#frm_td")[0].height=h;
}

function isoutorder(){
	var isout = $("#dispatchOther").val();
	if(isout=='0'){
		$("#outInfoArea")[0].style.display='none';
	}
	else{
		$("#outInfoArea")[0].style.display='block';
	}
}
</script>
</head>
<body topmargin="0">
<form method="post" id="orderForm" class="form" action="<%=path %>/order/ot-newOrder/saveNewOrder.htm">
<table id="mytable"  width="100%" bgcolor="#ddeeff" border="0" bordercolor="#CCCCCC" align="center" cellpadding="0" cellspacing="0" hspace="0" vspace="0" style="border-collapse:collapse;" class="small">
<th colspan="6"><span style="float:left;">修改OT单</span><span style="float:right;" title="工单编号"><span class="numclass">工单编号No:</span><span class="numclasscontent">${order.ordernum}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></th>
<tr>
		<td width="10%" align="center" nowrap>客户名称<font color="#ff0000">*</font>：<input name="id" type="hidden" value="${order.id}"/></td>
		<td width="23"><input name="ordernum" type="hidden" value="${order.ordernum}"/><input name="custName" type="text" value="${order.custName}" id="custName" />
		</td>
		<td width="10%" align="center">客户编号：</td>
		<td width="23%">
		<input name="custId" type="text" style="width:115px;background:#cfcfcf;" onfocus="blur()" id="custId" value="${order.custId}" readonly>
        <o:select style="width:60px" name="payType"  type="payType"  value="${order.payType}"></o:select>
		</td>
		<td width="10%" align="center" nowrap>是否月结客户：</td>
		<td width="24">
		<input name="dispatchOther" id="dispatchOther" type="hidden" value="${order.dispatchOther}"/>
		<o:select headValue="0" headText="---是否月结客户---" name="ext1" type="payCycleType" value="${order.ext1}"></o:select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>联系人<font color="#ff0000">*</font>：</td>
		<td width="30"><input name="linkMan" type="text" value="${order.linkMan}" />	</td>
		<td width="10%" align="center" nowrap>联系电话<font color="#ff0000">*</font>：</td>
		<td width="30"><input name="tel" type="text" value="${order.tel}" id="tel"  onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();" />
		</td>
		<td align="center" nowrap>客户地址<font color="#ff0000">*</font>:</td><td><input name="deliverAddress" type="text" value="${order.deliverAddress}" id="deliverAddress"/>
		</td>
	</tr>
	<tr id="outInfoArea" <c:choose><c:when test="${order.dispatchOther=='1'}">style="display:block;"</c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
		<td width="10%" align="center" nowrap>供货商<font color="#ff0000">*</font>：</td>
		<td width="30"><input name="dispatchName" id="dispatchName" type="text" value="${order.dispatchName}" />	</td>
		<td width="10%" align="center" nowrap>供货商电话<font color="#ff0000">*</font>：</td>
		<td width="30"><input name="agentMobile" type="text" value="${order.agentMobile}" id="agentMobile"  onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();" />
		</td>
		<td align="center" nowrap>供货商地址:</td><td><input name="agentAddress" type="text" value="" id="agentAddress" value="${order.agentAddress}"/>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>下单时间<font color="#ff0000">*</font>：</td>
		<td width="20%" nowrap><input onfocus="javascirpt:this.blur();" name="orderTimeStr" type="text" value="<fmt:formatDate  value="${order.orderTime}" pattern="yyyy-MM-dd"></fmt:formatDate >" id="orderTimePicker" readonly="true" />
		</td>
		<td width="10%" align="center" nowrap>交货时间<font color="#ff0000">*</font>：</td>
		<td width="20%" nowrap><input style="width:95px;" onfocus="javascirpt:this.blur();" name="deliverTimeStr" type="text" value="<fmt:formatDate  value="${order.deliverTime}" pattern="yyyy-MM-dd"></fmt:formatDate >" id="deliverTimePicker" readonly="true"/>
		<select name="hour" id="hour" style="width:65px;">
		<option value="0">-时间-</option>
		<option value="8">上午8点</option>
		<option value="9">上午9点</option>
		<option value="10">上午10点</option>
		<option value="11">上午11点</option>
		<option value="12">中午12点</option>
		<option value="13">下午1点</option>
		<option value="14">下午2点</option>
		<option value="15">下午3点</option>
		<option value="16">下午4点</option>
		<option value="17">下午5点</option>
		<option value="18">下午6点</option>
		<option value="19">晚上7点</option>
		<option value="20">晚上8点</option>
		<option value="21">晚上9点</option>
		<option value="22">晚上10点</option>
		</select></td>
		<td width="10%" align="center" nowrap>业务名称：</td>
		<td width="20%"><input name="orderTitle" type="text" value="${order.orderTitle}" id="orderTitle"/>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>印刷要求(详细描述)：</td>
		<td colspan="5" height="40"><textarea name="printMemo" width="100%" height="100%" style="width:100%;height:100%;">${order.printMemo}</textarea></td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>排版人员：<input type="hidden" name="typesetOpr" id="typesetOpr" value="${order.typesetOpr}"/></td>
		<td width="30" nowrap><input  type="text"  id="typesetOprName" onfocus="blur()" title="选择排版人员" value="${typeNames}" readonly style="background:#cfcccc;cursor:hand;" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selOpr(this,'typesetOpr')"/></td>
		<td width="10%" align="center" nowrap>装订人员：<input type="hidden" name="bindingOpr" id="bindingOpr" value="${order.bindingOpr}"/></td>
		<td width="30" nowrap><input  type="text" id="bindingOprName" onfocus="blur()" title="选择装订人员" value="${bindNames}" readonly style="background:#cfcccc;cursor:hand;" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selOpr(this,'bindingOpr')"/></td>
		<td width="10%" align="center" nowrap>交货方式<font color="#ff0000">*</font>：</td>
		<td width="30">
		<o:select name="deliverMethod" type="deliverType" headValue="" headText="---请选择---" value="${order.deliverMethod}"></o:select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>文件路径：</td>
		<td width="30"><input name="filepos" type="text" value="${order.filepos}"/></td>
		<td align="center" nowrap>软件版本：</td><td><input name="software" type="text" value="${order.software}"/></td>
		<td align="center" nowrap>开单员：</td><td><input name="creator" type="text" value="${order.creatorName}" readonly disabled/></td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>备注信息：</td>
		<td colspan="5" height="40"><textarea name="orderMemo" width="100%" height="100%" style="width:100%;height:100%;">${order.orderMemo}</textarea></td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>类型与规格：</td>
		<td colspan="5">
		
		<input type='hidden' id='tPrice1' name='tPrice1' value='<o:subprice type="1" ordernum="${order.ordernum}" suffix=""></o:subprice>'>
		<input type='hidden' id='tPrice2' name='tPrice2' value='<o:subprice type="2" ordernum="${order.ordernum}" suffix=""></o:subprice>'>
		<input type='hidden' id='tPrice3' name='tPrice3' value='<o:subprice type="3" ordernum="${order.ordernum}" suffix=""></o:subprice>'>
		<input type='hidden' id='tPrice4' name='tPrice4' value='<o:subprice type="4" ordernum="${order.ordernum}" suffix=""></o:subprice>'>
		<input type='hidden' id='tPrice5' name='tPrice5' value='<o:subprice type="5" ordernum="${order.ordernum}" suffix=""></o:subprice>'>
		<input type='hidden' id='tPrice99' name='tPrice99' value='<o:subprice type="99" ordernum="${order.ordernum}" suffix=""></o:subprice>'>
		<table width="100%" border="0" bordercolor="#000000" cellpadding="0" cellspacing="0">
			<tr align="center" height="20" id="busiTypeTr">	
				<td width='12%' nowrap id='BUSI_1'  onClick='selectBusiType(1)'  class="busiTd" style="background:#FFA2CA;border:3px"><b>设计制作</b>&nbsp;<label id='busi_num_1'><o:subnum type="1" ordernum="${order.ordernum}" prefix="共" suffix=" 项"></o:subnum></label><label id='busi_price_1'><o:subprice type="1" ordernum="${order.ordernum}" suffix="元"></o:subprice></label></td>
				<td width='12%' nowrap id='BUSI_2'  onClick='selectBusiType(2)' class="busiTd"><b>数码打印</b>&nbsp;<label id='busi_num_2'><o:subnum type="2" ordernum="${order.ordernum}" prefix="共" suffix=" 项"></o:subnum></label><label id='busi_price_2'><o:subprice type="2" ordernum="${order.ordernum}" suffix="元"></o:subprice></label></td>
				<td width='12%' nowrap id='BUSI_3'  onClick='selectBusiType(3)'  class="busiTd"><b>写真喷绘</b>&nbsp;<label id='busi_num_3'><o:subnum type="3" ordernum="${order.ordernum}" prefix="共" suffix=" 项"></o:subnum></label><label id='busi_price_3'><o:subprice type="3" ordernum="${order.ordernum}" suffix="元"></o:subprice></label></td>
				<td width='12%' nowrap id='BUSI_4'  onClick='selectBusiType(4)' class="busiTd"><b>工程图文</b>&nbsp;<label id='busi_num_4'><o:subnum type="4" ordernum="${order.ordernum}" prefix="共" suffix=" 项"></o:subnum></label><label id='busi_price_4'><o:subprice type="4" ordernum="${order.ordernum}" suffix="元"></o:subprice></label></td>
				<td width='12%' nowrap id='BUSI_5'  onClick='selectBusiType(5)' class="busiTd"><b>加工装订</b>&nbsp;<label id='busi_num_5'><o:subnum type="5" ordernum="${order.ordernum}" prefix="共" suffix=" 项"></o:subnum></label><label id='busi_price_5'><o:subprice type="5" ordernum="${order.ordernum}" suffix="元"></o:subprice></label></td>
				<td width='12%' nowrap id='BUSI_99' onClick='selectBusiType(99)' class="busiTd"><b>其他</b>&nbsp;<label id='busi_num_99'><o:subnum type="99" ordernum="${order.ordernum}" prefix="共" suffix=" 项"></o:subnum></label><label id='busi_price_99'><o:subprice type="99" ordernum="${order.ordernum}" suffix="元"></o:subprice></label></td>
				<td bgcolor="#33CCCC" id="tempTotal">合计:${order.price}元</td>
			</tr>
		</table>
		<div style="background:#ddeeff" align="left" width="100%" height="100%">
		<TABLE width="100%" style="border-collapse:collapse" cellpadding="0" cellspacing="0">
			<TR>
			<td align="left" id="frm_td">
			<iframe id="frm_business" width="100%" height="100%"  frameborder="0" marginwidth="0" marginheight="0" src="<%=path %>/order/ot-newOrder/toNewOrderDetail.htm?busiType=1&sec=${sec}"> </iframe>
			</td>
		  </TR>
		</TABLE>
		</div>
		</td>
	</tr>
</table>
</form>
<div style="background:#ddeeff">
<TABLE align="center"  style="background:#ddeeff;margin:0 0 0 0;padding:0 0 0 0;" border="0">
	<TR>
	<td width=100px align="right" style="background:#ddeeff">
		<input type="button" value="修改订单" name="btnSave" id="btnSave" >
	</td>
  </TR>
</TABLE>
</div>
</body>
</html>
