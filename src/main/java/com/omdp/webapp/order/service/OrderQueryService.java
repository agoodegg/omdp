package com.omdp.webapp.order.service;

import java.util.List;
import java.util.Map;

import cn.com.sunrise.util.report.model.Report;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.finance.bean.IFeeBean;
import com.omdp.webapp.model.TContractInfo;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TUser;


public interface OrderQueryService {

	public List<TOrderInfo> queryOrderInfo(TOrderInfo order, Page page, TUser u);

	public TOrderInfo loadOrderById(Integer id, TUser u);

	public List<TOrderDetail> loadOrderDetails(TOrderInfo order, TUser u);

	public void doneOrder(TOrderInfo order, String sendNum, TUser u);

	public void updateOrderInfoStatus(TOrderInfo order, TUser u);

	public TOrderInfo loadOrderByNum(String ordernum, TUser u);

	public List<TOrderInfo> queryDoneOrderInfo(TOrderInfo q, Page page, TUser u);

	public List<TOrderInfo> queryUnfinishOrderInfo(TOrderInfo q, Page page, TUser u);

	public List<TOrderInfo> queryTrashOrderInfo(TOrderInfo q, Page page, TUser u);

	public List<Object[]> getDetailStaticData(TOrderInfo order, TUser u);

	public List<TStakeHolder> queryStakeHolders(TOrderInfo order, String orderBind);

	public void trashOrder(TOrderInfo order, TUser u);

	public List<Object[]> queryPayedList(TOrderInfo q, Page page, TUser u);

	
	
	public Report getIfeeReport(IFeeBean q, TUser u, Map<String, Map<String, String>> sysDict);

	public List<IFeeBean> getTradRecList(IFeeBean q, Page page, TUser u);

	public IFeeBean getTradRecTotal(IFeeBean q, TUser u);

	public List<TOrderInfo> getBillList(IFeeBean q, TUser u);

	
	public List<TOrderInfo> getBillDetail(IFeeBean q, TUser u);

	public List<TOrderInfo> getBillDetailExport(IFeeBean q, TUser u);

	public void genContract(TOrderInfo order, TUser u, TContractInfo contract);

	public TContractInfo alreadyGenContract(TOrderInfo order, TUser u);
}
