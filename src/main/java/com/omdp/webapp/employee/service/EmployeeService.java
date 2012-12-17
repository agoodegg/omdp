package com.omdp.webapp.employee.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TUser;


public interface EmployeeService {

	public List<TUser> queryUserInfoList(TUser user, Page page, TUser u);

	public void removeUserByMark(TUser user, TUser u);

	public TUser loadUser(TUser user, TUser u);

	public void updateUserInfo(TUser user, TUser u);

	public void addUserInfo(TUser user, TUser u);

	public void resetUserPassword(TUser user, TUser u);

	public TUser loadUserByUserId(String userId, TUser u);


}
