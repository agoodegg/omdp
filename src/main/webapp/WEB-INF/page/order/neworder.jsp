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
	<title>增加新订单--</title>
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

	if($.trim($("#deliverMethod").val())=='3'){
		if($.trim($("#deliverAddress").val())==''){
			alert("交货方式为送货时，必须填写客户地址!");
			return false;
		}
	}
	
	return true;
}

function dirty(){
	window.onbeforeunload = function(){ return '订单数据未提交！确认离开该页面吗？'; } 
}

function noDirty(){
	window.onbeforeunload = null;
}
	
$(function() {
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
    
	$("#custName").autocomplete("<%=path %>/order/newOrder/queryCustomer.htm",{
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
			$("#linkMan").val(result.fullName);
			$("#ext1").val(result.payCycle);
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
			$("#linkMan").val(data[2]);
			$("#tel").val(data[3]);
			$("#deliverAddress").val(data[4]);
			$("#ext1").val(data[5]);
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
			    url: '<%=path %>/order/newOrder/saveNewOrder.htm',
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
				    			alert(jsondata.msg);
				    			window.location.replace("<%=path %>/order/orderQuery/queryAll.htm");
					    		
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
	document.getElementById('frm_business').src="<%=path %>/order/newOrder/toNewOrderDetail.htm?sec=${sec}&busiType="+busiCd;
	
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
			allTotal += parseFloat($(this).val());
		});
		
		
		
		$("#tempTotal")[0].innerHTML = "合计:"+format(allTotal)+"元"
		
	}
	else{
		$("#busi_num_"+busiType)[0].innerHTML="共"+num+"项";
		$("#busi_price_"+busiType)[0].innerHTML=" "+total+"元";
		
		$("#tPrice"+busiType).val(total);
		
		var allTotal = 0.0;
		$("input[id^='tPrice']").each(function(){
			allTotal += parseFloat($(this).val());
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

function custNameChanged(){
	$('#custId').val("");
}
function selCust(){
	var shortName = $('#custName').val();
	var result = window.showModalDialog("<%=path%>/order/newOrder/selCustomer.htm",shortName,"dialogWidth=470px;dialogHeight=700px");
	if(result){
		$("#custName").val(result.shortName);
		$("#custId").val(result.custId);
		$("#linkMan").val(result.fullName);
		$("#tel").val(result.mobileNo);
		$("#deliverAddress").val(result.address);
		$("#ext1").val(result.payCycle);
	}
}
</script>
</head>
<body topmargin="0">
<form method="post" id="orderForm" class="form" action="<%=path %>/order/newOrder/saveNewOrder.htm">
<table id="mytable"  width="100%" bgcolor="#ddeeff" border="0" bordercolor="#CCCCCC" align="center" cellpadding="0" cellspacing="0" hspace="0" vspace="0" style="border-collapse:collapse;" class="small">
<th colspan="6"><span style="float:left;">增加新订单(<font color="#ff0000">*</font>号为必填项)</span><span style="float:right;" title="工单编号自动生成"><span class="numclass">No:</span><span class="numclasscontent">......&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></th>
<tr>
		<td width="6%" align="center" nowrap>客户名称<font color="#ff0000">*</font>：</td>
		<td width="25%" ><input name="ordernum" type="hidden" value="${sec}"/><input name="custName" type="text" value="" id="custName" style="width:150px;vertical-align:middle;" onchange="custNameChanged()"/><img src="<%=path %>/pub/resources/images/se.png" onclick="selCust()" style="vertical-align:middle;cursor:hand;" title="查询已有客户"/>
		</td>
		<td width="10%" align="center">客户编号：</td>
		<td width="25%">
		<input name="custId" type="text" style="width:115px;background:#cfcfcf;" onfocus="blur()" id="custId" value="" readonly>
        <o:select style="width:60px" name="payType"  type="payType" ></o:select>
		</td>
		<td colspan="2">
		<!-- <input type="button" name="search" id="searchBtn" value="搜  索"> -->
		<input type="button" name="new" id="newBtn" value="新客户"> 
		<input type="hidden" name="dispatchOther" id="dispatchOther" value="0"/>
		<o:select headValue="0" headText="---是否月结客户---" id="ext1" name="ext1" type="payCycleType"></o:select>
		</td>
	</tr>
	<tr>
		<td width="6%" align="center" nowrap>联系人<font color="#ff0000">*</font>：</td>
		<td width="25%"><input name="linkMan" id="linkMan" type="text" value="" />	</td>
		<td width="10%" align="center" nowrap>联系电话<font color="#ff0000">*</font>：</td>
		<td width="25%"><input name="tel" type="text" value="" id="tel"  onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();" />
		</td>
		<td align="center" nowrap>客户地址:</td><td><input name="deliverAddress" id="deliverAddress" type="text" value="" id="deliverAddress"/>
		</td>
	</tr>
	<tr>
		<td width="6%" align="center" nowrap>下单时间<font color="#ff0000">*</font>：</td>
		<td width="25%" nowrap><input onfocus="javascirpt:this.blur();" name="orderTimeStr" type="text" value="<fmt:formatDate  value="${order.orderTime}" pattern="yyyy-MM-dd"></fmt:formatDate >" id="orderTimePicker" readonly="true" />
		</td>
		<td width="10%" align="center" nowrap>交货时间<font color="#ff0000">*</font>：</td>
		<td width="25%" nowrap><input style="width:95px;" onfocus="javascirpt:this.blur();" name="deliverTimeStr" type="text" value="<fmt:formatDate  value="${order.deliverTime}" pattern="yyyy-MM-dd"></fmt:formatDate >" id="deliverTimePicker" readonly="true"/>
		<select name="hour" style="width:65px;">
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
		<td width="20%"><input name="orderTitle" type="text" value="" id="orderTitle"/>
		</td>
	</tr>
	<tr>
		<td width="6%" align="center" nowrap>备注客户叮嘱：</td>
		<td colspan="5" height="40"><textarea name="printMemo" width="100%" height="100%" style="width:100%;height:100%;"></textarea></td>
	</tr>
	<tr>
		<td width="6%" align="center" nowrap>排版人员：<input type="hidden" name="typesetOpr" id="typesetOpr"/></td>
		<td width="25%" nowrap><input  type="text" value="     " id="typesetOprName" onfocus="blur()" title="选择排版人员" readonly style="background:#cfcccc;cursor:hand;" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selOpr(this,'typesetOpr')"/></td>
		<td width="10%" align="center" nowrap>装订人员：<input type="hidden" name="bindingOpr" id="bindingOpr"/></td>
		<td width="25%" nowrap><input  type="text" value="     " id="bindingOprName" onfocus="blur()" title="选择装订人员" readonly style="background:#cfcccc;cursor:hand;" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selOpr(this,'bindingOpr')"/></td>
		<td width="10%" align="center" nowrap>交货方式<font color="#ff0000">*</font>：</td>
		<td>
		<o:select name="deliverMethod" id="deliverMethod" type="deliverType" headValue="" headText="---请选择---"></o:select>
		</td>
	</tr>
	<tr>
		<td width="6%" align="center" nowrap>文件路径：</td>
		<td width="25%"><input name="filePos" type="text" value=""/></td>
		<td align="center" nowrap>软件版本：</td><td><input name="software" type="text" value=""/></td>
		<td align="center" nowrap>开单员<font color="#ff0000">*</font>：</td><td><input name="creator" type="text" value="${user.userName}" readonly disabled/></td>
	</tr>
	<tr>
		<td width="6%" align="center" nowrap>备注信息：</td>
		<td colspan="5" height="40"><textarea name="orderMemo" width="100%" height="100%" style="width:100%;height:100%;"></textarea></td>
	</tr>
	<tr>
		<td width="6%" align="center" nowrap>类型与规格：</td>
		<td colspan="5">
		
		<input type='hidden' id='tPrice1' name='tPrice1' value='0'>
		<input type='hidden' id='tPrice2' name='tPrice2' value='0'>
		<input type='hidden' id='tPrice3' name='tPrice3' value='0'>
		<input type='hidden' id='tPrice4' name='tPrice4' value='0'>
		<input type='hidden' id='tPrice5' name='tPrice5' value='0'>
		<input type='hidden' id='tPrice99' name='tPrice99' value='0'>
		<table width="100%" border="0" bordercolor="#000000" cellpadding="0" cellspacing="0">
			<tr align="center" height="20" id="busiTypeTr">	
				<td width='12%' nowrap id='BUSI_1'  onClick='selectBusiType(1)'  class="busiTd" style="background:#FFA2CA;border:3px"><b>设计制作</b>&nbsp;<label id='busi_num_1'></label><label id='busi_price_1'></label></td>
				<td width='12%' nowrap id='BUSI_2'  onClick='selectBusiType(2)' class="busiTd"><b>数码打印</b>&nbsp;<label id='busi_num_2'></label><label id='busi_price_2'></label></td>
				<td width='12%' nowrap id='BUSI_3'  onClick='selectBusiType(3)'  class="busiTd"><b>写真喷绘</b>&nbsp;<label id='busi_num_3'></label><label id='busi_price_3'></label></td>
				<td width='12%' nowrap id='BUSI_4'  onClick='selectBusiType(4)' class="busiTd"><b>工程图文</b>&nbsp;<label id='busi_num_4'></label><label id='busi_price_4'></label></td>
				<td width='12%' nowrap id='BUSI_5'  onClick='selectBusiType(5)' class="busiTd"><b>加工装订</b>&nbsp;<label id='busi_num_5'></label><label id='busi_price_5'></label></td>
				<td width='12%' nowrap id='BUSI_99' onClick='selectBusiType(99)' class="busiTd"><b>其他</b>&nbsp;<label id='busi_num_99'></label><label id='busi_price_99'></label></td>
				<td bgcolor="#33CCCC" id="tempTotal"></td>
			</tr>
		</table>
		<div style="background:#ddeeff" align="left" width="100%" height="100%">
		<TABLE width="100%" style="border-collapse:collapse" cellpadding="0" cellspacing="0">
			<TR>
			<td align="left" id="frm_td">
			<iframe id="frm_business" width="100%" height="100%"  frameborder="0" marginwidth="0" marginheight="0" src="<%=path %>/order/newOrder/toNewOrderDetail.htm?busiType=1&sec=${sec}"> </iframe>
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
		<input type="button" value="保存订单" name="btnSave" id="btnSave" >
	</td>
  </TR>
</TABLE>
</div>
</body>
</html>
