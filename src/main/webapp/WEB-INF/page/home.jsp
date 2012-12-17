<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="o" uri="/WEB-INF/omdp.tld"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><html>
<head>
<title>OMAX|深圳欧美数码印务有限公司管理系统V2.0</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/resources/css/ext-all.css" /> 
<link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/resources/css/xtheme-gray.css" /> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/pub/resources/libs/extjs3/ux/css/PanelResizer.css" /> 
 
    <!-- GC --> 
    <!-- LIBS --> 
    <script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-base.js"></script> 
    <!-- ENDLIBS --> 
    <script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-all.js"></script> 
    <script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ext-lang-zh_CN.js"></script> 
    <script type="text/javascript" src="<%=path%>/pub/resources/libs/extjs3/ux/PanelResizer.js"></script> 
    
    <script type="text/javascript">
    Ext.BLANK_IMAGE_URL = '<%=path%>/pub/resources/images/s.gif';
    </script>
<style type="text/css">
<!--

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

.info_box{
	padding:10px;
	margin-top:10px;
	font-size:14px;
	border:double;
	border-color:#ddDc0f;
	vertical-align:middle;
	text-align:left;
	line-height:15px;
}
.succ_box{
	padding:10px;
	margin-top:10px;
	font-size:14px;
	background-color: ;
	border:double;
	border-color:#00DD00;
	vertical-align:middle;
	text-align:left;
	line-height:15px;
}
.exportXls{
	background-image: url( <%=path%>/pub/resources/images/excel.png) !important;
}
.orderstatpanel{
	background-image: url( <%=path%>/pub/resources/images/leaf.png) !important;
}
.daycashpanel{
	background-image: url( <%=path%>/pub/resources/images/ruby.png) !important;
}
.monthlinepanel{
	background-image: url( <%=path%>/pub/resources/images/umbrella.png) !important;
}
.monthdaylinepanel{
	background-image: url( <%=path%>/pub/resources/images/chart_curve.png) !important;
}
.top20custpanel{
	background-image: url( <%=path%>/pub/resources/images/block-share.png) !important;
}
.search{
	background-image: url( <%=path%>/pub/resources/images/search-small.png) !important;
}
-->
</style>
</head>
<body bgcolor=#3366cc background="<%=path%>/pub/resources/images/back.gif">
<table width="100%" height="100%">
	<tr><td align="left" valign="middle">
		<div class="info_box">
		<img src="<%=path%>/pub/resources/images/asterisk-yellow.png"/>&nbsp;<a href="<%=path%>/order/orderQuery/queryAll.htm?orderTimePickerU=${today}">今日工单总数: ${todayOrderNum}</a> &nbsp;&nbsp;&nbsp;<a href="<%=path%>/order/orderQuery/queryAll.htm?orderTimePickerU=${lastWeekDay}">近一周工单总数: ${currentWeekOrderNum}</a>
		</div>
		</td>
	</tr>
	<c:if test="${unDoneOrderNum>0||unDoneOTOrderNum>0}">
	<tr><td align="left" valign="middle">
		<div class="info_box">
		<img src="<%=path%>/pub/resources/images/info.png"/>
		<c:if test="${unDoneOrderNum>0}">&nbsp;<a href="<%=path%>/order/orderQuery/queryUnfinish.htm">当前有${unDoneOrderNum}个未完工订单</a>&nbsp;&nbsp;&nbsp;
		</c:if>
		<c:if test="${unDoneOTOrderNum>0}">&nbsp;<a href="<%=path%>/order/ot-orderQuery/queryUnfinish.htm">当前有${unDoneOTOrderNum}个未完工OT单</a>
		</c:if>
		</div>
		</td>
	</tr></c:if>
	<o:urlguard url="/finance/trad/totrad.htm"><c:if test="${unTradPayOrderNum>0||unCheckPayOrderNum>0||delayTradOrderNum>0}">
	<tr><td align="left" valign="middle">
		<div class="info_box">
		<img src="<%=path%>/pub/resources/images/leaf.png"/>
		<c:if test="${unTradPayOrderNum>0}">&nbsp;<a href="<%=path%>/finance/trad/totrad.htm?tradStatus=0&ext1=0">当前有${unTradPayOrderNum}个已完工的非月结单待结算</a>&nbsp;&nbsp;&nbsp;
		</c:if>
		<c:if test="${unCheckPayOrderNum>0}"><a href="<%=path%>/finance/trad/totrad.htm?tradStatus=2">当前有${unCheckPayOrderNum}个已结算单未核销</a>&nbsp;&nbsp;&nbsp;
		</c:if>
		<c:if test="${delayTradOrderNum>0}">&nbsp;<a href="<%=path%>/finance/trad/totrad.htm?orderTimePickerD=${lastMonthDay}&ext1=1&tradStatus=0">当前有${delayTradOrderNum}个完工月结单已经超过30天未结算</a>
		</c:if>
		</div>
		</td>
	</tr></c:if></o:urlguard>
	<o:urlguard url="/deliver/deliverQuery/queryAll.htm"><c:if test="${deliverNum>0}">
	<tr><td align="left" valign="middle">
		<div class="info_box">
		<img src="<%=path%>/pub/resources/images/controller.png"/>
		&nbsp;<a href="<%=path%>/deliver/deliverQuery/queryTodo.htm">当前有${deliverNum}个送货单未签收</a>
		</div>
		</td>
	</tr></c:if></o:urlguard>
	<o:urlguard url="/finance/stat/ifee.htm">
	<tr>
		<td>
		<div id="daycash" style="height:100%;width:100%;">
		
		</div>
		</td>
	</tr>
	<script type="text/javascript" src="<%=path%>/pub/resources/js/AnyChart.js"></script>
	<script type="text/javascript">
