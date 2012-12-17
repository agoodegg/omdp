package cn.com.sunrise.util.report.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * 报表
 * @author hegang
 *
 */
public class Report extends Groupable{

	public static final int SORT_ASC=0;
	public static final int SORT_DESC=1;
	
	private boolean defaultRowKeySort=false;  //add by xiaohui.zhou    默认按行键值排序
	
	private static final String COL_SUM = "col-sum";
	String title;
	Head head=new Head();
	DataTable dataTable=new DataTable();
	String styleClass;
	String headStyleClass;
	String headTRStyleClass;
	String bodyStyleClass;
	String bodyTRStyleClass;
	String footStyleClass;
	int border=1;
	private String id;
	private String caption;
	
	HashMap keyColumns=new HashMap();
	ArrayList foots=new ArrayList(); 
	String width;
	
	Group group;

	/**
	 * @param dataTable the dataTable to set
	 */
	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public Report(){
		this.setRowSorter(Sorter.getDefaultSorter());
	}
	
	public void toHTML(Writer out) throws IOException {
		out.write("<table  "+((id==null||id.trim().length()==0)?"":("id=\""+id+"\""))+"  border='" + border + "'");
		if(this.styleClass!=null){
			out.write(" class='" + this.styleClass + "'");
		}
		if(this.width!=null){
			out.write(" width=");
			if(COL_SUM.equals(width)){
				out.write(getTotalWidth());
			}else{
				out.write(width);
			}			
		}
		out.write(">");
		if(this.caption!=null&&this.caption.trim().length()>0){
			out.write("<caption>");
			out.write(caption);
			out.write("</caption>");
		}
		head.toHTML(out);
		writeBody(out);
		out.write("</table>");
	}

	private String getTotalWidth(){
		int result=0;
		for(Iterator itr=this.getBottomColumns().iterator();itr.hasNext();){
			Column column=(Column) itr.next();
			String width=column.getWidth();
			width=width==null ? "100px" : width;
			if(width.indexOf("px")<0)throw new RuntimeException("报表宽度设置为" + COL_SUM + "时,列宽度的单位必须为px.而列'" + column.getTitle() + "'的设置为:" + column.getWidth());
			width=width.replaceAll("px", "");
			result += width==null ? 100 : Integer.parseInt(width);
		}
		return result + "px";
	}
	
	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public String getBodyStyleClass() {
		return bodyStyleClass;
	}

	public void setBodyStyleClass(String bodyStyleClass) {
		this.bodyStyleClass = bodyStyleClass;
	}

	public String getFootStyleClass() {
		return footStyleClass;
	}

	public void setFootStyleClass(String footStyleClass) {
		this.footStyleClass = footStyleClass;
	}

	public String getHeadStyleClass() {
		return headStyleClass;
	}

	public void setHeadStyleClass(String headStyleClass) {
		this.headStyleClass = headStyleClass;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStyleClass() {
		return styleClass;
	}
	
	public DataTable getDataTable() {
		return dataTable;
	}
	
	public Column getColumn(Object key){
		return (Column) keyColumns.get(key);
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	private void writeBody(Writer out) throws IOException {
		out.write("<tbody");
		if(this.bodyStyleClass!=null){
			out.write(" class='" + bodyStyleClass + "'");
		}
		out.write(">");
		
		getGroupCell().toHTML(out, false);
		out.write("</tbody>");
		
	}
	
	
	GroupCell getGroupCell(){		
		GroupCell result=new GroupCell();
		result.rowKeys=getDataTable().getRowKeys();
		result.group=this;
		result.setBodyTRStyleClass(bodyTRStyleClass);
		
		//sort by rowkeys
		Collection collect = getDataTable().getRowKeys();
		if(this.isDefaultRowKeySort()){
			List listData = new ArrayList(collect);
			Collections.sort(listData);
			collect = listData;
		}
		
		for(Iterator itr=collect.iterator();itr.hasNext();){
			Object rowKey=itr.next();
			Group group=this.childGroup;
			GroupCell gc=result;
			while(group!=null){
				Object groupKey=dataTable.getData(rowKey, group.getGroupColumn().getKey());
				if(gc.children==null)gc.children=new HashMap();
				GroupCell childGc=(GroupCell) gc.children.get(groupKey);
				if(childGc==null){
					childGc=new GroupCell();
					childGc.setBodyTRStyleClass(bodyTRStyleClass);
					childGc.parent=gc;
					childGc.group=group;
					childGc.groupKey=groupKey;
					gc.children.put(groupKey,childGc);
				}
				if(childGc.group.childGroup==null){
					if(childGc.rowKeys==null)childGc.rowKeys=new ArrayList();
					childGc.rowKeys.add(rowKey);
				}
				gc=childGc;
				group=group.childGroup;
			}			
		}
		return result;
	}
	
	
	List bottomColumns;
	List getBottomColumns(){
		return bottomColumns==null ? bottomColumns=getBottomColumns(head.columns,new ArrayList()) : bottomColumns;
	}
	
	List getBottomColumns(List columns,List result){
		for(Iterator itr=columns.iterator();itr.hasNext();){
			Column column=(Column)itr.next();
			if(column.columns.size()==0){
				result.add(column);
			}else{
				getBottomColumns(column.columns,result);
			}
		}
		return result;
	}
	

	class Head {
		int trCount=0;
		int tdCount=0;
		List columns=new ArrayList();
		
		public void toHTML(Writer out) throws IOException{
			out.write("<thead");
			if(headStyleClass!=null){
				out.write(" class='" + headStyleClass + "'");
			}
			out.write(">");
			
			if(title!=null){
				out.write("<tr class='"+headTRStyleClass+"'><td class='title' colspan=" + tdCount +"><h2>");
				out.write(title);
				out.write("</h2></td></tr>");
			}
			for(int tr=0;tr<trCount;tr++){
				out.write("<tr class='"+headTRStyleClass+"'>");
				for(int td=0;td<tdCount;td++){
					Column column=getColumn(tr,td);
					if(column!=null)column.toHTML(out);
				}
				out.write("</tr>");
			}
			out.write("</thead>");
		}
		
		public Column getColumn(int tr,int td){
			return getColumn(columns,tr,td);
		}
		
		private Column getColumn(List columns,int tr,int td){
			if(columns.size()==0)return null;
			int count=0;
			for(Iterator itr=columns.iterator();itr.hasNext();){
				Column column=(Column)itr.next();				
				if(count + column.colSpan > td){
					if(td == count && tr==column.getRowCount())return column;
					return getColumn(column.columns,tr,td-count);
				}else{
					count += column.colSpan;
				}
			}
			return null;
		}
		
		void addColumn(Column column){
			this.tdCount++;
			this.trCount=trCount==0 ? 1 : trCount;
			this.columns.add(column);
		}			
	}

	Report getReport() {
		return this;
	}

	public String getHeadTRStyleClass() {
		return headTRStyleClass;
	}

	public void setHeadTRStyleClass(String headTRStyleClass) {
		this.headTRStyleClass = headTRStyleClass;
	}

	public String getBodyTRStyleClass() {
		return bodyTRStyleClass;
	}

	public void setBodyTRStyleClass(String bodyTRStyleClass) {
		this.bodyTRStyleClass = bodyTRStyleClass;
	}

	public boolean isDefaultRowKeySort() {
		return defaultRowKeySort;
	}

	public void setDefaultRowKeySort(boolean defaultRowKeySort) {
		this.defaultRowKeySort = defaultRowKeySort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}
