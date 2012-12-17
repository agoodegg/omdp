package com.omdp.webapp.stat.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.omdp.webapp.base.AbstractController;
import com.omdp.webapp.base.ResponseUtils;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.util.IgnoreFieldProcessorImpl;
import com.omdp.webapp.base.common.util.JsonValueProcessorImpl;
import com.omdp.webapp.customer.service.CustomerManageService;
import com.omdp.webapp.employee.service.EmployeeService;
import com.omdp.webapp.finance.bean.IFeeBean;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.stat.service.CustomerBusiStatService;
import com.omdp.webapp.sys.backup.action.BackupAction;
import com.omdp.webapp.sys.log.action.UserLoginLogAction;
import com.omdp.webapp.sys.log.service.UserLoginLogService;


@Controller
@Scope("prototype")
public class CustomerBusiStatAction extends AbstractController {

	private final static String CONSUMPTION_STAT = "stat/custbusi/consumption";
	
	private final static String MYCUST_STAT = "stat/custbusi/mycuststat";
	private final static String EMPLOYEE_STAT = "stat/custbusi/employeestat";
	
	private final static String BUSI_RANK_CHART = "stat/custbusi/busirank";
	
	private final static String BUSI_MONTH_LINE = "stat/custbusi/busiMonthLineData";
	
	
	@Autowired
	private CustomerBusiStatService custBusiService;
	
	@Autowired
	private CustomerManageService cService;
	
	@Autowired
	private EmployeeService eService;
	
	@Autowired
	private UserLoginLogService uService;
	
	
	//客户消费统计
	@RequestMapping("/custbusistat/consumption/toConsumption.htm")
	public String toConsumption(HttpServletRequest request, HttpServletResponse response)
	{
		return CONSUMPTION_STAT;
	}
	
	
	
	//我的客户统计  按客户
	@RequestMapping("/custbusistat/ach/mycuststat.htm")
	public ModelAndView mycuststat(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cust")TCustInfo cust, @ModelAttribute("page")Page page)
	{
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
		
		if(cust==null){
			cust = new TCustInfo();
		}
		
		if(cust.getUptime()==null||cust.getUptime().trim().length()==0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cust.setUptime(sdf.format(cal.getTime()));
		}
		
		
		List<TCustInfo> custList = cService.myCustStatFee(cust, hasAllCustVisual, page, u);
		
		if(hasAllCustVisual){
			uService.saveSysLog(u, "查询所有客户", UserLoginLogAction.CUST_OPR);
		}
		else{
			uService.saveSysLog(u, "查询自己的客户", UserLoginLogAction.CUST_OPR);
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("custList", custList);
		mode.put("q", cust);
		return new ModelAndView(MYCUST_STAT,mode);
	}
	
	
	@RequestMapping("/custbusistat/ach/employeestat.htm")
	public ModelAndView employeestat(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")TUser user,@ModelAttribute("page")Page page)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		if(user==null){
			user = new TUser();
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("page", page);
		mode.put("q", user);
		
		return new ModelAndView(EMPLOYEE_STAT,mode);
	}
	
	
	@RequestMapping("/custbusistat/ach/employeestatData.htm")
	public void employeestatData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")TUser user,@ModelAttribute("page")Page page) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		if(user==null){
			user = new TUser();
		}
		
		if(user.getUptime()==null||user.getUptime().trim().length()==0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			user.setUptime(sdf.format(cal.getTime()));
		}
		
		
		List<TUser> userList = cService.userClientStatFee(user, page, u);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		
		JSONArray warray = JSONArray.fromObject(userList, cfg);
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
	}
	