Ext.onReady(function(){
	
	var golbalCustId = '';
	var charStoreCust = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'<%=path%>/custbusistat/busi/custline.htm'}),
		reader: new Ext.data.JsonReader({  
		             root: 'list',  
		             id: 'month'
		             },[  
		             'month','amount'
		        ]) 
    });
    
	charStoreCust.on('beforeload',function(data,options){
         var ottype = Ext.getCmp('custline-combo').getValue();
         var year = Ext.getCmp('custline-year').getValue();
    	 
	     Ext.apply(  
	     this.baseParams,  
	     {   'year':year,
	    	 'custId':golbalCustId,
	    	 "dispatchOther":ottype
	     });  
	     
	 });
    
	function loadCustInfo(custId, custName, dyear){
		golbalCustId = custId;
		var win = new Ext.Window({
			modal:true,
            layout:'fit',
            title:custName+"-业务量分析",
            iconCls:'',
            width:740,
            height:480,
            animEl: 'hometab',
            tbar:[{text:'OT类型:',xtype:'label'},new Ext.form.ComboBox({
        	    typeAhead: false,
        	    triggerAction: 'all',
        	    editable:false,
        	    width:110,
        	    id:'custline-combo',
        	    lazyRender:true,
        	    mode: 'local',
        	    store: new Ext.data.ArrayStore({
        	        id: 0,
        	        fields: [
        	            'cd',
        	            'name'
        	        ],
        	        data: [['', '所有'], ['0', '非OT'], ['1', 'OT']]
        	    }),
        	    value:'0',
        	    valueField: 'cd',
        	    displayField: 'name',
        	    listeners:{
        	    	'select':function(){
        	    		charStoreCust.load();
        	    	}
        	    }
        	}),'-',new Ext.form.ComboBox({
        	    typeAhead: false,
        	    triggerAction: 'all',
        	    editable:false,
        	    width:110,
        	    lazyRender:true,
        	    id:'custline-year',
        	    mode: 'local',
        	    store: new Ext.data.ArrayStore({
        	        id: 0,
        	        fields: [
        	            'cd',
        	            'name'
        	        ],
        	        data: ${yearList}
        	    }),
        	    value:dyear,
        	    valueField: 'cd',
        	    displayField: 'name',
        	    listeners:{
        	    	'select':function(){
        	    		charStoreCust.load();
        	    	}
        	    }
        	}),{text:'年',xtype:'label'},'-',{text:'查询',handler:function(){
	    		charStoreCust.load();
        	}}],
            items: {
                xtype: 'linechart',
                store: charStoreCust,
                xField: 'month',
                yField: 'amount',
                yAxis: new Ext.chart.NumericAxis({
                    displayName: 'Amount'
                }),
                tipRenderer : function(chart, record){
                    return Ext.util.Format.number(record.data.amount, '0,0.00')+"元";
                }
            }
        });
		
		win.show();
		charStoreCust.load();
		
		
	}
	
	Ext.chart.Chart.CHART_URL = '<%=path%>/pub/resources/libs/extjs3/resources/charts.swf';
	
    // create the data store
    var store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'<%=path%>/custbusistat/busi/daycash.htm'}),
		reader: new Ext.data.JsonReader({  
		             root: 'list',  
		             totalProperty: 'totalRows',
		             id: 'feeDate'
		             },[  
		             'feeDate','feeType','yfeeTotal','acFeeTotal'
		        ]) 
    });

	
	var combo = new Ext.form.ComboBox({
	    typeAhead: false,
	    triggerAction: 'all',
	    editable:false,
	    width:110,
	    lazyRender:true,
	    mode: 'local',
	    store: new Ext.data.ArrayStore({
	        id: 0,
	        fields: [
	            'cd',
	            'name'
	        ],
	        data: [['1', '现金'], ['2', '支票'], ['3', '银行转帐'], ['4', '记账']]
	    }),
	    value:'1',
	    valueField: 'cd',
	    displayField: 'name',
	    listeners:{
	    	'select':function(){
	    		store.reload();
	    	}
	    }
	});

	var currency1 = function(val , metaData, record, rowIndex, colIndex, store){
		var yfee = record.get("yfeeTotal");
		var acFee = record.get("acFeeTotal");
		
		if(yfee==acFee){
			return '<font color="#331133">￥'+val+'</font>';
		}
		else{
			return '<font color="#00ff00">￥'+val+'</font>';
		}
	}
	var currency2 = function(val , metaData, record, rowIndex, colIndex, store){
		var yfee = record.get("yfeeTotal");
		var acFee = record.get("acFeeTotal");
		
		if(yfee==acFee){
			return '<font color="#331133">￥'+val+'</font>';
		}
		else{
			return '<font color="#ff0000">￥'+val+'</font>';
		}
	}
    // create the Grid
    var grid = new Ext.grid.GridPanel({
    	iconCls:'daycashpanel',
        store: store,
        tbar:[{text:'结算方式:',xtype:'label'},combo,'-',{text:'查 询',handler:function(){
        		store.load({start:0,limit:15});
        	}},'->','-',{text:'导出Excel',iconCls:'exportXls',handler:function(){
        	Ext.get("tmpFrame").dom.src="<%=path%>/custbusistat/busi/daycashExport.htm?feeType="+combo.getValue();
        }}],
        columns: [
            {header:'日期',width:80,sortable:true,dataIndex:'feeDate'},
		    {header:'应收金额',width:90,sortable:false,dataIndex:'yfeeTotal',renderer:currency1},
		    {header:'实收金额',width:100,sortable:false,dataIndex:'acFeeTotal',renderer:currency2}
        ],
        stripeRows: true,
        title:'每日结算金额统计',
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
		         {   'feeType':combo.getValue(),
				     'pageNo':parseInt(startpos/15)+1
		         });  
		     });
    
    
    
    
    var otTypeCombo = new Ext.form.ComboBox({
	    typeAhead: false,
	    triggerAction: 'all',
	    editable:false,
	    width:110,
	    lazyRender:true,
	    mode: 'local',
	    store: new Ext.data.ArrayStore({
	        id: 0,
	        fields: [
	            'cd',
	            'name'
	        ],
	        data: [['', '所有'], ['0', '非OT'], ['1', 'OT']]
	    }),
	    value:'0',
	    valueField: 'cd',
	    displayField: 'name',
	    listeners:{
	    	'select':function(){
	    		reloadLineChart();
	    	}
	    }
	});
    
    var chartSample = null;
    
    function reloadLineChart(){
    	Ext.Ajax.request({
			url:"<%=path%>/custbusistat/busi/busiMonthLine.htm",
			method:'POST',
			params:{'dispatchOther':otTypeCombo.getValue()},
			timeout:20000,
			failure:function(){
				
			},
			success:function(result){
				chartSample.setData(result.responseText);
			}
		});	
    }
    

    var charStore01 = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'<%=path%>/custbusistat/busi/dayline.htm'}),
		reader: new Ext.data.JsonReader({  
		             root: 'list',  
		             id: 'day'
		             },[  
		             'day','amount'
		        ]) 
    });
    
    charStore01.on('beforeload',function(data,options){
         var ottype = Ext.getCmp('dayline-combo').getValue();
         var year = Ext.getCmp('dayline-year').getValue();
         var month = Ext.getCmp('dayline-month').getValue();
    	 
	     Ext.apply(  
	     this.baseParams,  
	     {   'year':year,
	    	 'montn':month,
	    	 "dispatchOther":ottype
	     });  
	 });

    var charStore02 = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'<%=path%>/custbusistat/busi/oldtop20cust.htm'}),
		reader: new Ext.data.JsonReader({  
		             root: 'list',  
		             id: 'custNo'
		             },[  
		             'custName','amount','custNo'
		        ]) 
    });
    
    charStore02.on('beforeload',function(data,options){
        var ottype = Ext.getCmp('oldtop20-combo').getValue();
        var year = Ext.getCmp('oldtop20-year').getValue();
        var month = Ext.getCmp('oldtop20-month').getValue();
   	 
	     Ext.apply(  
	     this.baseParams,  
	     {   'year':year,
	    	 'montn':month,
	    	 "dispatchOther":ottype
	     });  
	 });
    
    var charStore03 = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'<%=path%>/custbusistat/busi/deltatop20cust.htm'}),
		reader: new Ext.data.JsonReader({  
		             root: 'list',  
		             id: 'custNo'
		             },[  
		             'custName','amount','custNo'
		        ]) 
    });
    
    charStore03.on('beforeload',function(data,options){
        var ottype = Ext.getCmp('deltatop20-combo').getValue();
        var year = Ext.getCmp('deltatop20-year').getValue();
        var month = Ext.getCmp('deltatop20-month').getValue();
   	 
	     Ext.apply(  
	     this.baseParams,  
	     {   'year':year,
	    	 'montn':month,
	    	 "dispatchOther":ottype
	     });  
	 });
    
    var tabs = new Ext.TabPanel({
        activeTab: 0,
        id:'hometab',
        width:1000,
        height:580,
        plugins: new Ext.ux.PanelResizer({
            minHeight: 100
        }),
        items:[
               {
	        	title:'月度业务量日线',
	            iconCls:'monthdaylinepanel',
	            tbar:[{text:'OT类型:',xtype:'label'},new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    id:'dayline-combo',
            	    lazyRender:true,
            	    mode: 'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data: [['', '所有'], ['0', '非OT'], ['1', 'OT']]
            	    }),
            	    value:'0',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore01.load();
            	    	}
            	    }
            	}),'-',new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    lazyRender:true,
            	    id:'dayline-year',
            	    mode: 'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data: ${yearList}
            	    }),
            	    value:'${year}',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore01.load();
            	    	}
            	    }
            	}),{text:'年',xtype:'label'},new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    lazyRender:true,
            	    id:'dayline-month',
            	    mode: 'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data: [['01', '01'], ['02', '02'], ['03', '03'], ['04', '04'], ['05', '05'], ['06', '06'], ['07', '07'], ['08', '08'], ['09', '09'], ['10', '10'], ['11', '11'], ['12', '12']]
            	    }),
            	    value:'${month}',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore01.load();
            	    	}
            	    }
            	}),{text:'月',xtype:'label'},'-',{text:'查 询',iconCls:'search',handler:function(){
	            	charStore01.load();
	        	}}],
	        	listeners:{
	        		'activate':function(){
	        			charStore01.load();
	        		}
	        	},
	        	items: {
	                xtype: 'linechart',
	                store: charStore01,
	                xField: 'day',
	                yField: 'amount',
	                yAxis: new Ext.chart.NumericAxis({
	                    displayName: 'Amount'
	                }),
	                tipRenderer : function(chart, record){
	                    return Ext.util.Format.number(record.data.amount, '0,0.00')+"元";
	                }
	            },
	            extraStyle: {
	                xAxis: {
	                     labelRotation: -90
	                 }
	            }
	        },
            { title:'年度业务量月线',
              iconCls:'monthlinepanel',
              tbar:[{text:'OT类型:',xtype:'label'},otTypeCombo,'-',{text:'查 询',handler:function(){
		        		reloadLineChart();	
		        	}}],
              html:'<div id="mscolumn"></div>',
              listeners:{
              	'afterrender':function(){
					chartSample = new AnyChart('<%=path%>/pub/resources/libs/AnyChart.swf');
					chartSample.width = '100%';
					chartSample.height = 480;
					chartSample.menu = false;
					chartSample.wMode='opaque';
					chartSample.write('mscolumn');

              		Ext.Ajax.request({
						url:"<%=path%>/custbusistat/busi/busiMonthLine.htm",
						method:'GET',
						timeout:20000,
						params:{'dispatchOther':otTypeCombo.getValue()},
						failure:function(){
							
						},
						success:function(result){
							chartSample.setData(result.responseText);
						}
					});	
              	}
              } 
            },
            {
            	title:'存量TOP20客户',
                iconCls:'top20custpanel',
                tbar:[{text:'OT类型:',xtype:'label'},new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    lazyRender:true,
            	    id:'oldtop20-combo',
            	    mode: 'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data: [['', '所有'], ['0', '非OT'], ['1', 'OT']]
            	    }),
            	    value:'0',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore02.load();
            	    	}
            	    }
            	}),'-',new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    id:'oldtop20-year',
            	    lazyRender:true,
            	    mode:'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data:  ${yearList}
            	    }),
            	    value:'${year}',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore02.load();
            	    	}
            	    }
            	}),{text:'年',xtype:'label'},new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    lazyRender:true,
            	    id:'oldtop20-month',
            	    mode:'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data: [['01', '01'], ['02', '02'], ['03', '03'], ['04', '04'], ['05', '05'], ['06', '06'], ['07', '07'], ['08', '08'], ['09', '09'], ['10', '10'], ['11', '11'], ['12', '12']]
            	    }),
            	    value:'${month}',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore02.load();
            	    	}
            	    }
            	}),{text:'月',xtype:'label'},'-',{text:'查 询',iconCls:'search',handler:function(){
                	charStore02.load();
	        	}}],
	        	listeners:{
	        		'activate':function(){
	        			charStore02.load();
	        		}
	        	},
       			items:{
                     xtype: 'barchart',
                     store: charStore02,
                     yField: 'custName',
                     xAxis: new Ext.chart.NumericAxis({
                         stackingEnabled: true
                     }),
 	                 tipRenderer : function(chart, record){
 	                    return record.data.custName+":"+Ext.util.Format.number(record.data.amount, '0,0.00')+"元";
 	                 },
                     series: [{
                         xField: 'amount',
                         displayName: 'amount'
                     }],
                     listeners: {
         				itemclick: function(o){
         					var year = Ext.getCmp('oldtop20-year').getValue();
         					var rec = charStore02.getAt(o.index);
         					loadCustInfo(rec.get('custNo'),rec.get('custName'),year);
         				}
         			}
                }
            },
            {
            	title:'新增TOP20客户',
                iconCls:'top20custpanel',
                tbar:[{text:'OT类型:',xtype:'label'},new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    id:'deltatop20-combo',
            	    lazyRender:true,
            	    mode: 'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data: [['', '所有'], ['0', '非OT'], ['1', 'OT']]
            	    }),
            	    value:'0',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore03.load();
            	    	}
            	    }
            	}),'-',new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    lazyRender:true,
            	    id:'deltatop20-year',
            	    mode: 'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data:  ${yearList}
            	    }),
            	    value:'${year}',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore03.load();
            	    	}
            	    }
            	}),{text:'年',xtype:'label'},new Ext.form.ComboBox({
            	    typeAhead: false,
            	    triggerAction: 'all',
            	    editable:false,
            	    width:110,
            	    lazyRender:true,
            	    id:'deltatop20-month',
            	    mode: 'local',
            	    store: new Ext.data.ArrayStore({
            	        id: 0,
            	        fields: [
            	            'cd',
            	            'name'
            	        ],
            	        data: [['01', '01'], ['02', '02'], ['03', '03'], ['04', '04'], ['05', '05'], ['06', '06'], ['07', '07'], ['08', '08'], ['09', '09'], ['10', '10'], ['11', '11'], ['12', '12']]
            	    }),
            	    value:'${month}',
            	    valueField: 'cd',
            	    displayField: 'name',
            	    listeners:{
            	    	'select':function(){
            	    		charStore03.load();
            	    	}
            	    }
            	}),{text:'月',xtype:'label'},'-',{text:'查 询',iconCls:'search',handler:function(){
                	charStore03.load();
	        	}}],
	        	listeners:{
	        		'activate':function(){
	        			charStore03.load();
	        		}
	        	},
	        	items:{
                    xtype: 'barchart',
                    store: charStore03,
                    yField: 'custName',
                    xAxis: new Ext.chart.NumericAxis({
                        stackingEnabled: true
                    }),
	                tipRenderer : function(chart, record){
	 	                    return record.data.custName+":"+Ext.util.Format.number(record.data.amount, '0,0.00')+"元";
	 	            },
                    series: [{
                        xField: 'amount',
                        displayName: 'amount'
                    }],
                    listeners: {
         				itemclick: function(o){
         					var year = Ext.getCmp('deltatop20-year').getValue();
         					var rec = charStore03.getAt(o.index);
         					loadCustInfo(rec.get('custNo'),rec.get('custName'),year)
         				}
         			}
               }
            },
            grid
        ]
    });

    tabs.render('daycash');

    store.load({params:{start:0, limit:15}});	


});
</script>
	</o:urlguard>
</table>
<div style="display:none;">
<iframe id="tmpFrame"></iframe></div>
</body>
</html>