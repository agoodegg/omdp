package com.omdp.webapp.sys.right.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
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
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TRoleInfo;
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.security.SpringSecurityManager;
import com.omdp.webapp.sys.right.service.RightService;


@Controller
@Scope("prototype")
public class RightAction extends AbstractController {

	
	private final static String QUERY_DEPT = "sys/right/rightmanage";
	private final static String ADD_ROLE = "sys/right/addrole";
	private final static String EDIT_ROLE = "sys/right/editrole";
	private final static String ROLE_SEL = "sys/right/rolesel";
	
	private final static String ROLE_RES = "sys/res/reslist";
	
	@Autowired
	private RightService rService;
	
	@RequestMapping("/sys/roleManage/queryRole.htm")
	public ModelAndView queryRole(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("role")TRoleInfo role,@ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TRoleInfo> roleList = rService.queryRoleInfoList(role, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("roleList", roleList);
		mode.put("page", page);
		mode.put("q", role);
		
		return new ModelAndView(QUERY_DEPT,mode);
	}
	
	@RequestMapping("/sys/roleManage/roleList.htm")
	public ModelAndView roleList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("role")TRoleInfo role,@ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(50);
		
		List<TRoleInfo> roleList = rService.queryRoleInfoList(role, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("roleList", roleList);
		mode.put("page", page);
		mode.put("q", role);
		
		return new ModelAndView(ROLE_SEL,mode);
	}
	
	
	@RequestMapping("/sys/roleManage/toAdd.htm")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		
		return new ModelAndView(ADD_ROLE,mode);
		
	}
	
	@RequestMapping("/sys/roleManage/saveNew.htm")
	public void saveNew(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("role")TRoleInfo role) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		rService.addRoleInfo(role, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "新增成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
		
	}
	
	
	@RequestMapping("/sys/roleManage/toEdit.htm")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("role")TRoleInfo role){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TRoleInfo  po = rService.loadRoleInfo(role, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("po", po);
		
		return new ModelAndView(EDIT_ROLE,mode);
		
	}
	
	@RequestMapping("/sys/roleManage/updateRole.htm")
	public void updateDept(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("role")TRoleInfo role) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		rService.updateRoleInfo(role, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "修改成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
		
	}
	
	
	@RequestMapping("/sys/roleManage/delete.htm")
	public void delete(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("role")TRoleInfo role) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		rService.removeRoleInfo(role, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "删除成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	@RequestMapping("/sys/roleManage/roleRes.htm")
	public ModelAndView roleRes(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("role")TRoleInfo role){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TSysRes> resList = rService.getRoleRes(role,u);
		Set<String> resSet = new HashSet<String>();
		if(resList!=null&&resList.size()>0){
			for(TSysRes res:resList){
				resSet.add(String.valueOf(res.getId()));
			}
		}
		
		request.setAttribute("roleres", resSet);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("role", role);
		
		return new ModelAndView(ROLE_RES,mode);
		
	}
	
	@RequestMapping("/sys/roleManage/saveRoleRes.htm")
	public void saveRoleRes(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("role")TRoleInfo role,@RequestParam("resIds")String roleIds) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		rService.saveRoleRes(role.getId(), roleIds, u);
		
		ServletContext servletContext = request.getSession().getServletContext();
		SpringSecurityManager securityManager = (SpringSecurityManager) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("securityManager");
		Map<String, List<String>> urlAuthoritiesMList = securityManager.loadUrlAuthorities();
		Map<String, List<String>> tagAuthoritiesMList = securityManager.loadTagAuthorities();
		
		Map<String,String> urlAuthorities = securityManager.buildMapCommaString(urlAuthoritiesMList);
		Map<String,String> tagAuthorities = securityManager.buildMapCommaString(tagAuthoritiesMList);
		
		servletContext.setAttribute("urlAuthoritiesMList", urlAuthoritiesMList);
		servletContext.setAttribute("tagAuthoritiesMList", tagAuthoritiesMList);
		servletContext.setAttribute("urlAuthorities", urlAuthorities);
		servletContext.setAttribute("tagAuthorities", tagAuthorities);
		
		
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "保存成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
}
