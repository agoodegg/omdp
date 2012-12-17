package com.omdp.webapp.employee.action;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.employee.service.EmployeeService;
import com.omdp.webapp.model.TDeptInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.dept.service.DeptService;


@Controller
@Scope("prototype")
public class EmployeeAction extends AbstractController {

	private final static String QUERY_USERS = "sys/user/userlist";
	private final static String EDIT_USER = "sys/user/edituser";
	private final static String ADD_USER = "sys/user/adduser";
	private final static String USER_ROLE = "sys/user/userole";
	private final static String QUERY_SEL_USERS = "sys/user/usersellist";
	private final static String QUERY_SEL_IN = "sys/user/userselin";
	
	@Autowired
	private DeptService dService;
	
	@Autowired
	private EmployeeService eService;
	
	@RequestMapping("/sys/employeeManage/queryEmployee.htm")
	public ModelAndView queryEmployee(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")TUser user,@ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TUser> userList = eService.queryUserInfoList(user, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("userList", userList);
		mode.put("page", page);
		mode.put("q", user);
		
		return new ModelAndView(QUERY_USERS,mode);
	}
	
	@RequestMapping("/sys/employeeManage/selEmployeeIn.htm")
	public ModelAndView selEmployeeIn(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")TUser user,@ModelAttribute("page")Page page,@RequestParam("f")String f){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(100);
		
		List<TUser> userList = eService.queryUserInfoList(user, page, u);
		
		Map<String, Object> modle = new HashMap<String, Object>();  
		modle.put("user", u);
		modle.put("userList", userList);
		modle.put("page", page);
		modle.put("q", user);
		modle.put("f",f);
		
		return new ModelAndView(QUERY_SEL_IN,modle);
	}
	
	@RequestMapping("/sys/employeeManage/selEmployee.htm")
	public ModelAndView selEmployee(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")TUser user,@ModelAttribute("page")Page page,@RequestParam("mode")String mode,@RequestParam("f")String f){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(100);
		
		
		List<TUser> userList = eService.queryUserInfoList(user, page, u);
		
		Map<String, Object> modle = new HashMap<String, Object>();  
		modle.put("user", u);
		modle.put("userList", userList);
		modle.put("page", page);
		modle.put("q", user);
		modle.put("mode",mode);
		modle.put("f",f);
		
		return new ModelAndView(QUERY_SEL_USERS,modle);
	}
	
	
	@RequestMapping("/sys/employeeManage/markDel.htm")
	public void markDel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("TUser")TUser user) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		eService.removeUserByMark(user, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "删除成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	@RequestMapping("/sys/employeeManage/resetPassword.htm")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("TUser")TUser user) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		eService.resetUserPassword(user, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "密码重置为123456");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	@RequestMapping("/sys/employeeManage/toEdit.htm")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("TUser")TUser user){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TDeptInfo dept = new TDeptInfo();
		Page tempPage = new Page();
		tempPage.setPageSize(50);
		List<TDeptInfo> deptList = dService.queryDeptInfoList(dept, tempPage, u);
		
		TUser  po = eService.loadUser(user, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("po", po);
		mode.put("deptList", deptList);
		
		return new ModelAndView(EDIT_USER, mode);
	}
	
	@RequestMapping("/sys/employeeManage/updateUser.htm")
	public void updateUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("TUser")TUser user) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		eService.updateUserInfo(user, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "修改成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	
	@RequestMapping("/sys/employeeManage/toAdd.htm")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TDeptInfo dept = new TDeptInfo();
		Page tempPage = new Page();
		tempPage.setPageSize(50);
		List<TDeptInfo> deptList = dService.queryDeptInfoList(dept, tempPage, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("deptList", deptList);
		
		return new ModelAndView(ADD_USER, mode);
	}
	
	@RequestMapping("/sys/employeeManage/addUser.htm")
	public void addUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("TUser")TUser user) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		eService.addUserInfo(user, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "新增成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	@RequestMapping("/sys/employeeManage/toUserRole.htm")
	public ModelAndView toUserRole(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("TUser")TUser user){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TDeptInfo dept = new TDeptInfo();
		Page tempPage = new Page();
		tempPage.setPageSize(50);
		List<TDeptInfo> deptList = dService.queryDeptInfoList(dept, tempPage, u);
		
		TUser  po = eService.loadUser(user, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("po", po);
		mode.put("deptList", deptList);
		
		return new ModelAndView(EDIT_USER, mode);
	}
	
}
