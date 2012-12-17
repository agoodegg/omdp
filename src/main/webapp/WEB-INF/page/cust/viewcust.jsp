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
	<title>查看客户信息</title>
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

	$("#closeBtn").bind("click",function(){
		window.close();
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
	var result = window.showModalDialog("<%=path%>/sys/employeeManage/selEmployee.htm?mode=single&f=id",ids,"dialogWidth=470px;dialogHeight=640px");
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
			<h3 class="left">查看客户详细信息</h3><span class="addrtitle b_size"></span>					
			</div>

<form method="post" id="custForm" class="form" action="<%=path %>/customer/customerManage/saveCust.htm">
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;margin:0 0 0 0;padding:0 0 0 0;">
	<tr>
		<td width="10%" align="center" nowrap>客户名称：</td>
		<td width="23%">${cust.shortName}
		</td>
		<td width="10%" align="center" nowrap>客户拼音：</td>
		<td width="23%">${cust.pinyin}&nbsp;
		</td>
		<td width="10%" align="center">客户编号：</td>
		<td width="24%">
		${cust.custId}
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>联系人：</td>
		<td width="23%">${cust.fullName}&nbsp;</td>
		<td width="10%" align="center" nowrap>联系电话：</td>
		<td width="23%">${cust.mobileNo}&nbsp;
		</td>
		<td width="10%" align="center" nowrap>传真：</td>
		<td width="24%">${cust.fax}&nbsp;
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>固定电话：</td>
		<td width="23%">${cust.phoneNo}&nbsp;</td>
		<td width="10%" align="center" nowrap>邮件：</td>
		<td width="23%">${cust.email}&nbsp;
		</td>
		<td width="10%" align="center" nowrap>归属区域：</td>
		<td width="24%"><o:dict type="zone" value="${cust.zoneName}"></o:dict>&nbsp;
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>信用级别：</td>
		<td width="23%">
		<o:dict type="creditLvl" value="${cust.creditLvl}"></o:dict>&nbsp;
		</td>
		<td width="10%" align="center" nowrap>结算方式：</td>
		<td width="23%">
		<o:dict type="payType" value="${cust.payType}"></o:dict>&nbsp;
		</td>
		<td width="10%" align="center" nowrap>发票类型：</td>
		<td width="24%">
		<o:dict type="billType" value="${cust.billType}"></o:dict>&nbsp;
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>是否月结客户：</td>
		<td width="23%">
		<o:dict type="payCycle" value="${cust.payCycle}"></o:dict>&nbsp;
		</td>
		<td width="10%" align="center" nowrap>信用限额：</td>
		<td width="23%">${cust.credit}&nbsp;
		</td>
		<td width="10%" align="center" nowrap>客户类别：</td>
		<td width="24%">
		<o:dict type="custType" value="${cust.custType}"></o:dict>&nbsp;
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>公司网站:</td>
		<td width="23%">${cust.webSite}&nbsp;
		</td>
		<td width="10%" align="center" nowrap>归属业务员：</td>
		<td width="23%">
		${cust.userName}&nbsp;
		</td>
		<td width="10%" align="center" nowrap></td>
		<td width="24%"></td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>客户地址：</td>
		<td colspan="5">${cust.address }&nbsp;
		</td>
	</tr>
	<tr>
		<td width="10%" align="center" nowrap>简介及备注：</td>
		<td colspan="5" height="100">${cust.intro }&nbsp;
		</td>
	</tr>
	</table>
</div></form>
<div style="background:#ddeeff">
<TABLE align="center"  style="background:#ddeeff;margin:0 0 0 0;padding:0 0 0 0;" border="0">
	<TR>
	<td width=100px align="right" style="background:#ddeeff">
		<input type="button" value="关闭" name="btnSave" id="closeBtn" >
	</td>
  </TR>
</TABLE>
</div>
</body>
</html>
