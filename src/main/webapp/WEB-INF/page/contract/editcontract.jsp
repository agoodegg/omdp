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
<title>编辑合同信息</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/ux/css/RowEditor.css" /> 
<!-- GC --> 
<!-- LIBS --> 
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-base.js"></script> 
<!-- ENDLIBS --> 
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-all.js"></script>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ux/RowEditor.js"></script>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
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
		margin-top:20px;
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
	.rules{
		
	}
	.rules ol li{
		
	}
	
#loading-mask{
	position:absolute;
	left:0;
	top:0;
    width:100%;
    height:100%;
    z-index:20000;
    background-color:white;
}
#loading{
	position:absolute;
	left:45%;
	top:40%;
	padding:2px;
	z-index:20001;
    height:auto;
}
#loading img {
    margin-bottom:5px;
}
#loading .loading-indicator{
	background:white;
	color:#555;
	font:bold 13px helvetica,arial,tahoma,sans-serif;
	padding:10px;
	margin:0;
    text-align:center;
    height:auto;
}
</style>

<script type="text/javascript">
var ind = 0;
Ext.onReady(function(){	
	Ext.QuickTips.init();

    var ContractItem = Ext.data.Record.create([{
        name: 'id',
        type: 'number'
    },{
        name: 'itemName',
        type: 'string'
    }, {
        name: 'mount',
        type: 'number'
    }, {
        name: 'mountUnit',
        type: 'string'
    },  {
        name: 'spec',
        type: 'string'
    },{
        name: 'mertierial',
        type: 'string'
    },{
        name: 'requirement',
        type: 'string'
    },{
        name: 'price',
        type: 'number'
    },{
        name: 'total',
        type: 'number'
    }]);



    var store = new Ext.data.GroupingStore({
        reader: new Ext.data.JsonReader({fields: ContractItem}),
        data: ${info.itemData},
        sortInfo: {field: 'itemName', direction: 'ASC'}
    });

    var editor = new Ext.ux.grid.RowEditor({
        saveText: '确 定',
        cancelText: '取 消',
        commitChangesText: '该行已经修改,确定修改或放弃修改',
    	errorText: '提 示'
    });

    var grid = new Ext.grid.GridPanel({
        store: store,
        width: 858,
        height: 190,
        renderTo:'itemDetails',
        region:'center',
        margins: '0 5 5 5',
        plugins: [editor],
        tbar: [{
            iconCls: 'icon-user-add',
            text: '添加明细',
            handler: function(){
                var e = new ContractItem({
                	id:grid.getStore().getCount()+ind+1,
                    itemName : "员工手册",
                    mount : 10000,
                    mountUnit:'张',
                    spec: '440×297',
                    mertierial: '250克铜板',
                    requirement: '封面过哑膜',
                    price:1,
                    total:10000
                });
                editor.stopEditing();
                store.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                editor.startEditing(0);
            }
        },{
            ref: '../removeBtn',
            iconCls: 'icon-user-delete',
            text: '删除明细',
            disabled: true,
            handler: function(){
                editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    store.remove(r);
                }
            }
        },'->',{xtype:'label',text:'双击触发编辑修改'}],
		bbar:[{id:'totalText',xtype:'label',text:'总金额（大写）:'}],
        columns: [
        new Ext.grid.RowNumberer(),
        {
            id: 'itemName',
            header: '品名',
            dataIndex: 'itemName',
            width: 120,
            sortable: true,
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },{
            header: '数量',
            dataIndex: 'mount',
            width: 70,
            sortable: true,
            editor: {
                xtype: 'numberfield',
                allowBlank: false,
                allowNegative: false,
                maxValue: 10000000
            }
        },{
            header: '数量单位',
            dataIndex: 'mountUnit',
            width: 90,
            sortable: true,
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },{
            header: '规格',
            dataIndex: 'spec',
            width: 90,
            sortable: true,
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },{
            header: '用料',
            dataIndex: 'mertierial',
            width: 100,
            sortable: true,
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },{
            header: '生产要求',
            dataIndex: 'requirement',
            align: 'center',
            width: 110,
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },{
            header: '单价（元）',
            dataIndex: 'price',
            align: 'center',
            width: 100,
            editor: {
                xtype: 'numberfield',
                allowBlank: false,
                allowNegative: false,
                maxValue: 1000000
            }
        },{
            header: '金额（元）',
            dataIndex: 'total',
            align: 'center',
            width: 100,
            editor: {
                xtype: 'numberfield',
                allowBlank: false,
                allowNegative: false,
                maxValue: 1000000
            }
        }]
    });

    grid.getSelectionModel().on('selectionchange', function(sm){
        grid.removeBtn.setDisabled(sm.getCount() < 1);
    });
	
	setTimeout(function(){
	     Ext.get('loading').remove();
	     Ext.get('loading-mask').fadeOut({remove:true});
	 }, 250);
	 
	Ext.get('editContractBtn').on('click',function(){
	 	
	 	var buyer  = document.getElementById('buyerId').value.trim();
	 	var errMsg = [];

	 	if(buyer==''){
	 		errMsg.push("甲方（委托方）不能为空");
	 	}
	 	
	 	var records = grid.getStore().getRange();
	 	var data = [];
	 	for(var i=0;i<records.length;i++){
	 		data.push(records[i].data)
	 	}
	 	
	 	if(data.length==0){
	 		errMsg.push("请添加合同细项");
	 	}
	 	
	 	if(errMsg.length>0){
	 		alert(errMsg.join('\n'));
	 		return false;
	 	} 	

	 	Ext.getBody().mask("信息提交中...");
	 	
 		Ext.Ajax.request({
 			url: '<%=path %>/contract/doContractEdit.htm',
 			form:'editContractForm',
 			method:'POST',
 			params:{itemData:Ext.util.JSON.encode(data)},
 			timeout: 5000,
 			success:function(result){
 				Ext.getBody().unmask();
 				var json = {};
		   		try{
		   			json = Ext.util.JSON.decode(result.responseText);
		   		}
		   		catch(e){}
		   		if(json.success===true){
		   			alert(json.msg);
		   			
		   			window.opener.store.reload();
		   			
		   			window.close();
		   		}
		   		else if(json.success===false){
		   			alert(json.msg);
		   		}
		   		else{
		   			Ext.Msg.alert("操作失败","服务器超时或异常，操作失败，请联系管理员");
		   		}
 			},
 			failure:function(){
 				Ext.getBody().unmask();
 				Ext.Msg.alert("失败",'服务器异常或超时!新增失败!');
 			}
 		});
	 })
});
</script>
</head>
<body>
<div id="loading-mask" style=""></div> 
<div id="loading"> 
  <div class="loading-indicator"><img src="<%=path%>/pub/resources/images/extanim32.gif" width="32" height="32" style="margin-right:8px;" align="absmiddle"/>载入中...</div> 
