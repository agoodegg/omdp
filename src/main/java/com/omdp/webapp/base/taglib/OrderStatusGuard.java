package com.omdp.webapp.base.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;


public class OrderStatusGuard  extends BodyTagSupport{
	
	public PageContext pageContext;
	public Tag parent;

	private String status;
	private String maskValue;
	
	
	public String getStatus() {
		return status;
	}


	
	public void setStatus(String status) {
		this.status = status;
	}


	
	public String getMaskValue() {
		return maskValue;
	}


	
	public void setMaskValue(String maskValue) {
		this.maskValue = maskValue;
	}


	public int doStartTag() throws JspException {
		
		Integer s = Integer.parseInt(status, 2);
		
		Integer mask = Integer.parseInt(maskValue, 10);
		
		Integer result = s&mask;

		if(result==mask){
			return SKIP_BODY;
		}
		else{
			return EVAL_BODY_BUFFERED;
		}
	}

	public int doAfterBody() throws JspException {
		BodyContent body = getBodyContent(); 
		String code = body.getString();
		JspWriter out = body.getEnclosingWriter();
		try {
			out.print(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY; 
	}


	
	
}
