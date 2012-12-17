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
<title>OMAX|深圳欧美数码印务有限公司管理系统V1.0</title>
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
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/resources/css/ext-all.css" /> 
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/resources/css/xtheme-gray.css" /> 
<!-- GC --> 
<!-- LIBS --> 
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-base.js"></script> 
<!-- ENDLIBS --> 
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-all.js"></script>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-lang-zh_CN.js"></script>
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
<script type="text/javascript">


function trashContract(contractNo){

	if(confirm("确定作废所选合同信息吗?")){
		Ext.Ajax.request({
		   url: '<%=path%>/contract/trashContract.htm',
		   method:'POST',
		   success: function(result){
		   		var json = {};
		   		try{
		   			json = Ext.util.JSON.decode(result.responseText);
		   		}
		   		catch(e){}
		   		
		   		if(json.success===true){
		   			Ext.Msg.alert("操作成功",json.msg);
		   			Ext.getCmp('contractListGrid').getStore().reload();
		   		}
		   		else{
		   			Ext.Msg.alert("操作失败","服务器超时或异常，操作失败，请联系管理员");
		   		}
		   },
		   failure: function(){
		   		Ext.Msg.alert("操作失败","服务器超时或异常，操作失败，请联系管理员");
		   },
		   params: { 'contractNo': contractNo }
		});
	}
}

function modify(id){
	var url = "<%=path%>/contract/toContractEdit.htm?id="+id
	
	var iTop = (window.screen.availHeight-680)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-900)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'editContractWin', 'height=680,width=900,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=n o,status=no');
}

function addHandler(){
	var url = "<%=path%>/contract/toContractAdd.htm"
	
	var iTop = (window.screen.availHeight-680)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-900)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'addContractWin', 'height=680,width=900,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=n o,status=no');
}
function viewContract(id){
	var url = "<%=path%>/contract/toContractView.htm?id="+id
	
	var iTop = (window.screen.availHeight-680)/2;       //获得窗口的垂直位置;
  	var iLeft = (window.screen.availWidth-900)/2;           //获得窗口的水平位置;
  	
	window.open(url, 'viewContractWin', 'height=680,width=900,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=n o,status=no');
}
Ext.onReady(function(){
	
	// create the data store
    store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'<%=path%>/contract/contractListData.htm'}),
		reader: new Ext.data.JsonReader({  
		             root: 'list',  
		             totalProperty: 'totalRows',
		             id: 'id'
		             },[  
		             'id','contractNo','buyer','orderNum','custCd','seller','package_','deliverPlace','payChannel','memo','gross','buyerAddress','buyerTel','buyerFax','buyerAccountBank','buyerAccountNo','buyerMan','buyerDate','sellerAddress','sellerTel','sellerFax','sellerAccountBank','sellerAccountNo','sellerMan','sellerDate','creator','createTime','updateTime'
		        ]) 
    });
    
    var oprRender = function(val,meta,rec){
    	return '<a href="javascript:void(0)" onclick="modify('+val+')"><img src="<%=path%>/pub/resources/images/document-table.png" title="编辑合同信息" style="cursor:hand;"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="trashContract(\''+rec.get('contractNo')+'\')"><img src="<%=path%>/pub/resources/images/drop-no.gif" title="作废合同" style="cursor:hand;"/></a>';
    }
    
    // create the Grid
    var grid = new Ext.grid.GridPanel({
    	loadMask:true,
    	borer:false,
    	frame:false,
    	id:'contractListGrid',
        store: store,
        tbar:[{text:'创建新的合同',iconCls:'newContract',handler:addHandler},'-'],
        columns: [
            {header:'合同编号',width:80,sortable:true,dataIndex:'contractNo'},
		    {header:'甲方',width:90,sortable:false,dataIndex:'buyer'},
		    {header:'甲方代表',width:100,sortable:false,dataIndex:'buyerMan'},
		    {header:'包装',width:100,sortable:false,dataIndex:'package_'},
		    {header:'交货地点',width:100,sortable:false,dataIndex:'deliverPlace'},
		    {header:'合同金额',width:80,sortable:true,dataIndex:'gross'},
		    {header:'地址',width:80,sortable:true,dataIndex:'buyerAddress',hidden:true},
		    {header:'甲方电话',width:90,sortable:false,dataIndex:'buyerTel'},
		    {header:'甲方传真',width:80,sortable:true,dataIndex:'buyerFax',hidden:true},
		    {header:'创建时间',width:80,sortable:true,dataIndex:'createTime'},
		    {header:'备注',width:120,sortable:true,dataIndex:'memo',hidden:true},
		    {header:'操作',width:130,sortable:true,dataIndex:'id',renderer:oprRender}
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
        		viewContract(t.getStore().getAt(rInd).get("id"));
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
		         	 'contractNo':Ext.getCmp('contractNoF').getValue(),
		         	 'buyer':Ext.getCmp('buyerF').getValue(),
		         	 'buyerMan':Ext.getCmp('buyerManF').getValue(),
				     'pageNo':parseInt(startpos/15)+1
		         });  
		     });
		     
	var view = new Ext.Viewport({
		layout:'border',
		items:[{
				region:'north',
				height:90,
				maxHeight:120,
				minHeight:40,
				title:'查询区域',
				iconCls:'queryArea',
				split:true,
				collapsible:true,
				layoutConfig: {
                    padding:'5',
                    pack:'start',
                    align:'middle'
                },
                layout:'hbox',
                defaults:{margins:'0 5 0 0'},
                items:[{xtype:'label',text:'   合同编号:'},{
                    xtype:'textfield',id:'contractNoF'
                },{xtype:'label',text:'   甲方名称:'},{
                    xtype:'textfield',id:'buyerF'
                },{xtype:'label',text:'   甲方代表:'},{
                    xtype:'textfield',id:'buyerManF'
                },{
                    xtype:'button',
                    text: ' 查 询 ',
                    handler:function(){
                    	store.load({start:0,limit:10});
                    }
                }]
			},{
				region:'center',
				title:'合同列表',
				iconCls:'listArea',
				layout:'fit',
				items:[grid]
		}]
	});
});
</script>
</head>
<body>

</body>