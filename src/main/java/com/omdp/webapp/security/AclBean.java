package com.omdp.webapp.security;

import org.springframework.security.acls.Permission;


public interface AclBean {


	/**
	 * 返回当前查询用户对此ACL对像的权限值。<br>
	 * 业务bean可继承AbstractAclBean.java来实现此接口，亦可自行在业务bean内实现此接口。<br>
	 * 在前端页面上便可直接使用mask变量来决定是否有权限显示当前功能按钮。<br>
	 * （若是Hibernate的domin，以应在这三个方法加上@Transient的标签，让Hibernate不会持久化这些方法）
	 * @return int
	 */
	public int getMask();
	
	/**
	 * @return
	 */
	public String getDomainId();
	
	/**
	 * @return the pernission
	 */
	public Permission getPermission();

	
	/**
	 * @param pernission the pernission to set
	 */
	public void setPermission(Permission permission);
	
}
