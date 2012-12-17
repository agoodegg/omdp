package com.omdp.webapp.bulletin.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.bulletin.service.BulletinService;
import com.omdp.webapp.model.TBulletinInfo;
import com.omdp.webapp.model.TUser;

@Controller
@Scope("prototype")
public class BulletinAction extends AbstractController {

	private final static String QUERY_BULLET = "bulletin/bulletinlist";
	private final static String NEW_BULLETIN = "bulletin/newbulletin";
	private final static String SHOW_BULLETIN = "bulletin/showbulletin";
	
	@Autowired
	private BulletinService bService;
	
	
	@RequestMapping("/bulletin/bulletinManage/queryBulletin.htm")
	public ModelAndView queryBulletin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TBulletinInfo info,@ModelAttribute("page")Page page){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TBulletinInfo> infoList = bService.queryBulletInfoList(info, page, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("infoList", infoList);
		mode.put("page", page);
		
		return new ModelAndView(QUERY_BULLET,mode);
	}
	
	@RequestMapping("/bulletin/bulletinManage/toAddBulletin.htm")
	public String toAddBulletin(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		return NEW_BULLETIN;
	}
	
	
	@RequestMapping("/bulletin/bulletinManage/saveNewBulletin.htm")
	public void saveNewBulletin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TBulletinInfo info) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		//截止时间
		try{
			if(StringUtils.trimToNull(info.getEndTimeStr())!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				info.setEndTime(sdf.parse(StringUtils.trimToEmpty(info.getEndTimeStr())));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		bService.saveNewOrder(info,u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "新增成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	@RequestMapping("/bulletin/bulletinManage/showBulletin.htm")
	public ModelAndView showBulletin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TBulletinInfo info){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TBulletinInfo  bulletin = bService.loadBulletin(info, u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("bulletin", bulletin);
		
		System.out.println("bulletin:"+bulletin.getContent());
		return new ModelAndView(SHOW_BULLETIN, mode);
	}
	
	
	@RequestMapping("/bulletin/bulletinManage/markDel.htm")
	public void markDel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TBulletinInfo info) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		bService.removeBulletinByMark(info,u);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "删除成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
}
