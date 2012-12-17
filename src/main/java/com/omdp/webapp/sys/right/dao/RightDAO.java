package com.omdp.webapp.sys.right.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TRoleInfo;
import com.omdp.webapp.model.TRoleRes;
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;


public interface RightDAO<T> extends Dao<T> {

	public List<TRoleInfo> queryRoleInfoList(String querySql, Page page);

	public TRoleInfo load(Integer id, TUser u);

	public void executeUpdate(String updateSql);

	public List<TSysRes> getRoleRes(TRoleInfo po);

	public void saveRoleRes(TRoleRes resObj);

}
