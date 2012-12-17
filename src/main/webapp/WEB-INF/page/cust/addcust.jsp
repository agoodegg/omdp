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
	<title>增加新客户</title>
	<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
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
	
	<script src="<%=path%>/pub/resources/js/checkInput.js"></script>
<style type="text/css">
.busiTd{
	border:solid 2px #FFFFFF;
	cursor:hand;
}
</style>
<script type="text/javascript">

$(function(){
	var data = window.dialogArguments;
	$('#shortName').val(data.custName);
	
	$("#btnSave").bind("click",function(){
		if(Validator.Validate($('#custForm')[0], 2)){
			$("body").mask("信息提交中...");
			$.ajax({
			    url: '<%=path %>/customer/customerManage/saveCust.htm',
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
				    		window.returnValue=json.cust;
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
<form method="post" id="custForm" class="form" action="<%=path %>/customer/customerManage/saveCust.htm">
	<table id="mytable"  width="100%"  bgcolor="#ddeeff" border="0" bordercolor="#CCCCCC" align="center" cellpadding="0" cellspacing="0" hspace="0" vspace="0" style="border-collapse:collapse;" class="small">
	<th colspan="6"><span style="float:left;">新增客户</span></th>
	<tr>
		<td width="10%" align="center" nowrap>客户名称：</td>
		<td width="23"><input name="shortName" type="text" value="" id="shortName" dataType="LimitB" min="1"  max="40" msg="客户名称(非空,长度小于40)"/>
		</td>
		<td width="10%" align="center" nowrap>客户拼音：</td>
		<td width="23"><input name="pinyin" type="text" value="" id="pinyin"  dataType="English2"  max="40" msg="客户名字拼音(英文字母,长度小于40)" />
		</td>
		<td width="10%" align="center">客户编号：</td>
		<td width="24%">
		<input name="custId" type="text" readonly style="background:#cccccc;color:#ff0000;" value="编号自动生成..." onfocus="blur();">
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>联系人：</td>
		<td width="23"><input name="fullName" type="text" value="" dataType="LimitB" min="1"  max="40" msg="客户联系人,主要针对公司客户,个人客户则与客户名称相同(非空,长度小于40)"/></td>
		<td width="10%" align="center" nowrap>联系电话：</td>
		<td width="23"><input name="mobileNo" type="text" value="" id="mobileNo" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();" dataType=PhoneOrMobile2 min="1"  max="40" msg="联系电话(非空,长度小于20)"/>
		</td>
		<td width="10%" align="center" nowrap>传真：</td>
		<td width="24"><input name="fax" type="text" value="" id="fax" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();" />
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>固定电话：</td>
		<td width="23"><input name="phoneNo" type="text" value=""  onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();"  dataType="LimitB"  max="40" msg="固定电话(长度小于40)"/></td>
		<td width="10%" align="center" nowrap>邮件：</td>
		<td width="23"><input name="email" type="text" value="" id="email" dataType="LimitB"  max="40" msg="邮件(长度小于40)"/>
		</td>
		<td width="10%" align="center" nowrap>归属区域：</td>
		<td width="24">
		<o:select name="zoneName" type="zone" headValue="" headText="---请选择---" id="zoneName" dataType="LimitB"  max="30" msg="归属区域(长度小于30)"></o:select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>信用级别：</td>
		<td width="23">
		<o:select name="creditLvl" type="creditLvl" headValue="" headText="---请选择---" dataType="LimitB"  max="20" msg="信用级别(长度小于20)"></o:select>
		</td>
		<td width="10%" align="center" nowrap>结算方式：</td>
		<td width="23">
		<o:select headValue="" headText="---请选择---" name="payType" type="payType" id="payType" dataType="LimitB"  max="20" msg="结算方式(长度小于20)"></o:select>
		</td>
		<td width="10%" align="center" nowrap>发票类型：</td>
		<td width="24">
		<o:select headValue="" headText="---请选择---" name="billType" type="billType"  id="billType" dataType="LimitB"  max="20" msg="发票类型(长度小于20)"></o:select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>是否月结客户：</td>
		<td width="23">
		<o:select headValue="0" headText="---请选择---" name="payCycle" type="payCycle"  dataType="LimitB"  max="20" msg="是否月结客户(长度小于20)"></o:select>
		</td>
		<td width="10%" align="center" nowrap>信用限额：</td>
		<td width="23"><input name="credit" type="text" value="" id="credit" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();"  dataType="LimitB"  max="14"  msg="信用限额(长度小于14)"/>
		</td>
		<td width="10%" align="center" nowrap>客户类别：</td>
		<td width="24">
		<o:select headValue="" headText="---请选择---" name="custType" type="custType" id="custType" dataType="LimitB"  max="20" msg="客户类别(长度小于20)"></o:select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>公司网站:</td>
		<td width="23"><input name="webSite" type="text" value="" id="webSite"/>
		</td>
		<td width="10%" align="center" nowrap>归属业务员：</td>
		<td width="23">
		<c:choose>
		<c:when test="${hasAllCustVisual==true}">
		<input name="userId" type="hidden" value="" id="userId" />
		<input name="userName" type="text" value="" id="userIdName" onfocus="blur()" title="选择归属业务员" readonly style="background:#cfcccc;cursor:hand;" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selOpr(this,'userId')"/>
		</c:when>
		<c:when test="${hasAllCustVisual==false}">
		<input name="userId" type="hidden" id="userId" value="${user.idNo}"/>
		<input name="userName" type="text" id="userName" value="${user.userName}" disabled="disabled"/>
		</c:when>
		</c:choose>
		</td>
		<td width="10%" align="center" nowrap></td>
		<td width="24"></td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>客户地址：</td>
		<td colspan="5">
		<input type="text" name="address" style="width:620px;" dataType="LimitB"  max="100" msg="客户地址(长度小于100)"/>
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>简介及备注：</td>
		<td colspan="5" height="100">
		<textarea name="intro" style="width:100%;height:100%;" dataType="LimitB"  max="200" msg="简介及备注(长度小于200)"></textarea>
		</td>
	</tr>
	</table>
<div style="background:#ddeeff">
<TABLE align="center"  style="background:#ddeeff;margin:0 0 0 0;padding:0 0 0 0;" border="0">
	<TR>
	<td width=100px align="right" style="background:#ddeeff">
		<input type="button" value="新增客户" name="btnSave" id="btnSave" >
	</td>
  </TR>
</TABLE>
</div>
</form>
</body>
</html>
