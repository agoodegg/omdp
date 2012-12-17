package com.omdp.webapp.base.taglib;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;


public class SelectTag implements Tag{

	public PageContext pageContext;
	public Tag parent;

	private String value;
	private String type;
	private String busiType;
	
	private String name;
	
	private String id;
	private String style;
	private String cssClass;
	
	private String headText;
	private String headValue;
	
	private String dataType;
	private String min;
	private String max;
	private String msg;
	private String onchange;
	
	
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
	
	
	public String getId() {
		return id;
	}


	
	public void setId(String id) {
		this.id = id;
	}


	
	public String getStyle() {
		return style;
	}


	
	public void setStyle(String style) {
		this.style = style;
	}


	
	public String getCssClass() {
		return cssClass;
	}


	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}


	public PageContext getPageContext() {
		return pageContext;
	}

	public String getHeadText() {
		return headText;
	}

	public void setHeadText(String headText) {
		this.headText = headText;
	}

	public String getHeadValue() {
		return headValue;
	}

	public void setHeadValue(String headValue) {
		this.headValue = headValue;
	}


	public int doStartTag() throws JspException {
		Map<String, Map<String, String>> sysDict = (Map<String, Map<String, String>>) (pageContext.getServletContext().getAttribute("sysDictMap"));
		Map<String, Map<String, String>> busiDict = (Map<String, Map<String, String>>) (pageContext.getServletContext().getAttribute("busiDictMap"));

		StringBuffer buf = new StringBuffer();
		
		buf.append("<select ");
		
		if(id!=null&&id.trim().length()>0){
			buf.append(" id=\""+id+"\" ");
		}
		if(name!=null&&name.trim().length()>0){
			buf.append(" name=\""+name+"\" ");
		}
		if(style!=null&&style.trim().length()>0){
			buf.append(" style=\""+style+"\" ");
		}
		if(cssClass!=null&&cssClass.trim().length()>0){
			buf.append(" class=\""+cssClass+"\" ");
		}
		if(dataType!=null&&dataType.trim().length()>0){
			buf.append(" dataType=\""+dataType+"\" ");
		}
		if(min!=null&&min.trim().length()>0){
			buf.append(" min=\""+min+"\" ");
		}
		if(max!=null&&max.trim().length()>0){
			buf.append(" max=\""+max+"\" ");
		}
		if(msg!=null&&msg.trim().length()>0){
			buf.append(" msg=\""+msg+"\" ");
		}
		if(onchange!=null&&onchange.trim().length()>0){
			buf.append(" onchange=\""+onchange+"\" ");
		}
		buf.append(">");
		
		if(headText!=null&&headText.trim().length()>0){
			if(headValue==null){
				headValue="";
			}
			
			buf.append("<option value=\""+headValue+"\">"+headText+"</option>");
		}
		
		if(busiType!=null&&busiType.trim().length()>0){
			TreeMap<String,String> map = (TreeMap<String,String>)(busiDict.get(type+busiType));
			if(map!=null){
				Iterator<Entry<String, String>> itr = map.entrySet().iterator();
				while(itr.hasNext()){
					Entry<String, String> entry = itr.next();
					buf.append("<option value=\""+entry.getKey()+"\" "+((entry.getKey().equals(value))?"selected":"")+">"+entry.getValue()+"</option>");
				}
			}
		}
		else{
			TreeMap<String,String> map = (TreeMap<String,String>)(sysDict.get(type));
			if(map!=null){
				Iterator<Entry<String, String>> itr = map.entrySet().iterator();
				while(itr.hasNext()){
					Entry<String, String> entry = itr.next();
					buf.append("<option value=\""+entry.getKey()+"\" "+((entry.getKey().equals(value))?"selected":"")+">"+entry.getValue()+"</option>");
				}
			}
		}
		
		buf.append("</select>");
		JspWriter writer = pageContext.getOut();
		try{
			writer.write(buf.toString());
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


	
	public String getDataType() {
		return dataType;
	}


	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	
	public String getMin() {
		return min;
	}


	
	public void setMin(String min) {
		this.min = min;
	}


	
	public String getMax() {
		return max;
	}


	
	public void setMax(String max) {
		this.max = max;
	}


	
	public String getMsg() {
		return msg;
	}


	
	public void setMsg(String msg) {
		this.msg = msg;
	}


	
	public String getOnchange() {
		return onchange;
	}


	
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	
	
	
}
