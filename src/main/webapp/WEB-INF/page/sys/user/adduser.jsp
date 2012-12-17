<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><HTML>
<HEAD>
<TITLE>新增用户</TITLE>
<META content=数码印刷，数码打样设计，制作广告,装饰包装 name=Keywords>
<link href="<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/pub/resources/js/checkInput.js"></script>
<script type='text/javascript' src='<%=path%>/pub/resources/libs/loadingmask/jquery.loadmask.js'></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/biz.css"/>
<script language="javascript">
$(document).ready(function(){
	$("#modify_account_btn")[0].onclick=function(){
		if(Validator.Validate($('#edituserForm')[0], 2)){
			if(!($('#userPwd').val()==$('#newPwd1').val())){
				alert("两次输入的密码不符！");
				return false;
			}
			$("body").mask("信息提交中...");
			$.ajax({
			    url: '<%=path%>/sys/employeeManage/addUser.htm',
			    type: 'POST',
			    dataType: 'json',
			    timeout: 2000,
			    data:$('#edituserForm').serialize(),
			    error: function(){
			    	$("body").unmask();
			        alert('服务器异常或超时!新增失败!');
			    },
			    success: function(json){
			    	$("body").unmask();
			    	if(json){
			    		if(json.success){
			    			window.opener.query();
				    		alert(json.msg);
				    		window.close();
				    	}
				    	else{
				    		alert(json.msg);
				    	}
			    	}
			    	else{
			    		alert('服务器异常或超时!新增失败!');
			    	}
			    }
			});
		}
	}
});

function openRoleWin(){
	var ids = document.getElementById("roleIds").value;
	var result = window.showModalDialog("<%=path%>/sys/roleManage/roleList.htm",ids,"dialogWidth=430px;dialogHeight=500px");
	if(result){
		$('#roleIds').val(result.ids);
		$('#roleNames').val(result.names);
	}
	
}
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
	<h3 class="left">新增用户信息</h3><span class="addrtitle b_size"></span>					
</div>

<div name="status" class="content b_size"><form name="edituserForm" id="edituserForm" action="<%=path%>/sys/employeeManage/addUser.htm" method="post" >
<div style="width:100%;">
<table cellspacing="0" align="center" class="infoTable domainInfo listTable" style="width:100%;">
	<tr>
		<td align="right">用户帐号：</td>
		<td id="oldAccount"><INPUT type="text" name="userAccount" id="userAccount" value="${po.userAccount}" dataType="LimitB" min="1"  max="40" msg="用户帐号(非空,长度小于40)" ></td>
	</tr>
	<tr>
		<td align="right">用户密码：</td>
		<td id="oldAccount"><INPUT type="password" name="userPwd" id="userPwd" value="${po.userAccount}" dataType="LimitB" min="6"  max="40" msg="用户密码(非空,最小六位最大40位)" ></td>
	</tr>
	<tr>
		<td align="right">密码确认：</td>
		<td id="oldAccount"><INPUT type="password" name="newPwd1" id="newPwd1" value="${po.userAccount}"></td>
	</tr>
	<tr>
		<td align="right">员工编号：</td>
		<td><INPUT type="text" name="idNo" id="idNo" value="${po.idNo}" dataType="LimitB" min="1"  max="20" msg="员工编号(非空,长度小于20)" ></td>
	</tr>	
	<tr>
		<td align="right">名称：</td>
		<td><INPUT type="text" name="userName" id="userName" value="${po.userName}" dataType="LimitB" min="1"  max="40" msg="用户名称(非空,长度小于40)" ></td>
	</tr>
	<tr>
		<td align="right">所属部门：</td>
		<td>
		<select name="deptCd" id="deptCd"  dataType="LimitB" min="1"  max="20" msg="所属部门">
		<c:forEach items="${deptList}" var="dept" varStatus="status"><option value="${dept.id}">${dept.deptName}</option></c:forEach>
		</select>
		</td>
	</tr>
	<tr>
		<td align="right">联系电话：</td>
		<td><INPUT type="text" name="mobileNo" id="mobileNo" value="${po.mobileNo}" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();" dataType="PhoneOrMobile2" min="1"  max="13" msg="联系电话(手机号,长度小于13)" ></td>
	</tr>
	<tr>
		<td align="right">用户角色：<input type="hidden" name="roleIds" id="roleIds"/></td>
		<td><INPUT type="text" name="roleNames" id="roleNames" value=""  readOnly style="background:#cfcfcf;width:260px;" title="分配用户角色"><input type="button" value="添加角色" onclick="openRoleWin()"/></td>
	</tr>
	<tr>
		<td align="right">性别：</td>
		<td>
		<select name="gender" id="gender">
		<option value="">---请选择---</option>
		<option value="1" <c:if test="${po.gender==1}">selected</c:if> >男</option>
		<option value="0" <c:if test="${po.gender==0}">selected</c:if>>女</option>
		</select>
		</td>
	</tr>
	<tr>
		<td align="right">年龄：</td>
		<td><INPUT type="text" name="age" id="age" value="${po.age}" onKeyPress="inputInteger();"  onbeforepaste="pasteInteger();" dataType="LimitB"  max="3" msg="年龄(长度小于3)"></td>
	</tr>
	<tr>
		<td align="right">邮箱：</td>
		<td><INPUT type="text" name="email" id="email" value="${po.email}" dataType="Email2" max="60" msg="电子邮箱(必须符合邮箱格式)" ></td>
	</tr>
	<tr>
		<td align="right">住址：</td>
		<td><INPUT type="text" name="address" id="address" value="${po.address}" dataType="LimitB"  max="100" msg="住址(长度小于100)" ></td>
	</tr>
	<tr>
		<td align="right">邮政编码：</td>
		<td><INPUT type="text" name="postcode" id="postcode" value="${po.postcode}" dataType="LimitB"  max="10" msg="邮政编码(长度小于10)" ></td>
	</tr>
	<tr>
		<td align="right">备注信息：</td>
		<td><textarea rows="4" cols="30" name="memoDesc" id="memoDesc"  dataType="LimitB" max="200" msg="备注信息(非空,长度小于200)" >${po.memoDesc}</textarea></td>
	</tr>
	<tr><td></td><td><INPUT type="button" id="modify_account_btn" value="新  增"></td></tr>
</table>
</div></form>
</div>
</div>
</body>
</html>
