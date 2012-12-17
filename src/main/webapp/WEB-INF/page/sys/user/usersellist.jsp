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
	
<link rel="stylesheet" href="<%=path%>/pub/resources/libs/jqueryui/themes/redmond/jquery.ui.all.css">
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.widget.js"></script>
<script src="<%=path%>/pub/resources/js/checkInput.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>

<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
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

function resetForm(){
	$('#userName').val("");
	$('#userAccount').val("");
}

function query(){
	$("#queryForm")[0].submit();
}


function userRole(id){
	var url = "<%=path%>/sys/employeeManage/toUserRole.htm?id="+id
	
	var iTop = (window.screen.availHeight-590)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-790)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'userRole', 'height=590,width=790,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}

function sel(){
	
	var sel = [];
	var selName = [];
	document.getElementById('inlist').contentWindow.$("input[id^='U_']").each(function(){
		if($(this)[0].checked){
			sel.push($(this)[0].id.split('_')[1]);
			selName.push($(this)[0].value);
		}
	});
<c:if test="${mode=='single'}">
	if(sel.length>1||selName.length>1){
		alert("只能选择一个！");
		return false;
	}
</c:if>
	window.returnValue={'ids':sel.join(','),'names':selName.join(';')};
	window.close();
}

function init(){
	$("#ids").val(window.dialogArguments);
	query();
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



/*Credits: Dynamic Drive CSS Library */
/*URL: http://www.dynamicdrive.com/style/ */

.pagination{
width:100%;
padding: 2px;
}

.pagination ul{
margin: 0;
padding: 0;
text-align: right; /*Set to "right" to right align pagination interface*/
font-size: 12px;
}

.pagination li{
list-style-type: none;
display: inline;
padding-bottom: 1px;
}

.pagination li input{
	font-size: 11px;
}

.pagination a, .pagination a:visited{
padding: 0 5px;
border: 1px solid #9aafe5;
text-decoration: none;
color: #2e6ab1;
}

.pagination a:hover, .pagination a:active{
border: 1px solid #2b66a5;
color: #000;
background-color: lightyellow;
}

.pagination li.currentpage{
font-weight: bold;
padding: 0 5px;
border: 1px solid navy;
background-color: #2e6ab1;
color: #FFF;
}

.pagination li.disablepage{
padding: 0 5px;
border: 1px solid #929292;
color: #929292;
}

.pagination li.nextpage{
font-weight: bold;
}

* html .pagination li.currentpage, * html .pagination li.disablepage{ /*IE 6 and below. Adjust non linked LIs slightly to account for bugs*/
margin-right: 5px;
padding-right: 0;
}

.queryArea{
	font-size:13px;
	color:#0000FF;
}
.queryArea table{
	font-size:13px;
	color:#6363FF;
	font-weight:bold;
}
.labelField{
	text-align:right;
}
.nodataInfo{
	width:100%;
	font-size:12px;
	text-align:center;
	color:#ff0000;
}

.nodataInfo span{
	text-align:center;
	color:#ff0000;
}
</style>
</head>
<body onload="init()">
<div id="main" class="mainPanel wd txt_left">
	<div class="titleInfo">							
			<h3 class="left">用户列表</h3><span class="addrtitle b_size">(勾选需要分配的角色)</span>					
			</div>
	<div class="queryArea">							
	<fieldset style="margin:5 10 5 10;">
	 <legend>查询区域</legend>
	 <form action="<%=path %>/sys/employeeManage/selEmployeeIn.htm" method="GET" id="queryForm" target="inlist">
	 <input type="hidden" name="ids" id="ids" value=""/>
	 <input type="hidden" name="f" id="f" value="${f}"/>
	 <input type="hidden" name="pageNo" id="pageNoId" value="${page.pageNo}"/>
	 <table width="100%">
	 	<tr>
	 		<td class="labelField" width="18%">用户名称:</td><td width="16%"><input id="userName" type="text" name="userName" value="${q.userName}"/></td>
	 		<td width="34%"><input type="button" value="查 询" onclick="query()"/>&nbsp;&nbsp;<input type="button" value="确 定" onclick="sel()"/></td>
	 	</tr>
	 </table></form>
	</fieldset>	
	</div>
	<iframe id="inlist" name="inlist" src="" width="100%" height="100%" frameborder="0"></iframe>		
</div>
</body>
</html>