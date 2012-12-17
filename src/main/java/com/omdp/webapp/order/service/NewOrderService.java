package com.omdp.webapp.order.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.model.TUser;


public interface NewOrderService {

	public void saveNewOrder(TOrderInfo order, String sec, TUser u);

	public List<TCustInfo> queryCustList(TCustInfo cust, Page page, TUser u);

	public Integer updateSequence(String prefix, String name);

	public List<TOrderDetail> loadOrderDetailBySec(String sec, String busiType, TUser u);

	public List<TSysParam> loadFieldListByBusi(String busiType);

	public void saveNewOrderDetail(TOrderDetail orderDetail, TUser u);

	public void delOrderDetail(TOrderDetail orderDetail, TUser u);

	public void updateOrder(TOrderInfo order, TUser u);

	public void updateOrderDetail(TOrderDetail orderDetail, TUser u);

	public List<TCustInfo> queryUserInfoList(TCustInfo cust, Page page, TUser u);

}
