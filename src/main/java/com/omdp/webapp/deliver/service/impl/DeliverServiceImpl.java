package com.omdp.webapp.deliver.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.util.BaseStringUtils;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.deliver.dao.DeliverDAO;
import com.omdp.webapp.deliver.service.DeliverService;
import com.omdp.webapp.model.TSendProduct;
import com.omdp.webapp.model.TUser;


@Transactional
public class DeliverServiceImpl implements DeliverService {

	@Resource
	private DeliverDAO<TSendProduct> deliverDAO;

	public List<TSendProduct> queryDeliverInfo(TSendProduct q, Page page, TUser u) {
		
		String sql = " select t from TSendProduct t where t.doneFlag='?' and t.custName like '%?%' and t.createTime >= str_to_date('?', '%Y-%m-%d') and t.createTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.ordernum='?'  order by t.createTime desc";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDoneFlag()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getCustName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getCreateTimeU()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getCreateTimeD()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getOrdernum()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		
		int total = ((Long)(deliverDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		return deliverDAO.querySendProductInfo(querySql,page);
	}

	public TSendProduct loadSendProductById(Integer id, TUser u) {
		return deliverDAO.loadSendProduct(id,u);
	}

	public void doneSendOrder(TSendProduct q, TUser u) {
		
		
		TSendProduct po = deliverDAO.loadSendProduct(q.getId(),u);
		
		po.setSendOpr(q.getSendOpr());
		po.setDoneTime(new Date());
		po.setDoneFlag("1");
		
		deliverDAO.update(po);
	}
	
	
	
}
