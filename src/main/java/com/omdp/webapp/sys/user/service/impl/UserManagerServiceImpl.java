package com.omdp.webapp.sys.user.service.impl;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.user.dao.UserManagerDAO;
import com.omdp.webapp.sys.user.service.UserManagerService;


@Transactional
public class UserManagerServiceImpl implements UserManagerService{

	@Resource
	private UserManagerDAO userManagerDAO;
	
	public void updateUserAcount(TUser u, String newAccount) {
		
		String currentUserAccount = u.getUserAccount();
		
		userManagerDAO.updateUserAccount(currentUserAccount,newAccount);
		
	}

	public void updateUserPwd(TUser u, String pwd) {
		
		String currentUserAccount = u.getUserAccount();
		userManagerDAO.updateUserPwd(currentUserAccount,pwd);
	}
	
	
	
	
}
