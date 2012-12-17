<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="rp" uri="/WEB-INF/report.tld"%><%@ taglib prefix="o" uri="/WEB-INF/omdp.tld"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<HEAD>
<title>营帐统计信息</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
	
<link rel="stylesheet" href="<%=path%>/pub/resources/libs/jqueryui/themes/redmond/jquery.ui.all.css">
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.widget.js"></script>
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.datepicker.js"></script>
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script src="<%=path%>/pub/resources/js/checkInput.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/list.css"/>
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
<style type="text/css">
		v\:* { Behavior: url(#default#VML) }
		.text{vnd.ms-excel.numberformat:@}
		
		.foot_cls td{
			border:0 solid;
			cellspaceing:0;
			margin:0 0 0 0;
			background:#c1c1c1;
			color:#cc1111;
			font-color:#cc0000;
			font-size:10px;
			font-weight:bold;
		}
</style>
<%@ include file = "/reportcss.jsp" %>
		
<script type="text/javascript">
$(function() {
	$("#orderTimePickerU").datepicker({changeYear: true,  
        showOn: 'button',   
        buttonImage: '<%=path%>/pub/resources/images/calendar.gif',   
        buttonImageOnly: true,  
        duration: 0,  
        showTime: true,  
        constrainInput: false,
        minDate: '-5y', maxDate: '+5y'});
	$("#orderTimePickerD").datepicker({changeYear: true,  
        showOn: 'button',   
        buttonImage: '<%=path%>/pub/resources/images/calendar.gif',   
        buttonImageOnly: true,  
        duration: 0,  
        showTime: true,  
        constrainInput: false,
        minDate: '-5y', maxDate: '+5y'});
});

/*
以下2函数实现鼠标点击表格中某行之后，该行改变颜色
select_tr：选中某行，改变颜色在onClick中调用
mouse_out_tr:移出某行，改变颜色在onMouseOut中调用
mouse_over_tr:鼠标指针移到某行，改变颜色，在onMouseOver中调用
**/
var sel_tr = null;
var sel_id = null;
function mouseOverFn(tr){
	$(tr).addClass('mouseOverCls')
}
function select_tr(tr,i)
{	
	$(sel_tr).removeClass('selCls');
	
	sel_id = i;
	sel_tr = tr;
	$(tr).addClass('selCls');
}

function mouse_out_tr(tr,i){
	$(tr).removeClass('mouseOverCls')
}

var isNumber = function (e) {  
    if ($.browser.msie) {  
        if ( ((event.keyCode > 47) && (event.keyCode < 58)) ||  
              (event.keyCode == 8) ) {  
            return true;  
        } else {  
            return false;  
        }  
    } else {  
        if ( ((e.which > 47) && (e.which < 58)) ||  
              (e.which == 8) ) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
}

function query(){
	$("#queryForm")[0].submit();
}
</script>
</head>
<body>
<div id="main" class="mainPanel wd txt_left">
	<div class="titleInfo">							
			<h3 class="left">营帐统计信息</h3><span class="addrtitle b_size"></span>
			</div>
	<div class="queryArea">							
	<fieldset style="margin:5 10 5 10;">
	 <legend>查询区域</legend>
	 <form action="<%=path %>/finance/stat/ifee.htm" method="GET" id="queryForm">
	 <input type="hidden" name="pageNo" id="pageNoId" value="${page.pageNo}"/>
	 <table width="100%">
	 	<tr>
	 		<td class="labelField" width="10%">营帐周期≥:</td><td width="24%"><input style="width:87px;" type="text" name="uptime" id="orderTimePickerU" value="${q.uptime}"/>≤<input style="width:87px;" type="text" name="downtime" id="orderTimePickerD" value="${q.downtime}"/></td>
	 		<td class="labelField" width="12%">结算类型:</td>
	 		<td width="24%">
	 		<o:select name="payType" type="payType" headText="---请选择---" headValue="" value="${q.payType}"></o:select>
	 		</td>
	 		<td class="labelField" width="10%">是否OT单</td>
	 		<td>
	 		<o:select type="isOut" name="dispatchOther" id="dispatchOther" value="${q.dispatchOther}"></o:select>
	 		</td>
	 	</tr>
	 	<tr>
	 		<td class="labelField" width="10%">状态:</td>
	 		<td>
	 		<o:select type="ifeeType" name="ifeeType" headText="---请选择---" headValue="" value="${q.ifeeType}"></o:select>
	 		</td>
	 		<td class="labelField">是否月结:
	 		</td><td>
	 		<o:select headValue="" headText="---请选择---" value="${q.ext1}" name="ext1" id="ext1" type="payCycleType"></o:select>
	 		</td>
	 		<td class="labelField"></td><td width="24%">&nbsp;<input type="button" value="查 询" onclick="query()"/></td>
	 	</tr>
	 </table></form>
	</fieldset>	
	</div>
<div style="width:100%;">
	<div id="reportDiv">
		<rp:report model="report" width="col-sum" alt="<span style='font-size:x-small'>没有相关数据</span>"/><br/>
	</div>
</div>
</div>
</body>
</html>