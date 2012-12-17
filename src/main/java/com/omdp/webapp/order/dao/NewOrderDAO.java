package com.omdp.webapp.order.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.Sequence;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TUser;


public interface NewOrderDAO<T> extends Dao<T>{

	public Sequence getSeq(Sequence seq);

	public void updateSeq(Sequence seq);

	public void save(TOrderDetail orderDetail);

	public void executeUpdate(String string);

	public void updateDetail(String sec, String ordernum, TUser u);

	public Double getAllTotalPrice(String sec, TUser u);

	public void saveHolder(TStakeHolder holder, TUser u);

	public TOrderDetail loadOrderDetailById(Integer id, TUser u);

	public void updateDetail(TOrderDetail po, TUser u);

	public List<TCustInfo> queryByPage(String sql, Page page);


}
