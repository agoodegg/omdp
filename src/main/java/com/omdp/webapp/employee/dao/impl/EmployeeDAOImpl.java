package com.omdp.webapp.employee.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.employee.dao.EmployeeDAO;
import com.omdp.webapp.model.TRoleInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserRole;


@Repository
public class EmployeeDAOImpl extends AbstractJpaDAO<TUser> implements EmployeeDAO<TUser> {

	public List queryUserInfoList(String sql, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public void executeUpdate(String updateSql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(updateSql);
		q.executeUpdate();
	}

	public TUser load(Integer id, TUser u) {
		TUser po = this.getJpaTemplate().getEntityManager().find(TUser.class, id);
		return po;
	}

	public void saveUserRole(TUserRole userRoleObject) {
		this.getJpaTemplate().getEntityManager().persist(userRoleObject);
	}

	public List<TRoleInfo> loadUserRole(TUser po) { 
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" select s from TUserRole t,TRoleInfo s where t.id.roleId=s.roleId and t.id.userId=:userId order by t.id.roleId ");
		q.setParameter("userId", po.getIdNo());
		return q.getResultList();
	}

	public void clearUserRole(TUser po) {
		
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" delete from TUserRole t where t.id.userId=:userId ");
		q.setParameter("userId", po.getIdNo());
		
		q.executeUpdate();
	}

	public TUser loadUserByUserId(String userId, TUser u) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" select u from TUser u where u.idNo=:idno ");
		q.setParameter("idno", userId);
		
		List<TUser> data = q.getResultList();
		
		if(data==null||data.size()==0){
			return null;
		}
		else{
			return data.get(0);
		}
	}
	
	
	
}
