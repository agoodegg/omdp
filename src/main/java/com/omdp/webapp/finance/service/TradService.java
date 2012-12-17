package com.omdp.webapp.finance.service;

import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TTradInfo;
import com.omdp.webapp.model.TUser;


public interface TradService {

	public void payOrder(TOrderInfo order, TTradInfo t, TUser u);

	public TTradInfo loadTradByOrdernum(String ordernum, TUser u);

	public void checkOrder(TOrderInfo order, TTradInfo trad, TUser u);

	public void billSend(TTradInfo tradInfo, TUser u);

}
