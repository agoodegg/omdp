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
<title>查看合同信息</title>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<!--media=print 这个属性可以在打印时有效--> 
<style media=print> 
.Noprint{display:none;} 
.PageNext{page-break-after: always;} 
</style>
<style type="text/css">
	.WordSection1{
		font-size:14px;
	}
	.WordSection1 table tr td{
		font-size:14px;
	}
	.itemTable{
		margin-right:40px;
	}
	.MsoNormalTable{
		margin-right:40px;
	}
	.itemTable tr td{
			text-align:center;
			font-size:14px;
	}
	.suTable{
		margin-top:7px;
		margin-right:40px;
	}
	.rules{
		margin-top:13px;
		margin-right:40px;
	}
	.footer{
		border:0;
		margin-top:50px;
		margin-left:25px;
		margin-right:40px;
	}
</style>
</head>
<body  onLoad="window.focus()">
<span class="noprint" style="font-size=16px" >
<OBJECT	classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" width="0" height="0" name="myprint"></OBJECT>
</span>
	<div class="WordSection1">
		<table class="MsoNormalTable" border="1" cellspacing="0" cellpadding="0" style="width:100%;">
			<tr>
				<td rowspan="2" width="104">
					<img src="<%=path%>/pub/resources/images/image001.jpg" width="104">
				</td><td rowspan="2" width="190" align="center" v-align="middel" style="font-weight:bold;text-align:center;v-align:middle;font-size:25px;background:url(<%=path%>/pub/resources/images/image004.gif)">
					欧美印务<br/>Omax Printing
					</td><td align="center" v-align="middel" style="font-weight:bold;font-size:22px;background:url(<%=path%>/pub/resources/images/bbg.jpg) repeat-x;">
						印 刷 合 同
						</td>
			</tr>
			<tr>
				<td>
					<div class="content_1" style="font-size:15px;font-weight:bold;margin:10px 0 0 0;font-family: web-font-bold;">
						<ul style="list-style-type:none;">
							<li><span class="ctitle" style="font-size:15px;font-weight:bold;font-family:Arial,sans-serif;">Contract No.合同编号：</span>${info.contractNo}</li>
							<li><span class="ctitle" style="font-size:15px;font-weight:bold;font-family:Arial,sans-serif;">甲方（委托方）：</span>${info.buyer}</li>
							<li><span class="ctitle" style="font-size:15px;font-weight:bold;font-family:Arial,sans-serif;">乙方（承印方）：</span>${info.seller}</li>
						</ul>
					</div>
					</td>
			</tr>
		</table>
		
		<p>&nbsp;&nbsp;&nbsp;&nbsp;根据《中华人民共和国合同法》及有关法律、法规，甲乙双方本着真诚合作，利益共享的原则，经甲、乙双方友好协商，达成如下协议：</p>
		
		<table class="itemTable" border="1" cellspacing="0" cellpadding="0" style="width:100%;">
			<tr>
				<th>品名</th><th>数量</th><th>规格</th><th>用料</th><th>生产要求</th><th>单价（元）</th><th>金额（元）</th>
			</tr>
			<c:forEach items="${itemList}" var="item" varStatus="status">
			<tr>
				<td>${item.itemName}&nbsp;</td><td>${item.mount}${item.mountUnit}&nbsp;</td><td>${item.spec}&nbsp;</td><td>${item.mertierial}&nbsp;</td><td>${item.requirement}&nbsp;</td><td>${item.price}&nbsp;</td><td>${item.total}&nbsp;</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="7" style="font-weight:bold;text-align:left;">总金额（大写）: ${RMB}     ￥：${grossText }</td>
			</tr>
		</table>
		
		<table class="suTable" border="1" cellspacing="0" cellpadding="0" style="width:100%;">
			<tr>
				<td width="380">包    装：${info.package_}</td><td rowspan="2">付款方法：${info.payChannel}</td>
			</tr>
			<tr>
				<td>交货地点：${info.deliverPlace}</td>
			</tr>
			<tr>
				<td colspan="2">备    注：${info.memo}</td>
			</tr>
		</table>
		
		<div class="rules">
			<ol>
				<li>本合同双方签订后即生效，如甲方单方取消合同，则须赔偿总金额50％。</li>
				<li>签订合同后甲方即须付订金，收到甲方订金之日起开始计算制作时间。</li>
				<li>甲方保证所委托之印刷品没有违法、违规或假冒，如有上述隐情，甲方应付全部经济及法律责任。</li>
				<li>校稿：乙方交回的黑稿、彩稿如不符甲方的原稿和校正稿，乙方负责改正；如甲方改动黑稿、彩稿，甲方需付改版费，原定交货期顺延。产品以甲方最后一次校对好并同意生产的样本为准，如有错漏，责任由甲方承担。</li>
				<li>印刷时一般多放纸3%，由于印刷顺利情况不同，成品或多或少3%（误差），均不做增收费用或退款。</li>
				<li>甲方在收到货后如发现质量问题应在一个星期内提出书面质疑。逾期不予负责。</li>
				<li>货物验收标准参照广东省出版事业管理局颁布的《广东省书刊印刷品抽查检验标准》。</li>
				<li>本合同一式两份，甲乙双方各持一份，经双方签字盖章后，即具法律效力。</li>
				<li>合同纠纷：按照《中华人民共和国合同法》解决。</li>
			</ol>
		</div>
		
		<table class="footer" width="100%;">
			<tr>
			<td width="75">甲&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;方:</td><td>${info.buyer}</td><td width="35">&nbsp;</td>
			<td width="75">乙&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;方:</td><td>${info.seller}</td>
		  </tr>
		  <tr>
			<td width="75">单位地址:</td><td>&nbsp;${info.buyerAddress}</td><td width="35">&nbsp;</td>
			<td width="75">单位地址:</td><td>${info.sellerAddress}</td>
		  </tr>
			<tr>
			<td width="75">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</td><td>&nbsp;${info.buyerTel}</td><td width="35">&nbsp;</td>
			<td width="75">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</td><td>${info.sellerTel}</td>
		  </tr>
		  <tr>
			<td width="75">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真:</td><td>&nbsp;${info.buyerFax}</td><td width="35">&nbsp;</td>
			<td width="75">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真:</td><td>${info.sellerFax}</td>
		  </tr>
		  <tr>
			<td width="75">开户银行:</td><td>&nbsp;${info.buyerAccountBank}</td><td width="35">&nbsp;</td>
			<td width="75">开户银行:</td><td>${info.sellerAccountBank}</td>
		  </tr>
		  <tr>
			<td width="75">帐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</td><td>&nbsp;${info.buyerAccountNo}</td><td width="35">&nbsp;</td>
			<td width="75">帐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</td><td>${info.sellerAccountNo}</td>
		  </tr>
		  <tr>
			<td width="75">甲方代表:<br/>（盖章）</td><td>&nbsp;</td><td width="35">&nbsp;</td>
			<td width="75">乙方代表:<br/>（盖章）</td><td>&nbsp;</td>
		  </tr>
		  <tr>
			<td width="75">日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:</td><td>${info.buyerDateStr}</td><td width="35">&nbsp;</td>
			<td width="75">日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:</td><td>${info.sellerDateStr}</td>
		  </tr>
		</table>
	</div>
	
	
