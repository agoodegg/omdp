package com.omdp.webapp.contract.action;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.omdp.webapp.base.common.util.RMB;
import com.omdp.webapp.contract.service.IContractService;
import com.omdp.webapp.model.TContractInfo;
import com.omdp.webapp.model.TContractItem;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.service.NewOrderService;
import com.omdp.webapp.sys.log.action.UserLoginLogAction;
import com.omdp.webapp.sys.log.service.UserLoginLogService;

@Controller
@Scope("prototype")
public class ContractAction extends AbstractController  {

	private final static String QUERY_CONTRACT = "contract/contractlist";
	private final static String EDIT_CONTRACT = "contract/editcontract";
	private final static String ADD_CONTRACT = "contract/addcontract";
	private final static String VIEW_CONTRACT = "contract/viewcontract";
	
	public  final static String NORMAL_CONTRACT_STATUS="NORMAL";
	
	@Autowired
	private IContractService contractService;
	
	@Autowired
	private UserLoginLogService uService;
	
	@Autowired
	private NewOrderService nService;
	
	@RequestMapping("/contract/contractList.htm")
	public ModelAndView contractList(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		
		return new ModelAndView(QUERY_CONTRACT,mode);
	}
	
	@RequestMapping("/contract/contractListData.htm")
	public void contractListData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TContractInfo info,@ModelAttribute("page")Page page) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		List<TContractInfo> contractInfoList = contractService.queryContractInfo(info, u, page);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss"));
		//cfg.setJsonPropertyFilter(new IgnoreFieldProcessorImpl("authCode"));
		
		JSONArray warray = JSONArray.fromObject(contractInfoList, cfg);
		
		JSONObject o = new JSONObject();
		o.put("totalRows", page.getTotalCount());
		o.put("list", warray);
		
		ResponseUtils.writeString(response, o.toString());
		
	}
	
	@RequestMapping("/contract/trashContract.htm")
	public void trashContract(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TContractInfo info) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		if(info==null||info.getContractNo()==null||info.getContractNo().trim().length()==0){
			JSONObject o = new JSONObject();
			o.put("success", false);
			o.put("msg", "参数有误！作废失败");
			
			ResponseUtils.writeString(response, o.toString());
			return;
		}
		
		contractService.trashContract(info,u);
		
		uService.saveSysLog(u, "作废合同["+info.getContractNo()+"]", UserLoginLogAction.OTHER_OPR);
		
		JSONObject o = new JSONObject();
		o.put("success", true);
		o.put("msg", "作废成功");
		ResponseUtils.writeString(response, o.toString());
		
	}
	
	
	
	@RequestMapping("/contract/toContractEdit.htm")
	public ModelAndView toContractEdit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TContractInfo info){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		
		TContractInfo contractInfo = contractService.queryContractInfoById(info.getId(), u);
		List<TContractItem> itemList = contractService.queryContractItem(contractInfo.getContractNo(), u);
		
		String itemData = JSONArray.fromObject(itemList).toString();
		
		contractInfo.setItemData(itemData);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		if(contractInfo.getBuyerDate()!=null){
			contractInfo.setBuyerDateStr(sdf.format(contractInfo.getBuyerDate()));
		}
		
		if(contractInfo.getSellerDate()!=null){
			contractInfo.setSellerDateStr(sdf.format(contractInfo.getSellerDate()));
		}
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("info", contractInfo);
		return new ModelAndView(EDIT_CONTRACT,mode);
	}
	
	@RequestMapping("/contract/toContractAdd.htm")
	public ModelAndView toContractAdd(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		
		String nowDate = sdf.format(new Date());
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("nowDate", nowDate);
		return new ModelAndView(ADD_CONTRACT,mode);
	}
	
	@RequestMapping("/contract/doContractAdd.htm")
	public void doContractAdd(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TContractInfo info) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		SimpleDateFormat sdfParser = new SimpleDateFormat("yyyy年MM月dd日");
		StringBuffer errMsg = new StringBuffer();
		
		if(info.getBuyerDateStr()!=null&&info.getBuyerDateStr().trim().length()>0){
			try{
				info.setBuyerDate(sdfParser.parse(info.getBuyerDateStr().trim()));
			}
			catch(Exception e){
				e.printStackTrace();
				errMsg.append("甲方合同日期格式不对 如2011年07月02日");
			}
		}
		if(info.getSellerDateStr()!=null&&info.getSellerDateStr().trim().length()>0){
			try{
				info.setSellerDate(sdfParser.parse(info.getSellerDateStr().trim()));
			}
			catch(Exception e){
				e.printStackTrace();
				errMsg.append("乙方合同日期格式不对 如2011年07月02日");
			}
		}
		
		if(errMsg.length()>0){
			JSONObject o = new JSONObject();
			o.put("success", false);
			o.put("msg", errMsg.toString());
			ResponseUtils.writeString(response, o.toString());
			
			return;
		}
		
		List<TContractItem> items = new ArrayList<TContractItem>();
		JSONArray itemData = JSONArray.fromObject(info.getItemData());
		for(int i=0;i<itemData.size();i++){
			TContractItem itemp = new TContractItem();
			itemp.setItemName(itemData.getJSONObject(i).getString("itemName"));
			itemp.setMertierial(itemData.getJSONObject(i).getString("mertierial"));
			itemp.setMount(itemData.getJSONObject(i).getDouble("mount"));
			itemp.setMountUnit(itemData.getJSONObject(i).getString("mountUnit"));
			itemp.setPrice(itemData.getJSONObject(i).getDouble("price"));
			itemp.setRequirement(itemData.getJSONObject(i).getString("requirement"));
			itemp.setSpec(itemData.getJSONObject(i).getString("spec"));
			itemp.setTotal(itemData.getJSONObject(i).getDouble("total"));
			
			items.add(itemp);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String prefix = "HT"+sdf.format(new Date());
		Integer ind = nService.updateSequence(prefix,"ht_seq");
		DecimalFormat dFormat = new DecimalFormat("000");
		
		String contractNo = prefix+dFormat.format(ind);
		info.setContractNo(contractNo);
		info.setStatus(ContractAction.NORMAL_CONTRACT_STATUS);
		info.setCreateTime(new Date());
		info.setCreator(u.getUserName());
		
		contractService.addNewContract(info,items,u);
		
		contractService.updateContractTotal(info.getContractNo());
		
		JSONObject o = new JSONObject();
		o.put("success", true);
		o.put("msg", "新增成功");
		ResponseUtils.writeString(response, o.toString());
	}
	
	
	
	@RequestMapping("/contract/doContractEdit.htm")
	public void doContractEdit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TContractInfo info) throws IOException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		
		SimpleDateFormat sdfParser = new SimpleDateFormat("yyyy年MM月dd日");
		StringBuffer errMsg = new StringBuffer();
		
		if(info.getBuyerDateStr()!=null&&info.getBuyerDateStr().trim().length()>0){
			try{
				info.setBuyerDate(sdfParser.parse(info.getBuyerDateStr().trim()));
			}
			catch(Exception e){
				e.printStackTrace();
				errMsg.append("甲方合同日期格式不对 如2011年07月02日");
			}
		}
		if(info.getSellerDateStr()!=null&&info.getSellerDateStr().trim().length()>0){
			try{
				info.setSellerDate(sdfParser.parse(info.getSellerDateStr().trim()));
			}
			catch(Exception e){
				e.printStackTrace();
				errMsg.append("乙方合同日期格式不对 如2011年07月02日");
			}
		}
		
		if(errMsg.length()>0){
			JSONObject o = new JSONObject();
			o.put("success", false);
			o.put("msg", errMsg.toString());
			ResponseUtils.writeString(response, o.toString());
			
			return;
		}
		
		List<TContractItem> items = new ArrayList<TContractItem>();
		JSONArray itemData = JSONArray.fromObject(info.getItemData());
		for(int i=0;i<itemData.size();i++){
			TContractItem itemp = new TContractItem();
			itemp.setItemName(itemData.getJSONObject(i).getString("itemName"));
			itemp.setMertierial(itemData.getJSONObject(i).getString("mertierial"));
			itemp.setMount(itemData.getJSONObject(i).getDouble("mount"));
			itemp.setMountUnit(itemData.getJSONObject(i).getString("mountUnit"));
			itemp.setPrice(itemData.getJSONObject(i).getDouble("price"));
			itemp.setRequirement(itemData.getJSONObject(i).getString("requirement"));
			itemp.setSpec(itemData.getJSONObject(i).getString("spec"));
			itemp.setTotal(itemData.getJSONObject(i).getDouble("total"));
			
			items.add(itemp);
		}
		
		contractService.editContract(info,items,u);
		
		contractService.updateContractTotal(info.getContractNo());
		
		JSONObject o = new JSONObject();
		o.put("success", true);
		o.put("msg", "修改成功");
		ResponseUtils.writeString(response, o.toString());
	}
	
	
	@RequestMapping("/contract/toContractView.htm")
	public ModelAndView toContractView(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("info")TContractInfo info){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TUser u = (TUser)(auth.getPrincipal());
		
		TContractInfo contractInfo = contractService.queryContractInfoById(info.getId(), u);
		List<TContractItem> itemList = contractService.queryContractItem(contractInfo.getContractNo(), u);
		
		Double grossValue = contractInfo.getGross();
		DecimalFormat format = new DecimalFormat("0.00");
		String grossText = format.format(grossValue);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		if(contractInfo.getBuyerDate()!=null){
			contractInfo.setBuyerDateStr(sdf.format(contractInfo.getBuyerDate()));
		}
		
		if(contractInfo.getSellerDate()!=null){
			contractInfo.setSellerDateStr(sdf.format(contractInfo.getSellerDate()));
		}
		
		
		Map<String, Object> mode = new HashMap<String, Object>();  
		mode.put("user", u);
		mode.put("info", contractInfo);
		mode.put("itemList", itemList);
		mode.put("grossText", grossText);
		mode.put("RMB", RMB.toBigAmt(contractInfo.getGross()));
		
		return new ModelAndView(VIEW_CONTRACT,mode);
	}
}
