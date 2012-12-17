package com.omdp.webapp.bulletin.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.bulletin.dao.BulletinDAO;
import com.omdp.webapp.model.TBulletinInfo;
import com.omdp.webapp.model.TUser;


@Repository
public class BulletinDAOImpl  extends AbstractJpaDAO<TBulletinInfo> implements BulletinDAO<TBulletinInfo>{

	public List<TBulletinInfo> queryBulletInfoList(String sql, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public TBulletinInfo load(Integer id,TUser u) {
		TBulletinInfo info = this.getJpaTemplate().getEntityManager().find(TBulletinInfo.class, id);
		return info;
	}

	public void executeUpdate(String updateSql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(updateSql);
		q.executeUpdate();
	}

	

}
