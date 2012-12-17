package com.omdp.webapp.base.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CodeFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 7111029456585335078L;

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String code = request.getParameter("j_code");
		
		HttpSession session = request.getSession(false);
		
		String vcode = null;
		if(session!=null){
			vcode = (String)session.getAttribute("session.authentication.code");
		}
		
		if(code!=null&&code.equals(vcode)){
			filterChain.doFilter(request, response);
		}
		else{
			response.sendRedirect(request.getContextPath()+"/login.jsp?error=true&type=invalidCode");
		}
	}
}
