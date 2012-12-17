package com.omdp.webapp.sys.log.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserLogin;
import com.omdp.webapp.sys.log.dao.UserLoginLogDAO;


@Repository
public class UserLoginLogDAOImpl extends AbstractJpaDAO<TUserLogin> implements UserLoginLogDAO<TUserLogin> {

	public void saveUserLogin(TUserLogin userLogin) {
		this.getJpaTemplate().getEntityManager().persist(userLogin);
	}

	public List<TUserLogin> queryUserLogin(String sql, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public void saveSysLog(TUser u, String oprMsg, String logType) {
		
		
		try{
			TUserLogin log = new TUserLogin();
			log.setUserId(u.getIdNo());
			log.setUserName(u.getUserName());
			log.setLogTime(new Date());
			log.setLogType(logType);
			log.setAddInfo(oprMsg);
			log.setIpAddress(u.getIpAddress());
			
			this.getJpaTemplate().getEntityManager().persist(log);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
