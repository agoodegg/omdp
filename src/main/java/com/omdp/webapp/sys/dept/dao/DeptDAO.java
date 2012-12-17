package com.omdp.webapp.sys.dept.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TDeptInfo;
import com.omdp.webapp.model.TUser;


public interface DeptDAO<T> extends Dao<T> {

	public List<TDeptInfo> queryDeptInfoList(String querySql, Page page);

	public TDeptInfo load(Integer id, TUser u);

	public void executeUpdate(String updateSql);

}
