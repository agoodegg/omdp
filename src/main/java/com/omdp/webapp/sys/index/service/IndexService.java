package com.omdp.webapp.sys.index.service;

import com.omdp.webapp.model.TUser;


public interface IndexService {

	public Long queryTotalDeliverUnDone(TUser u);

	public Long queryTodayOrderNum(TUser u);

	public Long queryWeeklyOrderNum(TUser u);

	public Long queryUnDoneOrderNum(TUser u);

	public Long queryUnDoneOTOrderNum(TUser u);

	public Long queryUnTradPayOrderNum(TUser u);

	public Long queryUnCheckPayOrderNum(TUser u);

	public Long queryDelayTradOrderNum(TUser u);

}
