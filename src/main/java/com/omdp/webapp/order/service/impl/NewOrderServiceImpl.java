package com.omdp.webapp.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.customer.dao.CustomerManageDAO;
import com.omdp.webapp.employee.dao.EmployeeDAO;
import com.omdp.webapp.model.Sequence;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TOrderDetail;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TStakeHolder;
import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.order.dao.NewOrderDAO;
import com.omdp.webapp.order.dao.OrderQueryDAO;
import com.omdp.webapp.order.service.NewOrderService;

@Transactional
public class NewOrderServiceImpl implements NewOrderService {

	@Resource
	private NewOrderDAO<TOrderInfo> newOrderDAO;
	
	@Resource
	private EmployeeDAO<TUser> employeeDAO;
	
	@Resource
	private CustomerManageDAO<TCustInfo> customerManageDAO;
	
	@Resource
	private OrderQueryDAO<TOrderInfo> orderQueryDAO;

	public void saveNewOrder(TOrderInfo order, String sec, TUser u) {
		order.setCreator(String.valueOf(u.getId()));
		
		Double allTotal = newOrderDAO.getAllTotalPrice(sec,u);
		newOrderDAO.updateDetail(sec,order.getOrdernum(),u);
		
		if(allTotal==null){
			order.setPrice(0.00D);
		}
		else{
			order.setPrice(allTotal);
		}
		
		newOrderDAO.save(order);
		
		
		if(order.getBindingOpr()!=null&&order.getBindingOpr().trim().length()>0){
			String[] bindOprs = StringUtils.trimToEmpty(order.getBindingOpr()).split(",");
			
			
			for(String opr:bindOprs){
				
				Integer uid = Integer.parseInt(opr);
				TUser po = employeeDAO.load(uid,u);
				
				
				TStakeHolder holder = new TStakeHolder();
				holder.setCreateName(u.getUserName());
				holder.setCreateTime(new Date());
				holder.setCreatorId(u.getIdNo());
				holder.setRelaStatus("0");
				holder.setIdNo(po.getIdNo());
				holder.setUserName(po.getUserName());
				holder.setUid(po.getId());
				holder.setRelaBusi(TStakeHolder.ORDER);
				holder.setRelaType(TStakeHolder.ORDER_BIND);
				holder.setRelaValue(order.getOrdernum());
				
				newOrderDAO.saveHolder(holder,u);
			}
		}
		if(order.getTypesetOpr()!=null&&order.getTypesetOpr().trim().length()>0){
			String[] typeOprs = StringUtils.trimToEmpty(order.getTypesetOpr()).split(",");
			
			
			for(String opr:typeOprs){
				
				Integer uid = Integer.parseInt(opr);
				TUser po = employeeDAO.load(uid,u);
				
				
				TStakeHolder holder = new TStakeHolder();
				holder.setCreateName(u.getUserName());
				holder.setCreateTime(new Date());
				holder.setCreatorId(u.getIdNo());
				holder.setRelaStatus("0");
				holder.setIdNo(po.getIdNo());
				holder.setUserName(po.getUserName());
				holder.setUid(po.getId());
				holder.setRelaBusi(TStakeHolder.ORDER);
				holder.setRelaType(TStakeHolder.ORDER_TYPE);
				holder.setRelaValue(order.getOrdernum());
				
				newOrderDAO.saveHolder(holder,u);
			}
		}
		
		
		
	}

	//用于自动提示
	public List<TCustInfo> queryCustList(TCustInfo cust, Page page, TUser u) {
		String sql = " from TCustInfo t where t.status='0' and t.shortName like '%"+StringUtils.trimToEmpty(cust.getShortName()).replaceAll("'", "''")+"%' order by t.shortName asc ";
		return customerManageDAO.queryCustList(sql,page);
	}

	
	public synchronized Integer updateSequence(String prefix, String name) {
		Sequence seq = new Sequence();
		seq.setName(name);
		seq = (Sequence)(newOrderDAO.getSeq(seq));
		
		if(prefix.equals(seq.getPrefix())){
			Integer val = seq.getCurrentValue();
			seq.setCurrentValue(seq.getCurrentValue()+seq.getIncrement());
			newOrderDAO.updateSeq(seq);
			return val;
		}
		else{
			Integer val = 1;
			seq.setCurrentValue(1+seq.getIncrement());
			seq.setPrefix(prefix);
			newOrderDAO.updateSeq(seq);
			return val;
		}
	}

	public List<TOrderDetail> loadOrderDetailBySec(String sec, String busiType, TUser u) {
		return newOrderDAO.query(" from TOrderDetail t where t.ordernum = '"+sec+"' and t.itemType='"+busiType+"' order by t.id ");
	}

	public List<TSysParam> loadFieldListByBusi(String busiType) {
		return newOrderDAO.query(" from TSysParam t where t.ftypeCode = 'BUSI_"+busiType+"' order by t.priorLvl,t.id ");
	}

	public void saveNewOrderDetail(TOrderDetail orderDetail, TUser u) {
		newOrderDAO.save(orderDetail);
		

		Double allTotal = newOrderDAO.getAllTotalPrice(orderDetail.getOrdernum(),u);
		
		TOrderInfo order = orderQueryDAO.loadOrderInfoByNum(orderDetail.getOrdernum(),u);
		
		if(order!=null){
			order.setPrice(allTotal);
			
			newOrderDAO.update(order);
		}
	}

