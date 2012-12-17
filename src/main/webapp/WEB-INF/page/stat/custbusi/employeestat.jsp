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
<title>业务员业绩统计列表</title>
<%@include file="../../exthead.jsps"%>
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
<o:select id="payTypeSel" style="display:none;" type="payType" headText="---请选择---" headValue="" ></o:select>
<o:select type="isOut" style="display:none;"  id="dispatchOtherSel" headText="---是否OT---" headValue=""></o:select>
</div>

<script type="text/javascript">

var dataUrl = "<%=path%>/custbusistat/ach/employeestatData.htm";

function refreshGrid(){
	Ext.getCmp('employeeAchListGrid').getStore().reload();
}



Ext.onReady(function(){

	var payTypeSelOptions = document.getElementById('payTypeSel').options;
	var payTypeSelData = [];
	for(var i=0;i<payTypeSelOptions.length;i++){
		payTypeSelData.push([payTypeSelOptions[i].value,payTypeSelOptions[i].text]);
	}
	
	var dispatchSelOptions = document.getElementById('dispatchOtherSel').options;
	var dispatchSelData = [];
	for(var i=0;i<dispatchSelOptions.length;i++){
		dispatchSelData.push([dispatchSelOptions[i].value,dispatchSelOptions[i].text]);
	}
    
    var dt = new Date();

	// create the data store
    store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:dataUrl}),
		reader: new Ext.data.JsonReader({  
		             root: 'list',  
		             totalProperty: 'totalRows',
		             id: 'id'
		             },[  
		             'id','idNo','userAccount',"userName","clientNum","achFee"
		        ])
    });
    
    
    var priceRender = function(val,meta,rec){
    	if(val){
    		return Ext.util.Format.number(val,'0.00');
    	}
    }

    // create the Grid
    var grid = new Ext.grid.GridPanel({
    	loadMask:true,
    	borer:false,
    	frame:false,
    	id:'employeeAchListGrid',
        store: store,
        columns: [
            new Ext.grid.RowNumberer({header:'序号',width:40}),
		    {header:'用户名',width:90,sortable:false,dataIndex:'userAccount'},
		    {header:'用户名称',width:95,sortable:false,dataIndex:'userName',align:'center'},
		    {header:'总客户数',width:80,sortable:false,dataIndex:'clientNum'},
		    {header:'金额',width:80,sortable:false,dataIndex:'achFee',renderer:priceRender}
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

        	}
        }
    });
    
    store.on('beforeload',function(data,options){
		    	 var startpos = 0;
		    	 if(options.hasOwnProperty('params')&&options.params.hasOwnProperty('start')){
		    	 	startpos = options.params['start'];
		    	 	if(!startpos){startpos = 0;}
		    	 }
		    	 
		    	 var utimeVal = Ext.getCmp('uptime').getValue();
		    	 var dtimeVal = Ext.getCmp('downtime').getValue();
		    	 
		         Ext.apply(  
		         this.baseParams,  
		         {   
		         	 'uptime':utimeVal,
		         	 'downtime':dtimeVal,
		         	 'dispatchOther':Ext.getCmp('dispatchOther').getValue(),
		         	 'payType':Ext.getCmp('payType').getValue(),
		         	 'userName':Ext.getCmp('userName').getValue(),
		         	 'userAccount':Ext.getCmp('userAccount').getValue(),
				     'pageNo':parseInt(startpos/15)+1
		         });  
		     });
		     
	var view = new Ext.Viewport({
		layout:'border',
		border:false,
		items:[{
				region:'north',
				height:100,
				maxHeight:120,
				minHeight:100,
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
			                items:[{xtype:'label',text:'   结算时间≥:'},{
				                    xtype:'datefield',id:'uptime',width:125,format:'Y-m-d',editable:false,value:dt.getFirstDateOfMonth()
				                },{xtype:'label',text:'≤'},{
				                    xtype:'datefield',id:'downtime',width:125,format:'Y-m-d',editable:false
				                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   结算类型:'},{
				                    xtype:'combo',
				                    id:'payType',
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
								        data: payTypeSelData
								    }),
								    valueField: 'id',
								    displayField: 'val'
				                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},
				                {xtype:'label',text:'   是否OT单:'},{
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
			                items:[{xtype:'label',text:'   用户名:'},{
				                    xtype:'textfield',id:'userAccount'
				                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{xtype:'label',text:'   用户名称:'},{
				                     xtype:'textfield',id:'userName'
				                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},
				                {
				                    xtype:'button',
				                    text: ' 重 置 ',
				                    handler:function(){
				                    	 Ext.getCmp('uptime').setValue(dt.getFirstDateOfMonth());
							         	 Ext.getCmp('downtime').setValue("");
							         	 Ext.getCmp('dispatchOther').setValue("");
							         	 Ext.getCmp('payType').setValue("");
							         	 Ext.getCmp('userName').setValue("");
							         	 Ext.getCmp('userAccount').setValue("");
				                    }
				                },{
				                    xtype:'button',
				                    text: ' 查 询 ',
				                    handler:function(){
				                    	store.load();
				                    }
				                }]
	                	}
	                ]
				}]
			},{
				region:'center',
				title: '业务员业绩统计列表',
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