<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title>OMAX|深圳欧美数码印务有限公司管理系统V2.0</title>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=path%>/pub/resources/libs/dhtmlxtree/dhtmlxtree.css"></link>
<script  src="<%=path%>/pub/resources/libs/dhtmlxtree/dhtmlxcommon.js"></script>
<script  src="<%=path%>/pub/resources/libs/dhtmlxtree/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/resources/css/ext-all.css" /> 
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/resources/css/xtheme-gray.css" /> 
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/ux/css/PanelResizer.css" /> 

<!-- GC --> 
<!-- LIBS --> 
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-base.js"></script> 
<!-- ENDLIBS --> 
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-all.js"></script> 
    
<style type="text/css">
html,body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,p,blockquote,th,td{margin:0;padding:0;}img,body,html{border:0;}address,caption,cite,code,dfn,em,strong,th,var{font-style:normal;font-weight:normal;}ol,ul {list-style:none;}caption,th {text-align:left;}h1,h2,h3,h4,h5,h6{font-size:100%;}q:before,q:after{content:'';}.ext-el-mask {
    z-index: 100;
    position: absolute;
    top:0;
    left:0;
    -moz-opacity: 0.5;
    opacity: .50;
    filter: alpha(opacity=50);
    width: 100%;
    height: 100%;
    zoom: 1;
}
body{
	background: transparent;
	border: 0px;
	font-size: 100%;
	margin: 0px;
	outline: 0px;
	padding: 0px;
	vertical-align: baseline;
}
#headerbar{
	background: url(<%=path %>/pub/resources/images/head_bg.png);
	height: 83px;
	width: 100%;
}
#top_logo{
	float:left;
}
#right_opr{
	float:right;
	margin:6 20 0 0;
}
#right_opr a img{
	width:64px;
	height:64px;
	border:none;
}
#right_opr a:hover img{
	width:66px;
	height:66px;
	border:none;
}
.welcome_msg{
	font-size:11px;
	font-weight:bold;
	position: absolute;
	margin:50 0 0 701;
	color:#ffafaf;
}
</style>
<script language="JavaScript">
var LEFT_MENU_VIEW=0;
function leftmenu_open(){
   LEFT_MENU_VIEW=0;
   leftmenu_ctrl();
}

function leftmenu_ctrl(){
   if(LEFT_MENU_VIEW==0)   {
   	  document.getElementById('left_tree_menu').style.display="none";
      LEFT_MENU_VIEW=1;
   } else {
   	  document.getElementById('left_tree_menu').style.display="block";
      LEFT_MENU_VIEW=0;
   }
}

function setPointer(theRow, thePointerColor){
    if (typeof(theRow.style) == 'undefined' || typeof(theRow.cells) == 'undefined') {
        return false;
    }

    var row_cells_cnt=theRow.cells.length;
    for (var c = 0; c < row_cells_cnt; c++) {
        theRow.cells[c].bgColor = thePointerColor;
    }

    return true;
}

function viewHelp(){
	
	window.open("<%=path%>/help/index.jsp");
}

$(function(){
	$("#welcome-msg").fadeIn(2300);
})

var tree = null;
Ext.onReady(function(){
	
	var view = new Ext.Viewport({
		frame:false,
		layout:'border',
		items:[{
			region:'north',
			height:70,
			autoScroll:false,
			html:'<div id="headerbar"><img id="top_logo" src="<%=path %>/pub/resources/images/tbar.png"/></div>'
		},{
			region:'west',
			split:true,
			width:208,
			maxWidth:240,
			autoScroll:true,
			collapseMode:'mini',
			html:'<div id="treeboxbox_tree0" style="width:100%;height:100%;"></div>',
			listeners:{
				'afterRender':function(){
					tree = new dhtmlXTreeObject("treeboxbox_tree0", "100%", "100%", 0);
					tree.setSkin('vista');
					tree.setImagePath("<%=path%>/pub/resources/libs/dhtmlxtree/imgs/csh_vista/");
					tree.loadXML("<%=path%>/nav_menu.htm");
					tree.setOnClickHandler(tonclick);
					tree.setOnOpenHandler(tonopen);
				}
			}
		},{
			region:'center',
			html:'<iframe id="content_win" frameborder="0" width="100%" height="100%" src="<%=path %>/home.htm"></iframe>'
		}]
		
	});
	
	
});

</script>
</head>
<body style="overflow-x:hidden;">
<div class="welcome_msg" id="welcome-msg" style="display:none;">${welcome_msg}</div>

<script>

function tonclick(id,node) {
	var src = tree.getUserData(id,"src");
	if(id=="system_root"){
		document.getElementById("content_win").src="<%=path%>/home.htm";
	}
    else if(id=="sys_about_info"){
    	document.getElementById("content_win").src="<%=path%>/about.jsp";
    }
    else if(id=="quit"){
    	if(confirm("确定退出系统吗？")){
    		document.location.replace("<%=path%>/logout");
    	}
    }
    else if(src){
    	document.getElementById("content_win").src="<%=path%>"+src;
    }
}
function tonopen(id, mode) {
    return true;
}

document.onkeydown = function () {
	 var e = event || window.event;
	 var keyASCII = parseInt(e.keyCode, 10);
	 var src = e.srcElement;
	 var tag = src.tagName.toUpperCase();
	 if(keyASCII == 13) {
	  return false;
	 }
	 if(keyASCII == 8) {
	  if(src.readOnly || src.disabled || (tag != "INPUT" && tag != "TEXTAREA")) {
	   return false;
	  }
	  if(src.type) {
	   var type = ("" + src.type).toUpperCase();
	   return type != "CHECKBOX" && type != "RADIO" && type != "BUTTON";
	  }
	 }
	 return true;
}

document.oncontextmenu = function(){
	return false;
}

</script>
</body>
</html>