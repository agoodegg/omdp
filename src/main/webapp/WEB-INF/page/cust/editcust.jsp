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
	<base target="_self">
	<title>编辑客户信息</title>
	<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=path%>/pub/resources/js/checkInput.js"></script>
	<script type="text/javascript" src="<%=path%>/pub/resources/libs/validate/livevalidation_standalone.js"></script>
	<link rel="stylesheet" href="<%=path%>/pub/resources/libs/validate/consolidated_common.css">
	
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	<link rel="stylesheet" href="<%=path%>/pub/resources/libs/jqueryui/themes/redmond/jquery.ui.all.css">
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.core.js"></script>
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.widget.js"></script>
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.datepicker.js"></script>
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/default.css"/>
	<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
	<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
	
	<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
	
<style type="text/css">
.busiTd{
	border:solid 2px #FFFFFF;
	cursor:hand;
}
</style>
<script type="text/javascript">
function CheckForm(){
	return true;
}

$(function(){
	$("#editBtn").bind("click",function(){
		if(Validator.Validate($('#custForm')[0], 2)){
			$("body").mask("信息提交中...");
			$.ajax({
			    url: '<%=path %>/customer/customerManage/editCust.htm',
			    type: 'POST',
			    dataType: 'json',
			    timeout: 2000,
			    data:$('#custForm').serialize(),
			    error: function(){
			    	$("body").unmask();
			        alert('服务器异常或超时!新增失败!');
			    },
			    success: function(json){
			    	$("body").unmask();
			    	if(json){
			    		if(json.success){
				    		alert(json.msg);
				    		window.opener.query();
				    		window.close();
				    	}
				    	else{
				    		alert(json.msg);
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

function mover(obj){
	obj.style.backgroundColor="#bfb9b9";
}

function mout(obj){
	obj.style.backgroundColor="#cfcccc";
}

function selOpr(obj,id){
	var ids = document.getElementById(id).value;
	var result = window.showModalDialog("<%=path%>/sys/employeeManage/selEmployee.htm?mode=single&f=idNo",ids,"dialogWidth=470px;dialogHeight=640px");
	if(result){
		$('#'+id).val(result.ids);
		$('#'+id+"Name").val(result.names);
	}
}
</script>
</head>
<body style="overflow:hidden;">
<div id="main" class="mainPanel wd txt_left">
	<div class="titleInfo">							
			<h3 class="left">编辑客户信息</h3><span class="addrtitle b_size"></span>					
			</div>

<form method="post" id="custForm" class="form" action="<%=path %>/customer/customerManage/editCust.htm">
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;margin:0 0 0 0;padding:0 0 0 0;">
	<tr>
		<td width="10%" align="center" nowrap>客户名称：<input type="hidden" name="id" value="${cust.id }"></td>
		<td width="23"><input name="shortName" type="text" value="${cust.shortName}" id="shortName"  dataType="LimitB" min="1"  max="40" msg="客户名称(非空,长度小于40)" />
		</td>
		<td width="10%" align="center" nowrap>客户拼音：</td>
		<td width="23"><input name="pinyin" type="text" value="${cust.pinyin}" id="pinyin"   dataType="English2"  max="40" msg="客户名字拼音(英文字母,长度小于40)" />
		</td>
		<td width="10%" align="center">客户编号：</td>
		<td width="24%">
		<input name="custId" type="text" readonly style="background:#cccccc;color:#ff0000;" value="${cust.custId }" onfocus="blur();">
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>联系人：</td>
		<td width="23"><input name="fullName" type="text" value="${cust.fullName }"  dataType="LimitB" max="40" msg="联系人(长度小于40)"/></td>
		<td width="10%" align="center" nowrap>联系电话：</td>
		<td width="23"><input name="mobileNo" type="text" value="${cust.mobileNo}" id="mobileNo" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();"  dataType="LimitB" min="1"  max="40" msg="联系电话(非空,长度小于40)"/>
		</td>
		<td width="10%" align="center" nowrap>传真：</td>
		<td width="24"><input name="fax" type="text" value="${cust.fax }" id="fax" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();" dataType="LimitB"  max="40" msg="传真(长度小于40)"/>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>固定电话：</td>
		<td width="23"><input name="phoneNo" type="text" value="${cust.phoneNo }"  onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();"  dataType="LimitB"  max="40" msg="固定电话(长度小于40)"/></td>
		<td width="10%" align="center" nowrap>邮件：</td>
		<td width="23"><input name="email" type="text" value="${cust.email }" id="email" dataType="LimitB"  max="40" msg="邮件(长度小于40)"/>
		</td>
		<td width="10%" align="center" nowrap>归属区域：</td>
		<td width="24">
		<o:select name="zoneName" type="zone" value="${cust.zoneName}" headValue="" headText="---请选择---" id="zoneName" dataType="LimitB"  max="30" msg="归属区域(长度小于30)"></o:select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>信用级别：</td>
		<td width="23">
		<o:select name="creditLvl" type="creditLvl" value="${cust.creditLvl}" headValue="" headText="---请选择---" dataType="LimitB"  max="20" msg="信用级别(长度小于20)"></o:select>
		</td>
		<td width="10%" align="center" nowrap>结算方式：</td>
		<td width="23">
		<o:select headValue="" headText="---请选择---" value="${cust.payType}" name="payType" type="payType" id="payType" dataType="LimitB"  max="20" msg="结算方式(长度小于20)"></o:select>
		</td>
		<td width="10%" align="center" nowrap>发票类型：</td>
		<td width="24">
		<o:select headValue="" headText="---请选择---" value="${cust.billType}" name="billType" type="billType"  id="billType" dataType="LimitB"  max="20" msg="发票类型(长度小于20)"></o:select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>是否月结客户：</td>
		<td width="23">
		<o:select headValue="" headText="---请选择---" value="${cust.payCycle}" name="payCycle" type="payCycle"  dataType="LimitB"  max="20" msg="结算周期(长度小于20)"></o:select>
		</td>
		<td width="10%" align="center" nowrap>信用限额：</td>
		<td width="23"><input name="credit" type="text" value="${cust.credit }" id="credit" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();"  dataType="LimitB"  max="14"  msg="信用限额(长度小于14)"/>
		</td>
		<td width="10%" align="center" nowrap>客户类别：</td>
		<td width="24">
		<o:select headValue="" headText="---请选择---" value="${cust.custType}" name="custType" type="custType" id="custType" dataType="LimitB"  max="20" msg="客户类别(长度小于20)"></o:select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>公司网站:</td>
		<td width="23"><input name="webSite" type="text" value="${cust.webSite }" id="webSite" dataType="LimitB"  max="80" msg="公司网站(长度小于80)"/>
		</td>
		<td width="10%" align="center" nowrap>归属业务员：</td>
		<td width="23">
		<c:choose>
		<c:when test="${hasAllCustVisual==true}">
		<input name="userId" type="hidden" value="${cust.userId }" id="userId" />
		<input name="userName" type="text" value="${cust.userName}" id="userIdName" onfocus="blur()" title="选择归属业务员" readonly style="background:#cfcccc;cursor:hand;" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selOpr(this,'userId')"/>
		</c:when>
		<c:when test="${hasAllCustVisual==false}">
		<input name="userId" type="hidden"  id="userId" value="${user.idNo}"/>
		<input name="userName" type="text"  id="userIdName" value="${user.userName}"  disabled="disabled" dataType="LimitB"  max="40" msg="归属业务员(长度小于40)"/>
		</c:when>
		</c:choose>
		</td>
		<td width="10%" align="center" nowrap></td>
		<td width="24"></td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>客户地址：</td>
		<td colspan="5">
		<input type="text" name="address" value="${cust.address }" style="width:620px;" dataType="LimitB"  max="100" msg="客户地址(长度小于100)"/>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>简介及备注：</td>
		<td colspan="5" height="100">
		<textarea name="intro" style="width:100%;height:100%;" dataType="LimitB"  max="120" msg="简介及备注(长度小于120)">${cust.intro}</textarea>
		</td>
	</tr>
	</table>
</div></form>
<div style="background:#ddeeff">
<TABLE align="center"  style="background:#ddeeff;margin:0 0 0 0;padding:0 0 0 0;" border="0">
	<TR>
	<td width=100px align="right" style="background:#ddeeff">
		<input type="button" value="修改客户" name="editBtn" id="editBtn" >
	</td>
  </TR>
</TABLE>
</div>
</body>
</html>
