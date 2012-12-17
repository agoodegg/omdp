package com.omdp.webapp.sys.user.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.user.service.UserManagerService;


@Controller
@Scope("prototype")
public class UserManagerAction extends AbstractController {

	private final static String PASSWORD_PAGE = "sys/user/password";
	private final static String ACCOUNT_PAGE = "sys/user/account";
	
	@Autowired
	private UserManagerService uService;
	
	@RequestMapping("/sys/user/toPassword.htm")
	public String toPassword(HttpServletRequest request, HttpServletResponse response)
	{
		//System.out.println("["+new Md5PasswordEncoder().encodePassword("pwd", "zhouxiaohui")+"]");
		System.out.println("md5pwd:"+DigestUtils.md5Hex("pwdadmin"));
		return PASSWORD_PAGE;
	}
	
	@RequestMapping("/sys/user/updatePassword.htm")
	public void updatePwd(HttpServletRequest request, HttpServletResponse response,@RequestParam("oldPassword")String oldPwd,@RequestParam("newPassword")String newPwd1,@RequestParam("newPasswordAgain")String newPwd2) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		StringBuffer errMsg = new StringBuffer();
		if(StringUtils.isBlank(oldPwd)){
			errMsg.append("原始密码不能为空\n");
		}
		if(StringUtils.isBlank(newPwd1)){
			errMsg.append("新密码不能为空\n");
		}
		if(!(StringUtils.trimToEmpty(newPwd1).equals(StringUtils.trimToEmpty(newPwd2)))){
			errMsg.append("两次输入的密码不同\n");
		}
		else if(!(DigestUtils.md5Hex(StringUtils.trimToEmpty(oldPwd)).equals(u.getUserPwd()))){
			
			System.out.println("oldPwd:"+oldPwd);
			System.out.println("u.getUserPwd():"+u.getUserPwd());
			errMsg.append("原始密码不正确！修改失败！");
		}
		
		if(errMsg.length()>0){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", errMsg.toString());
			ResponseUtils.writeJSONString(response,map);
			return;
		}
		else{
			
			uService.updateUserPwd(u,DigestUtils.md5Hex(StringUtils.trimToEmpty(newPwd1)));
			
			u.setUserPwd(DigestUtils.md5Hex(StringUtils.trimToEmpty(newPwd1)));
			
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", true);
			map.put("msg", "修改成功");
			ResponseUtils.writeJSONString(response,map);
		}
		
	}
	
	@RequestMapping("/sys/user/updateUserAccount.htm")
	public void updateUserAccount(HttpServletRequest request, HttpServletResponse response,@RequestParam("newUserAccount")String newAccount) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		uService.updateUserAcount(u,newAccount);
		
		u.setUserAccount(newAccount);
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "修改成功");
		map.put("account", newAccount);
		ResponseUtils.writeJSONString(response,map);
	}
	
	
	@RequestMapping("/sys/user/toAccount.htm")
	public ModelAndView toAccount(HttpServletRequest request, HttpServletResponse response)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());

		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);  
		return new ModelAndView(ACCOUNT_PAGE,mode);
	}
	
}
