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
<title>业务分类统计</title>
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

var dataUrl = '<%=path%>/custbusistat/busi/busiRankChartData.htm';

var swfUrl = '<%=path%>/pub/resources/libs/extjs3/resources/charts.swf';
Ext.chart.Chart.CHART_URL = '<%=path%>/pub/resources/libs/extjs3/resources/charts.swf';

Ext.onReady(function(){
    
    var chartStore = new Ext.data.ArrayStore({
        fields: ['type', 'total'],
        proxy: new Ext.data.HttpProxy({url:dataUrl}),
        idIndex: 0
    });
    
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
	var view = new Ext.Viewport({
		layout:'border',
		border:false,
		items:[{
				region:'north',
				height:65,
				maxHeight:100,
				minHeight:60,
				title:'业务量分类统计排行',
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
			                },{xtype:'label', html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
			                    xtype:'button',
			                    text: ' 重 置 ',
			                    handler:function(){
			                    	 Ext.getCmp('uptime').setValue(dt.getFirstDateOfMonth());
						         	 Ext.getCmp('downtime').setValue("");
						         	 Ext.getCmp('dispatchOther').setValue("");
						         	 Ext.getCmp('payType').setValue("");
			                    }
			                },{
			                    xtype:'button',
			                    text: ' 查 询 ',
			                    handler:function(){
			                    	Ext.getBody().mask("查询中...");
			                    	chartStore.load();
			                    }
			                }]
	                	}
	                ]
				}]
			},{
				region:'center',
				title: '图表区',
				iconCls:'listArea',
				layout:'border',
				items:[{
					region:'west',
					width:470,
					split:true,
					minWidth:340,
					title: '业务分类统计饼状图',
			        items: {
			            store: chartStore,
			            xtype: 'piechart',
			            dataField: 'total',
			            categoryField: 'type',
			            extraStyle:
			            {
			                legend:
			                {
			                    display: 'bottom',
			                    padding: 5,
			                    font:
			                    {
			                        family: 'Tahoma',
			                        size: 13
			                    }
			                }
			            }
			        }
				},{
					region:'center',
					title: '业务分类统计柱状图',
			        items: {
			            xtype: 'columnchart',
			            store: chartStore,
			            yField: 'total',
				        url: swfUrl,
			            xField: 'type',
			            xAxis: new Ext.chart.CategoryAxis({
			                title: '业务类别'
			            }),
			            yAxis: new Ext.chart.NumericAxis({
			                title: '业务量（金额:元）'
			            })
			        }
				}]
		}]
	});
    
    
    chartStore.on('beforeload',function(data,options){
		    	 var utimeVal = Ext.getCmp('uptime').getValue();
		    	 var dtimeVal = Ext.getCmp('downtime').getValue();
		    	 
		    	 if(utimeVal){utimeVal = utimeVal.format('Y-m-d');}
		    	 if(dtimeVal){dtimeVal = dtimeVal.format('Y-m-d');}
		    	 
		         Ext.apply(  
		         this.baseParams,  
		         {   
		         	 'uptime':utimeVal,
		         	 'downtime':dtimeVal,
		         	 'dispatchOther':Ext.getCmp('dispatchOther').getValue(),
		         	 'payType':Ext.getCmp('payType').getValue()
		         });  
		     });
	chartStore.on('load',function(){
		Ext.getBody().unmask();
	});
	
	chartStore.load();
    
    setTimeout(function(){
	     Ext.get('loading').remove();
	     Ext.get('loading-mask').fadeOut({remove:true});
	 }, 120);
});


</script>

</body>
</html>