package com.omdp.webapp.sys.resource.action;

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
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.resource.service.ResourceService;


@Controller
@Scope("prototype")
public class ResourceAction extends AbstractController {

	private final static String QUERY_ALL_RES = "sys/res/reslist";
	
	@Autowired
	private ResourceService rService;
	
	@RequestMapping("/sys/sysres/toResources.htm")
	public ModelAndView toResources(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("res")TSysRes res,@ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TSysRes> resList = rService.querySysRes(res, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("resList", resList);
		mode.put("page", page);
		
		return new ModelAndView(QUERY_ALL_RES,mode);
	}
}
