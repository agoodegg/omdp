package com.omdp.webapp.employee.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TRoleInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserRole;


public interface EmployeeDAO<T> extends Dao<T> {

	public List queryUserInfoList(String sql, Page page);

	public void executeUpdate(String updateSql);

	public TUser load(Integer id, TUser u);

	public void saveUserRole(TUserRole userRoleObject);

	public List<TRoleInfo> loadUserRole(TUser po);

	public void clearUserRole(TUser po);

	public TUser loadUserByUserId(String userId, TUser u);


}
