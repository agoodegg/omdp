<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="o" uri="/WEB-INF/omdp.tld"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><HTML>
<HEAD>
<TITLE>发布新公告</TITLE>
<META content=数码印刷，数码打样设计，制作广告,装饰包装 name=Keywords>
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/libs/cleditor/jquery.cleditor.css" />
<script type="text/javascript" src="<%=path %>/pub/resources/libs/cleditor/jquery.cleditor.min.js"></script>

	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	<link rel="stylesheet" href="<%=path%>/pub/resources/libs/jqueryui/themes/redmond/jquery.ui.all.css">
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.core.js"></script>
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.widget.js"></script>
	<script src="<%=path%>/pub/resources/libs/jqueryui/ui/jquery.ui.datepicker.js"></script>
	
<script language="javascript">
function CheckForm()
{ 
	var errMsg = [];
	if($.trim($('#title').val())=="")
	{
		errMsg.push("公告标题不能为空\n");
	}
	if($.trim($('#buType').val())=="")
	{
		errMsg.push("公告类型不能为空\n");
	}
	if($.trim($('#content').val())=="")
	{
		errMsg.push("公告内容不能为空\n");
	}
	if($.trim($("#endTimeStr").val())==""){
		errMsg.push("截止时间不能为空\n");
	}
	
	if(errMsg.length>0){
		alert(errMsg.join(''));
		return false;
	}
	
	
	return true;
}


$(document).ready(function(){
	$("#endTimeStr").datepicker({changeYear: true,  
        showOn: 'button',   
        buttonImage: '<%=path%>/pub/resources/images/calendar.gif',   
        buttonImageOnly: true,  
        duration: 0,  
        showTime: true,  
        constrainInput: false,
        minDate: '-5y', maxDate: '+5y'});
        
    $("#content").cleditor({
      width:        650, // width not including margins, borders or padding
      height:       250, // height not including margins, borders or padding
      controls:     // controls to add to the toolbar
                    "bold italic underline strikethrough subscript superscript | font size " +
                    "style | color highlight removeformat | bullets numbering | outdent " +
                    "indent | alignleft center alignright justify | undo redo | " +
                    "rule image link unlink | cut copy paste pastetext | print source",
      colors:       // colors in the color popup
                    "FFF FCC FC9 FF9 FFC 9F9 9FF CFF CCF FCF " +
                    "CCC F66 F96 FF6 FF3 6F9 3FF 6FF 99F F9F " +
                    "BBB F00 F90 FC6 FF0 3F3 6CC 3CF 66C C6C " +
                    "999 C00 F60 FC3 FC0 3C0 0CC 36F 63F C3C " +
                    "666 900 C60 C93 990 090 399 33F 60C 939 " +
                    "333 600 930 963 660 060 366 009 339 636 " +
                    "000 300 630 633 330 030 033 006 309 303",    
      fonts:        // font names in the font popup
                    "Arial,Arial Black,Comic Sans MS,Courier New,Narrow,Garamond," +
                    "Georgia,Impact,Sans Serif,Serif,Tahoma,Trebuchet MS,Verdana",
      sizes:        // sizes in the font size popup
                    "1,2,3,4,5,6,7",
      styles:       // styles in the style popup
                    [["Paragraph", "<p>"], ["Header 1", "<h1>"], ["Header 2", "<h2>"],
                    ["Header 3", "<h3>"],  ["Header 4","<h4>"],  ["Header 5","<h5>"],
                    ["Header 6","<h6>"]],
      useCSS:       false, // use CSS to style HTML when possible (not supported in ie)
      docType:      // Document type contained within the editor
                    '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">',
      docCSSFile:   // CSS file used to style the document contained within the editor
                    "", 
      bodyStyle:    // style to assign to document body contained within the editor
                    "margin:4px; font:10pt Arial,Verdana; cursor:text"
    });

	$("#modify_account_btn")[0].onclick=function(){
		if(CheckForm()){
			$("body").mask("信息提交中...");
			$.ajax({
			    url: '<%=path%>/bulletin/bulletinManage/saveNewBulletin.htm',
			    type: 'POST',
			    dataType: 'json',
			    timeout: 2000,
			    data:{'title':$.trim($('#title').val()),
			    	  'buType':$.trim($('#buType').val()),
			    	  'content':$.trim($('#content').val()),
			    	  'endTimeStr':$.trim($('#endTimeStr').val())
			    },
			    error: function(){
			    	$("body").unmask();
			        alert('服务器异常或超时!登录修改失败!');
			    },
			    success: function(json){
			    	$("body").unmask();
			    	if(json){
			    		if(json.success){
			    			$("#addBulletin")[0].reset();
				    		alert(json.msg);
				    		window.location.replace("<%=path%>/bulletin/bulletinManage/queryBulletin.htm");
				    		
				    	}
				    	else{
				    		alert(json.msg);
				    	}
			    	}
			    	else{
			    		alert('服务器异常或超时!新增公告失败');
			    	}
			    }
			});
		}
	}
	
	
});
</script>
<style type="text/css">
<!--
td{font-size:11pt;}
-->
</style>
</HEAD>

<BODY>
<div id="main" class="mainPanel wd txt_left">
<div class="titleInfo">							
	<h3 class="left">公告发布</h3><span class="addrtitle b_size"></span>					
</div>

<div name="status" class="content b_size"><form name="addBulletin" id="addBulletin" action="<%=path%>/bulletin/bulletinManage/saveNewBulletin.htm" method="post" >
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;">
	<tr>
		<td align="right">公告标题：</td>
		<td colspan="3"><INPUT type="text" name="title" id="title" value="" style="width:84%;"><td>
	<tr>
	<tr>
		<td align="right" width="10%">公告类别：</td>
		<td width="40%"><o:select type="bullet_type" name="buType" id="buType" headValue="" headText="---请选择---"></o:select>
		</td>
		<td align="right" width="8%">截止时间：</td>
		<td width="40%"><INPUT type="text" name="endTimeStr" id="endTimeStr" value="" style="width:120px;" readonly="true" onfocus="javascirpt:this.blur();"></td>
	</tr>
	<tr>
		<td align="right" valign="top">公告内容：</td>
		<td colspan="3"><textarea rows="20" style="width:100%;" name="content" id="content"></textarea></td>
	</tr>
	<tr><td>&nbsp;</td><td><INPUT type="button" id="modify_account_btn" value="新 增"></td></tr>
</table>
</div></form>
</div>
</div>
</body>
</html>
