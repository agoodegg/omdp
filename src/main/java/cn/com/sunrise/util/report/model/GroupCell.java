package cn.com.sunrise.util.report.model;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class GroupCell {
	
	Object groupKey;
	GroupCell parent;
	Map children;
	Groupable group;
	Collection rowKeys;
	String bodyTRStyleClass;
	
	int rowSpan=0;	
	
	public void toHTML(Writer out,boolean writeTr) throws IOException{
		if(writeTr)out.write("<tr class='"+bodyTRStyleClass+"'>");
		writeGroupCell(out);
		writeChildGroups(out);
		writeFoots(out);
		
	}
	
	
	private void writeFoots(Writer out) throws IOException {
		for(Iterator itr=group.foots.iterator();itr.hasNext();){
			Foot foot=(Foot) itr.next();
			foot.toHTML(out,this);
		}		
	}


	private void writeGroupCell(Writer out) throws IOException {
		if(parent!=null){
			wirteCell(out,group.getGroupColumn(),groupKey,getRowSpan());		
		}
	}

	private void writeChildGroups(Writer out) throws IOException{
		boolean writeTr=parent==null;
		if(children!=null){
			for(Iterator itr=sortedGroups();itr.hasNext();){				
				GroupCell gc=(GroupCell)children.get(itr.next());
				gc.toHTML(out, writeTr);
				writeTr=true;
			}
		}else{
			writeRows(out);
		}
	}
	
	private Iterator sortedGroups(){
		return group.childGroup.getGroupSorter().sortGroupKeys(group.childGroup,children.keySet()).iterator();
		
	}


	private void writeRows(Writer out) throws IOException {
		boolean writeTr= parent==null;
		for(Iterator itr=sortedRowKeys();itr.hasNext();){
			writeRow(out,itr.next(),writeTr);
			writeTr=true;
		}		
	}
	
	private Iterator sortedRowKeys(){
		return group.getRowSorter().sortRowKeys(getDataTable(),getRowKeys()).iterator();
	}
	
	
	private void writeRow(Writer out,Object rowKey,boolean writeTr) throws IOException{
		if(writeTr)out.write("<tr class='"+bodyTRStyleClass+"'>");
		for(Iterator itr=this.group.unGroupedColumns();itr.hasNext();){
			Column column=(Column)itr.next();
			wirteCell(out,column,getDataTable().getData(rowKey, column.getKey()),1);			
		}
		out.write("</tr>");
	}
	
	
	DataTable getDataTable(){
		return group.getReport().getDataTable();
	}
	
	void wirteCell(Writer out, Column column,Object data,int rowSpan) throws IOException {
		out.write("<td");
		if(column.getRowAlign()!=null){
			out.write(" align=");
			out.write(column.getRowAlign());
		}
		String style=column.getLabelProvider().getStyleClass(data);
		if(style!=null){
			out.write(" class='" + style + "'");
		}
		if(rowSpan>1){
			out.write(" rowspan=" + rowSpan);
		}
		out.write(">");
		
		out.write(getLabel(column, data));
		out.write("</td>");
	}
	
	private String getLabel(Column column, Object data) {
		String label=column.getLabelProvider().getLabel(data);
		if(label==null || "".equals(label.trim()))label="&nbsp";
		return label;
	}
	

	public int getRowSpan(){
		if(rowSpan==0){			
			rowSpan=calculateRowSpan();
		}
		return rowSpan;
	}

	private int calculateRowSpan() {
		int result=0;
		if(children==null){
			result=rowKeys.size();
		}else{
			for(Iterator itr=children.values().iterator();itr.hasNext();){
				GroupCell gc=(GroupCell) itr.next();
				result += gc.getRowSpan();
			}
		}
		result += group.foots.size();
		return result;
	}
	

	Collection getRowKeys(){
		if(rowKeys==null){
			rowKeys=new LinkedList();
			for(Iterator itr=children.values().iterator();itr.hasNext();){
				GroupCell gc=(GroupCell) itr.next();
				rowKeys.addAll(gc.getRowKeys());
			}
		}
		return rowKeys;
	}


	public String getBodyTRStyleClass() {
		return bodyTRStyleClass;
	}


	public void setBodyTRStyleClass(String bodyTRStyleClass) {
		this.bodyTRStyleClass = bodyTRStyleClass;
	}
	
	
}
