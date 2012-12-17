package com.omdp.webapp.customer.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TUser;


public interface CustomerManageService {

	public void saveNewCust(TCustInfo cust, TUser u);

	public List<TCustInfo> queryCustInfo(TCustInfo cust, boolean hasAllCustVisual, Page page, TUser u);

	public TCustInfo loadCustInfoById(Integer id, TUser u);

	public void markDel(TCustInfo po, TUser u);

	public void updateCust(TCustInfo cust, TUser u);

	public List<TCustInfo> myCustStatFee(TCustInfo cust, boolean hasAllCustVisual, Page page, TUser u);

	public List<TUser> userClientStatFee(TUser user, Page page, TUser u);

	public TCustInfo loadCustInfoByCustId(String custId, TUser u);

	public boolean existCustName(TCustInfo cust);
	
}