	public void delOrderDetail(TOrderDetail orderDetail, TUser u) {

		TOrderDetail po = newOrderDAO.loadOrderDetailById(orderDetail.getId(),u);
		
		newOrderDAO.executeUpdate(" delete from TOrderDetail t where t.id="+orderDetail.getId()+" ");
		

		Double allTotal = newOrderDAO.getAllTotalPrice(po.getOrdernum(),u);
		
		TOrderInfo order = orderQueryDAO.loadOrderInfoByNum(po.getOrdernum(),u);
		
		if(order!=null){
			order.setPrice(allTotal);
			
			newOrderDAO.update(order);
		}
	}

	public void updateOrder(TOrderInfo order, TUser u) {
		
		TOrderInfo persistOrder = orderQueryDAO.loadOrderInfoByNum(order.getOrdernum(),u);
		
		Double allTotal = newOrderDAO.getAllTotalPrice(order.getOrdernum(),u);
		persistOrder.setPrice(allTotal);
		
		order.setPrice(allTotal);
		

		persistOrder.setBindingOpr(order.getBindingOpr());
		persistOrder.setCustId(order.getCustId());
		persistOrder.setCustName(order.getCustName());
		persistOrder.setDeliverAddress(order.getDeliverAddress());
		persistOrder.setDeliverMethod(order.getDeliverMethod());
		persistOrder.setDeliverTime(order.getDeliverTime());
		persistOrder.setFilepos(order.getFilepos());
		persistOrder.setFileSrc(order.getFileSrc());
		persistOrder.setLinkMan(order.getLinkMan());
		//persistOrder.setOrderTime(order.getOrderTime());
		persistOrder.setOrderTitle(order.getOrderTitle());
		persistOrder.setTel(order.getTel());
		persistOrder.setSoftware(order.getSoftware());
		persistOrder.setPrintMemo(order.getPrintMemo());
		persistOrder.setPayType(order.getPayType());
		persistOrder.setExt1(order.getExt1());
		
		
		orderQueryDAO.deleteStakeHoldersByOrdernum(order.getOrdernum());
		
		newOrderDAO.update(persistOrder);
		
		if(order.getBindingOpr()!=null&&order.getBindingOpr().trim().length()>0){
			String[] bindOprs = StringUtils.trimToEmpty(order.getBindingOpr()).split(",");
			
			for(String opr:bindOprs){
				
				Integer uid = Integer.parseInt(opr);
				TUser po = employeeDAO.load(uid,u);
				
				
				TStakeHolder holder = new TStakeHolder();
				holder.setCreateName(u.getUserName());
				holder.setCreateTime(new Date());
				holder.setCreatorId(u.getIdNo());
				holder.setRelaStatus("0");
				holder.setIdNo(po.getIdNo());
				holder.setUserName(po.getUserName());
				holder.setUid(po.getId());
				holder.setRelaBusi(TStakeHolder.ORDER);
				holder.setRelaType(TStakeHolder.ORDER_BIND);
				holder.setRelaValue(order.getOrdernum());
				
				newOrderDAO.saveHolder(holder,u);
			}
		}
		if(order.getTypesetOpr()!=null&&order.getTypesetOpr().trim().length()>0){
			String[] typeOprs = StringUtils.trimToEmpty(order.getTypesetOpr()).split(",");
			
			for(String opr:typeOprs){
				
				Integer uid = Integer.parseInt(opr);
				TUser po = employeeDAO.load(uid,u);
				
				
				TStakeHolder holder = new TStakeHolder();
				holder.setCreateName(u.getUserName());
				holder.setCreateTime(new Date());
				holder.setCreatorId(u.getIdNo());
				holder.setRelaStatus("0");
				holder.setIdNo(po.getIdNo());
				holder.setUserName(po.getUserName());
				holder.setUid(po.getId());
				holder.setRelaBusi(TStakeHolder.ORDER);
				holder.setRelaType(TStakeHolder.ORDER_TYPE);
				holder.setRelaValue(order.getOrdernum());
				
				newOrderDAO.saveHolder(holder,u);
			}
		}
		
		
		
		
	}

	
	
	public void updateOrderDetail(TOrderDetail orderDetail, TUser u) {
		
		TOrderDetail po = newOrderDAO.loadOrderDetailById(orderDetail.getId(),u);
		
		po.setItemName(orderDetail.getItemName());
		po.setEsp1(orderDetail.getEsp1());
		po.setEsp2(orderDetail.getEsp2());
		po.setEsp3(orderDetail.getEsp3());
		po.setEsp4(orderDetail.getEsp4());
		po.setUnit(orderDetail.getUnit());
		po.setUnitPrice(orderDetail.getUnitPrice());
		po.setTotal(orderDetail.getTotal());
		po.setAmount(orderDetail.getAmount());
		po.setRequest(orderDetail.getRequest());
		
		
		newOrderDAO.updateDetail(po, u);
		
		Double allTotal = newOrderDAO.getAllTotalPrice(orderDetail.getOrdernum(),u);
		
		TOrderInfo order = orderQueryDAO.loadOrderInfoByNum(orderDetail.getOrdernum(),u);
		
		if(order!=null){
			order.setPrice(allTotal);
			
			newOrderDAO.update(order);
		}
	}

	public List<TCustInfo> queryUserInfoList(TCustInfo cust, Page page, TUser u) {
		
		String sql = " select t from TCustInfo t where t.shortName like '%"+StringUtils.trimToEmpty(cust.getShortName()).replaceAll("'", "''")+"%' and t.status<>'1' order by t.shortName ";
		
		String countSql = " select count(*) from TCustInfo t where t.shortName like '%"+StringUtils.trimToEmpty(cust.getShortName()).replaceAll("'", "''")+"%' and t.status<>'1' ";
		
		int total = ((Long)(orderQueryDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		return newOrderDAO.queryByPage(sql,page);
	}
	
}
