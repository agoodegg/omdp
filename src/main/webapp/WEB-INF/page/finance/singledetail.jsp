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

function viewOrder(id,idnum){
	
	var url = "<%=path%>/order/newOrder/vieworder.htm?id="+id
	
	var iTop = (window.screen.availHeight-600)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-1000)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'viewOrderWin', 'height=600,width=1000,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
	
}

function exportExcel(){
	document.getElementById('exportForm').submit();
}
</script>
</head>
<body>
<div id="main" class="mainPanel wd txt_left">
<form action="<%=path%>/finance/bill/singleDetailExport.htm" method="POST" id="exportForm" target="temp">
<input type="hidden" name="uptime" value="${p.uptime}"/>
<input type="hidden" name="downtime" value="${p.downtime}"/>
<input type="hidden" name="custId" value="${p.custId}"/>
</form>
	<div class="titleInfo">							
		<span style="font-weight:bold;font-size:22px;">对账明细<input type="button" value="导出为Excel" onclick="exportExcel()"/></span>
	</div>
	<div class="queryArea">							
	<fieldset style="margin:5 10 5 10;">
	 <legend></legend>
	 客户姓名:${cust.shortName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户电话:${cust.mobileNo}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户地址:${cust.address}
	</fieldset>	
</div>
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;table-layout:fixed;">
				<tbody><tr>
					<th style="width:8%;">序号</th>
					<th style="width:14%;padding-left:50px">
					单号
					</th>
					<th style="width:16%;border-right:none;">
					下单时间
					</th>
					<th style="width:16%;border-right:none;">
					完工时间
					</th>
					<th style="width:20%;">业务名称</th>
					<th>金额</th>
				</tr>
				<c:forEach items="${billList}" var="order" varStatus="status">
					<tr class="content_line <c:if test="${status.count%2==0}">even_row</c:if>" title="双击查看" ondblclick="viewOrder(${order.id},'${order.ordernum}')" onMouseOver="mouseOverFn(this)"  onmouseout="mouse_out_tr(this, '${status.count}')" onClick="select_tr(this,'${status.count}');">
						<td>${status.count}&nbsp;</td>
						<td><a href="#"  onclick="viewOrder(${order.id},'${order.ordernum}')">${order.ordernum}</a></td>
						<td><fmt:formatDate  value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate >&nbsp;</td>
						<td><fmt:formatDate  value="${order.doneTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate >&nbsp;</td>
						<td>${order.orderTitle}</td>
						<td>${order.price}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
</div>
</div>
<div style="display:none;"><iframe id="temp" name="temp"></iframe></div>
</body>
</html>