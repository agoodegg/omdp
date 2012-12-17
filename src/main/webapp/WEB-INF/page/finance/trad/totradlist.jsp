<%@ page contentType="text/html;charset=UTF-8"%><%
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
<title>工单结算</title>
<%@include file="../../exthead.jsps"%>
<style type="text/css">
<!--
.queryArea{
	background-image: url( <%=path%>/pub/resources/images/search.png) !important;
}
.listArea{
	background-image: url( <%=path%>/pub/resources/images/list.png) !important;
}
.newContract{
	background-image: url( <%=path%>/pub/resources/images/list.png) !important;
}
-->
</style>
<head>
<body>
<div id="loading-mask" style=""></div> 
<div id="loading"> 
  <div class="loading-indicator"><img src="<%=path%>/pub/resources/images/extanim32.gif" width="32" height="32" style="margin-right:8px;" align="absmiddle"/>载入中...</div> 
</div>
<div style="display:none;" id="noDisplayDiv">
<o:select headValue="" style="display:none;" headText="---请选择---" value="${p.ext1}" id="ext1Sel" type="payCycleType"></o:select>
<o:select type="isOut" style="display:none;"  id="dispatchOtherSel" headText="---是否OT---" headValue="" value="${p.dispatchOther}"></o:select>
<o:select headValue="" style="display:none;" headText="---请选择---" value="${p.tradStatus}"  id="tradStatusSel" type="tradStatus"></o:select>
</div>

<script type="text/javascript">
var dataUrl = '<%=path%>/finance/trad/totradData.htm';
function refreshGrid(){
	Ext.getCmp('ordertListGrid').getStore().reload();
}

function printOrder(id,ordernum){
	var url = "<%=path%>/order/orderQuery/printOrder.htm?id="+id+"&ordernum="+ordernum;
	
	var iTop = (window.screen.availHeight-450)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-850)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'addUserWin', 'height=450,width=850,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}

function editOrder(id,idnum){
	
	var url = "<%=path%>/order/newOrder/toEdit.htm?id="+id
	
	var iTop = (window.screen.availHeight-600)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-1000)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'editOrderWin', 'height=600,width=1000,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}

function viewOrder(id,idnum){
	
	var url = "<%=path%>/order/newOrder/vieworder.htm?id="+id
	
	var iTop = (window.screen.availHeight-600)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-1000)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'viewOrderWin', 'height=600,width=1000,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}




function doneOrder(id,idnum){
	if(confirm("确定将工单【"+idnum+"】标记为完工！\n并生成送货单！")){
	
		Ext.getBody().mask("信息提交中...");
		Ext.Ajax.request({
			url: "<%=path%>/order/orderQuery/doneOrder.htm",
			method: 'POST',
		    timeout: 15000,
		    params:{'id':id,'ordernum':idnum},
		    success:function(response){
				var jsondata = {};
				Ext.getBody().unmask();
				
				try{
					jsondata = Ext.util.JSON.decode(response.responseText);
				}
				catch(e){}
				if(jsondata.success===true){
	    			alert(jsondata.msg);
	    			refreshGrid();
		    	}
		    	else if(jsondata.success===false){
		    		alert(jsondata.msg);
		    	}
		    	else{
		    		alert('服务器异常或超时!');
		    	}
				
			},
			failure:function(){
				Ext.getBody().unmask();
				alert('服务器异常或超时!操作失败!');
			}
		});
		
	}
}


function payOrder(id,idnum){
	var url = "<%=path%>/finance/trad/payorder.htm?id="+id
	
	var iTop = (window.screen.availHeight-600)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-1000)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'payOrderWin', 'height=600,width=1000,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}



function trashOrder(id,idnum){
	Ext.MessageBox.show({
        title: '确认作废所选工单',
        msg: '请输入作废原因(不超过100字):',
        width:400,
        buttons: Ext.MessageBox.OKCANCEL,
        multiline: true,
        fn: function(btn, text){
        	if(btn=='ok'){
        		var trashMemo = text;
        		Ext.getBody().mask("信息提交中...");
        		Ext.Ajax.request({
        			url: "<%=path%>/order/orderQuery/trashOrder.htm",
        			method:'POST',
        			timeout:10000,
        			params:{'id':id,'ordernum':idnum,'trashMemo':trashMemo},
        			success:function(response){
        				var jsondata = {};
        				Ext.getBody().unmask();
        				
        				try{
        					jsondata = Ext.util.JSON.decode(response.responseText);
        				}
        				catch(e){}
        				if(jsondata.success===true){
        	    			alert(jsondata.msg);
        	    			refreshGrid();
        		    	}
        		    	else if(jsondata.success===false){
        		    		alert(jsondata.msg);
        		    	}
        		    	else{
        		    		alert('服务器异常或超时!');
        		    	}
        				
        			},
        			failure:function(){
        				Ext.getBody().unmask();
        				alert('服务器异常或超时!作废失败!');
        			}
        		});
        	}
        },
        animEl: 'ordertListGrid'
    });
}

