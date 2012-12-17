package com.omdp.webapp.sys.user.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.user.dao.UserManagerDAO;


@Repository
public class UserManagerDAOImpl extends AbstractJpaDAO<TUser> implements UserManagerDAO<TUser> {

	public void updateUserAccount(String currentUserAccount, String newAccount) {
		
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" update TUser u set u.userAccount=:newUserAccount where u.userAccount=:userAccount ");
		q.setParameter("newUserAccount", newAccount);
		q.setParameter("userAccount", currentUserAccount);
		q.executeUpdate();
	}

	public void updateUserPwd(String currentUserAccount, String pwd) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" update TUser u set u.userPwd=:userPwd where u.userAccount=:userAccount ");
		q.setParameter("userPwd", pwd);
		q.setParameter("userAccount", currentUserAccount);
		q.executeUpdate();
	}

	
	
}
