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
<%@include file="../exthead.jsps"%>
<style type="text/css">
<!--
.queryArea{
	background-image: url( <%=path%>/pub/resources/images/search.png) !important;
}
.listArea{
	background-image: url( <%=path%>/pub/resources/images/list.png) !important;
}
-->
</style>
</head>
<body>
<div id="loading-mask" style=""></div> 
<div id="loading"> 
  <div class="loading-indicator"><img src="<%=path%>/pub/resources/images/extanim32.gif" width="32" height="32" style="margin-right:8px;" align="absmiddle"/>载入中...</div> 
</div>
<div style="display:none;" id="noDisplayDiv">

</div>
<script type="text/javascript">
var listTitle = '';
var dataUrl = '<%=path%>/deliver/deliverQuery/queryAllData.htm';
<c:choose><c:when test="${mode=='all'}">
listTitle = '';
dataUrl = '<%=path%>/deliver/deliverQuery/queryAllData.htm';
</c:when><c:when test="${mode=='todo'}">
listTitle = '待处理-';
dataUrl = '<%=path%>/deliver/deliverQuery/queryTodoData.htm';
</c:when><c:when test="${mode=='done'}">
listTitle = '已完成-';
dataUrl = '<%=path%>/deliver/deliverQuery/queryDoneData.htm';
</c:when></c:choose>

function printDeliver(id,ordernum){
	var url = "<%=path%>/deliver/deliverQuery/printSendOrder.htm?id="+id+"&ordernum="+ordernum;
	
	var iTop = (window.screen.availHeight-450)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-850)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'addUserWin', 'height=450,width=850,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}

function editDeliver(id,idnum){
	
}

function doneDeliver(id,ordernum){
	var url = "<%=path%>/deliver/deliverQuery/doneSendOrder.htm?id="+id+"&ordernum="+ordernum;

	var iTop = (window.screen.availHeight-450)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-850)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'addUserWin', 'height=450,width=850,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}

function viewdeliver(id,ordernum){
	var url = "<%=path%>/deliver/deliverQuery/viewdeliver.htm?id="+id+"&ordernum="+ordernum;
	
	var iTop = (window.screen.availHeight-450)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-850)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'viewdeliverWin', 'height=450,width=850,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}

function refreshGrid(){
	Ext.getCmp('deliverListGrid').getStore().reload();
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
		             'id','sendNo',"ordernum","createTime","custName","tel","money","moneyDesc","sendMemo","doneFlag","sendOpr","status","doneTime","sendTime"
		        ])
    });
    
    
    var priceRender = function(val,meta,rec){
    	return Ext.util.Format.number(val,'0.00');
    }
    
    var statusRender = function(val,meta,rec){
    	var doneFlag = rec.get('doneFlag');
    	if(doneFlag=='0'){
    		return '待送';
    	}
    	else{
    		if(val=='3'){
    			return '已签收';
    		}
    		else{
    			return '勿需送货';
    		}
    	}
    }
    
    var oprRender = function(val,meta,rec){
   		
   		var doneFlag = rec.get('doneFlag');
    	var oprArray = [];
    	oprArray.push('<a href="#" onclick="printDeliver('+rec.get("id")+',\''+rec.get('ordernum')+'\')">打印送货单</a>&nbsp;&nbsp;');
    	if(doneFlag=='0'){
    		oprArray.push('<a href="#" onclick="doneDeliver('+rec.get("id")+',\''+rec.get('ordernum')+'\')">已签收</a>&nbsp;&nbsp;');
    	}
    	
    	return oprArray.join('');
    }
    
    // create the Grid
    var grid = new Ext.grid.GridPanel({
    	loadMask:true,
    	borer:false,
    	frame:false,
    	id:'deliverListGrid',
        store: store,
        columns: [
            new Ext.grid.RowNumberer({header:'序号',width:40}),
		    {header:'工单号',width:90,sortable:false,dataIndex:'ordernum'},
		    {header:'送货单号',width:95,sortable:false,dataIndex:'sendNo',align:'center'},
		    {header:'状态',width:80,sortable:false,dataIndex:'status',renderer:statusRender},
		    {header:'生成时间',width:80,sortable:false,dataIndex:'createTime'},
		    {header:'客户',width:190,sortable:true,dataIndex:'custName'},
		    {header:'客户电话',width:80,sortable:true,dataIndex:'tel'},
		    {header:'送达时间',width:70,sortable:false,dataIndex:'sendTime'},
		    {header:'送货人',width:80,sortable:true,dataIndex:'sendOpr'},
		    {header:'操作',width:230,sortable:true,dataIndex:'id',renderer:oprRender}
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
        		store.load({start:0,limit:10});
        	},
        	'rowdblclick':function(t,rInd,e){
        		var rec = t.getStore().getAt(rInd);
        		viewdeliver(rec.get("id"),rec.get('ordernum'))
        	}
        }
    });
    
    store.on('beforeload',function(data,options){
		    	 var startpos = 0;
		    	 if(options.hasOwnProperty('params')&&options.params.hasOwnProperty('start')){
		    	 	startpos = options.params['start'];
		    	 	if(!startpos){startpos = 0;}
		    	 }
		    	 
		    	 var utime = Ext.getCmp('createTimeU').getValue();
		    	 var dtime = Ext.getCmp('createTimeD').getValue();
		    	 if(utime){utime = utime.format('Y-m-d');}
		    	 if(dtime){dtime = dtime.format('Y-m-d');}
		    	 
		         Ext.apply(  
		         this.baseParams,  
		         {   
		         	 'ordernum':Ext.getCmp('ordernum').getValue(),
		         	 'custName':Ext.getCmp('custName').getValue(),
		         	 'createTimeU':utime,
		         	 'createTimeD':dtime,
				     'pageNo':parseInt(startpos/15)+1
		         });  
		     });
		     
	var view = new Ext.Viewport({
		layout:'border',
		border:false,
		items:[{
				region:'north',
				height:65,
				maxHeight:100,
				minHeight:60,
				title:'查询区域',
				iconCls:'queryArea',
				split:true,
				collapsible:true,
				xtype:'form',
				layout:'fit',
				items:[{
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
			                items:[{xtype:'label',text:'   客户名:'},{
			                    xtype:'textfield',id:'custName'
			                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   创建时间≥:'},{
			                    xtype:'datefield',id:'createTimeU',width:125,format:'Y-m-d',editable:false
			                },{xtype:'label',text:'≤'},{
			                    xtype:'datefield',id:'createTimeD',width:125,format:'Y-m-d',editable:false
			                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   生产工单号:'},{
			                    xtype:'textfield',id:'ordernum'
			                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
			                    xtype:'button',
			                    text: ' 重 置 ',
			                    handler:function(){
			                    	 Ext.getCmp('ordernum').setValue("");
						         	 Ext.getCmp('createTimeU').setValue("");
						         	 Ext.getCmp('createTimeD').setValue("");
						         	 Ext.getCmp('custName').setValue("");
			                    }
			                },{
			                    xtype:'button',
			                    text: ' 查 询 ',
			                    handler:function(){
			                    	store.load({start:0,limit:15});
			                    }
			                }]
	                	}
	                ]
				}]
			},{
				region:'center',
				title: listTitle+'送货单列表',
				iconCls:'listArea',
				layout:'fit',
				items:[grid]
		}]
	});
    
    
    
    setTimeout(function(){
	     Ext.get('loading').remove();
	     Ext.get('loading-mask').fadeOut({remove:true});
	 }, 120);
});

</script>

</body>
</html>