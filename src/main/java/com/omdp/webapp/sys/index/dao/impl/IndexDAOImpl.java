package com.omdp.webapp.sys.index.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.sys.index.dao.IndexDAO;


@Repository
public class IndexDAOImpl extends AbstractJpaDAO<TOrderInfo> implements IndexDAO<TOrderInfo>{

	public Long getIntegerResultBySql(String sql) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		
		Long result = (Long)(q.getSingleResult());
		
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}
}
