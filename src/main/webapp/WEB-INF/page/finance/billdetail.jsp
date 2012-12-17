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
<title>客户对账单</title>
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

function viewCust(id){
	var url = "<%=path%>/customer/customerManage/viewcustByCustId.htm?custId="+id
	
	var iTop = (window.screen.availHeight-430)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-770)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'viewCustWin', 'height=450,width=850,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
	
}
</script>
</head>
<body>
<div id="main" class="mainPanel wd txt_left">
	<div class="titleInfo">							
			<span style="font-weight:bold;font-size:22px;">月结客户对账单查询</span><span style="font-size:15px;color:#cc0000;">(注意时间范围)</span>
			</div>
	<div class="queryArea">							
	<fieldset style="margin:5 10 5 10;">
	 <legend>查询区域</legend>
	 <form action="<%=path %>/finance/bill/billlist.htm" method="GET" id="queryForm">
	 <table width="100%">
	 	<tr>
	 		<td class="labelField" width="10%">起止时间≥:</td><td width="23%"><input style="width:87px;" type="text" name="uptime" id="orderTimePickerU" value="${q.uptime}"/>≤<input style="width:87px;" type="text" name="downtime" id="orderTimePickerD" value="${q.downtime}"/></td>
	 		<td class="labelField" width="10%">客户姓名:</td>
	 		<td width="23%">
	 		<input type="text" id="custName" name="custName" value="${q.custName}"/>
	 		</td>
	 		<td class="labelField" width="10%">客户手机号</td>
	 		<td>
	 		<input type="text" id="mobileNo" name="mobileNo" value="${q.mobileNo}" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();"/>
	 		</td>
	 	</tr>
	 	<tr>
	 	<td colspan="5">&nbsp;</td>
	 	<td><input type="button" value="查 询" onclick="query()"/></td>
	 	</tr>
	 </table></form>
	</fieldset>	
</div>
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;table-layout:fixed;">
				<tbody><tr>
					<th style="width:5%;">序号</th>
					<th style="width:14%;padding-left:50px">
					客户编号
					</th>
					<th style="width:21%;border-right:none;">
					客户姓名
					</th>
					<th style="width:15%;border-right:none;">
					已完工未结算工单数
					</th>
					<th style="width:10%;">金额</th>
					<th style="width:10%;">实收额</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${billList}" var="order" varStatus="status">
					<tr class="content_line <c:if test="${status.count%2==0}">even_row</c:if>" onMouseOver="mouseOverFn(this)"  onmouseout="mouse_out_tr(this, '${status.count}')" onClick="select_tr(this,'${status.count}');">
						<td>${status.count}&nbsp;</td>
						<td><a href="#"  onclick="viewCust('${order.custId}')">${order.custId}</a></td>
						<td>${order.custName}</td>
						<td align="right">${order.totalOrderCount}</td>
						<td align="right">${order.totalFee}</td>
						<td>&nbsp;</td>
						<td><a href="#">查看详细</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
</div>
</div>
</body>
</html>