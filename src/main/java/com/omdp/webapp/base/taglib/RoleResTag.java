package com.omdp.webapp.base.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;

import com.omdp.webapp.model.TUser;


public class RoleResTag extends BodyTagSupport {

	private String id;
	
	private String resources;

	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getResources() {
		return resources;
	}


	
	public void setResources(String resources) {
		this.resources = resources;
	}


	public int doStartTag() throws JspException {
		Set<String> resSet = (Set<String>)(pageContext.getRequest().getAttribute(resources));

		if(resSet.contains(StringUtils.trimToEmpty(id))){
			return EVAL_BODY_BUFFERED;
		}
		else{
			return SKIP_BODY;
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
