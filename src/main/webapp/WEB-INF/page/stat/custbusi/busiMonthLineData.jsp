<%@ page contentType="text/plain; charset=GBK"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%><%@ taglib prefix="o" uri="/WEB-INF/omdp.tld"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><anychart>
	<settings>
		<animation enabled="True"/>
	</settings>
  <charts>
    <chart plot_type="CategorizedVertical">
    	<chart_settings>
    		<legend enabled="true">
	          <title enabled="false"/>
	          <icon>
	            <marker enabled="true" type="%MarkerType" size="8"/>
	          </icon>
	        </legend>
    		<title>
    			<text>${queryBean.year}年度月业务量(含所有已结算及未结算工单)曲线(按工单月 非财务月)</text>
    			<background enabled="false"/>
    		</title>
    		<axes>
    			<x_axis tickmarks_placement="Center">
    				<title enabled="true">
							<text>月份</text>
							<font color="#135D8C"/>
					</title>
    			</x_axis>
					<y_axis>
						<title enabled="true">
							<text>总业务量(单位:元)</text>
							<font color="#135D8C"/>
						</title>
					</y_axis>
    		</axes>
    	</chart_settings>
    	<data_plot_settings default_series_type="Line">
    		<line_series>
    			<tooltip_settings enabled="true">
				<format>
{%YValue}元
</format>
    			</tooltip_settings>
    		</line_series>
    	</data_plot_settings>
	    <data>
			<series name="月业务量曲线">
			<c:forEach items="${data}" var="entryItem" varStatus="status">
			<c:choose>
		   	<c:when test="${entryItem[1]==null}"><point name="${entryItem[0]}月"/></c:when>
		   	<c:when test="${entryItem[1]!=null}"><point name="${entryItem[0]}月" y="${entryItem[1]}" /></c:when>
		   	</c:choose>
			</c:forEach>
			</series>
	    </data>
    </chart>
  </charts>
</anychart>