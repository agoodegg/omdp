package com.omdp.webapp.finance.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.sunrise.util.report.model.Report;

import com.ibm.icu.text.SimpleDateFormat;
import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.util.JsonValueProcessorImpl;
import com.omdp.webapp.base.taglib.OrderStatusTrans;
import com.omdp.webapp.customer.service.CustomerManageService;
import com.omdp.webapp.finance.bean.IFeeBean;
import com.omdp.webapp.finance.service.TradService;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TTradInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.service.NewOrderService;
import com.omdp.webapp.order.service.OrderQueryService;
import com.omdp.webapp.sys.log.action.UserLoginLogAction;
import com.omdp.webapp.sys.log.service.UserLoginLogService;

@Controller
@Scope("prototype")
public class TradAction extends AbstractController {

	private final static String QUERY_TO_TRADlIST = "finance/trad/totradlist";
	private final static String PAY_ORDER = "finance/trad/payorder";
	private final static String CHECK_ORDER = "finance/trad/checkorder";
	private final static String QUERY_TO_PAYED = "finance/trad/payedlist";
	private final static String VIEW_TRAD = "finance/trad/viewtrad";

	private final static String CASH = "finance/cash";
	private final static String BILLLIST = "finance/billlist";
	private final static String BILLDETAIL = "finance/billdetail";

	private final static String SINGLEDETAIL = "finance/singledetail";

	private final static String INVALID_PARAM = "finance/invalid";

	private final static String IFEE_INFO = "finance/stat/ifee";

	@Autowired
	private TradService tService;

	@Autowired
	private OrderQueryService oService;

	@Autowired
	private NewOrderService nService;

	@Autowired
	private UserLoginLogService uService;

	@Autowired
	private CustomerManageService cService;


	@RequestMapping("/finance/trad/totrad.htm")
	public ModelAndView queryAll(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());


