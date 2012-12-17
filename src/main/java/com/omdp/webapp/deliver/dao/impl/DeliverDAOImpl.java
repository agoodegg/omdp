package com.omdp.webapp.deliver.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.deliver.dao.DeliverDAO;
import com.omdp.webapp.model.TSendProduct;
import com.omdp.webapp.model.TUser;


@Repository
public class DeliverDAOImpl extends AbstractJpaDAO<TSendProduct> implements DeliverDAO<TSendProduct> {

	public List<TSendProduct> querySendProductInfo(String querySql, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(querySql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public TSendProduct loadSendProduct(Integer id, TUser u) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" select t from TSendProduct t where t.id=:id ");
		q.setParameter("id", id);
		List<TSendProduct> list = q.getResultList();
		if(list==null||list.size()==0){
			return null;
		}
		else{
			return list.get(0);
		}
	}

}
