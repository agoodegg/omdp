package com.omdp.webapp.base.taglib;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;


public class DictTag implements Tag{
	
	public PageContext pageContext;
	public Tag parent;

	private String value;
	private String type;
	private String busiType;
	
	private String name="";
	
	
	public String getValue() {
		return value;
	}

	
	public void setValue(String value) {
		this.value = value;
	}

	
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	public String getBusiType() {
		return busiType;
	}

	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}


	public int doStartTag() throws JspException {
		Map<String, Map<String, String>> sysDict = (Map<String, Map<String, String>>) (pageContext.getServletContext().getAttribute("sysDictMap"));
		Map<String, Map<String, String>> busiDict = (Map<String, Map<String, String>>) (pageContext.getServletContext().getAttribute("busiDictMap"));

		if(busiType!=null&&busiType.trim().length()>0){
			Map<String,String> map = (Map<String,String>)(busiDict.get(type+busiType));
			if(map!=null){
				name = map.get(value);
			}
		}
		else{
			Map<String,String> map = (Map<String,String>)(sysDict.get(type));
			if(map!=null){
				name = map.get(value);
			}
		}
		
		JspWriter writer = pageContext.getOut();
		try{
			writer.write(name);
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
