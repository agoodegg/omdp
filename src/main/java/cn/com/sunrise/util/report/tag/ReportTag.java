package cn.com.sunrise.util.report.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.sunrise.util.report.model.Report;

public class ReportTag extends TagSupport {

	private static final long serialVersionUID = 5194807116657153701L;

	
	private String model;
	private String width="auto";
	private String alt="";
	

	public int doEndTag() throws JspException {

		Object obj = pageContext.getRequest().getAttribute(model);
		if(obj instanceof Report[]){
			/*
			Report report = ((Report[]) obj)[0];
			Report report1 = ((Report[]) obj)[1];
			if(report==null){
				Writer out=pageContext.getOut();
				try {
					out.write("<table style='border:1px solid black' width=100% height=100%><tr><td align=center valign=middle>"+alt+"</td></tr></table>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return EVAL_PAGE;
			}else{
				try {
					pageContext.getOut().write("<table><tr><td id='rp_jike' align=center valign=middle>") ;
					report.setWidth(width);
					report.toHTML(pageContext.getOut());
					pageContext.getOut().write("</td><td id='rp_wangfu' align=center valign=middle>") ;
					report1.setWidth(width);
					report1.toHTML(pageContext.getOut());
					pageContext.getOut().write("</td></tr></table>") ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			*/

			try {
				int i=0;
				Report[] reports = ((Report[]) obj);
				pageContext.getOut().write("<table><tr>") ;
				for(Report r : reports){
					i++;
					if(r==null){
						Writer out=pageContext.getOut();
						try {
							out.write("<td><table style='border:1px solid black' width=100% height=100%><tr><td align=center valign=middle>"+alt+"</td></tr></table></td>");
						} catch (IOException e) {
							e.printStackTrace();
						}
						return EVAL_PAGE;
					}else{
						pageContext.getOut().write("<td id='rp_"+i+"' align=center valign=middle>") ;
						r.setWidth(width);
						r.toHTML(pageContext.getOut());
						pageContext.getOut().write("</td>") ;
					}
				}
				pageContext.getOut().write("</tr></table>") ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			Report report=(Report) pageContext.getRequest().getAttribute(model);		
			if(report==null){
				Writer out=pageContext.getOut();
				try {
					out.write("<table style='border:1px solid black' width=100% height=100%><tr><td align=center valign=middle>"+alt+"</td></tr></table>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return EVAL_PAGE;
			}else{
				report.setWidth(width);
				try {
					report.toHTML(pageContext.getOut());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return EVAL_PAGE;
	}
	
	

	public void setModel(String model) {
		this.model = model;
	}
	
	public void setWidth(String width){
		this.width=width;
	}


	public void setAlt(String alt) {
		this.alt = alt;
	}
	

}
