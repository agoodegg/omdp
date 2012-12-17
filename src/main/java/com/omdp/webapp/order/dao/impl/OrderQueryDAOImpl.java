package com.omdp.webapp.order.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.dao.OrderQueryDAO;

@Repository
public class OrderQueryDAOImpl extends AbstractJpaDAO<TOrderInfo> implements OrderQueryDAO<TOrderInfo> {

	public List<TOrderInfo> queryOrderInfo(String sql,Page page) {
		
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public TOrderInfo loadOrderInfo(Integer id, TUser u) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" select t from TOrderInfo t where t.id=:id ");
		q.setParameter("id", id);
		List<TOrderInfo> list = q.getResultList();
		if(list==null||list.size()==0){
			return null;
		}
		else{
			return list.get(0);
		}
	}

	public List<TOrderDetail> queryOrderDetail(String sql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		
		return q.getResultList();
	}

	public void executeUpdate(String sql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.executeUpdate();
		
	}

	public TOrderInfo loadOrderInfoByNum(String ordernum, TUser u) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" select t from TOrderInfo t where t.ordernum=:ordernum ");
		q.setParameter("ordernum", ordernum);
		List<TOrderInfo> list = q.getResultList();
		if(list==null||list.size()==0){
			return null;
		}
		else{
			return list.get(0);
		}
	}

	public List<TStakeHolder> loadStakeHolders(String sql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		return q.getResultList();
	}

	public void deleteStakeHoldersByOrdernum(String ordernum) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" delete from TStakeHolder t where t.relaValue='"+ordernum+"' and t.relaBusi='ORDER'   ");
		q.executeUpdate();
	}

	public List<Object[]> queryPayedList(String querySql, Page page) {
		
		Query q = this.getJpaTemplate().getEntityManager().createQuery(querySql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public List querySql(String querySql, TUser u) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(querySql);
		return q.getResultList();
	}

	
	
}