	@RequestMapping("/custbusistat/busi/busiRankChart.htm")
	public ModelAndView busiRankChart(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		if(q==null){
			q = new IFeeBean();
		}
		
		if(q.getUptime()==null||q.getUptime().trim().length()==0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			q.setUptime(sdf.format(cal.getTime()));
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("q", q);
		
		return new ModelAndView(BUSI_RANK_CHART,mode);
	}
	
	
	@RequestMapping("/custbusistat/busi/busiRankChartData.htm")
	public void busiRankChartData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		if(q==null){
			q = new IFeeBean();
		}
		
		if(q.getUptime()==null||q.getUptime().trim().length()==0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			q.setUptime(sdf.format(cal.getTime()));
		}
		
		List<Object[]> statList = custBusiService.queryBusiAchRank(q, u);
		
		uService.saveSysLog(u, "查看业务量分类统计排行图表", UserLoginLogAction.FINANCE_OPR);
		
		Map<String, Map<String, String>> sysDict = (Map<String, Map<String, String>>)request.getSession().getServletContext().getAttribute("sysDictMap");
		Map<String,String> map = (Map<String,String>)(sysDict.get("BUSI_TYPE"));
		if(map==null){
			map = new HashMap<String,String>();
		}
		
		JSONArray warray = new JSONArray();
		if(statList!=null){
			for(Object[] rowtemp:statList){
				JSONArray tmp = new JSONArray();
				tmp.add(map.get((String)rowtemp[0]));
				tmp.add((Double)rowtemp[1]);
				warray.add(tmp);
			}
		}
		
		ResponseUtils.writeString(response, warray.toString());
	}
	
	
	@RequestMapping("/custbusistat/busi/busiMonthLine.htm")
	public ModelAndView busiMonthLine(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Calendar cal = Calendar.getInstance();
		IFeeBean queryBean = new IFeeBean();
		queryBean.setYear(String.valueOf(cal.get(Calendar.YEAR)));
		queryBean.setDispatchOther(q.getDispatchOther());
		
		List<Object[]> statList = custBusiService.queryBusiMonthLine(queryBean, u);
		
		Map<String,Double> dataMap = new HashMap<String,Double>();
		if(statList!=null&&statList.size()>0){
			for(Object[] rowtemp:statList){
				Double tempPrice = 0.00;
				if(rowtemp[1]!=null){
					tempPrice = ((BigDecimal)(rowtemp[1])).doubleValue();
				}
				dataMap.put((String)rowtemp[0], tempPrice);
			}
		}
		
		String[] month = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		int m = cal.get(Calendar.MONTH);
		
		List data = new ArrayList();
		for(int i=0;i<month.length;i++){
			Object[] temp = new Object[2];
			temp[0]=month[i];
			
			if(i>=m){
				temp[1] = null;
			}
			else{
				Double d = dataMap.get(month[i]);
				if(d!=null){
					temp[1]=d;
				}
				else{
					temp[1]=0.00;
				}
			}
			data.add(temp);
		}
		
		
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("queryBean", queryBean);
		mode.put("data", data);
		
		return new ModelAndView(BUSI_MONTH_LINE,mode);
	}
	
