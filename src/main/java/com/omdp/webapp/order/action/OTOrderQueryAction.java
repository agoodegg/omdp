package com.omdp.webapp.order.action;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
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

import com.ibm.icu.text.SimpleDateFormat;
import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.util.JsonValueProcessorImpl;
import com.omdp.webapp.base.taglib.OrderStatusTrans;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.service.NewOrderService;
import com.omdp.webapp.order.service.OrderQueryService;
import com.omdp.webapp.sys.log.action.UserLoginLogAction;
import com.omdp.webapp.sys.log.service.UserLoginLogService;


@Controller
@Scope("prototype")
public class OTOrderQueryAction extends AbstractController {

	private final static String ORDER_QUERY_ALL = "otorder/queryall";
	private final static String PRINT_ORDER = "otorder/printorder";
	private final static String VIEW_ORDER = "otorder/vieworder";
	
	private final static String EDIT_ORDER = "otorder/editorder";
	
	//00000   从左至右 依次代表是否打印、是否完工、是否作废、是否付款(结算)、是否已核销
	public static final int PRINT=16;
	public static final int DONE =8;
	public static final int TRASH=4;
	public static final int PAY  =2;
	public static final int CHECKED =1;
	
	public static final String NEW_STATUS = "00000";
	
	
	
	/**
	 * matrix for the opr
	 * 
	 * ext1字段用于判断是否月结
	 * 
	 * 00000                 打单    修改    完工    作废    /     /  
	 * 00001                 打单     /      /      /      /     /    异常工单
	 * 00010                 打单     /      /      /      /     核销  异常工单
	 * 00011                 打单     /      /      /      /     /    异常工单
	 * 00100                  /      /      /      /      /     /
	 * 00101                  /      /      /      /      /     /    异常工单
	 * 00110                  /      /      /      /      /     /    异常工单
	 * 00111                  /      /      /      /      /     /    异常工单
	 * 01000                  打单   修改     /     作废    结算   核销
	 * 01001                  打单     /      /      /      /     /
	 * 01010                  打单     /      /      /      /     核销
	 * 01011                  打单     /      /      /      /     /
	 * 01100                  /      /      /      /      /     /
	 * 01101                  /      /      /      /      /     /
	 * 01110                  /      /      /      /      /     /
	 * 01111                  /      /      /      /      /     /
	 * 10000                 打单    修改    完工    作废    /     /  
	 * 10001                 打单     /      /      /      /     /    异常工单
	 * 10010                 打单     /      /      /      /     核销  异常工单
	 * 10011                 打单     /      /      /      /     /    异常工单
	 * 10100                  /      /      /      /      /     /
	 * 10101                  /      /      /      /      /     /    异常工单
	 * 10110                  /      /      /      /      /     /    异常工单
	 * 10111                  /      /      /      /      /     /    异常工单
	 * 11000                  打单   修改     /     作废    结算   核销
	 * 11001                  打单     /      /      /      /     /
	 * 11010                  打单     /      /      /      /     核销
	 * 11011                  打单     /      /      /      /     /
	 * 11100                  /      /      /      /      /     /
	 * 11101                  /      /      /      /      /     /
	 * 11110                  /      /      /      /      /     /
	 * 11111                  /      /      /      /      /     /
	 */
	
	@Autowired
	private OrderQueryService oService;
	
	@Autowired
	private NewOrderService nService;
	
	@Autowired
	private UserLoginLogService uService;
	
	
	//工单列表查询 
	@RequestMapping("/order/ot-orderQuery/queryAll.htm")
	public ModelAndView queryAll(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		
		if(q==null){
			q = new TOrderInfo();
		}
		q.setDispatchOther("1");
		
		List<TOrderInfo> orderList = oService.queryOrderInfo(q, page, u);
		
		uService.saveSysLog(u, "订单查询", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("orderList", orderList);
		mode.put("page", page);
		mode.put("p", q);
		mode.put("mode", 0);
		return new ModelAndView(ORDER_QUERY_ALL,mode);
	}
	
	
	@RequestMapping("/order/ot-orderQuery/queryAllData.htm")
	public void queryAllData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		
		if(q==null){
			q = new TOrderInfo();
		}
		q.setDispatchOther("1");
		
		List<TOrderInfo> orderList = oService.queryOrderInfo(q, page, u);
		
		if(orderList!=null){
			for(TOrderInfo o:orderList){
				o.setOrderStatusCn(OrderStatusTrans.getStatusLiteral(o.getOrderStatus()));
			}
		}
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(orderList, cfg);
		
		NumberFormat currency   =   NumberFormat.getCurrencyInstance();
	    currency.setMinimumFractionDigits(2);
	    currency.setMaximumFractionDigits(2);
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("totalAmount", currency.format(page.getTotalAmount()));
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}
	