		Map<String, Object> mode = new HashMap<String, Object>();
		mode.put("user", u);
		mode.put("p", q);
		mode.put("mode", 0);
		return new ModelAndView(QUERY_TO_TRADlIST, mode);
	}
	
	@RequestMapping("/finance/trad/totradData.htm")
	public void queryAllData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		page.setPageSize(15); // 每页大小15条

		List<TOrderInfo> orderList = oService.queryDoneOrderInfo(q, page, u);

		uService.saveSysLog(u, "订单结算查询", UserLoginLogAction.FINANCE_OPR);
		
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
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}
	
	

	@RequestMapping("/finance/trad/payorder.htm")
	public ModelAndView payorder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	TOrderInfo q) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		TOrderInfo order = oService.loadOrderById(q.getId(), u);

		List<TOrderDetail> details = oService.loadOrderDetails(order, u);

		List<TStakeHolder> typeList = oService.queryStakeHolders(order, TStakeHolder.ORDER_TYPE);
		List<TStakeHolder> bindList = oService.queryStakeHolders(order, TStakeHolder.ORDER_BIND);

		StringBuffer typeNameBuf = new StringBuffer();
		StringBuffer bindNameBuf = new StringBuffer();

		if (typeList != null) {
			for (TStakeHolder holder : typeList) {

				typeNameBuf.append(holder.getUserName()).append(";");
			}
		}

		if (bindList != null) {
			for (TStakeHolder holder : bindList) {
				bindNameBuf.append(holder.getUserName()).append(";");
			}
		}

		String deliverTimeHours = "";
		Calendar cal = Calendar.getInstance();
		if (order.getDeliverTime() != null) {
			cal.setTime(order.getDeliverTime());

			int hours = cal.get(Calendar.HOUR_OF_DAY);

			if (hours != 0) {
				deliverTimeHours = String.valueOf(hours);
			}
		}

		List<Object[]> detailsStaticsData = oService.getDetailStaticData(order, u);
		Map<String, Integer> numMap = new HashMap<String, Integer>();
		Map<String, Double> totalMap = new HashMap<String, Double>();

		if (detailsStaticsData != null) {
			for (Object[] rowtemp : detailsStaticsData) {
				String type = (String) rowtemp[0];

				Integer num = ((Long) rowtemp[1]).intValue();

				Double total = ((Double) rowtemp[2]).doubleValue();

				numMap.put(type, num);
				totalMap.put(type, total);
			}
		}

		request.setAttribute("numMap", numMap);
		request.setAttribute("totalMap", totalMap);

		if (order.getPrice() == null) {
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
		return new ModelAndView(PAY_ORDER, mode);
	}

	@RequestMapping("/finance/trad/dopayorder.htm")
	public void dopayorder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("t")
	TTradInfo t) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		TOrderInfo order = oService.loadOrderByNum(t.getOrdernum(), u);

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String prefix = "XF" + sdf.format(new Date());
		Integer ind = nService.updateSequence(prefix, "pay_order_seq");
		DecimalFormat dFormat = new DecimalFormat("000");
		t.setTradCd(prefix + dFormat.format(ind));

		tService.payOrder(order, t, u);

		uService.saveSysLog(u, "订单结算,单号【" + order.getOrdernum() + "】", UserLoginLogAction.FINANCE_OPR);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("msg", "结算成功！");
		ResponseUtils.writeJSONString(response, map);
		return;
	}

	@RequestMapping("/finance/trad/docheckorder.htm")
	public void docheckorder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("t")
	TTradInfo t) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		TOrderInfo order = oService.loadOrderByNum(t.getOrdernum(), u);

		tService.checkOrder(order, t, u);

		uService.saveSysLog(u, "订单核销,单号【" + order.getOrdernum() + "】", UserLoginLogAction.FINANCE_OPR);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("msg", "核销成功！");
		ResponseUtils.writeJSONString(response, map);
		return;
	}

	@RequestMapping("/finance/trad/billsend.htm")
	public void billsend(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("t")
	TTradInfo t) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		TTradInfo tradInfo = tService.loadTradByOrdernum(t.getOrdernum(), u);

		Map<String, Object> map = new HashMap<String, Object>();
		if ("1".equals(StringUtils.trim(tradInfo.getCheckStatus()))) {
			map.put("success", false);
			map.put("msg", "发票已开，不能重复开发票！");
			ResponseUtils.writeJSONString(response, map);
			return;
		}

		tService.billSend(tradInfo, u);

		uService.saveSysLog(u, "订单补开发票,单号【" + t.getOrdernum() + "】", UserLoginLogAction.FINANCE_OPR);

		map.put("success", true);
		map.put("msg", "开票成功！");
		ResponseUtils.writeJSONString(response, map);
		return;
	}

	@RequestMapping("/finance/trad/checkorder.htm")
	public ModelAndView checkorder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	TOrderInfo q) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		TOrderInfo order = oService.loadOrderById(q.getId(), u);

		List<TOrderDetail> details = oService.loadOrderDetails(order, u);

		List<TStakeHolder> typeList = oService.queryStakeHolders(order, TStakeHolder.ORDER_TYPE);
		List<TStakeHolder> bindList = oService.queryStakeHolders(order, TStakeHolder.ORDER_BIND);

		StringBuffer typeNameBuf = new StringBuffer();
		StringBuffer bindNameBuf = new StringBuffer();

		if (typeList != null) {
			for (TStakeHolder holder : typeList) {

				typeNameBuf.append(holder.getUserName()).append(";");
			}
		}

		if (bindList != null) {
			for (TStakeHolder holder : bindList) {
				bindNameBuf.append(holder.getUserName()).append(";");
			}
		}

		String deliverTimeHours = "";
		Calendar cal = Calendar.getInstance();
		if (order.getDeliverTime() != null) {
			cal.setTime(order.getDeliverTime());

			int hours = cal.get(Calendar.HOUR_OF_DAY);

			if (hours != 0) {
				deliverTimeHours = String.valueOf(hours);
			}
		}

		List<Object[]> detailsStaticsData = oService.getDetailStaticData(order, u);
		Map<String, Integer> numMap = new HashMap<String, Integer>();
		Map<String, Double> totalMap = new HashMap<String, Double>();

		if (detailsStaticsData != null) {
			for (Object[] rowtemp : detailsStaticsData) {
				String type = (String) rowtemp[0];

				Integer num = ((Long) rowtemp[1]).intValue();

				Double total = ((Double) rowtemp[2]).doubleValue();

				numMap.put(type, num);
				totalMap.put(type, total);
			}
		}

		request.setAttribute("numMap", numMap);
		request.setAttribute("totalMap", totalMap);

		if (order.getPrice() == null) {
			order.setPrice(0.0D);
		}

		TTradInfo trad = tService.loadTradByOrdernum(order.getOrdernum(), u);

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
		mode.put("trad", trad);
		return new ModelAndView(CHECK_ORDER, mode);
	}

	@RequestMapping("/finance/trad/viewtrad.htm")
	public ModelAndView viewtrad(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	TOrderInfo q) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		TOrderInfo order = oService.loadOrderByNum(q.getOrdernum(), u);

		List<TOrderDetail> details = oService.loadOrderDetails(order, u);

		List<TStakeHolder> typeList = oService.queryStakeHolders(order, TStakeHolder.ORDER_TYPE);
		List<TStakeHolder> bindList = oService.queryStakeHolders(order, TStakeHolder.ORDER_BIND);

		StringBuffer typeNameBuf = new StringBuffer();
		StringBuffer bindNameBuf = new StringBuffer();

		if (typeList != null) {
			for (TStakeHolder holder : typeList) {

				typeNameBuf.append(holder.getUserName()).append(";");
			}
		}

		if (bindList != null) {
			for (TStakeHolder holder : bindList) {
				bindNameBuf.append(holder.getUserName()).append(";");
			}
		}

		String deliverTimeHours = "";
		Calendar cal = Calendar.getInstance();
		if (order.getDeliverTime() != null) {
			cal.setTime(order.getDeliverTime());

			int hours = cal.get(Calendar.HOUR_OF_DAY);

			if (hours != 0) {
				deliverTimeHours = String.valueOf(hours);
			}
		}

		List<Object[]> detailsStaticsData = oService.getDetailStaticData(order, u);
		Map<String, Integer> numMap = new HashMap<String, Integer>();
		Map<String, Double> totalMap = new HashMap<String, Double>();

		if (detailsStaticsData != null) {
			for (Object[] rowtemp : detailsStaticsData) {
				String type = (String) rowtemp[0];

				Integer num = ((Long) rowtemp[1]).intValue();

				Double total = ((Double) rowtemp[2]).doubleValue();

				numMap.put(type, num);
				totalMap.put(type, total);
			}
		}

		request.setAttribute("numMap", numMap);
		request.setAttribute("totalMap", totalMap);

		if (order.getPrice() == null) {
			order.setPrice(0.0D);
		}

		TTradInfo trad = tService.loadTradByOrdernum(order.getOrdernum(), u);

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
		mode.put("trad", trad);
		return new ModelAndView(VIEW_TRAD, mode);
	}

	@RequestMapping("/finance/trad/payedlist.htm")
	public ModelAndView payedlist(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	TOrderInfo q, @ModelAttribute("page")
	Page page) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());


		Map<String, Object> mode = new HashMap<String, Object>();
		mode.put("user", u);
		mode.put("p", q);
		mode.put("mode", 0);
		return new ModelAndView(QUERY_TO_PAYED, mode);
	}
	
	
	@RequestMapping("/finance/trad/payedlistData.htm")
	public void payedlistData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")TOrderInfo q, @ModelAttribute("page")Page page) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		page.setPageSize(15); // 每页大小15条

		List<Object[]> payedList = oService.queryPayedList(q, page, u);
		
		List<TTradInfo> data = new ArrayList<TTradInfo>();
		
		if(payedList!=null){
			for(Object[] rowtemp:payedList){
				TOrderInfo order = (TOrderInfo)rowtemp[0];
				TTradInfo trad = (TTradInfo)rowtemp[1];
				trad.setOrderStatusCn(OrderStatusTrans.getStatusLiteral(order.getOrderStatus()));
				trad.setOrderStatus(order.getOrderStatus());
				
				data.add(trad);
			}
		}

		uService.saveSysLog(u, "结算记录查询", UserLoginLogAction.FINANCE_OPR);

		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(data, cfg);
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}

	@RequestMapping("/finance/stat/ifee.htm")
	public ModelAndView ifee(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	IFeeBean q) {

		Map<String, Map<String, String>> sysDict = (Map<String, Map<String, String>>) (request.getSession()
				.getServletContext().getAttribute("sysDictMap"));
		Map<String, Map<String, String>> busiDict = (Map<String, Map<String, String>>) (request.getSession()
				.getServletContext().getAttribute("busiDictMap"));

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		if (q == null) {
			q = new IFeeBean();
		}

		if (q.getDispatchOther() == null || q.getDispatchOther().trim().length() == 0) {
			q.setDispatchOther("0");
		}

		if (q.getUptime() == null || q.getUptime().trim().length() == 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			q.setUptime(sdf.format(cal.getTime()));
		}

		uService.saveSysLog(u, "查看营帐信息", UserLoginLogAction.FINANCE_OPR);

		Report rp = oService.getIfeeReport(q, u, sysDict);

		request.setAttribute("report", rp);

		Map<String, Object> mode = new HashMap<String, Object>();
		mode.put("user", u);
		mode.put("p", q);
		mode.put("mode", 0);
		return new ModelAndView(IFEE_INFO, mode);
	}

	@RequestMapping("/finance/trad/cash.htm")
	public ModelAndView cash(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	IFeeBean q, @ModelAttribute("page")
	Page page) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		page.setPageSize(15); // 每页大小15条

		if (q == null) {
			q = new IFeeBean();
		}

		if (q.getDispatchOther() == null || q.getDispatchOther().trim().length() == 0) {
			q.setDispatchOther("0");
		}

		if (q.getUptime() == null || q.getUptime().trim().length() == 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			q.setUptime(sdf.format(cal.getTime()));
		}

		uService.saveSysLog(u, "查看现金收款记录", UserLoginLogAction.FINANCE_OPR);

		List<IFeeBean> feeList = oService.getTradRecList(q, page, u);
		IFeeBean totalObject = oService.getTradRecTotal(q, u);

		Map<String, Object> mode = new HashMap<String, Object>();
		mode.put("user", u);
		mode.put("p", q);
		mode.put("mode", 0);
		mode.put("feeList", feeList);
		mode.put("totalObject", totalObject);
		return new ModelAndView(CASH, mode);
	}

	@RequestMapping("/finance/bill/billlist.htm")
	public ModelAndView billlist(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	IFeeBean q) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		if (q == null) {
			q = new IFeeBean();
		}

		if (q.getUptime() == null || q.getUptime().trim().length() == 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			q.setUptime(sdf.format(cal.getTime()));
		}

		List<TOrderInfo> billList = oService.getBillList(q, u);

		uService.saveSysLog(u, "查询月结客户对账信息", UserLoginLogAction.FINANCE_OPR);

		Map<String, Object> mode = new HashMap<String, Object>();
		mode.put("user", u);
		mode.put("p", q);
		mode.put("mode", 0);
		mode.put("billList", billList);
		return new ModelAndView(BILLLIST, mode);
	}

	@RequestMapping("/finance/bill/singleDetail.htm")
	public ModelAndView singleDetail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	IFeeBean q) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		if (q == null || q.getCustId() == null || q.getCustId().trim().length() == 0) {
			Map<String, Object> mode = new HashMap<String, Object>();
			return new ModelAndView(INVALID_PARAM, mode);
		}

		TCustInfo cust = cService.loadCustInfoByCustId(q.getCustId(), u);

		List<TOrderInfo> billList = oService.getBillDetail(q, u);

		uService.saveSysLog(u, "查询客户【" + q.getCustId() + "】对账详细", UserLoginLogAction.FINANCE_OPR);

		Map<String, Object> mode = new HashMap<String, Object>();
		mode.put("user", u);
		mode.put("p", q);
		mode.put("mode", 0);
		mode.put("billList", billList);
		mode.put("cust", cust);
		return new ModelAndView(SINGLEDETAIL, mode);
	}

	@RequestMapping("/finance/bill/billdetail.htm")
	public ModelAndView billdetail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("q")
	IFeeBean q) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		if (q == null) {
			q = new IFeeBean();
		}

		if (q.getUptime() == null || q.getUptime().trim().length() == 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			q.setUptime(sdf.format(cal.getTime()));
		}

		uService.saveSysLog(u, "查看月结客户对账明细", UserLoginLogAction.FINANCE_OPR);

		Map<String, Object> mode = new HashMap<String, Object>();
		mode.put("user", u);
		mode.put("p", q);
		mode.put("mode", 0);
		return new ModelAndView(BILLDETAIL, mode);
	}

	@RequestMapping("/finance/bill/singleDetailExport.htm")
	public void singleDetailExport(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("q")
			IFeeBean q) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser) (auth.getPrincipal());

		if (q == null || q.getCustId() == null || q.getCustId().trim().length() == 0) {
			Map<String, Object> mode = new HashMap<String, Object>();
			return;
		}

		TCustInfo cust = cService.loadCustInfoByCustId(q.getCustId(), u);

		List<TOrderInfo> billList = oService.getBillDetailExport(q, u);

		File f = File.createTempFile("export", ".xls", new File(System.getProperty("user.dir")));
		
		buildExcel(billList,cust,f);
		
		uService.saveSysLog(u, "导出客户【" + q.getCustId() + "】对账详细", UserLoginLogAction.FINANCE_OPR);

		String fileName = cust.getShortName()+"-对账单明细.xls";

		exportFile(response, f, fileName, true);

	}

	private void buildExcel(List<TOrderInfo> bills, TCustInfo cust, File f) throws Exception {
		OutputStream out = new FileOutputStream(f);
		
		//添加带有字体颜色,带背景颜色 Formatting的对象 
		jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED); 
		jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc); 
		wcfFC.setBackground(jxl.format.Colour.GRAY_25);

		//添加带有字体颜色,带背景颜色 Formatting的对象 
		jxl.write.WritableFont wfc1 = new jxl.write.WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED); 
		jxl.write.WritableCellFormat wcfFC1 = new jxl.write.WritableCellFormat(wfc1); 
		wcfFC1.setBackground(jxl.format.Colour.INDIGO);
		
		
		WritableWorkbook wwb = Workbook.createWorkbook(out);
		// 生成工作表
		WritableSheet sheet = wwb.createSheet("客户对账单明细", 0);
		
		Label label = new Label(0,0,"客户名称:");
		sheet.addCell(label);
		
		label = new Label(1,0,cust.getShortName());
		sheet.addCell(label);
		
		
		label = new Label(3,0,"客户地址:");
		sheet.addCell(label);
		
		label = new Label(4,0,cust.getAddress());
		sheet.addCell(label);


		label = new Label(0,2,"单号",wcfFC);
		sheet.addCell(label);
		
		label = new Label(1,2,"下单时间",wcfFC);
		sheet.addCell(label);
		
		label = new Label(2,2,"完工时间",wcfFC);
		sheet.addCell(label);
		
		label = new Label(3,2,"金额(元)",wcfFC);
		sheet.addCell(label);
		
		label = new Label(4,2,"品名",wcfFC1);
		sheet.addCell(label);
		
		label = new Label(5,2,"数量",wcfFC1);
		sheet.addCell(label);
		
		label = new Label(6,2,"单价(元)",wcfFC1);
		sheet.addCell(label);
		
		label = new Label(7,2,"金额(元)",wcfFC1);
		sheet.addCell(label);
		
		DecimalFormat formater = new DecimalFormat("0.##");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=2;
		for(TOrderInfo order:bills){
			i++;
			
			label = new Label(0,i,order.getOrdernum());
			sheet.addCell(label);
			
			label = new Label(1,i,order.getOrderTime()==null?"":sdf.format(order.getOrderTime()));
			sheet.addCell(label);
			
			label = new Label(2,i,order.getDoneTime()==null?"":sdf.format(order.getDoneTime()));
			sheet.addCell(label);
			
			jxl.write.Number number = new jxl.write.Number(3, i,order.getPrice());
			sheet.addCell(number);
			
			List<TOrderDetail> details = order.getOrderDetails();
			for(TOrderDetail d:details){
				i++;
				
				label = new Label(4,i,d.getItemName());
				sheet.addCell(label);
				
				
				label = new Label(5,i,String.valueOf(formater.format(d.getAmount()))+String.valueOf(d.getUnit()));
				sheet.addCell(label);
				
				number = new jxl.write.Number(6, i,d.getUnitPrice());
				sheet.addCell(number);
				
				number = new jxl.write.Number(7, i,d.getTotal());
				sheet.addCell(number);
			}
		}
		
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
		out.close();
		
	}

	public static void exportFile(HttpServletResponse response, File file, String fileName , boolean isDel) throws IOException {
		OutputStream out = null;
		InputStream in = null;

		// 获得文件名
		String filename = URLEncoder.encode(fileName, "UTF-8");
		// 定义输出类型(下载)
		response.setContentType("application/force-download");
		response.setHeader("Location", filename);
		// 定义输出文件头
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		out = response.getOutputStream();
		in = new FileInputStream(file.getPath());

		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
			out.write(buffer, 0, i);
		}

		in.close();
		out.close();

		if (isDel) {
			// 删除文件,删除前关闭所有的Stream.
			file.delete();
		}
	}

}
