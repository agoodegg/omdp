package com.omdp.webapp.sys.log.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserLogin;
import com.omdp.webapp.sys.log.service.UserLoginLogService;


@Controller
@Scope("prototype")
public class UserLoginLogAction extends AbstractController {

	private final static String QUERY_LOGIN_LOG = "sys/syslog/loginlog";
	private final static String QUERY_ALL_LOG = "sys/syslog/queryall";
	
	public final static String ORDER_OPR_LOG = "2";
	public final static String SEND_ORDER_OPR_LOG = "3";
	public final static String FINANCE_OPR = "4";
	public final static String CUST_OPR = "5";
	public final static String ACCOUNT_OPR = "6";
	public final static String OTHER_OPR = "7";
	
	@Autowired
	private UserLoginLogService uService;
	
	@RequestMapping("/sys/syslog/queryAllLog.htm")
	public ModelAndView queryAllLog(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("log")TUserLogin log,@ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TUserLogin> logList = uService.querySystemLog(log, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("logList", logList);
		mode.put("page", page);
		mode.put("log", log);
		
		return new ModelAndView(QUERY_ALL_LOG,mode);
	}
	
	@RequestMapping("/sys/syslog/queryLogin.htm")
	public ModelAndView queryLogin(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("log")TUserLogin log,@ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TUserLogin> logList = uService.queryUserLogin(log, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("logList", logList);
		mode.put("page", page);
		return new ModelAndView(QUERY_LOGIN_LOG,mode);
	}
}
