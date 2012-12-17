package com.omdp.webapp.order.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import cn.com.sunrise.util.report.model.Column;
import cn.com.sunrise.util.report.model.DataTable;
import cn.com.sunrise.util.report.model.Foot;
import cn.com.sunrise.util.report.model.FootSumColumn;
import cn.com.sunrise.util.report.model.Group;
import cn.com.sunrise.util.report.model.LabelProvider;
import cn.com.sunrise.util.report.model.Report;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.util.BaseStringUtils;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.contract.dao.IContractDao;
import com.omdp.webapp.deliver.dao.DeliverDAO;
import com.omdp.webapp.finance.bean.IFeeBean;
import com.omdp.webapp.model.TContractInfo;
import com.omdp.webapp.model.TContractItem;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TSendProduct;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TTradInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.action.OrderQueryAction;
import com.omdp.webapp.order.dao.OrderQueryDAO;
import com.omdp.webapp.order.service.OrderQueryService;


@Transactional
public class OrderQueryServiceImpl implements OrderQueryService {

	@Resource
	private OrderQueryDAO<TOrderInfo> orderQueryDAO;
	
	@Resource
	private DeliverDAO<TSendProduct> deliverDAO;
	
	@Resource
	private IContractDao<TContractInfo> contractDAO;

	public List<TOrderInfo> queryOrderInfo(TOrderInfo order, Page page, TUser u) {
		
		String sql = " select t from TOrderInfo t where t.ext1 = '?' and t.dispatchName like '%?%' and  t.dispatchOther='?' and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus='?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  order by t.orderTime desc";
		String sumSql = " select sum(t.price) from TOrderInfo t where t.ext1 = '?' and t.dispatchName like '%?%' and  t.dispatchOther='?' and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus='?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  order by t.orderTime desc";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getExt1()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getDispatchName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getDispatchOther()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrdernum()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCreatorName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderStatus()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCustName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getTel()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerU()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerD()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		sumSql = SQLCutter.cut(sumSql, paramsList);
		
		List amountRst = orderQueryDAO.query(sumSql);
		if(amountRst != null && amountRst.size()>0 && amountRst.get(0) !=null){
			double amount = ((Double)(amountRst.get(0))).doubleValue();
			page.setTotalAmount(amount);
		}
		
		int total = ((Long)(orderQueryDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		
		return orderQueryDAO.queryOrderInfo(querySql,page);
	}
	
	
	public List<Object[]> queryPayedList(TOrderInfo order, Page page, TUser u) {
		String sql = " select t,p from TOrderInfo t,TTradInfo p where t.ordernum=p.ordernum and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus='?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  order by t.orderTime desc";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrdernum()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCreatorName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderStatus()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCustName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getTel()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerU()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerD()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		
		int total = ((Long)(orderQueryDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		return orderQueryDAO.queryPayedList(querySql,page);
	}



	public List<TOrderDetail> loadOrderDetails(TOrderInfo order,TUser u) {
		
		String sql = " select t from TOrderDetail t where t.ordernum= '"+order.getOrdernum()+"' order by t.itemType";
		
		return orderQueryDAO.queryOrderDetail(sql);
	}

	//工单完成   生成送货单
	public void doneOrder(TOrderInfo order,String sendNum, TUser u) {
		
		Integer status = Integer.parseInt(order.getOrderStatus(), 2);
		Integer result = status|OrderQueryAction.DONE;
		String str = Integer.toBinaryString(result);
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<5-str.length();i++){
			buf.append("0");
		}
		order.setOrderStatus(buf.toString()+str);
		
		
		TOrderInfo po = orderQueryDAO.loadOrderInfo(order.getId(), u);
		po.setDoneTime(new Date());
		po.setOrderStatus(order.getOrderStatus());
		
		orderQueryDAO.update(po);
		
		TSendProduct sendProduct = new TSendProduct();
		sendProduct.setCreateTime(new Date());
		sendProduct.setCustName(order.getCustName());
		if("3".equals(StringUtils.trimToEmpty(po.getDeliverMethod()))){
			sendProduct.setDoneFlag("0");
			sendProduct.setDoneTime(new Date());
		}
		else{
			sendProduct.setDoneFlag("1");
		}
		sendProduct.setStatus(po.getDeliverMethod());
		sendProduct.setMoney(order.getPrice());
		sendProduct.setOrdernum(order.getOrdernum());
		sendProduct.setTel(order.getTel());
		sendProduct.setSendNo(sendNum);
		deliverDAO.save(sendProduct);
		
	}

	public void updateOrderInfoStatus(TOrderInfo order, TUser u) {
		
		String sql = " update TOrderInfo o set o.orderStatus='"+order.getOrderStatus()+"' where o.id = "+order.getId()+" ";
		orderQueryDAO.executeUpdate(sql);
	}

	public TOrderInfo loadOrderByNum(String ordernum, TUser u) {
		return orderQueryDAO.loadOrderInfoByNum(ordernum,u);
	}

	public TOrderInfo loadOrderById(Integer id, TUser u) {
		TOrderInfo order = orderQueryDAO.loadOrderInfo(id, u);
		
		String sql = " from TStakeHolder t where t.relaBusi='ORDER' and t.relaStatus='0' and t.relaType='ORDER_BIND' and t.relaValue='"+order.getOrdernum()+"' ";
		List<TStakeHolder> bindHolderList = orderQueryDAO.loadStakeHolders(sql);
		
		if(bindHolderList!=null&&bindHolderList.size()>0){
			StringBuffer buf1 = new StringBuffer();
			StringBuffer buf2 = new StringBuffer();
			for(TStakeHolder h:bindHolderList){
				buf1.append(h.getUserName()).append(";");
				buf2.append(h.getUid()).append(",");
			}
			
			order.setBindingOpr(buf2.substring(0,buf2.length()-1));
			order.setBindOprNames(buf1.substring(0, buf1.length()-1));
			
		}
		
		sql = " from TStakeHolder t where t.relaBusi='ORDER' and t.relaStatus='0' and t.relaType='ORDER_TYPE' and t.relaValue='"+order.getOrdernum()+"' ";
		List<TStakeHolder> typeHolderList = orderQueryDAO.loadStakeHolders(sql);
		
		if(typeHolderList!=null&&typeHolderList.size()>0){
			StringBuffer buf1 = new StringBuffer();
			StringBuffer buf2 = new StringBuffer();
			for(TStakeHolder h:typeHolderList){
				buf1.append(h.getUserName()).append(";");
				buf2.append(h.getUid()).append(",");
			}
			
			order.setTypesetOpr(buf2.substring(0,buf2.length()-1));
			order.setTypeOprNames(buf1.substring(0, buf1.length()-1));
		}
		
		return order;
	}

	public List<TOrderInfo> queryDoneOrderInfo(TOrderInfo order, Page page, TUser u) {
		
		if(order.getTradStatus()!=null){
			if("0".equals(order.getTradStatus().trim())){
				order.setOrderStatus("_1000");
			}
			else if("1".equals(order.getTradStatus().trim())){
				order.setOrderStatus("_1_1_");
			}
			else if("2".equals(order.getTradStatus().trim())){
				order.setOrderStatus("_1_10");
			}
			else if("3".equals(order.getTradStatus().trim())){
				order.setOrderStatus("_1_11");
			}
		}
		
		String sql = " select t from TOrderInfo t where t.ext1='?' and t.dispatchName like '%?%' and t.dispatchOther='?' and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus like '?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.orderStatus like '_1___' and t.orderStatus not like '__1__'  order by t.orderTime desc";
		String sumSql = " select sum(t.price) from TOrderInfo t where t.ext1='?' and t.dispatchName like '%?%' and t.dispatchOther='?' and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus like '?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.orderStatus like '_1___' and t.orderStatus not like '__1__'  order by t.orderTime desc";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getExt1()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getDispatchName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getDispatchOther()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrdernum()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCreatorName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderStatus()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCustName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getTel()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerU()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerD()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		sumSql = SQLCutter.cut(sumSql, paramsList);
		
		List amountRst = orderQueryDAO.query(sumSql);
		if(amountRst != null && amountRst.size()>0 && amountRst.get(0) !=null){
			double amount = ((Double)(amountRst.get(0))).doubleValue();
			page.setTotalAmount(amount);
		}
		
		int total = ((Long)(orderQueryDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		
		return orderQueryDAO.queryOrderInfo(querySql,page);
	}

	public List<TOrderInfo> queryTrashOrderInfo(TOrderInfo order, Page page, TUser u) {
		String sql = " select t from TOrderInfo t where t.dispatchName like '%?%' and  t.dispatchOther='?' and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus='?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.orderStatus like '__1__'  order by t.orderTime desc";
		String sumSql = " select sum(t.price) from TOrderInfo t where t.dispatchName like '%?%' and  t.dispatchOther='?' and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus='?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.orderStatus like '__1__'  order by t.orderTime desc";
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getDispatchName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getDispatchOther()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrdernum()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCreatorName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderStatus()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCustName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getTel()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerU()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerD()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		sumSql = SQLCutter.cut(sumSql, paramsList);
		
		List amountRst = orderQueryDAO.query(sumSql);
		if(amountRst != null && amountRst.size()>0 && amountRst.get(0) !=null){
			double amount = ((Double)(amountRst.get(0))).doubleValue();
			page.setTotalAmount(amount);
		}
		int total = ((Long)(orderQueryDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		
		
		
		return orderQueryDAO.queryOrderInfo(querySql,page);
	}

	public List<TOrderInfo> queryUnfinishOrderInfo(TOrderInfo order, Page page, TUser u) {
		String sql = " select t from TOrderInfo t where t.dispatchName like '%?%' and t.dispatchOther='?' and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus='?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.orderStatus like '_0___' and t.orderStatus not like '__1__'  order by t.orderTime desc";
		String sumSql = " select sum(t.price) from TOrderInfo t where t.dispatchName like '%?%' and t.dispatchOther='?' and t.ordernum='?' and t.creatorName like '%?%' and t.orderStatus='?' and t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.orderStatus like '_0___' and t.orderStatus not like '__1__'  order by t.orderTime desc";
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getDispatchName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getDispatchOther()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrdernum()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCreatorName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderStatus()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getCustName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getTel()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerU()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(order.getOrderTimePickerD()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		sumSql = SQLCutter.cut(sumSql, paramsList);
		
		List amountRst = orderQueryDAO.query(sumSql);
		if(amountRst != null && amountRst.size()>0 && amountRst.get(0) !=null){
			double amount = ((Double)(amountRst.get(0))).doubleValue();
			page.setTotalAmount(amount);
		}
		int total = ((Long)(orderQueryDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		
		return orderQueryDAO.queryOrderInfo(querySql,page);
	}

	public List<Object[]> getDetailStaticData(TOrderInfo order, TUser u) {
		
		String sql = " select t.itemType,count(*),sum(total) from TOrderDetail t where t.ordernum='"+order.getOrdernum()+"'  group by itemType ";
		
		
		return orderQueryDAO.query(sql);
	}

	
	
	public List<TStakeHolder> queryStakeHolders(TOrderInfo order, String orderBind) {
		
		String sql = " select t from  TStakeHolder t where t.relaValue = '"+order.getOrdernum()+"' and t.relaBusi='ORDER' and t.relaType = '"+orderBind+"' ";

		return orderQueryDAO.query(sql);
	}

	
	public void trashOrder(TOrderInfo order, TUser u) {
		Integer status = Integer.parseInt(order.getOrderStatus(), 2);
		Integer result = status|OrderQueryAction.TRASH;
		String str = Integer.toBinaryString(result);
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<5-str.length();i++){
			buf.append("0");
		}
		order.setOrderStatus(buf.toString()+str);
		
		TOrderInfo po = orderQueryDAO.loadOrderInfo(order.getId(), u);
		po.setOrderStatus(order.getOrderStatus());
		po.setTrashMemo(order.getTrashMemo());
		po.setTrashOprName(order.getTrashOprName());
		
		orderQueryDAO.update(po);
	}


	public Report getIfeeReport(IFeeBean q, TUser u, Map<String, Map<String, String>> sysDict) {
		
		Map<String,String> payTypeMap = sysDict.get("payType");
		Map<String,String> ifeeTypeMap = sysDict.get("ifeeType");
		Map<String,String> ext1Map = sysDict.get("payCycleType");
		
		
		Report report=new Report();
		
		report.setHeadStyleClass("part_edit_head2");
		report.setBodyStyleClass("part_edit_body");
		report.setBodyTRStyleClass("gridSecondTablesubContent5");
		report.setHeadTRStyleClass("gridSecondTablesubContent5");
		
		if("1".equals(q.getDispatchOther())){
			report.setTitle("OT单 营帐统计报表["+((q.getUptime()==null)?"":q.getUptime())+" -- "+((q.getDowntime()==null||q.getDowntime().trim().length()==0)?"当前":q.getDowntime())+"]");
		}
		else{
			report.setTitle("非OT 营帐统计报表["+((q.getUptime()==null)?"":q.getUptime())+" -- "+((q.getDowntime()==null||q.getDowntime().trim().length()==0)?"当前":q.getDowntime())+"]");
		}
		
		
		
		List<IFeeBean> result = getDataList(q, u);
		
		Column col1 = new Column(report);
		col1.setTitle("工单所处状态");
		col1.setKey("orderStatus");
		
		Column col2 = new Column(report);
		col2.setTitle("结算类型");
		col2.setKey("feeType");
		
		Column col3 = new Column(report);
		col3.setTitle("是否月结");
		col3.setKey("ext1");
		
		Column col4 = new Column(report);
		col4.setTitle("应收款额");
		col4.setKey("yfee");
		
		Column col5 = new Column(report);
		col5.setTitle("实收款额");
		col5.setKey("acFee");
		
		if("1".equals(q.getDispatchOther())){
			Column col6 = new Column(report);
			col6.setTitle("应付款额");
			col6.setKey("payFee");
		}
		
		
		Group g1 = new Group(report);
		Group g2 = new Group(g1);
		Group g3 = new Group(g2);
		
		DataTable dataTable = report.getDataTable();
		
		Integer rowKey = 0;
		Double totalYFee = 0.0D;
		Double totalAcFee = 0.0D;
		Double totalPayFee = 0.0D;
		
		DecimalFormat df =new DecimalFormat("0.00");
		
		for(IFeeBean fee:result){
			
			dataTable.setData(rowKey, "orderStatus", ifeeTypeMap.get(fee.getIfeeType()));
			dataTable.setData(rowKey, "feeType", payTypeMap.get(fee.getPayType()));
			dataTable.setData(rowKey, "ext1", ext1Map.get(fee.getExt1()));
			dataTable.setData(rowKey, "yfee", df.format(fee.getFee()));
			dataTable.setData(rowKey, "acFee", df.format(fee.getAcFee()));
			
			totalYFee+=fee.getFee();
			totalAcFee+=fee.getAcFee();
			
			if("1".equals(q.getDispatchOther())){
				dataTable.setData(rowKey, "payFee", fee.getPayFee());
				totalPayFee+=fee.getPayFee();
			}
			
			rowKey+=1;
		}
		
		Foot foot = new Foot(report);
		
		FootSumColumn fSumCol1 = new FootSumColumn(foot,col4);
		fSumCol1.setLabelProvider(LabelProvider.getNumberLabelProvider("0.00"));
		FootSumColumn fSumCol2 = new FootSumColumn(foot,col5);
		fSumCol2.setLabelProvider(LabelProvider.getNumberLabelProvider("0.00"));
		
		
		foot.setCls("foot_cls");
		
		return report;
	}


	private List<IFeeBean> getDataList(IFeeBean q, TUser u) {
		StringBuffer sqlbuf = new StringBuffer();
		String sql = "";
		
		String sql0 = " select t.payType,t.ext1,sum(t.price),0.0 from TOrderInfo t where t.orderStatus like '_00__' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  and t.payType='?' and t.ext1= '?' and t.dispatchOther='?' group by t.payType,t.ext1 ";
		String sql1 = " select t.payType,t.ext1,sum(t.price),0.0 from TOrderInfo t where t.orderStatus like '_100_' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.payType='?' and t.ext1= '?' and t.dispatchOther='?' group by t.payType,t.ext1 ";
		String sql2 = " select t.feeType,o.ext1,sum(t.yfee),sum(t.acFee) from TTradInfo t,TOrderInfo o where t.ordernum=o.ordernum and t.validStatus is null and t.tradTime>=str_to_date('?', '%Y-%m-%d') and t.tradTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  and t.feeType='?' and o.ext1= '?' and o.dispatchOther='?' group by t.feeType, o.ext1 ";
		String sql3 = " select t.feeType,o.ext1,sum(t.yfee),sum(t.acFee) from TTradInfo t,TOrderInfo o where t.ordernum=o.ordernum and t.validStatus ='0' and t.tradTime>=str_to_date('?', '%Y-%m-%d') and t.tradTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  and t.feeType='?' and o.ext1= '?' and o.dispatchOther='?' group by t.feeType, o.ext1 ";
		
		
		List<IFeeBean> result = new ArrayList<IFeeBean>();
		
		if("0".equals(q.getIfeeType())){
			result = getIfeeDataList(sql0,q,u);
		}
		else if("1".equals(q.getIfeeType())){
			result = getIfeeDataList(sql1,q,u);
		}
		else if("2".equals(q.getIfeeType())){
			result = getIfeeDataList(sql2,q,u);
		}
		else if("3".equals(q.getIfeeType())){
			result = getIfeeDataList(sql3,q,u);
		}
		else{
			q.setIfeeType("0");
			List<IFeeBean> result0 = getIfeeDataList(sql0,q,u);
			
			q.setIfeeType("1");
			List<IFeeBean> result1 = getIfeeDataList(sql1,q,u);
			
			q.setIfeeType("2");
			List<IFeeBean> result2 = getIfeeDataList(sql2,q,u);
			
			q.setIfeeType("3");
			List<IFeeBean> result3 = getIfeeDataList(sql3,q,u);
			
			q.setIfeeType(null);
			
			
			result.addAll(result0);
			result.addAll(result1);
			result.addAll(result2);
			result.addAll(result3);
		}
		
		return result;
	}


	private List<IFeeBean> getIfeeDataList(String sql, IFeeBean q, TUser u) {
		List<IFeeBean> result = new ArrayList<IFeeBean>();
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDowntime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getPayType()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getExt1()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDispatchOther()));
		
		sql = SQLCutter.cut(sql, paramsList);
		
		List<Object[]> data = orderQueryDAO.querySql(sql,u);
		if(data==null||data.size()==0){
			return result;
		}
		
		for(Object[] rowtemp:data){
			IFeeBean ifee = new IFeeBean();
			ifee.setPayType((String)rowtemp[0]);
			ifee.setExt1((String)rowtemp[1]);
			ifee.setFee((Double)rowtemp[2]);
			ifee.setAcFee((Double)rowtemp[3]);
			
			ifee.setUptime(q.getUptime());
			ifee.setDowntime(q.getDowntime());
			ifee.setIfeeType(q.getIfeeType());
			
			result.add(ifee);
		}
		
		return result;
	}


	public List<IFeeBean> getTradRecList(IFeeBean q, Page page, TUser u) {
		
		List<IFeeBean> result = new ArrayList<IFeeBean>();
		
		String sql = " select t,o from TTradInfo t, TOrderInfo o where t.ordernum=o.ordernum and t.tradTime>=str_to_date('?', '%Y-%m-%d') and t.tradTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  and t.feeType='?' and o.dispatchOther='?' order by t.tradTime asc ";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDowntime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getPayType()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDispatchOther()));
		
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		String querySql = SQLCutter.cut(sql, paramsList);
		
		int total = ((Long)(orderQueryDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		List<Object[]> data = orderQueryDAO.queryPayedList(querySql,page);
		
		if(data==null||data.size()==0){
			return result;
		}
		
		for(Object[] rowtemp:data){
			TTradInfo temp = (TTradInfo)rowtemp[0];
			TOrderInfo tempOrder = (TOrderInfo)rowtemp[1];
			
			IFeeBean o = new IFeeBean();
			o.setOrdernum(temp.getOrdernum());
			o.setOrderTime(tempOrder.getOrderTime());
			o.setTradTime(temp.getTradTime());
			o.setYfee(temp.getYfee());
			o.setAcFee(temp.getAcFee());
			o.setOpr(temp.getTradOprName());
			o.setPayType(temp.getFeeType());
			o.setOrderid(tempOrder.getId());
			
			result.add(o);
		}
		
		return result;
	}


	public IFeeBean getTradRecTotal(IFeeBean q, TUser u) {
		
		
		String sql = " select sum(t.yfee),sum(t.acFee) from TTradInfo t, TOrderInfo o where t.ordernum=o.ordernum and t.tradTime>=str_to_date('?', '%Y-%m-%d') and t.tradTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  and t.feeType='?' and o.dispatchOther='?' ";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDowntime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getPayType()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDispatchOther()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		
		List<Object[]> data = orderQueryDAO.querySql(querySql,u);
		
		if(data==null||data.size()==0){
			IFeeBean o = new IFeeBean();
			o.setTotalAcFee(0.0D);
			o.setTotalYfee(0.0D);
			
			return o;
		}
		
		Object[] result = data.get(0);
		
		IFeeBean o = new IFeeBean();
		if(result[0]==null){
			o.setTotalYfee(0.0D);
		}
		else{
			o.setTotalYfee((Double)result[0]);
		}
		
		
		if(result[1]==null){
			o.setTotalAcFee(0.0D);
		}
		else{
			o.setTotalAcFee((Double)result[1]);
		}
		
		
		return o;
		
	}


	public List<TOrderInfo> getBillList(IFeeBean q, TUser u) {
		
		List<TOrderInfo> result = new ArrayList<TOrderInfo>();
		
		String sql = " select t.custId, (select c.shortName from TCustInfo c where c.custId = t.custId ) ,count(*),sum(t.price) from TOrderInfo t where t.orderStatus like '%_100_%' and  t.custName like '%?%' and t.tel like '%?%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.ext1='1'  group by t.custId ";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getCustName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getMobileNo()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDowntime()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		
		List<Object[]> data = orderQueryDAO.querySql(querySql,u);
		
		if(data==null||data.size()==0){
			return result;
		}
		
		for(Object[] rowtemp:data){
			TOrderInfo o = new TOrderInfo();
			o.setCustId((String)rowtemp[0]);
			o.setCustName((String)rowtemp[1]);
			o.setTotalOrderCount(((Long)rowtemp[2]).intValue());
			o.setTotalFee((Double)rowtemp[3]);
			
			result.add(o);
		}
		
		return result;
	}


	public List<TOrderInfo> getBillDetail(IFeeBean q, TUser u) {
		
		String sql = " select t from TOrderInfo t where t.orderStatus like '%_100_%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.custId = '?' and t.ext1='1'  order by t.orderTime asc  ";
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDowntime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getCustId()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		
		List<TOrderInfo> data = orderQueryDAO.querySql(querySql,u);
		
		return data;
	}


	public List<TOrderInfo> getBillDetailExport(IFeeBean q, TUser u) {
		
		String sql = " select t from TOrderInfo t where t.orderStatus like '%_100_%' and t.orderTime>=str_to_date('?', '%Y-%m-%d') and t.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') and t.custId = '?' and t.ext1='1'  order by t.orderTime asc  ";
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDowntime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getCustId()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		
		List<TOrderInfo> data = orderQueryDAO.querySql(querySql,u);
		
		if(data==null||data.size()==0){
			return new ArrayList<TOrderInfo>();
		}
		
		
		StringBuffer buf = new StringBuffer();
		Map<String,TOrderInfo> map = new TreeMap<String,TOrderInfo>();
		for(TOrderInfo o:data){
			o.setOrderDetails(new ArrayList<TOrderDetail>());
			
			map.put(o.getOrdernum(), o);
			
			buf.append("'").append(o.getOrdernum()).append("',");
		}
		
		String orderNums = buf.substring(0,buf.length()-1);
		
		String queryDetailOrderSql = " select l from TOrderDetail l where l.ordernum in ("+orderNums+") order by l.id ";
		List<TOrderDetail> details = orderQueryDAO.querySql(queryDetailOrderSql,u);
		
		if(details!=null&&details.size()>0){
			for(TOrderDetail detail:details){
				TOrderInfo order = map.get(detail.getOrdernum());
				order.getOrderDetails().add(detail);
			}
		}
		
		return data;
	}


	public void genContract(TOrderInfo order, TUser u, TContractInfo contract) {
		List<TOrderDetail> details =  this.loadOrderDetails(order, u);
		
		
		List<TContractItem> itemList = new ArrayList<TContractItem>();
		if(details!=null){
			for(TOrderDetail odetail:details){
				TContractItem temp = new TContractItem();
				temp.setContractNo(contract.getContractNo());
				temp.setItemName(odetail.getItemName());
				temp.setMount(odetail.getAmount());
				temp.setMountUnit(odetail.getUnit());
				temp.setPrice(odetail.getUnitPrice());
				temp.setTotal(odetail.getTotal());
				temp.setRequirement(odetail.getRequest());
				
				itemList.add(temp);
			}
		}
		
		contractDAO.genContractFromOrder(contract,itemList);
		
		
	}


	public TContractInfo alreadyGenContract(TOrderInfo order, TUser u) {
		TContractInfo contract = contractDAO.findContractByOrdernum(order.getOrdernum(),u);
		return contract;
	}
	
	
	
}
