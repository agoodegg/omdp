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
<title>工单业务项与规格</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>

<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>

<script type="text/javascript" src="<%=path%>/pub/resources/js/checkInput.js"></script>
<style type="text/css">
.detailTable{
	border-collapse: collapse;
	text-align:left;
	font-size:12px;
}
.addedItem td{
	border: 1px solid #CCCCFF;
}
</style>
<script type="text/javascript">

$(function() {
	
	$('#form1').change(function(){
        window.parent.dirty();
    })
});  
function init(){
	var num = document.form1.num.value;
	var total = document.form1.totalPrice.value;
	var busiType = document.form1.itemType.value;
	
	window.parent.refreshTotal(num,total,busiType);
	
	window.parent.addTdHeight($("#dtable")[0].clientHeight+50);
}

function selectRequest(){
}

function getUnitPrice(){

}

function myRound(num,how){
	var temp = Math.pow(10,how);
	var result = Math.round(num*temp);
	return result*1.0/temp;
}

function calPrice(){
	var amount = $('#amount').val();
	var unitPrice = $('#unitPrice').val();
	
	try{
		amount = parseFloat(amount);
		if(!amount){
			$('#amount').val(0);
			amount=0;
		}
	}catch(e){
		$('#amount').val(0);
	}
	
	try{
		unitPrice = parseFloat(unitPrice);
		if(!unitPrice){
			$('#unitPrice').val(0);
			unitPrice=0;
		}
	}catch(e){
		$('#unitPrice').val(0);
	}

	$('#total').val(myRound(amount*unitPrice,2));
	
}

