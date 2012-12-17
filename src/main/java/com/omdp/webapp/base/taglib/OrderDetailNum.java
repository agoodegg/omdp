package com.omdp.webapp.base.taglib;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;


public class OrderDetailNum implements Tag{
	
	public PageContext pageContext;
	public Tag parent;

	private String type;
	private String ordernum;
	private String suffix = "";
	
	private String prefix = "";
	
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
	
	
	public String getPrefix() {
		return prefix;
	}



	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}



	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public int doStartTag() throws JspException {
		
		Map<String,Integer> dataMap = (Map<String,Integer>)(pageContext.getRequest().getAttribute("numMap"));
		
		Integer num = dataMap.get(type);
		if(num==null){
			num=0;
		}
		
		JspWriter writer = pageContext.getOut();
		try{
			if(num!=0){
				writer.write(prefix);
				writer.write(String.valueOf(num));
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