</div>

<form id="editContractForm">
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
							<li><span class="ctitle" style="font-size:15px;font-weight:bold;font-family:Arial,sans-serif;">Contract No.合同编号：</span><input  width="239" style="width:239px;" id="contractNoId" name="contractNo" value="${info.contractNo}" style="color:c21111;background:#c1c1c1;" readOnly="true"  onfocus="javascript:blur();"  dataType="LimitB" min="1"  max="20" msg="合同编号(非空,长度小于20)" /></li>
							<li><span class="ctitle" style="font-size:15px;font-weight:bold;font-family:Arial,sans-serif;">甲方（委托方）：</span><input name="buyer" width="280" value="${info.buyer}" style="width:280px;" id="buyerId" dataType="LimitB" min="1"  max="80" msg="甲方（委托方）(非空,长度小于40)" /></li>
							<li><span class="ctitle" style="font-size:15px;font-weight:bold;font-family:Arial,sans-serif;">乙方（承印方）：</span>深圳市欧美数码印务有限公司</li>
						</ul>
					</div>
					</td>
			</tr>
		</table>
		
		<p>&nbsp;&nbsp;&nbsp;&nbsp;根据《中华人民共和国合同法》及有关法律、法规，甲乙双方本着真诚合作，利益共享的原则，经甲、乙双方友好协商，达成如下协议：</p>
		
		<div id="itemDetails">
		
		</div>
		
		
		<table class="suTable" border="1" cellspacing="0" cellpadding="0" style="width:100%;">
			<tr>
				<td>包&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;装：<input name="package_" value="${info.package_}"  width="220" style="width:220px;" dataType="LimitB"  max="200" msg="包装(长度小于100)"/></td><td rowspan="2">付款方法：<input name="payChannel" value="${info.payChannel}" width="220" style="width:220px;" dataType="LimitB"  max="100" msg="付款方法(长度小于50)"/></td>
			</tr>
			<tr>
				<td>交货地点：<input name="deliverPlace"  value="${info.deliverPlace}"   width="220" style="width:220px;" dataType="LimitB"  max="120" msg="交货地点(长度小于60)"/></td>
			</tr>
			<tr>
				<td colspan="2">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：<input name="memo"   value="${info.memo}"   width="570" style="width:460px;"  dataType="LimitB"  max="240" msg="备注(长度小于120)"/></td>
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
			<td width="75">甲&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;方:</td><td><input width="220"  value="${info.buyer}"  style="width:220px;"/></td><td width="35">&nbsp;</td>
			<td width="75">乙&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;方:</td><td>深圳市欧美数码印务有限公司</td>
		  </tr>
		  <tr>
			<td width="75">单位地址:</td><td><input name="buyerAddress"  value="${info.buyerAddress}"   width="220" style="width:220px;" dataType="LimitB"  max="120" msg="甲方单位地址(长度小于60)"/></td><td width="35">&nbsp;</td>
			<td width="75">单位地址:</td><td>深圳南山区科技园南区C1栋一楼</td>
		  </tr>
			<tr>
			<td width="75">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</td><td><input name="buyerTel"  value="${info.buyerTel}"   width="220" style="width:220px;" dataType="LimitB"   max="40" msg="甲方电话(长度小于20)"/></td><td width="35">&nbsp;</td>
			<td width="75">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</td><td>0755-89808638</td>
		  </tr>
		  <tr>
			<td width="75">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真:</td><td><input name="buyerFax"  value="${info.buyerFax}"    width="220" style="width:220px;" dataType="LimitB"  max="40" msg="甲方传真(长度小于20)"/></td><td width="35">&nbsp;</td>
			<td width="75">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真:</td><td>0755-26551973</td>
		  </tr>
		  <tr>
			<td width="75">开户银行:</td><td><input name="buyerAccountBank"  value="${info.buyerAccountBank}"    width="220" style="width:220px;" dataType="LimitB"  max="60" msg="甲方开户银行(长度小于30)"/></td><td width="35">&nbsp;</td>
			<td width="75">开户银行:</td><td>深圳平安银行高新区支行</td>
		  </tr>
		  <tr>
			<td width="75">帐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</td><td><input name="buyerAccountNo"  value="${info.buyerAccountNo}"    width="220" style="width:220px;" dataType="LimitB"  max="40" msg="甲方帐号(长度小于20)"/></td><td width="35">&nbsp;</td>
			<td width="75">帐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</td><td>0412100133030</td>
		  </tr>
		  <tr>
			<td width="75">甲方代表:</td><td><input name="buyerMan"  value="${info.buyerMan}"    width="220" style="width:220px;" dataType="LimitB"  max="40" msg="甲方代表(长度小于20)"/></td><td width="35">&nbsp;</td>
			<td width="75">乙方代表:</td><td><input name="sellerMan"  value="${info.sellerMan}"     width="220" style="width:220px;"/></td>
		  </tr>
		  <tr>
			<td width="75">日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:</td><td><input name="buyerDateStr" value="${info.buyerDateStr}"  width="220" style="width:220px;"/></td><td width="35">&nbsp;</td>
			<td width="75">日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:</td><td><input name="sellerDateStr" value="${info.sellerDateStr}"  width="220" style="width:220px;"/></td>
		  </tr>
		</table>
	</div>
	
	<input name="seller" type="hidden" value="深圳市欧美数码印务有限公司"/>
	<input name="sellerAddress" type="hidden" value="深圳南山区科技园南区C1栋一楼"/>
	<input name="sellerTel" type="hidden" value="0755-89808638"/>
	<input name="sellerFax" type="hidden" value="0755-26551973"/>
	<input name="sellerAccountBank" type="hidden" value="深圳平安银行高新区支行"/>
	<input name="sellerAccountNo" type="hidden" value="0412100133030"/>
	
	<input name="status" type="hidden" value="${info.status}"/>
	<input name="id" type="hidden" value="${info.id}"/>
</form>

<hr/>
<table border="0" width="100%">
<tr>
<td align="center" style="text-align:center;"><input type="button" value="修 改" id="editContractBtn"/></td>
</tr>
</table>
</body>
</html>