function checkAudit(id,idnum){
	var url = "<%=path%>/finance/trad/checkorder.htm?id="+id
	
	var iTop = (window.screen.availHeight-600)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-1000)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'checkOrderWin', 'height=600,width=1000,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}


Ext.onReady(function(){
	
	// create the data store
    store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:dataUrl}),
		reader: new Ext.data.JsonReader({  
		             root: 'list',  
		             totalProperty: 'totalRows',
		             id: 'id'
		             },[  
		             'id','orderStatusCn',"ordernum","orderTitle","filepos","fileSrc","software","custId","custName","orderTime","doneTime","deliverTime","deliverMethod","deliverAddress","printMemo","typesetOpr","bindingOpr","linkMan","tel","device","orderMemo","seeOrderOpr","creatorName","creator","price","payType","payStatus","orderStatus","prepayFlag","prepay","prepayMemo","dispatchOther","dispatchName","agentAddress","agentMobile","col1","col2","col3","col4","col5","col6","col7","ext1","ext2","ext3","ext4"
		        ]) 
    });
    
    var oprMatrix = {};
    oprMatrix['00000']=parseInt("111100",2);
	oprMatrix['00001']=parseInt("000000",2);
	oprMatrix['00010']=parseInt("100001",2);
	oprMatrix['00011']=parseInt("000000",2);
	oprMatrix['00100']=parseInt("000000",2);
	oprMatrix['00101']=parseInt("000000",2);
	oprMatrix['00110']=parseInt("000000",2);
	oprMatrix['00111']=parseInt("000000",2);
	oprMatrix['01000']=parseInt("110110",2);
	oprMatrix['01001']=parseInt("000000",2);
	oprMatrix['01010']=parseInt("100001",2);
	oprMatrix['01011']=parseInt("000000",2);
	oprMatrix['01100']=parseInt("000000",2);
	oprMatrix['01101']=parseInt("000000",2);
	oprMatrix['01110']=parseInt("000000",2);
	oprMatrix['01111']=parseInt("000000",2);
	oprMatrix['10000']=parseInt("111100",2);
	oprMatrix['10001']=parseInt("000000",2);
	oprMatrix['10010']=parseInt("100001",2);
	oprMatrix['10011']=parseInt("000000",2);
	oprMatrix['10100']=parseInt("000000",2);
	oprMatrix['10101']=parseInt("000000",2);
	oprMatrix['10110']=parseInt("000000",2);
	oprMatrix['10111']=parseInt("000000",2);
	oprMatrix['11000']=parseInt("110110",2);
	oprMatrix['11001']=parseInt("000000",2);
	oprMatrix['11010']=parseInt("100001",2);
	oprMatrix['11011']=parseInt("000000",2);
	oprMatrix['11100']=parseInt("000000",2);
	oprMatrix['11101']=parseInt("000000",2);
	oprMatrix['11110']=parseInt("000000",2);
	oprMatrix['11111']=parseInt("000000",2);
	
	var ext1SelOptions = document.getElementById('ext1Sel').options;
	var ext1SelData = [];
	for(var i=0;i<ext1SelOptions.length;i++){
		ext1SelData.push([ext1SelOptions[i].value,ext1SelOptions[i].text]);
	}
	
	var dispatchSelOptions = document.getElementById('dispatchOtherSel').options;
	var dispatchSelData = [];
	for(var i=0;i<dispatchSelOptions.length;i++){
		dispatchSelData.push([dispatchSelOptions[i].value,dispatchSelOptions[i].text]);
	}
	
	var tradSelOptions = document.getElementById('tradStatusSel').options;
	var tradSelData = [];
	for(var i=0;i<tradSelOptions.length;i++){
		tradSelData.push([tradSelOptions[i].value,tradSelOptions[i].text]);
	}
    //alert(Ext.util.JSON.encode(ext1SelData));
    
    
    var oprRender = function(val,meta,rec){
    	var id = val;
    	var ordernum = rec.get('ordernum');
    	
    	var oprSec = oprMatrix[rec.get('orderStatus')];
    	var oprArray = [];
    	if(oprSec){
    	
    		if((oprSec&32)==32){
    			oprArray.push('<a href="javascript:void(0)" onclick="printOrder(\''+id+'\',\''+ordernum+'\')">打单</a>&nbsp;&nbsp;');
    		}
    		if((oprSec&16)==16){
    			oprArray.push('<a href="javascript:void(0)" onclick="editOrder(\''+id+'\',\''+ordernum+'\')">修改</a>&nbsp;&nbsp;');
    		}
    		if((oprSec&4)==4){
    			oprArray.push('<a href="javascript:void(0)" onclick="trashOrder(\''+id+'\',\''+ordernum+'\')">作废</a>&nbsp;&nbsp;');
    		}
    		if((oprSec&8)==8){
    			oprArray.push('<a href="javascript:void(0)" title="生成送货单" onclick="doneOrder(\''+id+'\',\''+ordernum+'\')">完工</a>&nbsp;&nbsp;');
    		}
    		if((oprSec&2)==2){
    			oprArray.push('<a href="javascript:void(0)" title="结算" onclick="payOrder(\''+id+'\',\''+ordernum+'\')">结算</a>&nbsp;&nbsp;');
    		}
    		if((oprSec&1)==1){
    			oprArray.push('<a href="javascript:void(0)" title="核销" onclick="checkAudit(\''+id+'\',\''+ordernum+'\')">核销</a>&nbsp;&nbsp;');
    		}
    	}
    	
    	return oprArray.join(" ");
    }
    
    var priceRender = function(val,meta,rec){
    	return Ext.util.Format.number(val,'0.00');
    }
    
    // create the Grid
    var grid = new Ext.grid.GridPanel({
    	loadMask:true,
    	borer:false,
    	frame:false,
    	id:'ordertListGrid',
        store: store,
        columns: [
            new Ext.grid.RowNumberer({header:'序号',width:40}),
		    {header:'订单编号',width:90,sortable:false,dataIndex:'ordernum'},
		    {header:'状态',width:195,sortable:false,dataIndex:'orderStatusCn',align:'center'},
		    {header:'下单时间',width:90,sortable:false,dataIndex:'orderTime'},
		    {header:'交货时间',width:90,sortable:false,dataIndex:'deliverTime'},
		    {header:'客户名称',width:210,sortable:true,dataIndex:'custName'},
		    {header:'金额',width:80,sortable:true,dataIndex:'price',renderer:priceRender,align:'right'},
		    {header:'开单员',width:70,sortable:false,dataIndex:'creatorName'},
		    {header:'操作',width:260,sortable:true,dataIndex:'id',renderer:oprRender}
        ],
        stripeRows: true,
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: store,
            displayInfo: true,
            displayMsg: '第{0} 到 {1} 条数据 共{2}条',  
		    emptyMsg: "没有数据"
        }),
        listeners:{
        	'afterrender':function(){
        		store.load({start:0,limit:15});
        	},
        	'rowdblclick':function(t,rInd,e){
        		var rec = t.getStore().getAt(rInd);
        		viewOrder(rec.get("id"),rec.get('ordernum'));
        	}
        }
    });
    
    store.on('beforeload',function(data,options){
		    	 var startpos = 0;
		    	 if(options.hasOwnProperty('params')&&options.params.hasOwnProperty('start')){
		    	 	startpos = options.params['start'];
		    	 	if(!startpos){startpos = 0;}
		    	 }
		    	 
		    	 var utime = Ext.getCmp('orderTimePickerU').getValue();
		    	 var dtime = Ext.getCmp('orderTimePickerD').getValue();
		    	 if(utime){utime = utime.format('Y-m-d');}
		    	 if(dtime){dtime = dtime.format('Y-m-d');}
		    	 
		         Ext.apply(  
		         this.baseParams,  
		         {   
		         	 'ordernum':Ext.getCmp('ordernum').getValue(),
		         	 'orderTimePickerU':utime,
		         	 'orderTimePickerD':dtime,
		         	 'ext1':Ext.getCmp('ext1').getValue(),
		         	 'creatorName':Ext.getCmp('creatorName').getValue(),
		         	 'custName':Ext.getCmp('custName').getValue(),
		         	 'tel':Ext.getCmp('tel').getValue(),
		         	 'dispatchOther':Ext.getCmp('dispatchOther').getValue(),
		         	 'tradStatus':Ext.getCmp('tradStatus').getValue(),
				     'pageNo':parseInt(startpos/15)+1
		         });  
		     });
		     
	var view = new Ext.Viewport({
		layout:'border',
		border:false,
		items:[{
				region:'north',
				height:130,
				maxHeight:140,
				minHeight:90,
				title:'查询区域',
				iconCls:'queryArea',
				split:true,
				collapsible:true,
				layoutConfig: {
                    padding:'5',
                    pack:'start',
                    align:'middle'
                },
                defaults:{margins:'0 5 0 0'},
                items:[{
                		layoutConfig: {
		                    padding:'5',
		                    pack:'start',
		                    align:'middle'
		                },
		                border:false,
                		layout:'hbox',
		                defaults:{margins:'0 5 0 0'},
		                items:[{xtype:'label',text:'   订单号:'},{
		                    xtype:'textfield',id:'ordernum'
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   下单时间≥:'},{
		                    xtype:'datefield',id:'orderTimePickerU',width:125,format:'Y-m-d',editable:false
		                },{xtype:'label',text:'≤'},{
		                    xtype:'datefield',id:'orderTimePickerD',width:125,format:'Y-m-d',editable:false,value:'${p.orderTimePickerD}'
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   是否月结:'},{
		                    xtype:'combo',
		                    id:'ext1',
		                    typeAhead: true,
						    triggerAction: 'all',
						    editable:false,
						    lazyRender:true,
						    value:'${p.ext1}',
						    mode: 'local',
						    store: new Ext.data.ArrayStore({
						        id: 0,
						        fields: [
						            'id',
						            'val'
						        ],
						        data: ext1SelData
						    }),
						    valueField: 'id',
						    displayField: 'val'
		                }]
                	},{
                		layoutConfig: {
		                    padding:'5',
		                    pack:'start',
		                    align:'middle'
		                },
		                border:false,
                		layout:'hbox',
		                defaults:{margins:'0 5 0 0'},
		                items:[{xtype:'label',text:'   开单员:'},{
		                    xtype:'textfield',id:'creatorName'
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   客户名称:'},{
		                    xtype:'textfield',id:'custName'
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   客户电话:'},{
		                    xtype:'numberfield',id:'tel'
		                }]
                	},{
                		layoutConfig:{
                			padding:'5',
		                    pack:'start',
		                    align:'middle'
                		},
                		border:false,
                		layout:'hbox',
                		defaults:{},
                		items:[{xtype:'label',text:'   是否OT单:'},{
		                    xtype:'combo',
		                    id:'dispatchOther',
		                    typeAhead: true,
						    triggerAction: 'all',
						    editable:false,
						    lazyRender:true,
						    mode: 'local',
						    store: new Ext.data.ArrayStore({
						        id: 0,
						        fields: [
						            'id',
						            'val'
						        ],
						        data: dispatchSelData
						    }),
						    valueField: 'id',
						    displayField: 'val'
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   是否已结算:'},{
		                    xtype:'combo',
		                    id:'tradStatus',
		                    typeAhead: true,
						    triggerAction: 'all',
						    editable:false,
						    lazyRender:true,
						    value:'${p.tradStatus}',
						    mode: 'local',
						    store: new Ext.data.ArrayStore({
						        id: 0,
						        fields: [
						            'id',
						            'val'
						        ],
						        data: tradSelData
						    }),
						    valueField: 'id',
						    displayField: 'val'
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		                    xtype:'button',
		                    text: ' 重 置 ',
		                    handler:function(){
		                    	 Ext.getCmp('ordernum').setValue("");
					         	 Ext.getCmp('orderTimePickerU').setValue("");
					         	 Ext.getCmp('orderTimePickerD').setValue("");
					         	 Ext.getCmp('ext1').setValue("");
					         	 Ext.getCmp('creatorName').setValue("");
					         	 Ext.getCmp('custName').setValue("");
					         	 Ext.getCmp('tel').setValue("");
					         	 Ext.getCmp('dispatchOther').setValue("");
					         	 Ext.getCmp('tradStatus').setValue("");
		                    }
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		                    xtype:'button',
		                    text: ' 查 询 ',
		                    handler:function(){
		                    	store.load({start:0,limit:15});
		                    }
		                }]
                	}
                ]
			},{
				region:'center',
				title: '工单列表',
				iconCls:'listArea',
				layout:'fit',
				items:[grid]
		}]
	});
	
	setTimeout(function(){
	     Ext.get('loading').remove();
	     Ext.get('loading-mask').fadeOut({remove:true});
	 }, 250);
});
</script>
</body>
</html>


