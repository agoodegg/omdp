package com.omdp.webapp.sys.index.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.ibm.icu.text.SimpleDateFormat;
import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.index.service.IndexService;


@Controller
@Scope("prototype")
public class IndexAction extends AbstractController {

	private final static String HOME = "home";
	private final static String INDEX = "index";
	private final static String NAV_MENU = "nav_menu";
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping("/index.htm")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		String[] dayOfWeeks = new String[]{"日","一","二","三","四","五","六"};
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String date = sdf.format(new Date());
		
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("welcome_msg", "欢迎您,"+u.getUserName()+"! 今天是星期"+dayOfWeeks[dayOfWeek-1]+" "+date);
		return new ModelAndView(INDEX, mode);
	}
	
	
	@RequestMapping("/home.htm")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DAY_OF_YEAR, -1);
		int year = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH)+1;
		
		DecimalFormat nf = new DecimalFormat("00");
		List<String> yearList = new ArrayList<String>();
		yearList.add(String.valueOf(year-1));
		yearList.add(String.valueOf(year));
		String month = nf.format(m);
		
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		String today = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, -7);
		String lastWeekDay = sdf.format(cal.getTime());
		
		cal.add(Calendar.DAY_OF_YEAR, (7-30-1));
		String lastMonthDay = sdf.format(cal.getTime());
		
		//查询未签收送货单
		Long deliverNum = indexService.queryTotalDeliverUnDone(u);
		
		Long todayOrderNum = indexService.queryTodayOrderNum(u);
		
		Long currentWeekOrderNum = indexService.queryWeeklyOrderNum(u);
		
		Long unDoneOrderNum = indexService.queryUnDoneOrderNum(u);
		
		Long unDoneOTOrderNum = indexService.queryUnDoneOTOrderNum(u);
		
		Long unTradPayOrderNum = indexService.queryUnTradPayOrderNum(u);
		
		Long unCheckPayOrderNum = indexService.queryUnCheckPayOrderNum(u);
		
		Long delayTradOrderNum = indexService.queryDelayTradOrderNum(u);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("deliverNum", deliverNum);
		mode.put("currentWeekOrderNum", currentWeekOrderNum);
		mode.put("todayOrderNum", todayOrderNum);
		mode.put("unDoneOrderNum", unDoneOrderNum);
		mode.put("unDoneOTOrderNum", unDoneOTOrderNum);
		mode.put("unTradPayOrderNum", unTradPayOrderNum);
		mode.put("unCheckPayOrderNum", unCheckPayOrderNum);
		mode.put("delayTradOrderNum", delayTradOrderNum);
		
		mode.put("today", today);
		mode.put("lastWeekDay", lastWeekDay);
		mode.put("lastMonthDay", lastMonthDay);
		
		StringBuffer y = new StringBuffer();
		y.append("[");
		for(String s:yearList){
			y.append("['").append(s).append("','").append(s).append("']").append(",");
		}
		
		mode.put("yearList", (y.substring(0,y.length()-1)+"]"));
		mode.put("month", month);
		mode.put("year", String.valueOf(year));
		
		return new ModelAndView(HOME, mode);
	}
	

	@RequestMapping("/nav_menu.htm")
	public ModelAndView navMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		return new ModelAndView(NAV_MENU, mode);
	}
	
}
