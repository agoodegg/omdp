package com.omdp.webapp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.Authentication;
import org.springframework.security.event.authentication.AuthenticationSuccessEvent;
import org.springframework.security.ui.WebAuthenticationDetails;

import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserLogin;
import com.omdp.webapp.sys.log.service.UserLoginLogService;

public class LoginSuccessListener implements ApplicationListener {
	
	@Autowired
	private UserLoginLogService uService;




	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AuthenticationSuccessEvent) {// 判断是否是认证成功的事件
			AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
			Authentication auth = authEvent.getAuthentication();
			String userName = auth.getName();

			WebAuthenticationDetails webDetail = (WebAuthenticationDetails) auth.getDetails();
			
			
			try{
				TUser u = (TUser)(auth.getPrincipal());
				
				TUserLogin userLogin = new TUserLogin();
				userLogin.setIpAddress(webDetail.getRemoteAddress());
				userLogin.setLogType("0"); //0为登录
				userLogin.setLogTime(new Date());
				userLogin.setUserName(userName);
				userLogin.setUserId(u.getIdNo());
				userLogin.setAddInfo(u.getUserName()+"登录系统");
				
				uService.saveUserLoginLog(userLogin);
				
				u.setIpAddress(webDetail.getRemoteAddress());
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		
	}
}
