package com.omdp.webapp.order.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.Sequence;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.dao.NewOrderDAO;

@Repository
public class NewOrderDAOImpl extends AbstractJpaDAO<TOrderInfo> implements NewOrderDAO<TOrderInfo> {

	public Sequence getSeq(Sequence seq) {
		return this.getJpaTemplate().getEntityManager().find(Sequence.class, seq.getName());
	}

	public void updateSeq(Sequence seq) {
		this.getJpaTemplate().getEntityManager().merge(seq);
	}

	public void save(TOrderDetail orderDetail) {
		
		this.getJpaTemplate().getEntityManager().persist(orderDetail);
	}

	public void executeUpdate(String sql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.executeUpdate();
	}

	public Double getAllTotalPrice(String sec, TUser u) {
		
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" select sum(t.total) from TOrderDetail t where t.ordernum=:sec ");
		q.setParameter("sec", sec);
		
		return (Double)(q.getSingleResult());
	}

	public void updateDetail(String sec, String ordernum, TUser u) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" update TOrderDetail t set t.ordernum=:ordernum where t.ordernum=:sec ");
		q.setParameter("ordernum", ordernum);
		q.setParameter("sec", sec);
		
		q.executeUpdate();
	}

	public void saveHolder(TStakeHolder holder, TUser u) {
		this.getJpaTemplate().getEntityManager().persist(holder);
	}

	public TOrderDetail loadOrderDetailById(Integer id, TUser u) {


		return this.getJpaTemplate().getEntityManager().find(TOrderDetail.class, id);
	}

	public void updateDetail(TOrderDetail po, TUser u) {
		this.getJpaTemplate().getEntityManager().merge(po);
		
	}

	public List<TCustInfo> queryByPage(String sql, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		
		return q.getResultList();
	}
	
	

}
