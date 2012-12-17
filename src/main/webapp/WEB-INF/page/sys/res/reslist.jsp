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
<head>
	<TITLE>功能权限列表</TITLE>
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/pub/resources/js/checkInput.js"></script>
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
	<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
	
	<style type="text/css">
	<!--
	.style2 {font-size: 9pt; line-height: 20px;}
	.style3 {font-size: 12pt;}
	
	.style2 tr{
		font-size: 9pt;
	}
	.style2 tr td{
		font-size: 9pt;
	}
	-->
	.tr_hide{
		display:none;
	}
	</style>

</head>

<script type="text/javascript">
function toggle_tr(id){
	var otemp = $("#sub_chk"+id);
	if(otemp){
		otemp.toggleClass("tr_hide");
	}
}

function collapse_all(){
	$("tr[id^='sub_chk']").addClass("tr_hide");
}

function expand_all(){
	$("tr[id^='sub_chk']").removeClass("tr_hide");
}

function check_all(val){
	if(val){
		$('#resForm').find("input[type='checkbox']").each(
            function()
            {
                $(this).attr('checked',true);
            }
        );
	}
	else{
		$('#resForm').find("input[type='checkbox']").each(
            function()
            {
                $(this).attr('checked',false);
            }
        );
	}
}

function check_item(obj,id){
	if(obj.checked){
		$("#sub_chk"+id).find("input[type='checkbox']").each(
            function()
            {
                $(this).attr('checked',true);
            }
        );
	}
	else{
		$("#sub_chk"+id).find("input[type='checkbox']").each(
            function()
            {
                $(this).attr('checked',false);
            }
        );
	}
}

function has_checked(arr){
	for(var i=0;i<arr.length;i++){
		if(arr[i].checked){
			return true;
		}
	}
	return false;
}
function all_unchecked(arr){
	for(var i=0;i<arr.length;i++){
		if(arr[i].checked){return false;}
	}
	
	return true;
}

function check_sub_item(obj,res_id,parent_id){
	if(obj.checked){
		$("input[name^='"+obj.name+"_']").each(
            function()
            {
                $(this).attr('checked',true);
            }
        );
	}
	else{
		$("input[name^='"+obj.name+"_']").each(
            function()
            {
                $(this).attr('checked',false);
            }
        );
	}
	
	var parnetPathNodeSet=[];
	var tempArray = obj.name.split("_");
	if(tempArray.length>1){
		var tempStr = tempArray[0];
		parnetPathNodeSet.push(tempStr);
		for(var i=1;i<tempArray.length-1;i++){
			tempStr = tempStr+"_"+tempArray[i];
			parnetPathNodeSet.push(tempStr);
		}
		
		
		for(var e=parnetPathNodeSet.length-1;e>=0;e--){
			var subsets = $("input[name^='"+parnetPathNodeSet[e]+"_']");
			var o = $("input[name='"+parnetPathNodeSet[e]+"']")[0];
			if(o.checked){
				if(all_unchecked(subsets)){
					o.checked=false;
				}
			}
			else{
				if(has_checked(subsets)){
					o.checked=true;
				}
			}
		}
		
	}
}

function saveRoleResInfo(){
	var ids = [];
	
	$("input[name^='chk']").each(function()
    {
        var obj = $(this)[0];
        
        if(obj.checked){
        	ids.push(obj.id);
        }
    });
    
    
    $("body").mask("信息提交中...");
	$.ajax({
	    url: '<%=path%>/sys/roleManage/saveRoleRes.htm',
	    type: 'POST',
	    dataType: 'json',
	    timeout: 8000,
	    data:{'resIds':ids.join(","),'id':${role.id}},
	    error: function(){
	    	$("body").unmask();
	        alert('服务器异常或超时!保存失败!');
	    },
	    success: function(json){
	    	$("body").unmask();
	    	if(json){
	    		if(json.success){
		    		alert(json.msg);
		    		window.close();
		    	}
		    	else{
		    		alert(json.msg);
		    	}
	    	}
	    	else{
	    		alert('服务器异常或超时!保存失败!');
	    	}
	    }
	});
}
</script>

