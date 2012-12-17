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
<title>现金收款记录</title>
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
	var url = "<%=path%>/finance/trad/viewtrad.htm?id="+id
	
	var iTop = (window.screen.availHeight-600)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-1000)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'checkOrderWin', 'height=600,width=1000,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}

function query(){
	$("#queryForm")[0].submit();
}
</script>
</head>
<body>
<div id="main" class="mainPanel wd txt_left">
	<div class="titleInfo">							
			<span style="font-weight:bold;font-size:22px;">现金收款记录</span><span style="font-size:15px;color:#cc0000;">(注意时间范围)</span>
			</div>
	<div class="queryArea">							
	<fieldset style="margin:5 10 5 10;">
	 <legend>查询区域</legend>
	 <form action="<%=path %>/finance/trad/cash.htm" method="GET" id="queryForm">
	 <input type="hidden" name="pageNo" id="pageNoId" value="${page.pageNo}"/>
	 <table width="100%">
	 	<tr>
	 		<td class="labelField" width="10%">起止时间≥:</td><td width="23%"><input style="width:87px;" type="text" name="uptime" id="orderTimePickerU" value="${q.uptime}"/>≤<input style="width:87px;" type="text" name="downtime" id="orderTimePickerD" value="${q.downtime}"/></td>
	 		<td class="labelField" width="10%">结算类型:</td>
	 		<td width="23%">
	 		<o:select name="payType" type="payType" headText="---请选择---" headValue="" value="${q.payType}"></o:select>
	 		</td>
	 		<td class="labelField" width="10%">是否OT单</td>
	 		<td>
	 		<o:select type="isOut" name="dispatchOther" id="dispatchOther" value="${q.dispatchOther}"></o:select>
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
					<th style="width:13%;padding-left:50px">
					订单编号
					</th>
					<th style="width:12%;border-right:none;">
					结算类型
					</th>
					<th style="width:14%;border-right:none;">
					结算时间
					</th>
					<th style="width:10%;">应收额</th>
					<th style="width:10%;">实收额</th>
					<th>结算人</th>
				</tr>
				<c:forEach items="${feeList}" var="fee" varStatus="status">
					<tr class="content_line <c:if test="${status.count%2==0}">even_row</c:if>" title="双击查看" ondblclick="viewOrder(${fee.orderid},'${fee.ordernum}')" onMouseOver="mouseOverFn(this)"  onmouseout="mouse_out_tr(this, '${status.count}')" onClick="select_tr(this,'${status.count}');">
						<td>${status.count}&nbsp;</td>
						<td>${fee.ordernum}</td>
						<td><o:dict type="payType" value="${fee.payType}"></o:dict> </td>
						<td><fmt:formatDate  value="${fee.tradTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate >&nbsp;</td>
						<td align="right"><fmt:formatNumber value="${fee.yfee}" pattern="0.00"/></td>
						<td align="right"><fmt:formatNumber value="${fee.acFee}" pattern="0.00"/></td>
						<td>${fee.opr}</td>
					</tr>
				</c:forEach>
				<tr class="content_line" style="color:#ff0000;font-weight:bold;" >
					<td colspan="4" style="text-align:right;font-weight:bold;" align="right">合计:</td>
					<td align="right"><fmt:formatNumber value="${totalObject.totalYfee}" pattern="0.00"/> 元</td>
					<td align="right"><fmt:formatNumber value="${totalObject.totalAcFee}" pattern="0.00"/>元</td>
					<td>&nbsp;</td>
				</tr>
				</tbody>
			</table>
		</div>
		<c:choose>
	   	<c:when test="${page.totalPages>0}">
	   	<div class="pagination">
			<script type="text/javascript">
			function goto(){
				var pageNo = $.trim($('#toPageNo').val());
				try{
					var i = parseInt(pageNo);
					if(i!=${page.pageNo}&&i>0&&i<=${page.totalPages}){
						gotoPage(i);
					}
				}catch(e){}
			}
			function gotoPage(pageNo){
				$("#pageNoId").val(pageNo);
				$("#queryForm")[0].submit();
			}
			</script>
			<ul>
			<c:if test="${page.pageNo>1}"><li class="nextpage"><a href="#" onclick="gotoPage(${page.pageNo-1})">« 上一页</a></li></c:if><c:if test="${page.pageNo==1}"><li class="disablepage">« 上一页</li></c:if>
			<li class="currentpage">&nbsp;${page.pageNo}/${page.totalPages}&nbsp;</li>
			<c:if test="${page.pageNo<page.totalPages}"><li class="nextpage"><a href="#" onclick="gotoPage(${page.pageNo+1})">下一页 »</a></li></c:if><c:if test="${page.pageNo==page.totalPages}"><li class="disablepage">下一页 »</li></c:if>
			<li class="disablepage">转到&nbsp;<input id="toPageNo"  onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();"  type="text" style="width:22px;height:14px;"/>&nbsp;页<input style="width:22px;height:14px;font-size:11px;" type="button" value="Go" name="gotoBtn" onclick="goto()"/></li>
			</ul>
		</div>
	   	</c:when>
	   	<c:otherwise>
	   	<div class="nodataInfo">
			<span>没有检索到数据</span>
		</div>
	   	</c:otherwise>
		</c:choose>
</div>
</div>
</body>
</html>