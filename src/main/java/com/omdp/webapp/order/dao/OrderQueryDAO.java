package com.omdp.webapp.order.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TUser;


public interface OrderQueryDAO<T> extends Dao<T> {

	public List<TOrderInfo> queryOrderInfo(String sql, Page page);

	public TOrderInfo loadOrderInfo(Integer id, TUser u);

	public List<TOrderDetail> queryOrderDetail(String sql);

	public void executeUpdate(String sql);

	public TOrderInfo loadOrderInfoByNum(String ordernum, TUser u);

	public List<TStakeHolder> loadStakeHolders(String sql);

	public void deleteStakeHoldersByOrdernum(String ordernum);

	public List<Object[]> queryPayedList(String querySql, Page page);

	public List querySql(String string, TUser u);

}
