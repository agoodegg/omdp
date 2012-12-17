package com.omdp.webapp.base.taglib;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;


public class OrderDetailPrice implements Tag{
	
	public PageContext pageContext;
	public Tag parent;

	private String type;
	private String ordernum;
	private String suffix;
	
	private String result="";
	

	public String getType() {
		return type;
	}


	
	public void setType(String type) {
		this.type = type;
	}


	
	public String getOrdernum() {
		return ordernum;
	}


	
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}


	
	public String getSuffix() {
		return suffix;
	}


	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public int doStartTag() throws JspException {
		
		Map<String,Double> dataMap = (Map<String,Double>)pageContext.getRequest().getAttribute("totalMap");
		
		Double price = dataMap.get(type);
		if(price==null){
			price=0.0;
		}
		
		JspWriter writer = pageContext.getOut();
		try{
			if(price!=0.0){
				writer.write(String.valueOf(price));
				writer.write(suffix);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return EVAL_BODY_INCLUDE;
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