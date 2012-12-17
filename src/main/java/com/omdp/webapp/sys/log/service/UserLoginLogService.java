package com.omdp.webapp.sys.log.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserLogin;


public interface UserLoginLogService {

	public void saveUserLoginLog(TUserLogin userLogin);

	public List<TUserLogin> queryUserLogin(TUserLogin log, Page page, TUser u);

	public List<TUserLogin> querySystemLog(TUserLogin log, Page page, TUser u);
	
	public void saveSysLog(TUser u,String oprMsg,String logType);

}
