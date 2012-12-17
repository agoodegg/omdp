<%@ page contentType="text/xml; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="o" uri="/WEB-INF/omdp.tld"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><?xml version='1.0' encoding='utf-8'?>
<tree id="0">
	<item text="欧美数码印务管理系统" im0="home.gif" im1="home.gif" im2="home.gif" id="system_root" open="1" call="1" select="1">
		<o:guard tag="TOP_MENU_000002"><item text="订单管理" id="order" open="1" im1="direction--plus.png" im2="direction--plus.png">
			<o:urlguard url="/order/newOrder/toNewOrder.htm"><item text="增加新订单" id="neworder" im0="umbrella--plus.png" im1="umbrella--plus.png" im2="umbrella--plus.png"><userdata name="src">/order/newOrder/toNewOrder.htm</userdata></item></o:urlguard>
			<o:urlguard url="/order/orderQuery/queryAll.htm"><item text="订单查询" id="orderquery"   im0="blue-document-search-result.png" im1="blue-document-search-result.png" im2="blue-document-search-result.png"><userdata name="src">/order/orderQuery/queryAll.htm</userdata></item></o:urlguard>
			<o:urlguard url="/order/orderQuery/queryUnfinish.htm"><item text="未完成订单" id="unfinish"   im0="box-search-result.png" im1="box-search-result.png" im2="box-search-result.png"><userdata name="src">/order/orderQuery/queryUnfinish.htm</userdata></item></o:urlguard>
			<o:urlguard url="/order/orderQuery/queryDone.htm"><item text="已完成订单" id="doneorder" im0="calendar-search-result.png" im1="calendar-search-result.png" im2="calendar-search-result.png"><userdata name="src">/order/orderQuery/queryDone.htm</userdata></item></o:urlguard>
			<o:urlguard url="/order/orderQuery/queryTrash.htm"><item text="已作废订单" id="trashorder" im0="clipboard-search-result.png" im1="clipboard-search-result.png" im2="clipboard-search-result.png"><userdata name="src">/order/orderQuery/queryTrash.htm</userdata></item></o:urlguard>
		</item></o:guard>
		<o:guard tag="TOP_MENU_000096"><item text="OT单管理" id="ot-order" im1="direction--plus.png" im2="direction--plus.png">
			<o:urlguard url="/order/ot-newOrder/toNewOrder.htm"><item text="增加新OT单" id="ot-neworder" im0="umbrella--plus.png" im1="umbrella--plus.png" im2="umbrella--plus.png"><userdata name="src">/order/ot-newOrder/toNewOrder.htm</userdata></item></o:urlguard>
			<o:urlguard url="/order/ot-orderQuery/queryAll.htm"><item text="OT单查询" id="ot-orderquery"   im0="blue-document-search-result.png" im1="blue-document-search-result.png" im2="blue-document-search-result.png"><userdata name="src">/order/ot-orderQuery/queryAll.htm</userdata></item></o:urlguard>
			<o:urlguard url="/order/ot-orderQuery/queryUnfinish.htm"><item text="未完成OT单" id="ot-unfinish"   im0="box-search-result.png" im1="box-search-result.png" im2="box-search-result.png"><userdata name="src">/order/ot-orderQuery/queryUnfinish.htm</userdata></item></o:urlguard>
			<o:urlguard url="/order/ot-orderQuery/queryDone.htm"><item text="已完成OT单" id="ot-doneorder" im0="calendar-search-result.png" im1="calendar-search-result.png" im2="calendar-search-result.png"><userdata name="src">/order/ot-orderQuery/queryDone.htm</userdata></item></o:urlguard>
			<o:urlguard url="/order/ot-orderQuery/queryTrash.htm"><item text="已作废OT单" id="ot-trashorder" im0="clipboard-search-result.png" im1="clipboard-search-result.png" im2="clipboard-search-result.png"><userdata name="src">/order/ot-orderQuery/queryTrash.htm</userdata></item></o:urlguard>
		</item></o:guard>
		<o:guard tag="TOP_MENU_000003"><item text="送货单管理" id="send_order" open="1"  im1="stamp--arrow.png" im2="stamp--arrow.png">
			<o:urlguard url="/deliver/deliverQuery/queryAll.htm"><item text="送货单查询" id="send_order_query" im0="blue-document-search-result.png" im1="blue-document-search-result.png" im2="blue-document-search-result.png"><userdata name="src">/deliver/deliverQuery/queryAll.htm</userdata></item></o:urlguard>
			<o:urlguard url="/deliver/deliverQuery/queryTodo.htm"><item text="待处理送货单" id="todo_send_order" im0="ui-tab--arrow.png" im1="ui-tab--arrow.png" im2="ui-tab--arrow.png"><userdata name="src">/deliver/deliverQuery/queryTodo.htm</userdata></item></o:urlguard>
			<o:urlguard url="/deliver/deliverQuery/queryDone.htm"><item text="客户已签收送货单" id="done_send_order" im0="ui-status-bar-blue.png" im1="ui-status-bar-blue.png" im2="ui-status-bar-blue.png"><userdata name="src">/deliver/deliverQuery/queryDone.htm</userdata></item></o:urlguard>
		</item></o:guard>
		<o:guard tag="TOP_MENU_000004"><item text="合同管理" id="contract_manage"  open="1">
			<o:urlguard url="/contract/contractList.htm"><item text="合同查询" id="contract" im0="ui-status-bar-blue.png" im1="ui-status-bar-blue.png" im2="ui-status-bar-blue.png"><userdata name="src">/contract/contractList.htm</userdata></item></o:urlguard>
		</item></o:guard>
		<o:guard tag="TOP_MENU_000005"><item text="财务及帐务处理" id="financial" open="1" im0="money.gif" im1="money.gif" im2="money.gif">
			<o:urlguard url="/finance/trad/totrad.htm"><item text="订单结算" id="ddjs" im0="direction--plus.png" im1="direction--plus.png" im2="direction--plus.png"><userdata name="src">/finance/trad/totrad.htm</userdata></item></o:urlguard>
			<o:urlguard url="/finance/trad/payedlist.htm"><item text="订单结算记录" id="ddjsjl" im0="subversion.png" im1="subversion.png" im2="subversion.png"><userdata name="src">/finance/trad/payedlist.htm</userdata></item></o:urlguard>
			<o:urlguard url="/finance/trad/cash.htm"><item text="现金收款记录" id="xjskjl" im0="currency-yen.png" im1="currency-yen.png" im2="currency-yen.png"><userdata name="src">/finance/trad/cash.htm</userdata></item></o:urlguard>
			<o:urlguard url="/finance/stat/ifee.htm"><item text="营帐统计" id="yztj" im0="balance-unbalance.png" im1="balance-unbalance.png" im2="balance-unbalance.png"><userdata name="src">/finance/stat/ifee.htm</userdata></item></o:urlguard>
			<o:urlguard url="/finance/bill/billlist.htm"><item text="客户对账信息" id="khdzd" im0="application-icon.png" im1="application-icon.png" im2="application-icon.png"><userdata name="src">/finance/bill/billlist.htm</userdata></item></o:urlguard>
			<!-- <o:urlguard url="/finance/bill/billdetail.htm"><item text="客户对账单明细" id="khdzdmx" im0="blue-folder-sticky-note.png" im1="blue-folder-sticky-note.png" im2="blue-folder-sticky-note.png"><userdata name="src">/finance/bill/billdetail.htm</userdata></item></o:urlguard> -->
			<!-- <o:urlguard url="/developing.htm"><item text="客户账户调整" id="khzhtz" ></item></o:urlguard> -->
			<!-- <o:urlguard url="/developing.htm"><item text="客户账户变更记录" id="khzhbgjl" ></item></o:urlguard> -->
		</item></o:guard>
		<o:guard tag="TOP_MENU_000006"><item text="统计与查询" id="statics">
			<o:guard tag="TOP_MENU_000027"><item text="业务员业绩统计" id="busi_statics" >
				<o:urlguard url="/custbusistat/mycust/mycuststat.htm"><item text="我的客户业务量" id="myachieve" im0="chart_pie.png" im1="chart_pie.png" im2="chart_pie.png"><userdata name="src">/custbusistat/ach/mycuststat.htm</userdata></item></o:urlguard>
				<o:urlguard url="/custbusistat/ach/employeestat.htm"><item text="按业务员统计" id="employstatics" im0="pieicon.png" im1="pieicon.png" im2="pieicon.png"><userdata name="src">/custbusistat/ach/employeestat.htm</userdata></item></o:urlguard>
			</item></o:guard>
			<o:guard tag="TOP_MENU_000028"><item text="客户业务查询统计" id="cust_busi_statics" >
				<o:urlguard url="/developing.htm"><item text="客户消费统计" id="consumption_stat"  im0="pieicon.png" im1="pieicon.png" im2="pieicon.png"><userdata name="src">/custbusistat/consumption/toConsumption.htm</userdata></item></o:urlguard>
				<o:urlguard url="/developing.htm"><item text="按业务类别统计" id="cust_busi_type_stat"  im0="pieicon.png" im1="pieicon.png" im2="pieicon.png"><userdata name="src">/custbusistat/consumption/toConsumption.htm</userdata></item></o:urlguard>
				<o:urlguard url="/developing.htm"><item text="按结算方式统计" id="pay_type_stat"  im0="pieicon.png" im1="pieicon.png" im2="pieicon.png"><userdata name="src">/custbusistat/consumption/toConsumption.htm</userdata></item></o:urlguard>
				<o:urlguard url="/developing.htm"><item text="按客户名称统计" id="cust_per_stat"  im0="pieicon.png" im1="pieicon.png" im2="pieicon.png"><userdata name="src">/custbusistat/consumption/toConsumption.htm</userdata></item></o:urlguard>
			</item></o:guard>
			<o:guard tag="TOP_MENU_000029"><item text="业务分类统计" id="prod_type_statics" >
				<o:urlguard url="/custbusistat/busi/busiRankChart.htm"><item text="业务量分类统计" id="prod_stat"  im0="block-share.png" im1="block-share.png" im2="block-share.png"><userdata name="src">/custbusistat/busi/busiRankChart.htm</userdata></item></o:urlguard>
			</item></o:guard>
		</item></o:guard>
		<o:guard tag="TOP_MENU_000008"><item text="客户管理" id="customer">
			<o:urlguard url="/customer/customerManage/queryCustomer.htm"><item text="我的客户" id="my_customer"  im0="customer.gif" im1="customer.gif" im2="customer.gif"><userdata name="src">/customer/customerManage/queryCustomer.htm</userdata></item></o:urlguard>
			<o:urlguard url="/customer/customerManage/toNewCust.htm"><item text="增加新客户" id="add_customer"  im0="customer-i.gif" im1="customer-i.gif" im2="customer-i.gif"><userdata name="src">/customer/customerManage/toNewCust.htm</userdata></item></o:urlguard>
		</item></o:guard>
		<o:guard tag="TOP_MENU_000007"><item text="公告管理" id="bulletin_manage" im0="uses.gif" im1="uses.gif" im2="uses.gif">
			<o:urlguard url="/bulletin/bulletinManage/queryBulletin.htm"><item text="公告查询" id="bulletin_info" im0="employee.gif" im1="uses.gif" im2="uses.gif"><userdata name="src">/bulletin/bulletinManage/queryBulletin.htm</userdata></item></o:urlguard>
			<o:urlguard url="/bulletin/bulletinManage/toAddBulletin.htm"><item text="公告发布" id="new_bulletin" im0="dept.gif" im1="dept.gif" im2="dept.gif"><userdata name="src">/bulletin/bulletinManage/toAddBulletin.htm</userdata></item></o:urlguard>
		</item></o:guard>
		<o:guard tag="TOP_MENU_000009"><item text="系统管理" id="system_manage"  im0="system.gif" im1="system.gif" im2="system.gif">
			<o:urlguard url="/sys/employeeManage/queryEmployee.htm"><item text="员工管理" id="employee_manage" im0="employee.gif" im1="uses.gif" im2="uses.gif"><userdata name="src">/sys/employeeManage/queryEmployee.htm</userdata></item></o:urlguard>
			<o:urlguard url="/sys/deptManage/queryDept.htm"><item text="部门管理" id="dept_mamage" im0="dept.gif" im1="dept.gif" im2="dept.gif"><userdata name="src">/sys/deptManage/queryDept.htm</userdata></item></o:urlguard>
			<o:urlguard url="/sys/roleManage/queryRole.htm"><item text="角色管理" id="right_manage" im0="role.gif" im1="role.gif" im2="role.gif"><userdata name="src">/sys/roleManage/queryRole.htm</userdata></item></o:urlguard>
			<% //<o:urlguard url="/sys/sysres/toResources.htm"><item text="资源管理" id="sys_res_manage" ><userdata name="src">/sys/sysres/toResources.htm</userdata></item></o:urlguard>%>
			<o:urlguard url="/sys/busiParamsManage/toBusiParams.htm"><item text="业务参数管理" id="business_params_manage" >
			<item text="设计制作" id="bus_params_1" ><userdata name="src">/sys/busiParamsManage/toBusiParams.htm?type=1</userdata></item>
			<item text="数码打印" id="bus_params_2" ><userdata name="src">/sys/busiParamsManage/toBusiParams.htm?type=2</userdata></item>
			<item text="写真喷绘" id="bus_params_3" ><userdata name="src">/sys/busiParamsManage/toBusiParams.htm?type=3</userdata></item>
			<item text="工程图文" id="bus_params_4" ><userdata name="src">/sys/busiParamsManage/toBusiParams.htm?type=4</userdata></item>
			<item text="加工装订" id="bus_params_5" ><userdata name="src">/sys/busiParamsManage/toBusiParams.htm?type=5</userdata></item>
			<item text="其他" id="bus_params_99" ><userdata name="src">/sys/busiParamsManage/toBusiParams.htm?type=99</userdata></item>
			</item></o:urlguard>
			<o:urlguard url="/sys/sysParamsManage/toSysParams.htm"><item text="系统参数设置" id="sys_params" ><userdata name="src">/sys/sysParamsManage/toSysParams.htm</userdata></item></o:urlguard>
			<o:urlguard url="/sys/busiDataManage/toBusiData.htm"><item text="业务数据管理" id="busi_data_manage" ><userdata name="src">/sys/busiDataManage/toBusiData.htm</userdata></item></o:urlguard>
			<o:urlguard url="/sys/dataBackup/toDataBackup.htm"><item text="数据备份" id="data_backup" ><userdata name="src">/sys/backup.htm</userdata></item></o:urlguard>
			<o:urlguard url="/sys/syslog/queryLogin.htm"><item text="用户登录日志" id="login_log" im0="dept.gif" im1="dept.gif" im2="dept.gif"><userdata name="src">/sys/syslog/queryLogin.htm</userdata></item></o:urlguard>
			<o:urlguard url="/sys/syslog/queryAllLog.htm"><item text="系统操作日志" id="opr_log" im0="dept.gif" im1="dept.gif" im2="dept.gif"><userdata name="src">/sys/syslog/queryAllLog.htm</userdata></item></o:urlguard>
		</item></o:guard>
		<!-- <item text="我的欧美" id="my_omax">
			<item text="我的考勤" id="attendance_detail"><userdata name="src">/myomax/attendance.htm</userdata></item>
			<item text="员工守则" id="employee_tutorial"><userdata name="src">/myomax/rule.htm</userdata></item>
		</item> -->
		<o:guard tag="TOP_MENU_000010"><item text="帐号与密码" id="password_account">
			<o:urlguard url="/sys/user/toPassword.htm"><item text="修改密码" id="edit_password" im0="password.png" im1="password.png" im2="password.png"><userdata name="src">/sys/user/toPassword.htm</userdata></item></o:urlguard>
			<o:urlguard url="/sys/user/toAccount.htm"><item text="修改登录帐号" id="edit_user_account" im0="leaf.png" im1="leaf.png" im2="leaf.png"><userdata name="src">/sys/user/toAccount.htm</userdata></item></o:urlguard>
		</item></o:guard>
		<item text=".关于." id="sys_about_info" im0="about.gif" im1="about.gif" im2="about.gif"></item>
		<item text="退出系统" id="quit" im0="quit.gif" im1="quit.gif" im2="quit.gif"></item>
	</item>
</tree> 