function checkDetail(){
	if(Validator.Validate($('#form1')[0], 2)){
		$("body").mask("信息提交中...");
		$.ajax({
		    url: '<%=path %>/order/newOrder/saveOrderDetail.htm',
		    type: 'POST',
		    timeout: 2000,
		    data:$('#form1').serialize(), 
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
		    				if(window.parent.opener){
			    				window.parent.opener.refreshGrid();
			    			}
			    			window.location.replace("<%=path %>/order/newOrder/toNewOrderDetail.htm?sec=${sec}&busiType=${busiType}");
				    		//alert(jsondata.msg);
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

function del(id){
	if(!(confirm("确认删除所选记录？"))){
		return false;
	}
	$("body").mask("信息提交中...");
	$.ajax({
	    url: '<%=path %>/order/newOrder/delOrderDetail.htm',
	    type: 'POST',
	    timeout: 2000,
	    data:{'id':id}, 
	    error: function(){
	    	$("body").unmask();
	        alert('服务器异常或超时!删除失败!');
	    },
	    success: function(json){
	    	var jsondata = {};
	    	$("body").unmask();
	    	if(json){
	    		try{
	    			jsondata = $.parseJSON(json);
	    			if(jsondata.success===true){
	    				if(window.parent.opener){
		    				window.parent.opener.refreshGrid();
		    			}
		    			window.location.replace("<%=path %>/order/newOrder/toNewOrderDetail.htm?sec=${sec}&busiType=${busiType}");
			    		alert(jsondata.msg);
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

function modify(id,itemName,esp_1,esp_2,esp_3,esp_4,unit,amount,unitPrice,total){

	$("#itemId").val(id);
	
	$("#itemName").val(itemName);
	
	var esp1 = $("#esp1");
	if(esp1){esp1.val(esp_1);}
	
	var esp2 = $("#esp2");
	if(esp2){esp2.val(esp_2);}
	
	var esp3 = $("#esp3");
	if(esp3){esp3.val(esp_3);}
	
	var esp4 = $("#esp4");
	if(esp4){esp4.val(esp_4);}
	
	$("#unit").val(unit);
	$("#amount").val(amount);
	$("#unitPrice").val(unitPrice);
	$("#total").val(total);
	
	$("#request").val($("#request_"+id)[0].innerHTML);
	
	$("#btn_add")[0].style.display="none";
	$("#btn_edit")[0].style.display="block";
	
}

function editDetail(){
	if(Validator.Validate($('#form1')[0], 2)){
		$("body").mask("信息提交中...");
		$.ajax({
		    url: '<%=path %>/order/newOrder/updateOrderDetail.htm',
		    type: 'POST',
		    timeout: 2000,
		    data:$('#form1').serialize(), 
		    error: function(){
		    	$("body").unmask();
		        alert('服务器异常或超时!修改失败!');
		    },
		    success: function(json){
		    	var jsondata = {};
		    	$("body").unmask();
		    	if(json){
		    		try{
		    			jsondata = $.parseJSON(json);
		    			if(jsondata.success===true){
		    				if(window.parent.opener){
			    				window.parent.opener.refreshGrid();
			    			}
			    			alert(jsondata.msg);
			    			window.location.replace("<%=path %>/order/newOrder/toNewOrderDetail.htm?sec=${sec}&busiType=${busiType}");
				    		//alert(jsondata.msg);
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
<body scroll="no" topmargin="0"  onLoad="init()">

<form name="form1" id="form1" action="<%=path %>/order/newOrder/toNewOrderDetail.htm" method="post" style="margin-top:0px">
<input type="hidden" name="ordernum" id="ordernum" value="${sec}">
<input type="hidden" name="itemType" id="itemType" value="${busiType}" >
<input type="hidden" name="totalPrice" value="${total}">
<input type="hidden" name="num" id="num" value="${itemListSize}">
<table class="detailTable" id="dtable" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
	<tr align="center" bgcolor="#66DDFF">
		<td width=30 nowrap>序号</td>
		<c:forEach items="${fieldList}" var="field" varStatus="fieldStatus">
		<td colspan="2">${field.paramName}</td>
		</c:forEach>
		<td  colspan="2">计量单位</td>
		<td>数量</td>
		<td>单价</td>
		<td>金额</td> 
		<td>操作</td>
	</tr>
	<c:forEach items="${itemList}" var="item" varStatus="itemStatus">
	<tr align="center" height="20" title="鼠标双击可修改本产品" onDblClick="modify(${item.id});" class="addedItem">
		<td rowspan="2"><font color="#FF0000"><b>${itemStatus.count}</b></font></td>
		<td colspan='2'>${item.itemName}</td>
		<c:if test="${item.esp1!=null}"><td colspan='2'>${item.esp1}</td></c:if>
		<c:if test="${item.esp2!=null}"><td colspan='2'>${item.esp2}</td></c:if>
		<c:if test="${item.esp3!=null}"><td colspan='2'>${item.esp3}</td></c:if>
		<c:if test="${item.esp4!=null}"><td colspan='2'>${item.esp4}</td></c:if>
		<td  colspan='2'>${item.unit}</td>
		<td><fmt:formatNumber value="${item.amount}" pattern="0.##"/></td>
		<td><fmt:formatNumber value="${item.unitPrice}" pattern="0.00"/></td>
		<td><fmt:formatNumber value="${item.total}" pattern="0.00"/></td>
		<td rowspan="2" align="center">
			<a href="#" onClick="modify(${item.id},'${item.itemName}','${item.esp1}','${item.esp2}','${item.esp3}','${item.esp4}','${item.unit}',${item.amount},${item.unitPrice},${item.total});">修改</a>
			<a href="#" onClick="del(${item.id});">删除</a>
		</td>
	</tr>
	<tr class="addedItem">
		<td colspan="${fieldListSize*2+5}"><b>制作要求:</b><pre id="request_${item.id}">${item.request}</pre></td>
	</tr>
	</c:forEach>
	
	<tr align="center" style="background:#eeffee;">
		<td rowspan="2">0</td><input type="hidden" name="id" id="itemId"/>
		<c:forEach items="${fieldList}" var="field2" varStatus="fieldStatus2">
		<c:if test="${fieldStatus2.count==1}">
		<td><input type="text" name="itemName" id="itemName" style="width:120px" dataType="LimitB" min="1"  max="120" msg="项目名称(非空,最大长度120)"></td>
		<td width="18"><span style="width:18px;">
		<o:select name="selItemName" style="margin-left:-150px;width:168px;display:block;" type="${field2.paramCode}" busiType="${busiType}" headValue="" headText="---自定义---" id="itemNameSel"  onchange="document.form1.itemName.value=this.options[this.selectedIndex].text;getUnitPrice();"></o:select>
		</span></td>
		</c:if>
		<c:if test="${fieldStatus2.count>1}">
		<td><input type='text' name='esp${fieldStatus2.count-1}' id='esp${fieldStatus2.count-1}' style='width:100px' dataType="LimitB"  max="40" msg="${field2.paramName}(最大长度40)"></td>
		<td width="18"><span style="width:18px;">
		<o:select name="code${fieldStatus2.count-1}" style="margin-left:-150px;width:168px;display:block;" type="${field2.paramCode}" busiType="${busiType}" headValue="" headText="---自定义---" id="itemNameSel"  onchange="document.form1.esp${fieldStatus2.count-1}.value=this.options[this.selectedIndex].text;getUnitPrice();"></o:select>
		</span></td>
		</c:if>
		</c:forEach>
		<td>
		<input  name="unit" id="unit"  type="text" style='width:100px' dataType="LimitB" max="10"  min="1" msg="计量单位(非空)">
		</td>
		<td width="18"><span style="width:18px;">
		<select style="margin-left:-150px;width:168px;display:block;" onChange="document.form1.unit.value=this.options[this.selectedIndex].text;">
			  <option value='本' >本</option><option value='张' >张</option><option value='P' >P</option><option value='平方米' >平方米</option><option value='' selected="selected">-自定义-</option></span></select>
		</td>
		<td><input name="amount" id="amount" type="text" value="0" style="width:80px" onChange="calPrice()" onblur="calPrice()" onKeyPress="inputNumber();"  onbeforepaste="pasteInteger();" dataType="Currency"  max="10" msg="数量(数值型,长度小于10)">		
		</td>
		<td><input name="unitPrice"  id="unitPrice" type="text" value="0" style="width:40px" onChange="calPrice()" onblur="calPrice()" onKeyPress="inputNumber();"  onbeforepaste="pasteInteger();" dataType="Currency"  max ="12" msg="单价(数值型,长度小于12)"></td>
		<td><input name="total" id="total" type="text" value="0" style="width:60px" readonly></td>
		<td rowspan="2" align="center" valign="middle"><input type="button" id="btn_add" name="btn_action" value="添  加" onclick="checkDetail();" class="line" style="width:60px" /><input type="button" id="btn_edit"  value="修  改" onclick="editDetail();" class="line" style="width:60px;display:none;" /></td>
	</tr>
	<tr>
		<td colspan="${fieldListSize*2+3}" valign="middle">
			<textarea name="request" id="request" class="line" title="选择或者输入制作要求" style="width:100%;height:30px"></textarea>
		</td>
		<td align="left">
			<input type="button" name="sel_make" value="要求" onClick="selectRequest()" title="点击选择制作要求">
		</td>
	</tr>
		
</table>
</form>
</body>
</html>
