package com.omdp.webapp.security;

import java.util.List;
import java.util.Map;

import com.omdp.webapp.model.TSysParam;


/**
 * 
 * <p><b>classname:</b> SecurityManager
 * <p><b>date:</b>  Mar 8, 2011 11:01:20 AM
 * <p><b>lastUpdate:</b>  Mar 8, 2011 11:01:20 AM
 * <p><b>version:</b>  1.0
 * @author zhouxiaohui
 */
public interface SpringSecurityManager {


	/**
	 * 找出数据库里所有的URL资源
	 * @return
	 */
	public Map<String, List<String>> loadUrlAuthorities();
	
	/**
	 * 找出数据库里所有的TAG资源
	 * @return
	 */
	public Map<String, List<String>> loadTagAuthorities();
	
	public Map<String, String> buildMapCommaString(Map<String, List<String>> authoritiesMList);
	
	public List<TSysParam> loadSysParams();

}