<body>
<div id="main" class="mainPanel wd txt_left">
	<div class="titleInfo">							
		<h3 class="left">功能及权限列表</h3><span class="addrtitle b_size"></span>					
	</div>
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;table-layout:fixed;margin:4 0 0 0;">
	<tr>
    <th width="10%"><input type="checkbox" name="checkall" onClick="check_all(this.checked)">选择全部</th>   
	<th colspan="3" algin="center">&nbsp;</th>
	<th width="15%" colspan="1" align="left"><a href="#" onClick="expand_all()">全部展开</a>&nbsp;<a href="#" onClick="collapse_all()">全部收起</a></th>
   </tr>
</table>

<form name="resForm" id="resForm">
<table width="100%" border="1" class="style2" cellSpacing="0" cellPadding="2" bgcolor="#ECFFEC"
		bordercolorlight="#D7EBFF" bordercolordark="#D7EBFF" style="border-collapse: collapse" bordercolor="#66CCCC" align="center">
	<tr>
	<td width=4%><input type="checkbox" name="chk2" id="000002" <o:roleres resources="roleres" id="2">checked</o:roleres>  onClick="check_item(this,2);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(2)" >订单管理</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk2" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk2_11" id="000011" <o:roleres resources="roleres" id="11">checked</o:roleres>  onClick="check_sub_item(this,11,'chk2');">增加新订单</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk2_12" id="000012" <o:roleres resources="roleres" id="12">checked</o:roleres>  onClick="check_sub_item(this,12,'chk2');">订单查询</td>
			<td>
				<input type="checkbox" name="chk2_12_46" id="000046" <o:roleres resources="roleres" id="46">checked</o:roleres>  onClick="check_sub_item(this,46,'chk2_12');">修改
				<input type="checkbox" name="chk2_12_47" id="000047" <o:roleres resources="roleres" id="47">checked</o:roleres>  onClick="check_sub_item(this,47,'chk2_12');">打单
				<input type="checkbox" name="chk2_12_48" id="000048" <o:roleres resources="roleres" id="48">checked</o:roleres>  onClick="check_sub_item(this,48,'chk2_12');">完工(工单结单--自动生成送货单)
				<input type="checkbox" name="chk2_12_49" id="000049" <o:roleres resources="roleres" id="49">checked</o:roleres>  onClick="check_sub_item(this,49,'chk2_12');">结算
				<input type="checkbox" name="chk2_12_50" id="000050" <o:roleres resources="roleres" id="50">checked</o:roleres>  onClick="check_sub_item(this,50,'chk2_12');">作废
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk2_13" id="000013" <o:roleres resources="roleres" id="13">checked</o:roleres>  onClick="check_sub_item(this,13,'chk2');">未完成订单查询</td>
			<td>
				<input type="checkbox" name="chk2_13_51" id="000051" <o:roleres resources="roleres" id="51">checked</o:roleres>  onClick="check_sub_item(this,51,'chk2_13');">修改
				<input type="checkbox" name="chk2_13_52" id="000052" <o:roleres resources="roleres" id="52">checked</o:roleres>  onClick="check_sub_item(this,52,'chk2_13');">打单
				<input type="checkbox" name="chk2_13_53" id="000053" <o:roleres resources="roleres" id="53">checked</o:roleres>  onClick="check_sub_item(this,53,'chk2_13');">完工(工单结单--自动生成送货单)
				<input type="checkbox" name="chk2_13_54" id="000054" <o:roleres resources="roleres" id="54">checked</o:roleres>  onClick="check_sub_item(this,54,'chk2_13');">结算
				<input type="checkbox" name="chk2_13_55" id="000055" <o:roleres resources="roleres" id="55">checked</o:roleres>  onClick="check_sub_item(this,55,'chk2_13');">作废
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk2_14" id="000014" <o:roleres resources="roleres" id="14">checked</o:roleres>  onClick="check_sub_item(this,13,'chk2');">已完成订单查询</td>
			<td>
				<input type="checkbox" name="chk2_14_56" id="000056" <o:roleres resources="roleres" id="56">checked</o:roleres>  onClick="check_sub_item(this,56,'chk2_14');">修改
				<input type="checkbox" name="chk2_14_57" id="000057" <o:roleres resources="roleres" id="57">checked</o:roleres>  onClick="check_sub_item(this,57,'chk2_14');">打单
				<input type="checkbox" name="chk2_14_58" id="000058" <o:roleres resources="roleres" id="58">checked</o:roleres>  onClick="check_sub_item(this,58,'chk2_14');">结算
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk2_15" id="000015" <o:roleres resources="roleres" id="15">checked</o:roleres>  >已作废订单查询</td>
		</tr>
		</table>
		</td>
		<td></td>
	</tr>
	
	<tr>
	<td width=4%><input type="checkbox" name="chk96" id="000096" <o:roleres resources="roleres" id="96">checked</o:roleres>  onClick="check_item(this,96);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(96)" >外派(OT)单管理</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk96" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk96_97" id="000097" <o:roleres resources="roleres" id="97">checked</o:roleres>  onClick="check_sub_item(this,97,'chk96');">增加外派单</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk96_98" id="000098" <o:roleres resources="roleres" id="98">checked</o:roleres>  onClick="check_sub_item(this,98,'chk96');">外派单查询</td>
			<td>
				<input type="checkbox" name="chk96_98_102" id="000102" <o:roleres resources="roleres" id="102">checked</o:roleres>  onClick="check_sub_item(this,102,'chk96_98');">修改
				<input type="checkbox" name="chk96_12_103" id="000103" <o:roleres resources="roleres" id="103">checked</o:roleres>  onClick="check_sub_item(this,103,'chk96_98');">打单
				<input type="checkbox" name="chk96_12_104" id="000104" <o:roleres resources="roleres" id="104">checked</o:roleres>  onClick="check_sub_item(this,104,'chk96_98');">完工(外派单结单--自动生成送货单)
				<input type="checkbox" name="chk96_12_105" id="000105" <o:roleres resources="roleres" id="105">checked</o:roleres>  onClick="check_sub_item(this,105,'chk96_98');">结算
				<input type="checkbox" name="chk96_12_106" id="000106" <o:roleres resources="roleres" id="106">checked</o:roleres>  onClick="check_sub_item(this,106,'chk96_98');">作废
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk96_99" id="000099" <o:roleres resources="roleres" id="99">checked</o:roleres>  onClick="check_sub_item(this,99,'chk96');">未完成外派单查询</td>
			<td>
				<input type="checkbox" name="chk96_13_107" id="000107" <o:roleres resources="roleres" id="107">checked</o:roleres>  onClick="check_sub_item(this,107,'chk96_99');">修改
				<input type="checkbox" name="chk96_13_108" id="000108" <o:roleres resources="roleres" id="108">checked</o:roleres>  onClick="check_sub_item(this,108,'chk96_99');">打单
				<input type="checkbox" name="chk96_13_109" id="000109" <o:roleres resources="roleres" id="109">checked</o:roleres>  onClick="check_sub_item(this,109,'chk96_99');">完工(外派单结单--自动生成送货单)
				<input type="checkbox" name="chk96_13_110" id="000110" <o:roleres resources="roleres" id="110">checked</o:roleres>  onClick="check_sub_item(this,110,'chk96_99');">结算
				<input type="checkbox" name="chk96_13_111" id="000111" <o:roleres resources="roleres" id="111">checked</o:roleres>  onClick="check_sub_item(this,111,'chk96_99');">作废
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk96_100" id="000100" <o:roleres resources="roleres" id="100">checked</o:roleres>  onClick="check_sub_item(this,100,'chk96');">已完成外派单查询</td>
			<td>
				<input type="checkbox" name="chk96_14_112" id="000112" <o:roleres resources="roleres" id="112">checked</o:roleres>  onClick="check_sub_item(this,112,'chk96_100');">修改
				<input type="checkbox" name="chk96_14_113" id="000113" <o:roleres resources="roleres" id="113">checked</o:roleres>  onClick="check_sub_item(this,113,'chk96_100');">打单
				<input type="checkbox" name="chk96_14_114" id="000114" <o:roleres resources="roleres" id="114">checked</o:roleres>  onClick="check_sub_item(this,114,'chk96_100');">结算
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk96_101" id="000101" <o:roleres resources="roleres" id="101">checked</o:roleres>  >已作废外派单查询</td>
		</tr>
		</table>
		</td>
		<td></td>
	</tr>
	
	<tr>
	<td width=4%><input type="checkbox" name="chk3" id="000003" <o:roleres resources="roleres" id="3">checked</o:roleres>  onClick="check_item(this,3);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(3)" >送货单管理</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk3" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk3_16" id="000016" <o:roleres resources="roleres" id="16">checked</o:roleres>  onClick="check_sub_item(this,16,'chk3');">送货单查询</td>
			<td>
				<input type="checkbox" name="chk3_16_59" id="000059" <o:roleres resources="roleres" id="59">checked</o:roleres>  onClick="check_sub_item(this,59,'chk3_16');">修改
				<input type="checkbox" name="chk3_16_60" id="000060" <o:roleres resources="roleres" id="60">checked</o:roleres>  onClick="check_sub_item(this,60,'chk3_16');">打印送货单
				<input type="checkbox" name="chk3_16_61" id="000061" <o:roleres resources="roleres" id="61">checked</o:roleres>  onClick="check_sub_item(this,61,'chk3_16');">完工(送货单结单)
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk3_17" id="000017" <o:roleres resources="roleres" id="17">checked</o:roleres>   onClick="check_sub_item(this,17,'chk3');">待处理送货单</td>
			<td>
				<input type="checkbox" name="chk3_17_62" id="000062" <o:roleres resources="roleres" id="62">checked</o:roleres>   onClick="check_sub_item(this,62,'chk3_17');">修改
				<input type="checkbox" name="chk3_17_63" id="000063" <o:roleres resources="roleres" id="63">checked</o:roleres>    onClick="check_sub_item(this,63,'chk3_17');">打印送货单
				<input type="checkbox" name="chk3_17_64" id="000064" <o:roleres resources="roleres" id="64">checked</o:roleres>   onClick="check_sub_item(this,64,'chk3_17');">完工(送货单结单)
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk3_18"  id="000018" <o:roleres resources="roleres" id="18">checked</o:roleres>  onClick="check_sub_item(this,18,'chk3');">客户已签收送货单</td>
		</tr>
		</table>
		</td>
		<td></td>
	</tr>
	
	
	
	<tr>
	<td width=4%><input type="checkbox" name="chk4" id="000004" <o:roleres resources="roleres" id="4">checked</o:roleres>  onClick="check_item(this,4);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(4)" >合同管理</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk4" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk4_19" id="000019" <o:roleres resources="roleres" id="19">checked</o:roleres>  onClick="check_sub_item(this,19,'chk4');">合同查询与编辑</td>
			<td>
				
			</td>
		</tr>
		
		</table>
		</td>
		<td></td>
	</tr>
	
	
	<tr>
	<td width=4%><input type="checkbox" name="chk5" id="000005"  <o:roleres resources="roleres" id="5">checked</o:roleres>  onClick="check_item(this,5);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(5)" >财务及帐务管理</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk5" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk5_21" id="000021"  <o:roleres resources="roleres" id="21">checked</o:roleres>  onClick="check_sub_item(this,21,'chk5');">订单结算</td>
			<td>
				<input type="checkbox" name="chk5_21_65"  id="000065"  <o:roleres resources="roleres" id="65">checked</o:roleres>  onClick="check_sub_item(this,65,'chk5_21');">结算
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk5_22" id="000022"  <o:roleres resources="roleres" id="22">checked</o:roleres>  onClick="check_sub_item(this,22,'chk5');">订单结算记录</td>
			<td>
				
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk5_23" id="000023"  <o:roleres resources="roleres" id="23">checked</o:roleres>  onClick="check_sub_item(this,23,'chk5');">现金收款记录</td>
			<td>
				
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk5_24" id="000024"  <o:roleres resources="roleres" id="24">checked</o:roleres>  onClick="check_sub_item(this,24,'chk5');">营帐统计</td>
			<td>
				
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk5_25" id="000025"  <o:roleres resources="roleres" id="25">checked</o:roleres>  onClick="check_sub_item(this,25,'chk5');">客户对账信息</td>
			<td>
				
			</td>
		</tr>
		<!-- 
		<tr>
			<td width=20%><input type="checkbox" name="chk5_26" id="000026"  <o:roleres resources="roleres" id="26">checked</o:roleres>  onClick="check_sub_item(this,26,'chk5');">客户对账单明细</td>
			<td>
				
			</td>
		</tr>  -->
		</table>
		</td>
		<td></td>
	</tr>
	
	
	
	<tr>
	<td width=4%><input type="checkbox" name="chk6" id="000006" <o:roleres resources="roleres" id="6">checked</o:roleres>  onClick="check_item(this,6);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(6)" >统计与查询</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk6" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk6_27" id="000027" <o:roleres resources="roleres" id="27">checked</o:roleres>  onClick="check_sub_item(this,27,'chk6');">业务员业绩统计</td>
			<td>
				<input type="checkbox" name="chk6_27_66" id="000066" <o:roleres resources="roleres" id="66">checked</o:roleres>   onClick="check_sub_item(this,66,'chk6_27');">我的客户业务量
				<input type="checkbox" name="chk6_27_67" id="000067" <o:roleres resources="roleres" id="67">checked</o:roleres>  onClick="check_sub_item(this,67,'chk6_27');">按业务员统计
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk6_28" id="000028" <o:roleres resources="roleres" id="28">checked</o:roleres>  onClick="check_sub_item(this,28,'chk6');">客户业务查询统计</td>
			<td>
				<input type="checkbox" name="chk6_28_68" id="000068" <o:roleres resources="roleres" id="68">checked</o:roleres> onClick="check_sub_item(this,68,'chk6_28');">客户消费统计
				<input type="checkbox" name="chk6_28_69" id="000069" <o:roleres resources="roleres" id="69">checked</o:roleres> onClick="check_sub_item(this,69,'chk6_28');">按业务类别统计
				<input type="checkbox" name="chk6_28_70" id="000070" <o:roleres resources="roleres" id="70">checked</o:roleres> onClick="check_sub_item(this,70,'chk6_28');">按结算方式统计
				<input type="checkbox" name="chk6_28_71" id="000071" <o:roleres resources="roleres" id="71">checked</o:roleres> onClick="check_sub_item(this,71,'chk6_28');">按客户名称统计
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk6_29" id="000029" <o:roleres resources="roleres" id="29">checked</o:roleres>  onClick="check_sub_item(this,29,'chk6');">产品分类统计</td>
			<td>
				<input type="checkbox" name="chk6_29_72" id="000072" <o:roleres resources="roleres" id="72">checked</o:roleres>  onClick="check_sub_item(this,72,'chk6_29');">产品统计
			</td>
		</tr>
		</table>
		</td>
		<td></td>
	</tr>
	
	<tr>
	<td width=4%><input type="checkbox" name="chk8" id="000008" <o:roleres resources="roleres" id="8">checked</o:roleres>  onClick="check_item(this,8);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(8)" >客户管理</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk8" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk8_30" id="000030" <o:roleres resources="roleres" id="30">checked</o:roleres>  onClick="check_sub_item(this,30,'chk8');">我的客户</td>
			<td>
				<input type="checkbox" name="chk8_30_95" id="000095" <o:roleres resources="roleres" id="95">checked</o:roleres> onClick="check_sub_item(this,95,'chk8_30');">查看
				<input type="checkbox" name="chk8_30_87" id="000087" <o:roleres resources="roleres" id="87">checked</o:roleres> onClick="check_sub_item(this,87,'chk8_30');">编辑
				<input type="checkbox" name="chk8_30_88" id="000088" <o:roleres resources="roleres" id="88">checked</o:roleres> onClick="check_sub_item(this,88,'chk8_30');">删除
				
				<input type="checkbox" name="chk8_30_93" id="000093" <o:roleres resources="roleres" id="93">checked</o:roleres> onClick="check_sub_item(this,93,'chk8_30');"><font color="#ff0000">可见全部客户</font>
				<input type="checkbox" name="chk8_30_94" id="000094" <o:roleres resources="roleres" id="94">checked</o:roleres> onClick="check_sub_item(this,94,'chk8_30');"><font color="#ff0000">仅可见自己的客户</font>
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk8_31" id="000031" <o:roleres resources="roleres" id="31">checked</o:roleres> onClick="check_sub_item(this,31,'chk8');">增加新客户</td>
			<td>
			</td>
		</tr>
		</table>
		</td>
		<td></td>
	</tr>
	
	
	
	<tr>
	<td width=4%><input type="checkbox" name="chk7" id="000007" <o:roleres resources="roleres" id="7">checked</o:roleres>  onClick="check_item(this,7);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(7)" >公告管理</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk7" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk7_32" id="000032" <o:roleres resources="roleres" id="32">checked</o:roleres>  onClick="check_sub_item(this,32,'chk7');">公告查询</td>
			<td>
				<input type="checkbox" name="chk7_32_73" id="000073" <o:roleres resources="roleres" id="73">checked</o:roleres>  onClick="check_sub_item(this,73,'chk7_32');">删除
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk7_33" id="000033" <o:roleres resources="roleres" id="33">checked</o:roleres>  onClick="check_sub_item(this,33,'chk7');">发布公告</td>
			<td>
			</td>
		</tr>
		</table>
		</td>
		<td></td>
	</tr>
	
	
	
	<tr>
	<td width=4%><input type="checkbox" name="chk9" id="000009" <o:roleres resources="roleres" id="9">checked</o:roleres>  onClick="check_item(this,9);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(9)" >系统管理</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk9" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk9_34" id="000034" <o:roleres resources="roleres" id="34">checked</o:roleres>  onClick="check_sub_item(this,34,'chk9');">用户员工管理</td>
			<td>
				<input type="checkbox" name="chk9_34_74" id="000074" <o:roleres resources="roleres" id="74">checked</o:roleres>   onClick="check_sub_item(this,74,'chk9_34');">新增
				<input type="checkbox" name="chk9_34_75" id="000075" <o:roleres resources="roleres" id="75">checked</o:roleres>  onClick="check_sub_item(this,75,'chk9_34');">修改
				<input type="checkbox" name="chk9_34_76" id="000076" <o:roleres resources="roleres" id="76">checked</o:roleres>  onClick="check_sub_item(this,76,'chk9_34');">删除
				<input type="checkbox" name="chk9_34_115" id="000115" <o:roleres resources="roleres" id="115">checked</o:roleres>  onClick="check_sub_item(this,115,'chk9_34');">密码重置
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk9_35" id="000035" <o:roleres resources="roleres" id="35">checked</o:roleres>  onClick="check_sub_item(this,35,'chk9');">部门管理</td>
			<td>
				<input type="checkbox" name="chk9_35_77" id="000077" <o:roleres resources="roleres" id="77">checked</o:roleres>   onClick="check_sub_item(this,77,'chk9_35');">新增
				<input type="checkbox" name="chk9_35_78" id="000078" <o:roleres resources="roleres" id="78">checked</o:roleres>  onClick="check_sub_item(this,78,'chk9_35');">修改
				<input type="checkbox" name="chk9_35_79" id="000079" <o:roleres resources="roleres" id="79">checked</o:roleres>  onClick="check_sub_item(this,79,'chk9_35');">删除
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk9_36" id="000036" <o:roleres resources="roleres" id="36">checked</o:roleres>  onClick="check_sub_item(this,36,'chk9');">角色及权限管理</td>
			<td>
				<input type="checkbox" name="chk9_36_89" id="000089" <o:roleres resources="roleres" id="89">checked</o:roleres>   onClick="check_sub_item(this,89,'chk9_36');">新增
				<input type="checkbox" name="chk9_36_90" id="000090" <o:roleres resources="roleres" id="90">checked</o:roleres>  onClick="check_sub_item(this,90,'chk9_36');">修改
				<input type="checkbox" name="chk9_36_91" id="000091" <o:roleres resources="roleres" id="91">checked</o:roleres>  onClick="check_sub_item(this,91,'chk9_36');">删除
				<input type="checkbox" name="chk9_36_92" id="000092" <o:roleres resources="roleres" id="92">checked</o:roleres>  onClick="check_sub_item(this,92,'chk9_36');">角色权限
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk9_38" id="000038" <o:roleres resources="roleres" id="38">checked</o:roleres> onClick="check_sub_item(this,38,'chk9');">业务参数管理</td>
			<td>
				<input type="checkbox" name="chk9_38_80" id="000080" <o:roleres resources="roleres" id="80">checked</o:roleres>   onClick="check_sub_item(this,80,'chk9_38');">新增
				<input type="checkbox" name="chk9_38_81" id="000081" <o:roleres resources="roleres" id="81">checked</o:roleres>  onClick="check_sub_item(this,81,'chk9_38');">修改
				<input type="checkbox" name="chk9_38_82" id="000082" <o:roleres resources="roleres" id="82">checked</o:roleres> onClick="check_sub_item(this,82,'chk9_38');">删除
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk9_39" id="000039" <o:roleres resources="roleres" id="39">checked</o:roleres> onClick="check_sub_item(this,39,'chk9');">系统参数管理</td>
			<td>
				<input type="checkbox" name="chk9_39_83" id="000083" <o:roleres resources="roleres" id="83">checked</o:roleres>   onClick="check_sub_item(this,83,'chk9_39');">新增
				<input type="checkbox" name="chk9_39_84" id="000084" <o:roleres resources="roleres" id="84">checked</o:roleres>  onClick="check_sub_item(this,84,'chk9_39');">修改
				<input type="checkbox" name="chk9_39_85" id="000085" <o:roleres resources="roleres" id="85">checked</o:roleres>  onClick="check_sub_item(this,85,'chk9_39');">删除
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk9_41" id="000041" <o:roleres resources="roleres" id="41">checked</o:roleres> onClick="check_sub_item(this,41,'chk9');">数据备份</td>
			<td>
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk9_41" id="000042" <o:roleres resources="roleres" id="42">checked</o:roleres> onClick="check_sub_item(this,42,'chk9');">用户登录日志</td>
			<td>
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk9_41" id="000043" <o:roleres resources="roleres" id="43">checked</o:roleres> onClick="check_sub_item(this,43,'chk9');">系统操作日志</td>
			<td>
			</td>
		</tr>
		</table>
		</td>
		<td></td>
	</tr>
	
	
	<tr>
	<td width=4%><input type="checkbox" name="chk10" id="000010" <o:roleres resources="roleres" id="10">checked</o:roleres>  onClick="check_item(this,10);">&nbsp;</td>
	<td colspan="5" title="单击展开/收缩" style="CURSOR:hand" onClick="toggle_tr(10)" >帐号与密码</td>
    <td width="10%">&nbsp;</td>
	</tr>
	<tr id="sub_chk10" class="tr_hide">
		<td></td>
		<td colspan="5" style="background:#ffffff;">
		<table style="border:1px solid;" width="100%">
		<tr>
			<td width=20%><input type="checkbox" name="chk10_44" id="000044" <o:roleres resources="roleres" id="44">checked</o:roleres>  onClick="check_sub_item(this,44,'chk10');">修改密码</td>
			<td>
			</td>
		</tr>
		<tr>
			<td width=20%><input type="checkbox" name="chk10_45" id="000045" <o:roleres resources="roleres" id="45">checked</o:roleres>  onClick="check_sub_item(this,45,'chk10');">修改自己的登录帐号</td>
			<td>
			</td>
		</tr>
		</table>
		</td>
		<td></td>
	</tr>
</table>
</form>

<div style="text-align:center;margin:12 0 0 0;"><span style="color:#ff0000;align:center;">请注意：只要某个类别有一项权限需要设置，都必须选中它相对应模块前的选择框（<input type="checkbox" readonly checked>）</span></div>
</div>

<div><input type="button" value="重 置"/>&nbsp;&nbsp;<input type="button" value="保 存" onclick="saveRoleResInfo()"/>&nbsp;&nbsp;</div>
</body>
</html>
