package com.omdp.webapp.order.action;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.service.NewOrderService;
import com.omdp.webapp.sys.log.action.UserLoginLogAction;
import com.omdp.webapp.sys.log.service.UserLoginLogService;


@Controller
@Scope("prototype")
public class OTNewOrderAction extends AbstractController {

	private final static String NEW_ORDER_PAGE = "otorder/neworder";
	private final static String NEW_ORDER_DETAIL_PAGE = "otorder/neworderdetail";
	
	@Autowired
	private NewOrderService nService;
	
	@Autowired
	private UserLoginLogService uService;
	
	@RequestMapping("/order/ot-newOrder/toNewOrder.htm")
	public ModelAndView toNewOrder(HttpServletRequest request, HttpServletResponse response)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		
		//随机串
		String randStr = UUID.randomUUID().toString();
		
		
		TOrderInfo order = new TOrderInfo();
		order.setOrderTime(new Date());
		order.setDeliverTime(new Date());
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("order", order);
		mode.put("sec", randStr);
		
		return new ModelAndView(NEW_ORDER_PAGE,mode);
	}
	
	@RequestMapping("/order/ot-newOrder/toNewOrderDetail.htm")
	public ModelAndView toNewOrderDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam("sec")String sec,@RequestParam("busiType")String busiType){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Map<String,TreeMap<String,String>> sysDict = (Map<String,TreeMap<String,String>>)(request.getSession().getServletContext().getAttribute("sysDictMap"));
		Map<String,TreeMap<String,String>> busiDict = (Map<String,TreeMap<String,String>>)(request.getSession().getServletContext().getAttribute("busiDictMap"));

		List<TSysParam> fieldList = nService.loadFieldListByBusi(busiType);

		List<TOrderDetail> itemList = nService.loadOrderDetailBySec(sec,busiType, u);
		
		Double total = 0.0D;
		if(itemList!=null){
			for(TOrderDetail detail:itemList){
				total += detail.getTotal();
			}
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("sec", sec);
		mode.put("busiType", busiType);
		mode.put("fieldList", fieldList);
		mode.put("fieldListSize", fieldList.size());
		mode.put("itemList", itemList);
		mode.put("itemListSize", itemList.size());
		mode.put("total", total);
		
		return new ModelAndView(NEW_ORDER_DETAIL_PAGE,mode);
	}
	
	@RequestMapping("/order/ot-newOrder/saveOrderDetail.htm")
	public void saveOrderDetail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("orderDetail")TOrderDetail orderDetail) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		StringBuffer errMsg = new StringBuffer();
		validateOrderDetail(orderDetail,errMsg);
		if(errMsg.length()>0){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", errMsg.toString());
			ResponseUtils.writeJSONString(response,map);
			
			return;
		}
		
		Double amount = orderDetail.getAmount();
		Double price = orderDetail.getUnitPrice();
		
		if(amount==null){
			amount=0.0D;
		}
		if(price==null){
			price=0.0D;
		}
		
		Double total = amount*price;
		orderDetail.setTotal(total);
		
		nService.saveNewOrderDetail(orderDetail,u);
		
		uService.saveSysLog(u, "添加了工单明细", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "成功添加项目！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	@RequestMapping("/order/ot-newOrder/updateOrderDetail.htm")
	public void updateOrderDetail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("orderDetail")TOrderDetail orderDetail) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		StringBuffer errMsg = new StringBuffer();
		validateOrderDetail(orderDetail,errMsg);
		if(errMsg.length()>0){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", errMsg.toString());
			ResponseUtils.writeJSONString(response,map);
			
			return;
		}
		
		if(orderDetail.getId()==null){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", "参数异常!主键为空！");
			ResponseUtils.writeJSONString(response,map);
			
			return;
		}
		
		Double amount = orderDetail.getAmount();
		Double price = orderDetail.getUnitPrice();
		
		if(amount==null){
			amount=0.0D;
		}
		if(price==null){
			price=0.0D;
		}
		
		Double total = amount*price;
		orderDetail.setTotal(total);
		
		nService.updateOrderDetail(orderDetail,u);
		
		uService.saveSysLog(u, "修改了工单明细", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "修改成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	@RequestMapping("/order/ot-newOrder/delOrderDetail.htm")
	public void delOrderDetail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("orderDetail")TOrderDetail orderDetail) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		nService.delOrderDetail(orderDetail,u);
		
		uService.saveSysLog(u, "删除了工单明细", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "删除成功！");
		ResponseUtils.writeJSONString(response,map);
		
		
		return;
	}
	
	
	private void validateOrderDetail(TOrderDetail orderDetail, StringBuffer errMsg) {
		
	}

	@RequestMapping("/order/ot-newOrder/saveNewOrder.htm")
	public void saveNewOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("order")TOrderInfo order) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		StringBuffer errMsg = new StringBuffer();
		validateOrder(order,errMsg);
		if(errMsg.length()>0){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", errMsg.toString());
			ResponseUtils.writeJSONString(response,map);
			
			return;
		}
		
		
		String sec = order.getOrdernum();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String prefix = "N"+sdf.format(new Date());
		Integer ind = nService.updateSequence(prefix,"order_seq");
		DecimalFormat dFormat = new DecimalFormat("000");
		order.setOrdernum(prefix+dFormat.format(ind));
		order.setOrderTime(new Date());
		order.setCreator(String.valueOf(u.getId()));
		order.setCreatorName(u.getUserName());
		order.setOrderStatus(OrderQueryAction.NEW_STATUS);
		
		if(StringUtils.trimToEmpty(order.getDispatchOther()).equals("1")){
			order.setOrdernum(order.getOrdernum()+"-OT");
		}
		
		nService.saveNewOrder(order,sec,u);
		
		uService.saveSysLog(u, "添加新订单，单号【"+order.getOrdernum()+"】", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "新增成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	
	@RequestMapping("/order/ot-newOrder/updateOrder.htm")
	public void updateOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("order")TOrderInfo order) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		StringBuffer errMsg = new StringBuffer();
		validateOrder(order,errMsg);
		if(errMsg.length()>0){
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("success", false);
			map.put("msg", errMsg.toString());
			ResponseUtils.writeJSONString(response,map);
			
			return;
		}
		
		nService.updateOrder(order,u);
		
		uService.saveSysLog(u, "编辑修改了订单，单号【"+order.getOrdernum()+"】", UserLoginLogAction.ORDER_OPR_LOG);
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("success", true);
		map.put("msg", "修改成功！");
		ResponseUtils.writeJSONString(response,map);
		return;
	}
	
	private void validateOrder(TOrderInfo otemp,StringBuffer errMsg){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.trimToNull(otemp.getCustName())==null){
			errMsg.append("用户名不能为空！\n");
		}
		if(StringUtils.trimToNull(otemp.getCustId())==null){
			errMsg.append("用户编码不能为空！\n");
		}
		if("3".equals(otemp.getDeliverMethod())){
			if(StringUtils.trimToNull(otemp.getDeliverAddress())==null){
				errMsg.append("送货地址不能为空！\n");
			}
		}
		if(StringUtils.trimToNull(otemp.getTel())==null){
			errMsg.append("用户电话不能为空！\n");
		}
		
		if(StringUtils.trimToEmpty(otemp.getDispatchOther()).equals("1")){
			if(StringUtils.trimToNull(otemp.getDispatchName())==null){
				errMsg.append("外派单供货商不能为空！\n");
			}
			if(StringUtils.trimToEmpty(otemp.getDispatchName()).length()>40){
				errMsg.append("供货商名称太长了！\n");
			}
			if(StringUtils.trimToEmpty(otemp.getAgentMobile()).length()>20){
				errMsg.append("供货商电话太长了！\n");
			}
			if(StringUtils.trimToEmpty(otemp.getAgentAddress()).length()>50){
				errMsg.append("供货商地址太长了！\n");
			}
		}
		else{
			otemp.setDispatchName(null);
			otemp.setAgentAddress(null);
			otemp.setAgentMobile(null);
		}
		
		if(StringUtils.trimToNull(otemp.getOrderTimeStr())==null){
			errMsg.append("下单时间不能为空！\n");
		}
		else{
			try{
				otemp.setOrderTime(sdf.parse(otemp.getOrderTimeStr()));
			}
			catch(Exception e){
				errMsg.append("下单时间格式不合法(yyyy-MM-dd)！\n");
				e.printStackTrace();
			}
		}
		if(StringUtils.trimToNull(otemp.getDeliverTimeStr())==null){
			errMsg.append("交货时间不能为空！\n");
		}else{
			try{
				otemp.setDeliverTime(sdf.parse(otemp.getDeliverTimeStr()));
				
				if(otemp.getHour()!=0){
					Calendar cal = Calendar.getInstance();
					cal.setTime(otemp.getDeliverTime());
					cal.set(Calendar.HOUR_OF_DAY, otemp.getHour());
					
					otemp.setDeliverTime(cal.getTime());
				}
			}
			catch(Exception e){
				errMsg.append("交货时间格式不合法(yyyy-MM-dd)！\n");
				e.printStackTrace();
			}
		}
