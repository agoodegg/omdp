package com.omdp.webapp.finance.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.finance.dao.TradDAO;
import com.omdp.webapp.model.TTradDetail;
import com.omdp.webapp.model.TTradInfo;
import com.omdp.webapp.model.TUser;


@Repository
public class TradDAOImpl extends AbstractJpaDAO<TTradInfo> implements TradDAO<TTradInfo> {

	public void saveTradDetail(TTradDetail tradDetail) {

		this.getJpaTemplate().getEntityManager().persist(tradDetail);
	}

	public TTradInfo loadTradInfoByOrdernum(String ordernum, TUser u) {
		
		String sql = " select t from TTradInfo t where t.ordernum = '"+ordernum+"' ";
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		
		List<TTradInfo> result = q.getResultList();
		if(result!=null&&result.size()>0){
			return result.get(0);
		}
		else{
			return null;
		}
	}

}