	@RequestMapping("/order/ot-newOrder/toEdit.htm")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TOrderInfo order = oService.loadOrderById(q.getId(),u);
		
		List<TOrderDetail> details = oService.loadOrderDetails(order,u);
		
		List<TStakeHolder> typeList = oService.queryStakeHolders(order,TStakeHolder.ORDER_TYPE);
		List<TStakeHolder> bindList = oService.queryStakeHolders(order,TStakeHolder.ORDER_BIND);
		
		StringBuffer typeNameBuf = new StringBuffer();
		StringBuffer bindNameBuf = new StringBuffer();
		
		if(typeList!=null){
			for(TStakeHolder holder:typeList){
				
				typeNameBuf.append(holder.getUserName()).append(";");
			}
		}
		
		if(bindList!=null){
			for(TStakeHolder holder:bindList){
				bindNameBuf.append(holder.getUserName()).append(";");
			}
		}
		
		String deliverTimeHours = "";
		Calendar cal = Calendar.getInstance();
		if(order.getDeliverTime()!=null){
			cal.setTime(order.getDeliverTime());
			
			int hours = cal.get(Calendar.HOUR_OF_DAY);
			
			if(hours!=0){
				deliverTimeHours = String.valueOf(hours);
			}
		}
		
		List<Object[]> detailsStaticsData = oService.getDetailStaticData(order,u);
		Map<String,Integer> numMap = new HashMap<String,Integer>();
		Map<String,Double> totalMap = new HashMap<String,Double>();
		
		if(detailsStaticsData!=null){
			for(Object[] rowtemp:detailsStaticsData){
				String type = (String)rowtemp[0];
				
				Integer num = ((Long)rowtemp[1]).intValue();
				
				Double total = ((Double)rowtemp[2]).doubleValue();
				
				numMap.put(type, num);
				totalMap.put(type, total);
			}
		}
		
		request.setAttribute("numMap", numMap);
		request.setAttribute("totalMap", totalMap);
		
		if(order.getPrice()==null){
			order.setPrice(0.0D);
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("sec", order.getOrdernum());
		mode.put("q", q);
		mode.put("order", order);
		mode.put("details", details);
		mode.put("now", new Date());
		mode.put("typeNames", typeNameBuf.toString());
		mode.put("bindNames", bindNameBuf.toString());
		mode.put("deliverTimeHours", deliverTimeHours);
		return new ModelAndView(EDIT_ORDER,mode);
	}
	
	
	@RequestMapping("/order/ot-newOrder/vieworder.htm")
	public ModelAndView vieworder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TOrderInfo order = oService.loadOrderById(q.getId(),u);
		
		List<TOrderDetail> details = oService.loadOrderDetails(order,u);
		
		List<TStakeHolder> typeList = oService.queryStakeHolders(order,TStakeHolder.ORDER_TYPE);
		List<TStakeHolder> bindList = oService.queryStakeHolders(order,TStakeHolder.ORDER_BIND);
		
		StringBuffer typeNameBuf = new StringBuffer();
		StringBuffer bindNameBuf = new StringBuffer();
		
		if(typeList!=null){
			for(TStakeHolder holder:typeList){
				
				typeNameBuf.append(holder.getUserName()).append(";");
			}
		}
		
		if(bindList!=null){
			for(TStakeHolder holder:bindList){
				bindNameBuf.append(holder.getUserName()).append(";");
			}
		}
		
		String deliverTimeHours = "";
		Calendar cal = Calendar.getInstance();
		if(order.getDeliverTime()!=null){
			cal.setTime(order.getDeliverTime());
			
			int hours = cal.get(Calendar.HOUR_OF_DAY);
			
			if(hours!=0){
				deliverTimeHours = String.valueOf(hours);
			}
		}
		
