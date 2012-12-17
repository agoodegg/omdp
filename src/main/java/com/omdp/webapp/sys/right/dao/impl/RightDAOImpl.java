package com.omdp.webapp.sys.right.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TRoleInfo;
import com.omdp.webapp.model.TRoleRes;
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.right.dao.RightDAO;


@Repository
public class RightDAOImpl extends AbstractJpaDAO<TRoleInfo> implements RightDAO<TRoleInfo> {

	public List<TRoleInfo> queryRoleInfoList(String querySql, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(querySql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public void executeUpdate(String updateSql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(updateSql);
		q.executeUpdate();
	}

	public TRoleInfo load(Integer id, TUser u) {
		TRoleInfo po = this.getJpaTemplate().getEntityManager().find(TRoleInfo.class, id);
		return po;
	}

	public List<TSysRes> getRoleRes(TRoleInfo po) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" select t from TSysRes t,TRoleRes s where t.resId = s.id.resId and s.id.roleId =:roleId order by t.resId ");
		q.setParameter("roleId", po.getRoleId());
		return q.getResultList();
	}

	public void saveRoleRes(TRoleRes resObj) {
		this.getJpaTemplate().getEntityManager().persist(resObj);
	}

	
	
}