//		if(StringUtils.trimToNull(otemp.getTypesetOpr())==null){
//			errMsg.append("排版人员不能为空！\n");
//		}
//		if(StringUtils.trimToNull(otemp.getBindingOpr())==null){
//			errMsg.append("装订人员不能为空！\n");
//		}
	}
	
	
	
	//客户姓名自动完成
	@RequestMapping("/order/ot-newOrder/queryCustomer.htm")
	public void queryCustomer(HttpServletRequest request, HttpServletResponse response, @RequestParam("q")String q) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		if(q==null||q.trim().length()==0){
			return;
		}
		else{
			q  = java.net.URLDecoder.decode(q, "utf-8");
			Page page = new Page();
			TCustInfo cust = new TCustInfo();
			cust.setShortName(q);
			List<TCustInfo> custList = nService.queryCustList(cust, page, u);
			
			if(custList!=null&&custList.size()>0){
				StringBuffer buf = new StringBuffer();
				for(TCustInfo custInfo:custList){
					buf.append(custInfo.getShortName()).append("|").append(custInfo.getCustId()).append("|").append(StringUtils.trimToEmpty(custInfo.getFullName())+" ").append("|").append(StringUtils.trimToEmpty(custInfo.getMobileNo())+" ").append("|").append(StringUtils.trimToEmpty(custInfo.getAddress())+" ").append("|").append(custInfo.getPayCycle()).append("\n");
				}
				buf.append("没有找到...【新增客户】\n");
				
				ResponseUtils.writeString(response,buf.toString());
			}
			else{
				ResponseUtils.writeString(response,"没有找到...【新增客户】\n");
			}
		}
		
	}
}
