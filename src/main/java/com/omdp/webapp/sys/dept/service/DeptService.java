package com.omdp.webapp.sys.dept.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TDeptInfo;
import com.omdp.webapp.model.TUser;


public interface DeptService {

	public List<TDeptInfo> queryDeptInfoList(TDeptInfo dept, Page page, TUser u);

	public TDeptInfo loadDept(TDeptInfo dept, TUser u);

	public void updateDeptInfo(TDeptInfo dept, TUser u);

	public void addDeptInfo(TDeptInfo dept, TUser u);

	public void removeDeptInfo(TDeptInfo dept, TUser u);

}
