package cn.com.sunrise.util.report.model;

import java.io.IOException;
import java.io.Writer;


/**
 * 表脚列
 * @author hegang
 *
 */
public abstract class FootColumn {

	Foot foot;
	Column positionColumn;
	int colSpan=1;
	LabelProvider labelProvider;
	String rowAlign;
	
	public FootColumn(Foot foot,Column positionColumn){
		this.foot=foot;
		this.positionColumn=positionColumn;
		this.foot.footColumns.put(positionColumn, this);
	}
	
	public void toHTML(Writer out,GroupCell gc) throws IOException {
		Object data=getData(gc);
		out.write("<td");
		if(doGetRowAlign()!=null){
			out.write(" align=");
			out.write(doGetRowAlign());
		}				
		String style=doGetLabelProvider().getStyleClass(data);
		if(style!=null){
			out.write(" class='" + style + "'");
		}	
		if(colSpan>1){
			out.write(" colSpan=" + colSpan);
		}
		out.write(">");
		out.write(getLabel(data));		
		out.write("</td>");
		
	}
	
	String doGetRowAlign(){
		return rowAlign==null ? positionColumn.getRowAlign() : rowAlign;
	}
	
	private String getLabel(Object data) {
		String label=doGetLabelProvider().getLabel(data);
		if(label==null || "".equals(label.trim()))label="&nbsp";
		return label;
	}

	protected abstract Object getData(GroupCell gc);

	LabelProvider doGetLabelProvider(){
		return labelProvider==null ? positionColumn.getLabelProvider() : labelProvider;
	}
	
	
	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public LabelProvider getLabelProvider() {
		return labelProvider;
	}

	public void setLabelProvider(LabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public Foot getFoot() {
		return foot;
	}

	public Column getPositionColumn() {
		return positionColumn;
	}


	
}
