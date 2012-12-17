package com.omdp.webapp.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.RegexUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class SecureResourceFilterInvocationDefinitionSource  implements FilterInvocationDefinitionSource, InitializingBean {

	private UrlMatcher urlMatcher;

	private boolean useAntPath = true;

	private boolean lowercaseComparisons = true;
	
	private List<String> securityResourceList;


	/**
	 * @param securityResourceList the securityResourceList to set
	 */
	public void setSecurityResourceList(List<String> securityResourceList) {
		this.securityResourceList = securityResourceList;
	}

	/**
	 * @param useAntPath
	 *            the useAntPath to set
	 */
	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	/**
	 * @param lowercaseComparisons
	 */
	public void setLowercaseComparisons(boolean lowercaseComparisons) {
		this.lowercaseComparisons = lowercaseComparisons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBeanafterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {

		// default url matcher will be RegexUrlPathMatcher
		this.urlMatcher = new RegexUrlPathMatcher();

		if (useAntPath) { // change the implementation if required
			this.urlMatcher = new AntUrlPathMatcher();
		}

		// Only change from the defaults if the attribute has been set
		if ("true".equals(lowercaseComparisons)) {
			if (!this.useAntPath) {
				((RegexUrlPathMatcher) this.urlMatcher).setRequiresLowerCaseUrl(true);
			}
		} else if ("false".equals(lowercaseComparisons)) {
			if (this.useAntPath) {
				((AntUrlPathMatcher) this.urlMatcher).setRequiresLowerCaseUrl(false);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.intercept.ObjectDefinitionSourcegetAttributes
	 * (java.lang.Object)
	 */
	public ConfigAttributeDefinition getAttributes(Object filter) throws IllegalArgumentException {

		FilterInvocation filterInvocation = (FilterInvocation) filter;
		String requestURI = filterInvocation.getRequestUrl();
		// Strip anything after a question mark symbol, as per SEC-161. See also SEC-321
        int firstQuestionMarkIndex = requestURI.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
        	requestURI = requestURI.substring(0, firstQuestionMarkIndex);
        } 
		
        //根据请求主动刷新资源权限
        if ("/reloadAuthorities".equalsIgnoreCase(requestURI)) {
        	//System.out.println("### Start ReloadAuthorities");
			this.reloadAuthorities(filterInvocation);
			//System.out.println("### ReloadAuthorities Success");
			throw new AccessDeniedException("Access Is Denied.");//不做任何操作
		}
		Map<String, String> urlAuthorities = this.getUrlAuthorities(filterInvocation);
		
		String grantedAuthorities = null;
		for (Iterator<Map.Entry<String, String>> iter = urlAuthorities.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, String> entry = iter.next();
			String url = entry.getKey();
			
			if (urlMatcher.pathMatchesUrl(url, requestURI)) {
				if (StringUtils.isNotBlank(entry.getValue())) {
					grantedAuthorities = entry.getValue();
					//System.out.println("+++ Security Resource match。 *RequestURL: " + requestURI + ", *MatchesUrl:" + url + ", *RoleId:" + entry.getValue());
				} else {
					//如果资源尚未分配权限，则禁止访问该资源
					throw new AccessDeniedException("Access Is Denied.");
				}
				break;
			}
		}
		
		if (grantedAuthorities != null) {
			ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
			configAttrEditor.setAsText(grantedAuthorities);
			
			return (ConfigAttributeDefinition) configAttrEditor.getValue();
		}
		//System.out.println(" ~~~~ URL Check Fail. " + SecurityUserHolder.getCurrentUser().getAuthorities()[0]);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.security.intercept.
	 * ObjectDefinitionSourcegetConfigAttributeDefinitions()
	 */
	@SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.intercept.ObjectDefinitionSourcesupports
	 * (java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return true;
	}

	/**
	 * 
	 * @param filterInvocation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getUrlAuthorities(FilterInvocation filterInvocation) {
		ServletContext servletContext = filterInvocation.getHttpRequest().getSession().getServletContext();
		return (Map<String, String>) servletContext.getAttribute("urlAuthorities");
	}
	
	/**
	 * reloadAuthorities
	 */
	private void reloadAuthorities(FilterInvocation filterInvocation) {
		ServletContext servletContext = filterInvocation.getHttpRequest().getSession().getServletContext();
		SpringSecurityManager securityManager = (SpringSecurityManager) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("securityManager");
		Map<String, List<String>> urlAuthoritiesMList = securityManager.loadUrlAuthorities();
		Map<String, List<String>> tagAuthoritiesMList = securityManager.loadTagAuthorities();
		
		Map<String,String> urlAuthorities = securityManager.buildMapCommaString(urlAuthoritiesMList);
		Map<String,String> tagAuthorities = securityManager.buildMapCommaString(tagAuthoritiesMList);
		
		servletContext.setAttribute("urlAuthoritiesMList", urlAuthoritiesMList);
		servletContext.setAttribute("tagAuthoritiesMList", tagAuthoritiesMList);
		servletContext.setAttribute("urlAuthorities", urlAuthorities);
		servletContext.setAttribute("tagAuthorities", tagAuthorities);
	}

}
