package com.omdp.webapp.base.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;

import com.omdp.webapp.model.TUser;


public class UrlGuard extends BodyTagSupport {

	private static final long serialVersionUID = 5628047375675640415L;

	private String url;

	
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}

	
	
public int doStartTag() throws JspException {
		
		Map<String, List<String>> urlAuthoritiesMList = (Map<String, List<String>>)(pageContext.getServletContext().getAttribute("urlAuthoritiesMList"));
		
		for (Iterator<Map.Entry<String, List<String>>> iter = urlAuthoritiesMList.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, List<String>> entry = iter.next();
			String iurl = StringUtils.trimToEmpty(entry.getKey());
			
			if (iurl.equals(url)){
				List<String> tagAuthList = entry.getValue();
				if(tagAuthList.size()>0){
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					TUser u = (TUser)(auth.getPrincipal());
					GrantedAuthority[] authorities = u.getAuthorities();
					List<String> grantedAuthorites = new ArrayList<String>();
					if(authorities!=null){
						for(GrantedAuthority g:authorities){
							grantedAuthorites.add(g.getAuthority());
						}
						grantedAuthorites.retainAll(tagAuthList);
						if(grantedAuthorites.size()>0){
							return EVAL_BODY_BUFFERED;
						}
						else{
							//用户无角色权限
							return SKIP_BODY;
						}
					}
					else{
						//用户无角色权限
						return SKIP_BODY;
					}
				}
				else {
					//如果按钮或标签资源尚未分配 则默认不显示标签体
					return SKIP_BODY;
				}
			}
		}
		
		//资源未入资源库表  默认显示
		return EVAL_BODY_BUFFERED;
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
