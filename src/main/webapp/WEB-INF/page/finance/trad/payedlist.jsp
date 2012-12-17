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
<title>结算记录查询</title>
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
</head>
<body>
<div id="loading-mask" style=""></div> 
<div id="loading"> 
  <div class="loading-indicator"><img src="<%=path%>/pub/resources/images/extanim32.gif" width="32" height="32" style="margin-right:8px;" align="absmiddle"/>载入中...</div> 
</div>

<div style="display:none;" id="noDisplayDiv">
<o:select headValue="" style="display:none;" headText="---请选择---" value="${p.ext1}" id="ext1Sel" type="payCycleType"></o:select>
</div>


<script type="text/javascript">

var dataUrl = "<%=path%>/finance/trad/payedlistData.htm";
function viewOrder(id,idnum){
	var url = "<%=path%>/finance/trad/viewtrad.htm?id="+id+"&ordernum="+idnum
	
	var iTop = (window.screen.availHeight-600)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-1000)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'checkOrderWin', 'height=600,width=1000,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}



function refreshGrid(){
	Ext.getCmp('payedListGrid').getStore().reload();
}

function billSend(id,ordernum,tradcd){
	if(confirm("订单号【"+ordernum+"】确认开据发票？")){
		Ext.getBody().mask("信息提交中...");
		Ext.Ajax.request({
			url: '<%=path %>/finance/trad/billsend.htm',
			method:'POST',
			timeout: 15000,
			params:{'id':id,'tradCd':tradcd,'ordernum':ordernum},
			success:function(response){
				Ext.getBody().unmask();
				var jsondata = {};
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
				alert('服务器异常或超时!提交失败!');
			}
		});
	}
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
		             'id','ordernum',"custName","orderStatus","orderStatusCn","yfee","acFee","tradOprName","tradTime","tradCd","checkStatus"
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
	

    var oprRender = function(val,meta,rec){
    	var id = val;
    	var ordernum = rec.get('ordernum');
    	
    	var oprArray = [];
		
		if(rec.get('checkStatus')!='1'){
			oprArray.push('<a href="javascript:void(0);" onclick="billSend('+rec.get('id')+',\''+rec.get('ordernum')+'\',\''+rec.get('tradCd')+'\')">开 票</a>');
		}
    	
    	return oprArray.join(" ");
    }
    
    var priceRender = function(val,meta,rec){
    	return Ext.util.Format.number(val,'0.00');
    }
    
    var sRender = function(val,meta,rec){
    	if(rec.get('checkStatus')=='1'){
    		return val+'<font color="#FF1100">已开票</font>';
    	}
    	else{
    		return val+'<font color="#00FF11">未开票</font>';
    	}
    }
    
    // create the Grid
    var grid = new Ext.grid.GridPanel({
    	loadMask:true,
    	borer:false,
    	frame:false,
    	id:'payedListGrid',
        store: store,
        columns: [
            new Ext.grid.RowNumberer({header:'序号',width:40}),
		    {header:'订单编号',width:80,sortable:false,dataIndex:'ordernum'},
		    {header:'状态',width:195,sortable:false,dataIndex:'orderStatusCn',align:'center',renderer:sRender},
		    {header:'客户名称',width:160,sortable:false,dataIndex:'custName'},
		    {header:'应收金额',width:85,sortable:false,dataIndex:'yfee'},
		    {header:'实收金额',width:85,sortable:false,dataIndex:'acFee'},
		    {header:'结算人',width:90,sortable:true,dataIndex:'tradOprName'},
		    {header:'结算时间',width:80,sortable:true,dataIndex:'tradTime'},
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
		    	 
		         Ext.apply(  
		         this.baseParams,  
		         {   
		         	 'ordernum':Ext.getCmp('ordernum').getValue(),
		         	 'custName':Ext.getCmp('custName').getValue(),
		         	 'tel':Ext.getCmp('tel').getValue(),
				     'pageNo':parseInt(startpos/15)+1
		         });  
		     });
		     
	var view = new Ext.Viewport({
		layout:'border',
		border:false,
		items:[{
				region:'north',
				height:55,
				maxHeight:60,
				minHeight:40,
				title:'订单结算记录查询',
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
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   客户名称:'},{
		                     xtype:'textfield',id:'custName'
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   客户电话:'},{
		                    xtype:'textfield',id:'tel'
		                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		                    xtype:'button',
		                    text: ' 重 置 ',
		                    handler:function(){
		                    	 Ext.getCmp('ordernum').setValue("");
					         	 Ext.getCmp('custName').setValue("");
					         	 Ext.getCmp('tel').setValue("");
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
				title: '结算记录查询',
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
