package com.omdp.webapp.base.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

	public SessionManager(HttpServletRequest req) {
		request = req;
	}

	private HttpSession getRequestSession() {
		return request.getSession();
	}

	public void addValue(String key, Object value) {
		HttpSession session = getRequestSession();
		session.setAttribute(key, value);
	}

	public void resetSession() {
		HttpSession session = getRequestSession();
		session.invalidate();
	}

	public void removeValue(String key) {
		HttpSession session = getRequestSession();
		session.removeAttribute(key);
	}

	public Object getValue(String key) {
		HttpSession session = getRequestSession();
		return session.getAttribute(key);
	}


	HttpServletRequest request;
}
