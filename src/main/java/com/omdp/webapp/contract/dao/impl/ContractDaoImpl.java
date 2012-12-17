package com.omdp.webapp.contract.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.contract.dao.IContractDao;
import com.omdp.webapp.model.TContractInfo;
import com.omdp.webapp.model.TContractItem;
import com.omdp.webapp.model.TUser;


@Repository
public class ContractDaoImpl  extends AbstractJpaDAO<TContractInfo> implements IContractDao<TContractInfo>{

	public List<TContractInfo> queryContractInfo(TContractInfo info, TUser u, Page page) {
		
		String rawSql = " select t from TContractInfo t where t.status='NORMAL' and t.contractNo='?' and t.buyer like '%?%' and t.buyerMan like '%?%' order by t.createTime desc";
		String[] params=new String[]{StringUtils.trimToNull(info.getContractNo()),StringUtils.trimToNull(info.getBuyer()),StringUtils.trimToNull(info.getBuyerMan())};
		
		String sql = SQLCutter.cut(rawSql, params);
		String countSql = SQLCutter.cutToCountSQL(rawSql, params);
		
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());


		Query countQuery = this.getJpaTemplate().getEntityManager().createQuery(countSql);
		int total = ((Long)countQuery.getSingleResult()).intValue();
		page.setTotalCount(total);
		
		
		return q.getResultList();
	}

	
	public void trashContract(String contractNo, TUser u) {
		TContractInfo t = this.queryContractByContractNo(contractNo);
		
		if(t!=null){
			t.setStatus("TRASH");
		}
		
		this.getJpaTemplate().getEntityManager().merge(t);
	}


	public TContractInfo queryContractByContractNo(String contractNo) {
		
		String sql = " select t from  TContractInfo t where t.contractNo='"+contractNo+"' ";
		
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		List<TContractInfo> data = q.getResultList();
		if(data==null||data.size()==0){
			return null;
		}
		else{
			return data.get(0);
		}
	}


	public void saveDetail(TContractItem item) {
		this.getJpaTemplate().getEntityManager().persist(item);
	}


	public void updateContractTotal(String contractNo) {
		String queryCountSql = " select count(*) from TContractItem m where m.contractNo = :contractNo ";
		Query countQuery = this.getJpaTemplate().getEntityManager().createQuery(queryCountSql);
		countQuery.setParameter("contractNo", contractNo);
		Long total = (Long)(countQuery.getSingleResult());
		if(total==0L){
			String sql = "  update TContractInfo t set t.gross = 0.00 where t.contractNo = :cno ";
			Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
			q.setParameter("cno", contractNo);
			q.executeUpdate();
		}
		else{
			String sql = "  update TContractInfo t set t.gross = ( select sum(m.total) from TContractItem m where m.contractNo = :contractNo ) where t.contractNo = :cno ";
			Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
			q.setParameter("contractNo", contractNo);
			q.setParameter("cno", contractNo);
			q.executeUpdate();
		}
		
	}


	public TContractInfo queryContractById(Integer id) {
		return this.getJpaTemplate().getEntityManager().find(TContractInfo.class, id);
	}


	public List<TContractItem> queryContractItemByContractNo(String contractNo, TUser u) {
		String sql = " select t from TContractItem t where t.contractNo = :contractNo order by t.id asc ";
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setParameter("contractNo", contractNo);
		
		return q.getResultList();
	}


	public void deleteAllItems(String contractNo) {
		String sql = " delete from TContractItem t where t.contractNo = :contractNo ";
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setParameter("contractNo", contractNo);
		
		q.executeUpdate();
	}


	public TContractInfo findContractByOrdernum(String ordernum, TUser u) {
	
		String sql = " select f from TContractInfo f where f.orderNum = :ordernum and f.status <> 'TRASH' ";
		Query q = this.getJpaTemplate().getEntityManager().createQuery(sql);
		q.setParameter("ordernum", ordernum);
		
		List<TContractInfo> data = q.getResultList();
		
		if(data==null||data.size()==0){
			return null;
		}
		else{
			return data.get(0);
		}
	}


	public void genContractFromOrder(TContractInfo contract, List<TContractItem> itemList) {
		this.getJpaTemplate().getEntityManager().persist(contract);
		
		for(TContractItem item:itemList){
			this.getJpaTemplate().getEntityManager().persist(item);
		}
		
		updateContractTotal(contract.getContractNo());
	}
	
}
