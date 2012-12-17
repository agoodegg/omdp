package com.omdp.webapp.deliver.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TSendProduct;
import com.omdp.webapp.model.TUser;


public interface DeliverService {

	public List<TSendProduct> queryDeliverInfo(TSendProduct q, Page page, TUser u);

	public TSendProduct loadSendProductById(Integer id, TUser u);

	public void doneSendOrder(TSendProduct q, TUser u);

}
