<%@ page contentType="text/html; charset=UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
<meta http-equiv=”content-type” content=”text/html;charset=utf-8″>
<title>客户消费统计</title>
<link rel="stylesheet" type="text/css" href="<%=path %>/pub/resources/css/default.css"/>
<script type="text/javascript" src="<%=path%>/pub/resources/libs/jscharts_mb.js"></script>
</head>
<body>
<div id="graph">统计数据载入中...</div>
<script type="text/javascript">
	var myData = new Array(['开发中...', 21], ['开发中...', 28], ['开发中...', 12], ['开发中...', 17]);
	var colors = ['#AF0202', '#EC7A00', '#FCD200', '#81C714'];
	var myChart = new JSChart('graph', 'bar');
	myChart.patchMbString();
	myChart.setFontFamily("微软雅黑"); // 设置显示字体为微软雅黑
	myChart.setAxisValuesFontSize(9); // 设置柱状图和线条图的标尺字体大小为9px
    myChart.setPieUnitsFontSize(10); // 设置饼图的项目标识字体大小为10px
	myChart.setDataArray(myData);
	myChart.colorizeBars(colors);
	myChart.setTitle('客户消费统计开发中...');
	myChart.setTitleColor('#8E8E8E');
	myChart.setAxisNameX('');
	myChart.setAxisNameY('%');
	myChart.setAxisColor('#C4C4C4');
	myChart.setAxisNameFontSize(16);
	myChart.setAxisNameColor('#999');
	myChart.setAxisValuesColor('#7E7E7E');
	myChart.setBarValuesColor('#7E7E7E');
	myChart.setAxisPaddingTop(60);
	myChart.setAxisPaddingRight(140);
	myChart.setAxisPaddingLeft(150);
	myChart.setAxisPaddingBottom(40);
	myChart.setTextPaddingLeft(105);
	myChart.setTitleFontSize(11);
	myChart.setBarBorderWidth(1);
	myChart.setBarBorderColor('#C4C4C4');
	myChart.setBarSpacingRatio(50);
	myChart.setGrid(false);
	myChart.setSize(616, 321);
	myChart.setBackgroundImage('<%=path%>/pub/resources/images/chart_bg.jpg');
	myChart.draw();
</script>

</body>
</html>