<div class="Noprint" > 
<div style="margin-top:18px;"></div>
<hr>
<table width="100%" height="50" border=0 class="noprint" align="center" style="background:#9191a1;">
	<TR>
		<TD width="50%" align="center"><input type="hidden" name="countPrint" id="countPrint" value="0" />
			<input type="button" onClick="javascript:self.close();" value="关  闭">
		</TD>
		<td align="left"><input type="button" name="print1" id="print1" onClick="printit();" value="打    印" class="groove">
				<input type="button" name="print2" id="print2" onClick="printpreview()" value="打印预览" class="groove">
				<input type="button" name="print3" id="print3" onClick="printsetup()" value="打印设置" class="groove">
		</td>		
	</tr>
	</table>
</div>
</body>

<script language="javascript">
	
function printsetup(){ 
	myprint.execwb(8,1); 
} 

// 打印页面预览
function printpreview(){
	myprint.execwb(7,1);
} 

function printit(){
	var printCountObj = document.getElementById('countPrint');
	var printCountVal = printCountObj.value;
	if (printCountVal >= 1) {
		if (!confirm('本单已经打印过一次，要再打印一次吗？')) {
			window.close();
			return;
		}
	}else{
		if (!confirm('确定打印吗？')) {
			window.close();
			return;
		}
	}
	
	// 禁止在本界面连续点击打印
	document.getElementById('print1').disabled = true;
	printCountObj.value = parseInt(printCountObj.value)+1;
	
	myprint.execwb(6,1);  // 提示用户选择打印机
	
	window.close();
}
</script>
</html>
