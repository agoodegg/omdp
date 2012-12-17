package com.omdp.webapp.stat.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.finance.bean.IFeeBean;
import com.omdp.webapp.model.TUser;


public interface CustomerBusiStatService {

	public List<Object[]> queryBusiAchRank(IFeeBean statParam, TUser u);

	public List<Object[]> queryBusiMonthLine(IFeeBean q, TUser u);

	public List<Object[]> queryDayCash(IFeeBean q, TUser u, Page page);

	public List<Object[]> queryDeltaTop20(IFeeBean q, TUser u, Page page);

	public List<Object[]> queryOldTop20(IFeeBean q, TUser u, Page page);

	public List<Object[]> queryDayLine(IFeeBean q, TUser u, Page page);

	public List<Object[]> queryCustMonthLine(IFeeBean queryBean, TUser u);

}
