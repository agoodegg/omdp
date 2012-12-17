package com.omdp.webapp.base.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.omdp.webapp.order.action.OrderQueryAction;


public class OrderStatusTrans implements Tag{
	
	public PageContext pageContext;
	public Tag parent;

	private String value;
	
	private String name="未打印";
	
	
	public String getValue() {
		return value;
	}

	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}


	public int doStartTag() throws JspException {
		
		name = OrderStatusTrans.getStatusLiteral(value);
		
		JspWriter writer = pageContext.getOut();
		try{
			writer.write(name);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return EVAL_BODY_INCLUDE;
	}


	public static String getStatusLiteral(String val) {
		
		String result = "";
		
		StringBuffer buf = new StringBuffer();
		
		Integer status = Integer.parseInt(val, 2);
		Integer result1 = status&OrderQueryAction.PRINT;
		Integer result2 = status&OrderQueryAction.DONE;
		Integer result3 = status&OrderQueryAction.TRASH;
		Integer result4 = status&OrderQueryAction.PAY;
		Integer result5 = status&OrderQueryAction.CHECKED;
		
		if(result1==OrderQueryAction.PRINT){
			buf.append("-已打印-");
		}
		else{
			buf.append("-未打印-");
		}
		if(result2==OrderQueryAction.DONE){
			buf.append("-<font color=\"#00ff00\">已完成</font>-");
		}
		else{
			buf.append("-未完成-");
		}
		if(result4==OrderQueryAction.PAY){
			buf.append("-已结算-");
		}
		else{
			buf.append("-<font color=\"#0000ff\">未结算</font>-");
		}
		if(result5==OrderQueryAction.CHECKED){
			buf.append("-已核销-");
		}

		else{
			buf.append("-<font color=\"#0000ff\">未核销</font>-");
		}
		
		if(buf.length()>0){
			result=buf.toString();
		}
		
		if(result5==OrderQueryAction.CHECKED){
			result="-<font color=\"#0000ff\">已核销</font>-";
		}
		
		if(result3==OrderQueryAction.TRASH){
			result="-<font color=\"#ff0000\">已作废</font>-";
		}
		
		return result;
	}


	public Tag getParent() {
		return parent;
	}


	public void release() {
		
	}


	public void setPageContext(PageContext pageContext) {
		 this.pageContext = pageContext;
	}


	public void setParent(Tag parent) {
		this.parent = parent;
	}


	public int doEndTag() throws JspException {
		 return EVAL_PAGE;
	}


	
	
}
