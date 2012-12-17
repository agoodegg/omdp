package com.omdp.webapp.sys.dept.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TDeptInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.dept.dao.DeptDAO;


@Repository
public class DeptDAOImpl extends AbstractJpaDAO<TDeptInfo> implements DeptDAO<TDeptInfo> {

	public List<TDeptInfo> queryDeptInfoList(String querySql, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(querySql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public TDeptInfo load(Integer id, TUser u) {
		TDeptInfo po = this.getJpaTemplate().getEntityManager().find(TDeptInfo.class, id);
		return po;
	}

	public void executeUpdate(String updateSql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(updateSql);
		q.executeUpdate();
	}

	
	
}
