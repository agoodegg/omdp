package cn.com.sunrise.util.report.model;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 表脚
 * @author hegang
 *
 */
public class Foot{
	
	Groupable groupable;
	String cls;
	HashMap footColumns=new HashMap();
	
	public Foot(Groupable groupable){
		this.groupable=groupable;
		this.groupable.addFoot(this);
	}
		
	public void toHTML(Writer out,GroupCell gc) throws IOException{
		out.write("<tr "+((cls!=null&&cls.trim().length()>0)?("class=\""+cls+"\""):"")+">");
		for(Iterator itr=groupable.unGroupedColumns();itr.hasNext();){
			FootColumn fc=(FootColumn) footColumns.get(itr.next());
			if(fc==null){
				out.write("<td>&nbsp</td>");
			}else{
				fc.toHTML(out,gc);
				for(int i=1;i<fc.getColSpan() && itr.hasNext();i++){
					itr.next();
				}
			}
		}
		out.write("</tr>");
	}

	public Report getReport() {
		return groupable.getReport();
	}

	
	public String getCls() {
		return cls;
	}

	
	public void setCls(String cls) {
		this.cls = cls;
	}
	
}