	@RequestMapping("/custbusistat/busi/daycash.htm")
	public void dayCash(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q,@ModelAttribute("page")Page page) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);
		
		List<Object[]> dayCashList = custBusiService.queryDayCash(q, u, page);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl());
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl());
		cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = new JSONArray();
		
		if(dayCashList!=null&&dayCashList.size()>0){
			for(Object[] rowtemp:dayCashList){
				JSONObject tmp = new JSONObject();
				tmp.put("feeType", rowtemp[0]);
				tmp.put("feeDate", rowtemp[1]);
				tmp.put("yfeeTotal", ((BigDecimal)rowtemp[2]).doubleValue());
				tmp.put("acFeeTotal", ((BigDecimal)rowtemp[3]).doubleValue());
				
				warray.add(tmp);
			}
		}
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("list", warray);
		
		response.getWriter().write(o.toString());
		
	}
	
	
	@RequestMapping("/custbusistat/busi/dayline.htm")
	public void dayline(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q,@ModelAttribute("page")Page page) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(q.getYear(),10));
		cal.set(Calendar.MONTH, Integer.parseInt(q.getMontn(),10)-1);
		
		int day = cal.getMaximum(Calendar.DAY_OF_MONTH);
		
		List<Object[]> dayLineList = custBusiService.queryDayLine(q, u, page);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl());
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl());
		cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = new JSONArray();
		
		
		DecimalFormat fmt = new DecimalFormat("00");
		
		if(dayLineList!=null){
			if(dayLineList.size()>0){
				
				int ind = 0;
				for(int i=1;i<=day;i++){
					
					if(ind>=dayLineList.size()){
						
						JSONObject tmp = new JSONObject();
						tmp.put("day", ""+i);
						tmp.put("amount", null);
						
						warray.add(tmp);
					}
					else{
						
						Object[] rowtemp = dayLineList.get(ind);
						String dayOfMonth = (String)(rowtemp[0]);
						
						
						if((fmt.format(i)).equals(dayOfMonth)){
							ind++;
							
							JSONObject tmp = new JSONObject();
							tmp.put("day", ""+i);
							tmp.put("amount", ((BigDecimal)(rowtemp[1])).doubleValue());
							
							warray.add(tmp);
						}
						else{
							JSONObject tmp = new JSONObject();
							tmp.put("day", ""+i);
							tmp.put("amount", 0.00);
							
							warray.add(tmp);
						}
					}
				}
			}
		}
		
		
		JSONObject o = new JSONObject();
		o.put("list", warray);
		
		response.getWriter().write(o.toString());
		
	}
	
	
	@RequestMapping("/custbusistat/busi/custline.htm")
	public void custline(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q,@ModelAttribute("page")Page page) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Calendar cal = Calendar.getInstance();
		IFeeBean queryBean = new IFeeBean();
		queryBean.setYear(q.getYear());
		queryBean.setCustId(q.getCustId());
		queryBean.setDispatchOther(q.getDispatchOther());
		
		List<Object[]> statList = custBusiService.queryCustMonthLine(queryBean, u);
		
		Map<String,Double> dataMap = new HashMap<String,Double>();
		if(statList!=null&&statList.size()>0){
			for(Object[] rowtemp:statList){
				Double tempPrice = 0.00;
				if(rowtemp[1]!=null){
					tempPrice = ((BigDecimal)(rowtemp[1])).doubleValue();
				}
				dataMap.put((String)rowtemp[0], tempPrice);
			}
		}
		
		String[] month = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		
		JSONArray warray = new JSONArray();
		
		
		for(int i=0;i<month.length;i++){
			Object[] temp = new Object[2];
			temp[0]=month[i];
			
			Double d = dataMap.get(month[i]);
			if(d!=null){
				temp[1]=d;
			}
			else{
				String calcMonth = q.getYear()+month[i];
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				String curMonth = sdf.format(Calendar.getInstance().getTime());
				if(Long.parseLong(calcMonth) > Long.parseLong(curMonth)){
					temp[1]=null;
				}
				else{
					temp[1]=0.00;
				}
			}
			
			
			JSONObject tmp = new JSONObject();
			tmp.put("month", month[i]);
			tmp.put("amount", temp[1]);
			
			warray.add(tmp);
		}
		
		JSONObject o = new JSONObject();
		o.put("list", warray);
		
		response.getWriter().write(o.toString());
		
	}
	
	@RequestMapping("/custbusistat/busi/oldtop20cust.htm")
	public void oldtop20cust(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q,@ModelAttribute("page")Page page) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);
		
		List<Object[]> rstList = custBusiService.queryOldTop20(q, u, page);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl());
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl());
		cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = new JSONArray();
		
		if(rstList!=null){
			if(rstList.size()>0){
				for(int i=0;i<rstList.size();i++){
					JSONObject tmp = new JSONObject();
					
					Object[] rowtemp = rstList.get(i);
					
					tmp.put("custName", (String)(rowtemp[0]));
					tmp.put("amount", ((BigDecimal)(rowtemp[1])).doubleValue());
					tmp.put("custNo", (String)(rowtemp[2]));
					
					warray.add(tmp);
				}
			}
		}
		
		
		JSONObject o = new JSONObject();
		o.put("list", warray);
		
		response.getWriter().write(o.toString());
		
	}
	
	@RequestMapping("/custbusistat/busi/deltatop20cust.htm")
	public void deltatop20cust(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q,@ModelAttribute("page")Page page) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		page.setPageSize(15);
		
		List<Object[]> rstList = custBusiService.queryDeltaTop20(q, u, page);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl());
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl());
		cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = new JSONArray();
		
		if(rstList!=null){
			if(rstList.size()>0){
				for(int i=0;i<rstList.size();i++){
					JSONObject tmp = new JSONObject();
					
					Object[] rowtemp = rstList.get(i);
					
					tmp.put("custName", (String)(rowtemp[0]));
					tmp.put("amount", ((BigDecimal)(rowtemp[1])).doubleValue());
					tmp.put("custNo", (String)(rowtemp[2]));
					
					warray.add(tmp);
				}
			}
		}
		
		
		JSONObject o = new JSONObject();
		o.put("list", warray);
		
		response.getWriter().write(o.toString());
		
	}
	
	@RequestMapping("/custbusistat/busi/daycashExport.htm")
	public void dayCashExport(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("params")IFeeBean q) throws IOException, WriteException{
		
		InputStream in = BackupAction.class.getResourceAsStream("/conf.properties");
		
		Properties prop = new Properties();
		
		try{
			prop.load(in);
			
			in.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		String exportPath=prop.getProperty("export_path", "");
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		Page page = new Page();
		page.setPageSize(500);

		List<Object[]> dayCashList = custBusiService.queryDayCash(q, u, page);
		
		String tmpFileName = UUID.randomUUID().toString();
		File tmpFile = new File(exportPath+"\\"+tmpFileName);
		
		OutputStream out = new FileOutputStream(tmpFile);
		
		//添加带有字体颜色,带背景颜色 Formatting的对象 
		jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED); 
		jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc); 
		wcfFC.setBackground(jxl.format.Colour.GRAY_25);

		//添加带有字体颜色,带背景颜色 Formatting的对象 
		jxl.write.WritableFont wfc1 = new jxl.write.WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED); 
		jxl.write.WritableCellFormat wcfFC1 = new jxl.write.WritableCellFormat(wfc1); 
		wcfFC1.setBackground(jxl.format.Colour.INDIGO);
		
		
		WritableWorkbook wwb = Workbook.createWorkbook(out);
		
		
		Map<String, Map<String, String>> sysDict = (Map<String, Map<String, String>>) (request.getSession().getServletContext().getAttribute("sysDictMap"));
		
		Map<String,String> payTypeMap = sysDict.get("payType");
		
		String filename = payTypeMap.get(q.getFeeType())+"-日结算金额明细";
		String suffix = ".xls";
		// 生成工作表
		WritableSheet sheet = wwb.createSheet(filename, 0);
		
		Label label = new Label(0,0,"日期");
		sheet.addCell(label);
		Label label2 = new Label(1,0,"应收金额");
		sheet.addCell(label2);
		Label label3 = new Label(2,0,"实收金额");
		sheet.addCell(label3);
		
		DecimalFormat formater = new DecimalFormat("0.##");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		for(Object[] rowtemp:dayCashList){
			i++;
			
			String date = (String)rowtemp[1];
			Double yfee = ((BigDecimal)rowtemp[2]).doubleValue();
			Double acFee = ((BigDecimal)rowtemp[3]).doubleValue();
			
			label = new Label(0,i,date);
			sheet.addCell(label);
			
			label = new Label(1,i,String.valueOf(formater.format(yfee)));
			sheet.addCell(label);

			jxl.write.NumberFormat nfPERCENT_FLOAT = new jxl.write.NumberFormat("0.00");
			jxl.write.WritableCellFormat wcfnfPERCENT_FLOAT = new jxl.write.WritableCellFormat(nfPERCENT_FLOAT);
			jxl.write.Number numSalerate = new jxl.write.Number(1,i,yfee, wcfnfPERCENT_FLOAT);
			sheet.addCell(numSalerate);
			
			wcfnfPERCENT_FLOAT = new jxl.write.WritableCellFormat(nfPERCENT_FLOAT);
			numSalerate = new jxl.write.Number(2,i,acFee,wcfnfPERCENT_FLOAT);
			sheet.addCell(numSalerate);
			
		}
		
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
		out.close();
		
		exportFile(response, new File(exportPath+"\\"+tmpFileName), filename+suffix , false);
		
	}
	
	
}
