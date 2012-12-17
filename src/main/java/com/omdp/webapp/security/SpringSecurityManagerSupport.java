package com.omdp.webapp.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.omdp.webapp.model.TDeptRole;
import com.omdp.webapp.model.TRoleRes;
import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserRole;


@Repository("securityManager")
public class SpringSecurityManagerSupport extends JpaDaoSupport implements SpringSecurityManager, UserDetailsService {

	@PersistenceContext
	public void setEm(EntityManager em) {
		super.setEntityManager(em);
	}
	
	/* 
     * 与loadUrlAuthorities()功能一样,但查取的是TAG资源信息,可依照此方法进行资源种类的扩展
     * @see com.omdp.security.SpringSecurityManager#loadTagAuthorities()
     */
	public Map<String, List<String>> loadTagAuthorities() {
		Map<String, List<String>> tagAuthorities = new HashMap<String, List<String>>();  
		List<TSysRes> tagResources = this.getJpaTemplate().find(" from TSysRes res where res.resIsInvalid='0' and res.resType='TAG'  ");
        for(TSysRes resource : tagResources) {  
            if (StringUtils.isNotBlank(resource.getResTag())) {
            	//找资源对应的roleId
            	List<TRoleRes> roleReses = getJpaTemplate().find("FROM TRoleRes roleRes where roleRes.id.resId='"+resource.getResId()+"' ");  
            	List<String> authoritiesList = new ArrayList<String>();
        		for (TRoleRes rur : roleReses) {
        			authoritiesList.add(rur.getId().getRoleId());
        		}
            	tagAuthorities.put(resource.getResTag(), authoritiesList);
            }
        }
        //System.out.println("++++loalTagAuthorities: " + tagAuthorities);
        return tagAuthorities;
	}

	/* 
     * 此方法实现SpringSecurityManager的接口。
     * 在web.xml中已配置了一个ServletContextLoaderListener，会在容器启动的时候加载URL资源和对应的角色ID到ServletContext中。
     * 当用户提交URL请求的时候，会检查请求URL是否需要进行安全检查。
     * 如果需要，则拿出该资源对应的可访问角色ID与ServletContext中保存的用户权限信息进行对比。
     * 当系统管理中资源角色权限发生变化时，该资源信息会被刷新，详情请查看RoleManagerAction.java
     * @see com.omdp.security.SpringSecurityManager#loadUrlAuthorities() 
     */
	public Map<String, List<String>> loadUrlAuthorities() {
		Map<String,  List<String>> urlAuthorities = new HashMap<String,  List<String>>();
		List<TSysRes> urlResources = this.getJpaTemplate().find(" from TSysRes res where res.resIsInvalid='0' and res.resType='URL' ");
        for(TSysRes resource : urlResources) {  
            if (StringUtils.isNotBlank(resource.getResUrl())) {
            	//找资源对应的roleId
            	List<TRoleRes> roleReses = getJpaTemplate().find("FROM TRoleRes roleRes where roleRes.id.resId='"+resource.getResId()+"' "); 
            	List<String> authoritiesList = new ArrayList<String>();
        		for (TRoleRes rur : roleReses) {
        			authoritiesList.add(rur.getId().getRoleId());
        		}
        		
            	urlAuthorities.put(resource.getResUrl(), authoritiesList);  
            }
        }
        //System.out.println("++++loadUrlAuthorities: " + urlAuthorities);
        return urlAuthorities;
	}
	
	public Map<String, String> buildMapCommaString(Map<String, List<String>> authoritiesMList) {
		Map<String,String> resultMap = new HashMap<String,String>();
		for (Iterator<Map.Entry<String, List<String>>> iter = authoritiesMList.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, List<String>> entry = iter.next();
			
			List<String> authList = entry.getValue();
			StringBuffer buf = new StringBuffer();
			for(String s:authList){
				buf.append(s).append(",");
			}
			
			if(buf.length()>0){
				resultMap.put(entry.getKey(), buf.substring(0,buf.length()-1));
			}
			else{
				resultMap.put(entry.getKey(), "");
			}
		}
		
		return resultMap;
	}

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		List<TUser> users = this.getJpaTemplate().find("FROM TUser user WHERE user.userAccount = ? AND user.isInvalid = '0' ", userName);  
		TUser user = null;
        if(users.isEmpty()) {  
            throw new UsernameNotFoundException("User " + userName + " has no GrantedAuthority");  
        } else {
        	user = users.get(0);
        	//在这里查找用户对应的roleId
        	if (user.getUserInRoleIds().size() == 0) {
        		List<TUserRole> userRoles = getJpaTemplate().find("FROM TUserRole userInRole WHERE userInRole.id.userId = ?", user.getIdNo());
            	user.setUserInRoleIds(extractUserInRoleIds(userRoles));
        	}
        	//找用户对应的组织架构对应的roleId
        	if (user.getUserInOrgRoleIds().size() == 0) {
        		List<TDeptRole> deptInRoles = getJpaTemplate().find("select oir FROM TDeptRole oir,TUser u WHERE u.deptCd=oir.id.deptCd and u.idNo = ? ", user.getIdNo());
        		user.setUserInOrgRoleIds(extractUserInOrgRoleIds(deptInRoles));
        	}
        }
        //System.out.println("++++loadUserByUsername Success. userId: " + user.getIdNo() + ", authorities: " + user.getUserInRoleIds() + user.getUserInOrgRoleIds());
        return user;
	}

	
	/**
     * @param userInRole
     * @return
     */
    private List<String> extractUserInRoleIds(List<TUserRole> userRoles) {
    	List<String> userInRoleIds = new ArrayList<String>();
    	if (userRoles != null) {
    		for (TUserRole uir : userRoles) {
    			userInRoleIds.add(uir.getId().getRoleId());
    		}
    	}
    	return userInRoleIds;
    }
    
    /**
     * @param userInOrg
     * @return
     */
    private List<String> extractUserInOrgRoleIds(List<TDeptRole> deptInRoles) {
    	List<String> userInRoleIds = new ArrayList<String>();
    	if (deptInRoles != null) {
    		for (TDeptRole oir : deptInRoles) {
    			userInRoleIds.add(oir.getId().getRoleId());
    		}
    	}
    	return userInRoleIds;
    }

    
    
    
	public List<TSysParam> loadSysParams() {
		List<TSysParam> params = this.getJpaTemplate().find("FROM TSysParam s order by s.id,s.priorLvl ");
		
		return params;
	}
    
}