		List<Object[]> detailsStaticsData = oService.getDetailStaticData(order,u);
		Map<String,Integer> numMap = new HashMap<String,Integer>();
		Map<String,Double> totalMap = new HashMap<String,Double>();
		
		if(detailsStaticsData!=null){
			for(Object[] rowtemp:detailsStaticsData){
				String type = (String)rowtemp[0];
				
				Integer num = ((Long)rowtemp[1]).intValue();
				
				Double total = ((Double)rowtemp[2]).doubleValue();
				
				numMap.put(type, num);
				totalMap.put(type, total);
			}
		}
		
		request.setAttribute("numMap", numMap);
		request.setAttribute("totalMap", totalMap);
		
		if(order.getPrice()==null){
			order.setPrice(0.0D);
		}
		
		uService.saveSysLog(u, "查看订单明细,单号【"+order.getOrdernum()+"】", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("sec", order.getOrdernum());
		mode.put("q", q);
		mode.put("order", order);
		mode.put("details", details);
		mode.put("now", new Date());
		mode.put("typeNames", typeNameBuf.toString());
		mode.put("bindNames", bindNameBuf.toString());
		mode.put("deliverTimeHours", deliverTimeHours);
		return new ModelAndView(VIEW_ORDER,mode);
	}
	
	
	@RequestMapping("/order/ot-orderQuery/printOrder.htm")
	public ModelAndView printOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TOrderInfo order = oService.loadOrderById(q.getId(),u);
		
		List<TOrderDetail> details = oService.loadOrderDetails(order,u);
		
