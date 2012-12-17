package com.omdp.webapp.customer.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.customer.service.CustomerManageService;
import com.omdp.webapp.employee.service.EmployeeService;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.log.action.UserLoginLogAction;
import com.omdp.webapp.sys.log.service.UserLoginLogService;


@Controller
@Scope("prototype")
public class CustomerManageAction extends AbstractController {

	private final static String QUERY_CUST = "cust/mycust";
	private final static String ADD_CUST = "cust/addcust";
	private final static String NEW_CUST = "cust/newcust";
	private final static String QUERY_OLD_CUST = "cust/oldcust";
	
	private final static String VIEW_CUST = "cust/viewcust";
	private final static String EDIT_CUST = "cust/editcust";
	
	private final static String ACCESS_DENY = "exceptions/403";
	
	@Autowired
	private CustomerManageService cService;
	
	@Autowired
	private EmployeeService eService;
	
	@Autowired
	private UserLoginLogService uService;
	
	
	@RequestMapping("/customer/customerManage/queryCustomer.htm")
	public ModelAndView queryCustomer(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cust")TCustInfo cust, @ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String,List<String>> tagMap = (Map<String,List<String>>)request.getSession().getServletContext().getAttribute("tagAuthoritiesMList");
		List<String> roleList = tagMap.get("ALL_CUST_VISUAL");
		
		boolean hasAllCustVisual = false;
		GrantedAuthority[] auths = u.getAuthorities();
		for(GrantedAuthority a:auths){
			if(roleList.contains(a.getAuthority())){
				hasAllCustVisual = true;
				break;
			}
		}
		
		
		List<TCustInfo> custList = cService.queryCustInfo(cust, hasAllCustVisual, page, u);
		
		if(hasAllCustVisual){
			uService.saveSysLog(u, "查询所有客户", UserLoginLogAction.CUST_OPR);
		}
		else{
			uService.saveSysLog(u, "查询自己的客户", UserLoginLogAction.CUST_OPR);
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("custList", custList);
		return new ModelAndView(QUERY_CUST,mode);
	}
	
	
	
	@RequestMapping("/customer/customerManage/toAddCust.htm")
	public ModelAndView toAddCust(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		
		Map<String,List<String>> tagMap = (Map<String,List<String>>)request.getSession().getServletContext().getAttribute("tagAuthoritiesMList");
		List<String> roleList = tagMap.get("ALL_CUST_VISUAL");
		
		boolean hasAllCustVisual = false;
		GrantedAuthority[] auths = u.getAuthorities();
		for(GrantedAuthority a:auths){
			if(roleList.contains(a.getAuthority())){
				hasAllCustVisual = true;
				break;
			}
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("hasAllCustVisual",hasAllCustVisual);
		return new ModelAndView(ADD_CUST,mode);
	}
	
	@RequestMapping("/customer/customerManage/toNewCust.htm")
	public ModelAndView toNewCust(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String,List<String>> tagMap = (Map<String,List<String>>)request.getSession().getServletContext().getAttribute("tagAuthoritiesMList");
		List<String> roleList = tagMap.get("ALL_CUST_VISUAL");
		
		boolean hasAllCustVisual = false;
		GrantedAuthority[] auths = u.getAuthorities();
		for(GrantedAuthority a:auths){
			if(roleList.contains(a.getAuthority())){
				hasAllCustVisual = true;
				break;
			}
		}
		
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("hasAllCustVisual",hasAllCustVisual);
		return new ModelAndView(NEW_CUST,mode);
	}
	
	@RequestMapping("/customer/customerManage/saveCust.htm")
	public void saveCust(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cust")TCustInfo cust) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		if(cust==null||cust.getShortName()==null||cust.getShortName().trim().length()==0){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", "客户名称不能为空！");
			ResponseUtils.writeJSONString(response,map);
			return;
		}
		
		if(cService.existCustName(cust)){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", "相同的客户名称似乎已经存在！");
			ResponseUtils.writeJSONString(response,map);
			return;
		}
		
		cService.saveNewCust(cust,u);
		
		
		JSONObject jsonObj = JSONObject.fromObject(cust);
		
		
		uService.saveSysLog(u, "新增客户", UserLoginLogAction.CUST_OPR);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "新增成功");
		map.put("cust", jsonObj);
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	
	@RequestMapping("/customer/customerManage/editCust.htm")
	public void editCust(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cust")TCustInfo cust) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		cService.updateCust(cust,u);
		
		JSONObject jsonObj = JSONObject.fromObject(cust);
		
		uService.saveSysLog(u, "编辑客户,客户编号【"+cust.getCustId()+"】", UserLoginLogAction.CUST_OPR);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "修改成功");
		map.put("cust", jsonObj);
		ResponseUtils.writeJSONString(response,map);
	}
	
	
	@RequestMapping("/customer/customerManage/toEdit.htm")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cust")TCustInfo cust){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String,List<String>> tagMap = (Map<String,List<String>>)request.getSession().getServletContext().getAttribute("tagAuthoritiesMList");
		List<String> roleList = tagMap.get("ALL_CUST_VISUAL");
		
		boolean hasAllCustVisual = false;
		GrantedAuthority[] auths = u.getAuthorities();
		for(GrantedAuthority a:auths){
			if(roleList.contains(a.getAuthority())){
				hasAllCustVisual = true;
				break;
			}
		}
		
		TCustInfo po = cService.loadCustInfoById(cust.getId(),u);
		if(!hasAllCustVisual){
			if(!(String.valueOf(u.getId()).equals(po.getUserId()))){
				return new ModelAndView(ACCESS_DENY,null);
			}
		}
		
		if(po.getUserId()!=null){
			try{
				TUser yewuUser =eService.loadUserByUserId(po.getUserId(),u);
				
				if(yewuUser!=null){
					po.setUserName(yewuUser.getUserName());
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("hasAllCustVisual",hasAllCustVisual);
		mode.put("cust", po);
		return new ModelAndView(EDIT_CUST,mode);
	}
	
	@RequestMapping("/customer/customerManage/viewcust.htm")
	public ModelAndView viewcust(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cust")TCustInfo cust){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String,List<String>> tagMap = (Map<String,List<String>>)request.getSession().getServletContext().getAttribute("tagAuthoritiesMList");
		List<String> roleList = tagMap.get("ALL_CUST_VISUAL");
		
		boolean hasAllCustVisual = false;
		GrantedAuthority[] auths = u.getAuthorities();
		for(GrantedAuthority a:auths){
			if(roleList.contains(a.getAuthority())){
				hasAllCustVisual = true;
				break;
			}
		}
		
		TCustInfo po = cService.loadCustInfoById(cust.getId(),u);
		if(!hasAllCustVisual){
			if(!(String.valueOf(u.getIdNo()).equals(po.getUserId()))){
				return new ModelAndView(ACCESS_DENY,null);
			}
		}
		
		if(po.getUserId()!=null){
			try{
				TUser yewuUser =eService.loadUserByUserId(po.getUserId(), u);
				
				if(yewuUser!=null){
					po.setUserName(yewuUser.getUserName());
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		uService.saveSysLog(u, "查询客户信息,客户ID【"+cust.getCustId()+"】", UserLoginLogAction.CUST_OPR);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("hasAllCustVisual",hasAllCustVisual);
		mode.put("cust", po);
		return new ModelAndView(VIEW_CUST,mode);
	}
	
	@RequestMapping("/customer/customerManage/viewcustByCustId.htm")
	public ModelAndView viewcustByCustId(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cust")TCustInfo cust){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
//		Map<String,List<String>> tagMap = (Map<String,List<String>>)request.getSession().getServletContext().getAttribute("tagAuthoritiesMList");
//		List<String> roleList = tagMap.get("ALL_CUST_VISUAL");
//		
//		boolean hasAllCustVisual = false;
//		GrantedAuthority[] auths = u.getAuthorities();
//		for(GrantedAuthority a:auths){
//			if(roleList.contains(a.getAuthority())){
//				hasAllCustVisual = true;
//				break;
//			}
//		}
		
		TCustInfo po = cService.loadCustInfoByCustId(cust.getCustId(),u);
		
		
//		if(!hasAllCustVisual){
//			if(!(String.valueOf(u.getId()).equals(po.getUserId()))){
//				return new ModelAndView(ACCESS_DENY,null);
//			}
//		}
		
		if(po.getUserId()!=null){
			try{
				TUser voUser = new TUser();
				voUser.setId(Integer.parseInt(po.getUserId()));
				TUser yewuUser =eService.loadUser(voUser, u);
				
				po.setUserName(yewuUser.getUserName());
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		uService.saveSysLog(u, "查询客户信息,客户ID【"+cust.getCustId()+"】", UserLoginLogAction.CUST_OPR);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("cust", po);
		return new ModelAndView(VIEW_CUST,mode);
	}
	
	
	@RequestMapping("/customer/customerManage/markDel.htm")
	public void markDel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cust")TCustInfo cust) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String,List<String>> tagMap = (Map<String,List<String>>)request.getSession().getServletContext().getAttribute("tagAuthoritiesMList");
		List<String> roleList = tagMap.get("ALL_CUST_VISUAL");
		
		boolean hasAllCustVisual = false;
		GrantedAuthority[] auths = u.getAuthorities();
		for(GrantedAuthority a:auths){
			if(roleList.contains(a.getAuthority())){
				hasAllCustVisual = true;
				break;
			}
		}
		
		TCustInfo po = cService.loadCustInfoById(cust.getId(),u);
		if(!hasAllCustVisual){
			if(!(String.valueOf(u.getId()).equals(po.getUserId()))){
				Map<String, Object> map = new HashMap<String, Object>(); 
				map.put("success", false);
				map.put("msg", "删除失败！您没有删除该用户的权限");
				ResponseUtils.writeJSONString(response,map);
			}
		}
		
		cService.markDel(po,u);
		
		uService.saveSysLog(u, "删除客户信息,客户名【"+cust.getShortName()+"】,客户编码【"+cust.getCustId()+"】", UserLoginLogAction.CUST_OPR);
		
		JSONObject jsonObj = JSONObject.fromObject(cust);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "删除成功");
		ResponseUtils.writeJSONString(response,map);
	}
	
}
