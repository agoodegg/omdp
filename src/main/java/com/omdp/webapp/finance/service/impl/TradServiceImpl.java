package com.omdp.webapp.finance.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.util.Calendar;
import com.omdp.webapp.finance.dao.TradDAO;
import com.omdp.webapp.finance.service.TradService;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TTradDetail;
import com.omdp.webapp.model.TTradInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.action.OrderQueryAction;
import com.omdp.webapp.order.dao.NewOrderDAO;
import com.omdp.webapp.order.dao.OrderQueryDAO;


@Transactional
public class TradServiceImpl implements TradService{

	@Resource
	private TradDAO<TTradInfo> tradDAO;

	@Resource
	private NewOrderDAO<TOrderInfo> newOrderDAO;
	
	@Resource
	private OrderQueryDAO<TOrderInfo> orderQueryDAO;
	
	public void payOrder(TOrderInfo order, TTradInfo t, TUser u){
		
		Integer status = Integer.parseInt(order.getOrderStatus(), 2);
		Integer result = status|OrderQueryAction.PAY;
		String str = Integer.toBinaryString(result);
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<5-str.length();i++){
			buf.append("0");
		}
		order.setOrderStatus(buf.toString()+str);
		
		newOrderDAO.update(order);
		
		
		t.setCustAddress(order.getDeliverAddress());
		t.setCustCd(order.getCustId());
		t.setCustName(order.getCustName());
		t.setTel(order.getTel());
		t.setTradOpr(u.getIdNo());
		t.setTradOprName(u.getUserName());
		t.setTradTime(new Date());
		tradDAO.save(t);
		
		String sql = " from TOrderDetail t where t.ordernum = '"+order.getOrdernum()+"' ";
		List<TOrderDetail> details = orderQueryDAO.queryOrderDetail(sql);
		
		if(details!=null){
			for(TOrderDetail d:details){
				TTradDetail tradDetail = new TTradDetail();
				try{
					BeanUtils.copyProperties(tradDetail, d);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				tradDetail.setTradCd(t.getTradCd());
				tradDetail.setId(null);
				tradDAO.saveTradDetail(tradDetail);
				
			}
		}
	}

	
	public TTradInfo loadTradByOrdernum(String ordernum, TUser u) {
		
		return tradDAO.loadTradInfoByOrdernum(ordernum,u);
	}


	public void checkOrder(TOrderInfo order, TTradInfo t, TUser u) {
		Integer status = Integer.parseInt(order.getOrderStatus(), 2);
		Integer result = status|OrderQueryAction.CHECKED;
		String str = Integer.toBinaryString(result);
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<5-str.length();i++){
			buf.append("0");
		}
		order.setOrderStatus(buf.toString()+str);
		
		newOrderDAO.update(order);
		
		TTradInfo tradInfo = tradDAO.loadTradInfoByOrdernum(order.getOrdernum(),u);
		
		tradInfo.setValidComm(t.getValidComm());
		tradInfo.setValidOpr(u.getIdNo());
		tradInfo.setValidOprName(u.getUserName());
		tradInfo.setValidTime(new Date());
		tradInfo.setValidStatus("0");
		
		tradDAO.update(tradInfo);
	}


	public void billSend(TTradInfo tradInfo, TUser u) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tradInfo.setTradComm(sdf.format(Calendar.getInstance().getTime()) + "  "+ u.getUserName()+"补开了发票");
		tradInfo.setCheckStatus("1");
		tradDAO.update(tradInfo);
		
	}
	
	
	
}
