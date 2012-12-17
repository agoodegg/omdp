package com.omdp.webapp.sys.user.service;

import com.omdp.webapp.model.TUser;


public interface UserManagerService {

	public void updateUserAcount(TUser u, String newAccount);

	public void updateUserPwd(TUser u, String trimToEmpty);

}
