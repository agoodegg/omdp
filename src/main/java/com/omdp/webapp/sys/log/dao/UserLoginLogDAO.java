package com.omdp.webapp.sys.log.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserLogin;


public interface UserLoginLogDAO <T> extends Dao<T>{

	public void saveUserLogin(TUserLogin userLogin);

	public List<TUserLogin> queryUserLogin(String sql, Page page);
	
	public void saveSysLog(TUser u,String oprMsg,String logType);

}
