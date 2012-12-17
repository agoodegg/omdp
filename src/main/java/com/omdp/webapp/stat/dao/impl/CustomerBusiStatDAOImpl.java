package com.omdp.webapp.stat.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.stat.dao.CustomerBusiStatDAO;


@Repository
public class CustomerBusiStatDAOImpl<TOrderInfo> extends AbstractJpaDAO<TOrderInfo> implements CustomerBusiStatDAO<TOrderInfo> {

	public List<Object[]> querySql(String sql, TUser u) {
		
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		
		return q.getResultList();
	}

	public List<Object[]> queryNativeSql(String sql, TUser u) {
		Query q = this.getJpaTemplate().getEntityManager().createNativeQuery(sql);
		
		return q.getResultList();
	}

	public List<Object[]> queryNativeSql(String sql, TUser u, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createNativeQuery(sql);
		
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		
		return q.getResultList();
	}

	public List<Object> nativeQuery(String sql) {
		Query q = this.getJpaTemplate().getEntityManager().createNativeQuery(sql);
		
		return q.getResultList();
	}
	
	
}
