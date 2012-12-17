package cn.com.sunrise.util.report.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 列
 * @author hegang
 *
 */
public class Column {
		
	String title; //列标题
	String width; //列宽度
	List columns=new ArrayList(); //子列
	String styleClass; //列样式
	Object key; //列键
	String rowAlign="center";
	String align="center";
	LabelProvider labelProvider=LabelProvider.getDefaultLabelProvider(); //
	

	Column parent;
	Report report;
	
	int colSpan=1; 
	HashMap attributes=new HashMap();
	
	
	
	
	/**
	 * 
	 * @param report
	 */
	public Column(Report report){
		if(report==null)throw new IllegalArgumentException();
		this.report=report;
		report.head.addColumn(this);
	}
	
	public Column(Column parent){
		this.parent=parent;
		if(parent==null || getReport()==null)throw new IllegalArgumentException();
		parent.addColumn(this);
	}
	
	void addColumn(Column child){
		this.columns.add(child);
		getReport().head.trCount=Math.max(getReport().head.trCount,child.getRowCount() + 1);		
		if(this.columns.size()<2)return;
		Column c=this;
		while(c!=null){
			c.colSpan++;
			c=c.parent;			
		}
		getReport().head.tdCount++;
	}
	
	
	public LabelProvider getLabelProvider() {
		return labelProvider;
	}

	public void setLabelProvider(LabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Column getParent(){
		return this.parent;
	}
	
	
	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getRowAlign() {
		return rowAlign;
	}

	public void setRowAlign(String rowAlign) {
		this.rowAlign = rowAlign;
	}

	public Report getReport(){
		return report==null ? report=parent.getReport() : report;
	}
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public void toHTML(Writer out) throws IOException {
		out.write("<td");
		
		if(this.styleClass!=null){
			out.write(" class='" + this.styleClass + "'");
		}
		
		if(this.colSpan!=1){
			out.write(" colspan=" + colSpan);
		}
		if(this.getRowSpan()!=1){
			out.write(" rowspan=" + this.getRowSpan());
		}
		
		if(this.getAlign()!=null){
			out.write(" align=" + this.getAlign());
		}
		
		if(this.width!=null){
			out.write(" width=" + this.width);		
		}
		
		out.write(">");
		out.write((this.title==null?"":this.title));		
		out.write("</td>");
	}
	
	public int getRowSpan(){
		return this.columns.size()==0 ? getReport().head.trCount - getRowCount() : 1;
	}
	
	public int getRowCount(){
		int count=0;
		for(Column c=parent;c!=null;c=c.parent){
			count++;
		}
		return count;
	}
	
	public Object getAttribute(Object key){
		return attributes.get(key);
	}
	
	public void setAttribute(Object key,Object value){
		attributes.put(key, value);
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.getReport().keyColumns.put(key, this);
		this.key = key;
	}
	
	Object getData(Object rowKey){
		return getReport().getDataTable().getData(rowKey,getKey());
	}
	
}
