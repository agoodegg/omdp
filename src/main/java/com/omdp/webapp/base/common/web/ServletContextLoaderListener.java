package com.omdp.webapp.base.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.security.SpringSecurityManager;




/**
 * 
 * <p><b>classname:</b> ServletContextLoaderListener
 * <p><b>date:</b>  Mar 8, 2011 10:59:42 AM
 * <p><b>lastUpdate:</b>  Mar 8, 2011 10:59:42 AM
 * <p><b>version:</b>  1.0
 * @author zhouxiaohui
 */
public class ServletContextLoaderListener implements ServletContextListener {

	public static final String BUSI_TYPE = "0";
	public static final String SYS_TYPE = "1";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		SpringSecurityManager securityManager = this.getSecurityManager(servletContext);

		Map<String, List<String>> urlAuthoritiesMList = securityManager.loadUrlAuthorities();
		Map<String, List<String>> tagAuthoritiesMList = securityManager.loadTagAuthorities();
		
		Map<String,String> urlAuthorities = securityManager.buildMapCommaString(urlAuthoritiesMList);
		Map<String,String> tagAuthorities = securityManager.buildMapCommaString(tagAuthoritiesMList);
		
		servletContext.setAttribute("urlAuthoritiesMList", urlAuthoritiesMList);
		servletContext.setAttribute("tagAuthoritiesMList", tagAuthoritiesMList);
		servletContext.setAttribute("urlAuthorities", urlAuthorities);
		servletContext.setAttribute("tagAuthorities", tagAuthorities);
		
		
		List<TSysParam> paramsList = securityManager.loadSysParams();
		
		//缓存静态参数表
		Map<String,TreeMap<String,String>> sysDict = new HashMap<String,TreeMap<String,String>>();
		
		//缓存业务参数表
		Map<String,TreeMap<String,String>> busiDict = new HashMap<String,TreeMap<String,String>>();
		
		this.buildDictMap(paramsList, sysDict, busiDict);
		
		
		servletContext.setAttribute("sysDictMap", sysDict);
		servletContext.setAttribute("busiDictMap", busiDict);
	}
	
	
	private void buildDictMap(List<TSysParam> paramsList, Map<String, TreeMap<String, String>> sysDict, Map<String, TreeMap<String, String>> busiDict) {
		
		if(paramsList==null&&paramsList.size()==0){
			return;
		}
		
		for(TSysParam param:paramsList){
			if(BUSI_TYPE.equals(param.getBaseType())){
				TreeMap<String,String> map = busiDict.get(param.getFtypeCode()+param.getBusiCd());
				if(map==null){
					map = new TreeMap<String,String>();
					map.put(param.getParamCode(), param.getParamName());
					busiDict.put(param.getFtypeCode()+param.getBusiCd(), map);
				}
				else{
					map.put(param.getParamCode(), param.getParamName());
				}
			}
			else{
				TreeMap<String,String> map = sysDict.get(param.getFtypeCode());
				
				if(map==null){
					map = new TreeMap<String,String>();
					map.put(param.getParamCode(), param.getParamName());
					sysDict.put(param.getFtypeCode(), map);
				}
				else{
					map.put(param.getParamCode(), param.getParamName());
				}
			}
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		servletContextEvent.getServletContext().removeAttribute("urlAuthoritiesMList");
		servletContextEvent.getServletContext().removeAttribute("tagAuthoritiesMList");
		servletContextEvent.getServletContext().removeAttribute("urlAuthorities");
		servletContextEvent.getServletContext().removeAttribute("tagAuthorities");
		
		
		
		servletContextEvent.getServletContext().removeAttribute("sysDictMap");
		servletContextEvent.getServletContext().removeAttribute("busiDictMap");
	}

	/**
	 * Get SecurityManager from ApplicationContext
	 * 
	 * @param servletContext
	 * @return
	 */
	protected SpringSecurityManager getSecurityManager(ServletContext servletContext) {
		return (SpringSecurityManager) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("securityManager");
	}
}
