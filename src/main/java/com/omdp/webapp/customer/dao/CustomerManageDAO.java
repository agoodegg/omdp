package com.omdp.webapp.customer.dao;

import java.util.List;
import java.util.Map;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TUser;


public interface CustomerManageDAO<T> extends Dao<T> {

	public List queryCustList(String sql, Page page);

	public TCustInfo loadCustById(Integer id, TUser u);

	public TCustInfo queryCustByCustId(String custId, TUser u);

	public Object queryForSingleObject(String sql, Map<String, Object> map);

}
