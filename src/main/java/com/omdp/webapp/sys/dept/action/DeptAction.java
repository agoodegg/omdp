package com.omdp.webapp.sys.dept.action;

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
import org.springframework.web.servlet.ModelAndView;

import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TDeptInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.dept.service.DeptService;



@Controller
@Scope("prototype")
public class DeptAction extends AbstractController {

	private final static String QUERY_DEPT = "sys/dept/deptlist";
	private final static String ADD_DEPT = "sys/dept/adddept";
	private final static String EDIT_DEPT = "sys/dept/editdept";
	
	@Autowired
	private DeptService dService;
	
	@RequestMapping("/sys/deptManage/queryDept.htm")
	public ModelAndView queryDept(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("dept")TDeptInfo dept,@ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TDeptInfo> deptList = dService.queryDeptInfoList(dept, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("deptList", deptList);
		mode.put("page", page);
		mode.put("q", dept);
		
		return new ModelAndView(QUERY_DEPT,mode);
	}
	
	
	@RequestMapping("/sys/deptManage/toAdd.htm")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TDeptInfo dept = new TDeptInfo();
		Page page = new Page();
		page.setPageSize(50);
		List<TDeptInfo> deptList = dService.queryDeptInfoList(dept, page, u);
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("deptList", deptList);
		
		return new ModelAndView(ADD_DEPT,mode);
		
	}
	
	@RequestMapping("/sys/deptManage/saveNew.htm")
	public void saveNew(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("dept")TDeptInfo dept) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		dService.addDeptInfo(dept, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "新增成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
		
	}
	
	
	@RequestMapping("/sys/deptManage/toEdit.htm")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("dept")TDeptInfo dept){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TDeptInfo  po = dService.loadDept(dept, u);
		
		TDeptInfo mockDeptVo = new TDeptInfo();
		Page page = new Page();
		page.setPageSize(50);
		List<TDeptInfo> deptList = dService.queryDeptInfoList(mockDeptVo, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("po", po);
		mode.put("deptList", deptList);
		
		return new ModelAndView(EDIT_DEPT,mode);
		
	}
	
	@RequestMapping("/sys/deptManage/updateDept.htm")
	public void updateDept(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("dept")TDeptInfo dept) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		dService.updateDeptInfo(dept, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "修改成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
		
	}
	
	
	@RequestMapping("/sys/deptManage/delete.htm")
	public void delete(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("dept")TDeptInfo dept) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		dService.removeDeptInfo(dept, u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "删除成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
}
