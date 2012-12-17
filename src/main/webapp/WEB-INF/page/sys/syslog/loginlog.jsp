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
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
<script type="text/javascript">
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

</script>
<style type="text/css">
.content_line{
	cursor:hand;
}
.content_line td{
	background:#FFFFFF;
}

.even_row{
	background:#CCFFFF;
}

.even_row td{
	background:#CCFFFF;
}
.mouseOverCls{
	background:#FFFFBB;
}
.mouseOverCls td{
	background:#FFFFBB;
}
.selCls{
	background:#FFCCCC;
}
.selCls td{
	background:#FFCCCC;
}
</style>
</head>
<body>
<div id="main" class="mainPanel wd txt_left">
	<div class="titleInfo">							
			<h3 class="left">系统登录日志</h3><span class="addrtitle b_size"></span>					
			</div>
			<div name="status" class="content b_size">
			<div style="width:100%;">
			<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;table-layout:fixed;">
								<caption style="text-align:right" class="f_size" colspan="4">
			<div class="pagination" style="display:block;">
					<c:if test="${page.pageNo>1}"><a href="<%=path %>/sys/syslog/queryLogin.htm?pageNo=${page.pageNo-1}">上一页</a></c:if><c:if test="${page.pageNo==1}">上一页</c:if>
					&nbsp;${page.pageNo}/${page.totalPages}&nbsp;
					<c:if test="${page.pageNo<page.totalPages}"><a href="<%=path %>/sys/syslog/queryLogin.htm?pageNo=${page.pageNo+1} ">下一页</a></c:if><c:if test="${page.pageNo==page.totalPages}">下一页</c:if>
				</div>
	</caption>
				<tbody><tr>
					<th style="width:11%;">序号</th>
					<th style="width:15%;padding-left:50px">
					<a href="#syslog/operlog/time,asc">登录帐号</a>
					</th>
					<th style="width:10%;">
					<a href="#syslog/operlog/operation,asc">登录类型</a>
					</th>
					<th style="width:19%;border-right:none;">
					<a href="#syslog/operlog/operator,asc">时间</a>
					</th>
					<th style="width:12%;">IP地址</th>
					<th style="width:33%;">备注信息</th>
				</tr>
				<c:forEach items="${logList}" var="log" varStatus="status">
					<tr class="content_line <c:if test="${status.count%2==0}">even_row</c:if>" title="双击查看" onMouseOver="mouseOverFn(this)"  onmouseout="mouse_out_tr(this, '${status.count}')" onClick="select_tr(this,'${status.count}');">
						<td>${status.count}&nbsp;</td>
						<td>${log.userName}&nbsp;</td>
						<td><o:dict type="logType" value="${log.logType}"></o:dict>&nbsp;</td>
						<td><fmt:formatDate  value="${log.logTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate >&nbsp;</td>
						<td>${log.ipAddress}&nbsp;</td>
						<td>${log.addInfo}&nbsp;</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>			
	</div>
</div>
</body>
</html>