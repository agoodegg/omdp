package com.omdp.webapp.sys.index.action;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.bulletin.service.BulletinService;
import com.omdp.webapp.model.TBulletinInfo;
import com.omdp.webapp.model.TUser;

@Controller
@Scope("prototype")
@RequestMapping("/redirect.htm")
public class RedirectAction extends AbstractController {

	private final static String REDIRECT = "redirect";
	
	@Autowired
	private BulletinService bService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TBulletinInfo> bulletinList = bService.queryCurrentBulletins();
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("bulletinList", bulletinList);
		return new ModelAndView(REDIRECT, mode);
	}
}
