package com.omdp.webapp.sys.user.dao;

import com.omdp.webapp.base.common.dao.Dao;


public interface UserManagerDAO<T> extends Dao<T>{

	public void updateUserAccount(String currentUserAccount, String newAccount);

	public void updateUserPwd(String currentUserAccount, String pwd);

}
