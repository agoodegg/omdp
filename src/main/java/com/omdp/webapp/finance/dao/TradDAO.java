package com.omdp.webapp.finance.dao;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.model.TTradDetail;
import com.omdp.webapp.model.TTradInfo;
import com.omdp.webapp.model.TUser;


public interface TradDAO<T> extends Dao<T> {

	public void saveTradDetail(TTradDetail tradDetail);

	public TTradInfo loadTradInfoByOrdernum(String ordernum, TUser u);

}
