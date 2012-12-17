package com.omdp.webapp.customer.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.customer.dao.CustomerManageDAO;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TUser;


@Repository
public class CustomerManageDAOImpl extends AbstractJpaDAO<TCustInfo> implements CustomerManageDAO<TCustInfo> {

	public List queryCustList(String sql, Page page) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		return q.getResultList();
	}

	public TCustInfo loadCustById(Integer id, TUser u) {
		return this.getJpaTemplate().getEntityManager().find(TCustInfo.class, id);
	}

	public TCustInfo queryCustByCustId(String custId, TUser u) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(" from TCustInfo t where t.custId = :custId  ");
		q.setParameter("custId", custId);
		
		List<TCustInfo> custList = q.getResultList();
		
		if(custList==null||custList.size()==0){
			return null;
		}
		else{
			return custList.get(0);
		}
	}

	public Object queryForSingleObject(String sql, Map<String, Object> map) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			q.setParameter(key, map.get(key));
		}
		
		return q.getSingleResult();
	}

	

	
	
}
