<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><html>
<HEAD>
<title>业务参数设置</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<link rel="stylesheet" href="<%=path%>/pub/resources/libs/jqueryui/themes/redmond/jquery.ui.all.css">
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/pub/resources/js/common.js"></script>
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.widget.js"></script>
<script src="<%=path%>/pub/resources/js/checkInput.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/list.css"/>
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
<script type="text/javascript">
function resetForm(){
	$('#userName').val("");
	$('#userAccount').val("");
}

function query(){
	$("#queryForm")[0].submit();
}

</script>
</head>
<body>
<div id="main" class="mainPanel wd txt_left">
	<div class="titleInfo">							
			<h3 class="left">业务参数设置</h3><span class="addrtitle b_size"></span>					
			</div>
	<div class="typeArea">							
	
	</div>
			<div style="width:100%;">
			<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;table-layout:fixed;">
				<tbody><tr>
					<th style="width:7%;">序号</th>
					<th style="width:11%;padding-left:50px">
					参数编码
					</th>
					<th style="width:14%;">
					参数名称
					</th>
					<th style="width:24%;border-right:none;">
					参数说明
					</th>
					<th>
					操作
					</th>
				</tr>
				<c:forEach items="${userList}" var="user" varStatus="status">
					<tr class="content_line <c:if test="${status.count%2==0}">even_row</c:if>" title="双击查看" onMouseOver="mouseOverFn(this)"  onmouseout="mouse_out_tr(this, '${status.count}')" onClick="select_tr(this,'${status.count}');">
						<td>${status.count}&nbsp;</td>
						<td>${user.idNo}&nbsp;</td>
						<td>${user.userAccount}&nbsp;</td>
						<td>${user.userName}&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</c:forEach>
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
</body>
</html>