		Integer status = Integer.parseInt(order.getOrderStatus(), 2);
		Integer result = status|OrderQueryAction.PRINT;
		String str = Integer.toBinaryString(result);
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<5-str.length();i++){
			buf.append("0");
		}
		order.setOrderStatus(buf.toString()+str);
		
		
		oService.updateOrderInfoStatus(order,u);
		
		uService.saveSysLog(u, "打印订单,单号【"+order.getOrdernum()+"】", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("q", q);
		mode.put("order", order);
		mode.put("details", details);
		mode.put("now", new Date());
		return new ModelAndView(PRINT_ORDER,mode);
	}
	
	@RequestMapping("/order/ot-orderQuery/doneOrder.htm")
	public void doneOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TOrderInfo order = oService.loadOrderById(q.getId(),u);
		
		if(order.getPrice()==null||order.getPrice()==0.0D){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", "外派单金额为0，请确认工单信息及细项填写是否正确！");
			ResponseUtils.writeJSONString(response,map);
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String prefix = "S"+sdf.format(new Date());
		Integer ind = nService.updateSequence(prefix,"send_seq");
		DecimalFormat dFormat = new DecimalFormat("000");
		String sendNum = prefix+dFormat.format(ind);
		
		oService.doneOrder(order,sendNum,u);
		
		uService.saveSysLog(u, "订单完工,单号【"+order.getOrdernum()+"】", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "工单完成，并成功生成送货单(可到送货单列表打印)！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	@RequestMapping("/order/ot-orderQuery/trashOrder.htm")
	public void trashOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		if(q.getId() == null || q.getId() == 0){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", "参数异常，无法获取工单数据！");
			ResponseUtils.writeJSONString(response,map);
			return;
		}
		
		if(q.getTrashMemo() == null || "".equals(q.getTrashMemo().trim()) || q.getTrashMemo().trim().length()>100){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", "作废原因不能为空且不能超过100个汉字！");
			ResponseUtils.writeJSONString(response,map);
			return;
		}
		
		TOrderInfo order = oService.loadOrderById(q.getId(),u);
		
		if(order == null){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", "参数异常，无法获取工单数据！");
			ResponseUtils.writeJSONString(response,map);
			return;
		}
		
		order.setTrashMemo(q.getTrashMemo().trim());
		order.setTrashOprName(u.getUserName());
		oService.trashOrder(order,u);
		
		uService.saveSysLog(u, "订单作废,单号【"+order.getOrdernum()+"】", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "工单已作废！");
		ResponseUtils.writeJSONString(response,map);
		return;
		
	}
	
	@RequestMapping("/order/ot-orderQuery/queryUnfinish.htm")
	public ModelAndView queryUnfinish(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		if(q==null){
			q = new TOrderInfo();
		}
		q.setDispatchOther("1");
		
		
		uService.saveSysLog(u, "查询未完成订单", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("page", page);
		mode.put("p", q);
		mode.put("mode", 1);
		return new ModelAndView(ORDER_QUERY_ALL,mode);
	}
	
	
	@RequestMapping("/order/ot-orderQuery/queryUnfinishData.htm")
	public void queryUnfinishData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		if(q==null){
			q = new TOrderInfo();
		}
		q.setDispatchOther("1");
		
		List<TOrderInfo> orderList = oService.queryUnfinishOrderInfo(q, page, u);
		
		if(orderList!=null){
			for(TOrderInfo o:orderList){
				o.setOrderStatusCn(OrderStatusTrans.getStatusLiteral(o.getOrderStatus()));
			}
		}
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(orderList, cfg);
		
		NumberFormat currency   =   NumberFormat.getCurrencyInstance();
	    currency.setMinimumFractionDigits(2);
	    currency.setMaximumFractionDigits(2);
	    
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("totalAmount", currency.format(page.getTotalAmount()));
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}
	
	@RequestMapping("/order/ot-orderQuery/queryDone.htm")
	public ModelAndView queryDone(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page)
	{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		if(q==null){
			q = new TOrderInfo();
		}
		q.setDispatchOther("1");
		
		List<TOrderInfo> orderList = oService.queryDoneOrderInfo(q, page, u);
		
		uService.saveSysLog(u, "查询已完成订单", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("orderList", orderList);
		mode.put("page", page);
		mode.put("p", q);
		mode.put("mode", 2);
		return new ModelAndView(ORDER_QUERY_ALL,mode);
	}
	
	@RequestMapping("/order/ot-orderQuery/queryDoneData.htm")
	public void queryDoneData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page) throws IOException
	{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		if(q==null){
			q = new TOrderInfo();
		}
		q.setDispatchOther("1");
		
		List<TOrderInfo> orderList = oService.queryDoneOrderInfo(q, page, u);
		
		if(orderList!=null){
			for(TOrderInfo o:orderList){
				o.setOrderStatusCn(OrderStatusTrans.getStatusLiteral(o.getOrderStatus()));
			}
		}
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(orderList, cfg);
		
		NumberFormat currency   =   NumberFormat.getCurrencyInstance();
	    currency.setMinimumFractionDigits(2);
	    currency.setMaximumFractionDigits(2);
	    
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("totalAmount", currency.format(page.getTotalAmount()));
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}
	
	@RequestMapping("/order/ot-orderQuery/queryTrash.htm")
	public ModelAndView queryTrash(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page)
	{
		request.setAttribute("action", "queryTrash");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		if(q==null){
			q = new TOrderInfo();
		}
		q.setDispatchOther("1");
		
		List<TOrderInfo> orderList = oService.queryTrashOrderInfo(q, page, u);
		
		uService.saveSysLog(u, "查询已作废订单", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("orderList", orderList);
		mode.put("page", page);
		mode.put("p", q);
		mode.put("mode", 3);
		return new ModelAndView(ORDER_QUERY_ALL,mode);
	}
	
	
	@RequestMapping("/order/ot-orderQuery/queryTrashData.htm")
	public void queryTrashData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page) throws IOException
	{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);  //每页大小15条
		if(q==null){
			q = new TOrderInfo();
		}
		q.setDispatchOther("1");
		
		List<TOrderInfo> orderList = oService.queryTrashOrderInfo(q, page, u);
		
		if(orderList!=null){
			for(TOrderInfo o:orderList){
				o.setOrderStatusCn(OrderStatusTrans.getStatusLiteral(o.getOrderStatus()));
			}
		}
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(orderList, cfg);
		
		NumberFormat currency   =   NumberFormat.getCurrencyInstance();
	    currency.setMinimumFractionDigits(2);
	    currency.setMaximumFractionDigits(2);
	    
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("totalAmount", currency.format(page.getTotalAmount()));
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}
}
