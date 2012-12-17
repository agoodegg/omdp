package com.omdp.webapp.deliver.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TSendProduct;
import com.omdp.webapp.model.TUser;


public interface DeliverDAO<T> extends Dao<T> {

	public List<TSendProduct> querySendProductInfo(String querySql, Page page);

	public TSendProduct loadSendProduct(Integer id, TUser u);

}
