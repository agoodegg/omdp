package com.omdp.webapp.deliver.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
import com.omdp.webapp.base.common.util.JsonValueProcessorImpl;
import com.omdp.webapp.base.common.util.RMB;
import com.omdp.webapp.deliver.service.DeliverService;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TSendProduct;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.action.OrderQueryAction;
import com.omdp.webapp.order.service.OrderQueryService;
import com.omdp.webapp.sys.log.action.UserLoginLogAction;
import com.omdp.webapp.sys.log.service.UserLoginLogService;


@Controller
@Scope("prototype")
public class DeliverAction extends AbstractController {

	private final static String DELIVER_QUERY = "deliver/deliverlist";
	private final static String PRINT_SENDORDER = "deliver/print";
	private final static String VIEW_SENDORDER = "deliver/viewdeliver";
	private final static String INVALID_PRINT_SENDORDER = "deliver/invalidprint";
	
	private final static String DONE_SENDORDER = "deliver/done";
	
	@Autowired
	private DeliverService dService;
	
	@Autowired
	private OrderQueryService oService;
	
	@Autowired
	private UserLoginLogService uService;
	
	//送货单查询 
	@RequestMapping("/deliver/deliverQuery/queryAll.htm")
	public ModelAndView queryAll(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q, @ModelAttribute("page")Page page)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("p", q);
		mode.put("mode", "all");
		return new ModelAndView(DELIVER_QUERY,mode);
	}
	
	@RequestMapping("/deliver/deliverQuery/queryAllData.htm")
	public void queryAllData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q, @ModelAttribute("page")Page page) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		
		List<TSendProduct> deliverList = dService.queryDeliverInfo(q, page, u);
		
		uService.saveSysLog(u, "送货单查询", UserLoginLogAction.SEND_ORDER_OPR_LOG);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(deliverList, cfg);
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
		
	}
	
	
	//待处理送货单查询 
	@RequestMapping("/deliver/deliverQuery/queryTodo.htm")
	public ModelAndView queryTodo(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q, @ModelAttribute("page")Page page)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("mode", "todo");
		return new ModelAndView(DELIVER_QUERY,mode);
	}
	
	//待处理送货单查询 
	@RequestMapping("/deliver/deliverQuery/queryTodoData.htm")
	public void queryTodoData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q, @ModelAttribute("page")Page page) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		q.setDoneFlag("0");
		
		List<TSendProduct> deliverList = dService.queryDeliverInfo(q, page, u);
		
		uService.saveSysLog(u, "待处理送货单查询", UserLoginLogAction.SEND_ORDER_OPR_LOG);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(deliverList, cfg);
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}
	
	
	//已完送货单查询 
	@RequestMapping("/deliver/deliverQuery/queryDone.htm")
	public ModelAndView queryDone(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q, @ModelAttribute("page")Page page)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("mode", "done");
		return new ModelAndView(DELIVER_QUERY,mode);
	}
	
	//已完送货单查询 
	@RequestMapping("/deliver/deliverQuery/queryDoneData.htm")
	public void queryDoneData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q, @ModelAttribute("page")Page page) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		q.setDoneFlag("1");
		
		List<TSendProduct> deliverList = dService.queryDeliverInfo(q, page, u);
		
		uService.saveSysLog(u, "已完送货单查询", UserLoginLogAction.SEND_ORDER_OPR_LOG);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(deliverList, cfg);
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}
	
	
	@RequestMapping("/deliver/deliverQuery/printSendOrder.htm")
	public ModelAndView printSendOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TOrderInfo order = oService.loadOrderByNum(q.getOrdernum(),u);
		
		List<TOrderDetail> details = oService.loadOrderDetails(order,u);
		
		TSendProduct deliver = dService.loadSendProductById(q.getId(),u);
		
		if(order.getPrice()==null){
			order.setPrice(0.0D);
		}
		
		uService.saveSysLog(u, "打印送货单,订单号【"+order.getOrdernum()+"】", UserLoginLogAction.SEND_ORDER_OPR_LOG);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("q", q);
		mode.put("order", order);
		mode.put("details", details);
		mode.put("deliver", deliver);
		mode.put("now", new Date());
		mode.put("RMB", RMB.toBigAmt(order.getPrice()));
		
		
		if(order.getPrice()==0.0D){
			return new ModelAndView(INVALID_PRINT_SENDORDER,mode);
		}
		else{
			return new ModelAndView(PRINT_SENDORDER,mode);
		}
		
	}
	
	@RequestMapping("/deliver/deliverQuery/viewdeliver.htm")
	public ModelAndView viewdeliver(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TOrderInfo order = oService.loadOrderByNum(q.getOrdernum(),u);
		
		List<TOrderDetail> details = oService.loadOrderDetails(order,u);
		
		TSendProduct deliver = dService.loadSendProductById(q.getId(),u);
		
		if(order.getPrice()==null){
			order.setPrice(0.0D);
		}
		
		uService.saveSysLog(u, "查看送货单,订单号【"+order.getOrdernum()+"】", UserLoginLogAction.SEND_ORDER_OPR_LOG);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("q", q);
		mode.put("order", order);
		mode.put("details", details);
		mode.put("deliver", deliver);
		mode.put("now", new Date());
		mode.put("RMB", RMB.toBigAmt(order.getPrice()));
		return new ModelAndView(VIEW_SENDORDER,mode);
	}
	
	@RequestMapping("/deliver/deliverQuery/doneSendOrder.htm")
	public ModelAndView doneSendOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TOrderInfo order = oService.loadOrderByNum(q.getOrdernum(),u);
		
		List<TOrderDetail> details = oService.loadOrderDetails(order,u);
		
		TSendProduct deliver = dService.loadSendProductById(q.getId(),u);
		
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("q", q);
		mode.put("order", order);
		mode.put("details", details);
		mode.put("deliver", deliver);
		mode.put("now", new Date());
		
		return new ModelAndView(DONE_SENDORDER,mode);
	}
	
	
	@RequestMapping("/deliver/deliverQuery/doneDeliver.htm")
	public void doneDeliver(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TSendProduct q) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		
		
		if(q==null&&q.getId()==null||q.getSendOpr()==null||q.getSendOpr().trim().length()==0){
			map.put("success", false);
			map.put("msg", "参数异常！");
			ResponseUtils.writeJSONString(response,map);
			return;
		}
		
		
		dService.doneSendOrder(q,u);
		
		uService.saveSysLog(u, "完成送货单,ID["+q.getId()+"]", UserLoginLogAction.SEND_ORDER_OPR_LOG);
		map.put("success", true);
		map.put("msg", "操作成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
}
