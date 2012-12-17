package com.omdp.webapp.sys.right.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TRoleInfo;
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;


public interface RightService {

	public List<TRoleInfo> queryRoleInfoList(TRoleInfo role, Page page, TUser u);

	public void addRoleInfo(TRoleInfo role, TUser u);

	public TRoleInfo loadRoleInfo(TRoleInfo role, TUser u);

	public void updateRoleInfo(TRoleInfo role, TUser u);

	public void removeRoleInfo(TRoleInfo role, TUser u);

	public List<TSysRes> getRoleRes(TRoleInfo role, TUser u);

	public void saveRoleRes(Integer id, String roleIds, TUser u);

}
