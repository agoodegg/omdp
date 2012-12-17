package com.omdp.webapp.customer.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.util.BaseStringUtils;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.customer.dao.CustomerManageDAO;
import com.omdp.webapp.customer.service.CustomerManageService;
import com.omdp.webapp.employee.dao.EmployeeDAO;
import com.omdp.webapp.model.TCustInfo;
import com.omdp.webapp.model.TUser;


@Transactional
public class CustomerManageServiceImpl implements CustomerManageService {

	@Resource
	private CustomerManageDAO<TCustInfo> customerManageDAO;
	
	@Resource
	private EmployeeDAO<TUser> employeeDAO;
	

	public void saveNewCust(TCustInfo cust, TUser u) {
		Calendar cal = Calendar.getInstance();
		DecimalFormat dFormat = new DecimalFormat("0000000");
		
		cust.setOprId(u.getIdNo());
		cust.setCreateTime(new Date());
		cust.setCustId(null);
		cust.setStatus("0");
		String zoneCd = cust.getZoneCd();
		if(zoneCd==null||zoneCd.trim().length()==0){
			zoneCd = "00";
		}
		customerManageDAO.save(cust);
		cust.setCustId("SZ"+zoneCd+String.valueOf(cal.get(Calendar.YEAR))+dFormat.format(cust.getId()));
		customerManageDAO.update(cust);
	}


	public List<TCustInfo> queryCustInfo(TCustInfo cust, boolean hasAllCustVisual, Page page, TUser u) {
		
		String sql = " ";
		if(hasAllCustVisual){
			sql = " select t,(select u.userName from TUser u where u.idNo=t.userId),(select u.userName from TUser u where u.idNo=t.oprId) from TCustInfo t where t.status='0' and t.payCycle = '?' and t.custId like '%?%' and t.shortName like '%?%' and t.mobileNo like '%?%' order by t.custId ";
		}
		else{
			sql = " select t,(select u.userName from TUser u where u.idNo=t.userId),(select u.userName from TUser u where u.idNo=t.oprId)  from  TCustInfo t where  t.status='0' and t.payCycle = '?' and t.custId like '%?%' and t.userId = '"+u.getIdNo()+"' and t.shortName like '%?%' and t.mobileNo like '%?%' order by t.custId ";
		}
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getPayCycle()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getCustId()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getShortName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getMobileNo()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		
		int total = ((Long)(customerManageDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		List<Object[]> data =  customerManageDAO.queryCustList(querySql, page);
		
		List<TCustInfo> result = new ArrayList<TCustInfo>();
		if(data==null||data.size()==0){
			return result;
		}
		
		for(Object[] rowtemp:data){
			TCustInfo temp = (TCustInfo)rowtemp[0];
			temp.setUserName((String)rowtemp[1]);
			temp.setOprName((String)rowtemp[2]);
			
			result.add(temp);
		}
		
		return result;
	}


	public TCustInfo loadCustInfoById(Integer id, TUser u) {
		
		return customerManageDAO.loadCustById(id,u);
	}


	public void markDel(TCustInfo t, TUser u) {
		
		TCustInfo cust = customerManageDAO.loadCustById(t.getId(),u);
		cust.setStatus("1");
		
		customerManageDAO.update(cust);
	}


	public void updateCust(TCustInfo cust, TUser u) {
		TCustInfo po = customerManageDAO.loadCustById(cust.getId(),u);
		
		
		po.setShortName(cust.getShortName());
		po.setFullName(cust.getFullName());
		po.setPinyin(cust.getPinyin());
		po.setCustLvl(cust.getCustLvl());
		po.setSource(cust.getSource());
		po.setFax(cust.getFax());
		po.setCreditLvl(cust.getCreditLvl());
		po.setPayType(cust.getPayType());
		po.setBillType(cust.getBillType());
		po.setPayCycle(cust.getPayCycle());
		po.setCredit(cust.getCredit());
		po.setCustType(cust.getCustType());
		po.setMobileNo(cust.getMobileNo());
		po.setPhoneNo(cust.getPhoneNo());
		po.setEmail(cust.getEmail());
		po.setZoneCd(cust.getZoneCd());
		po.setZoneName(cust.getZoneName());
		po.setUserId(cust.getUserId());
		po.setBusiType1(cust.getBusiType1());
		po.setBusiType2(cust.getBusiType2());
		po.setProvince(cust.getProvince());
		po.setCity(cust.getCity());
		po.setAddress(cust.getAddress());
		po.setWebSite(cust.getWebSite());
		po.setIntro(cust.getIntro());
		
		customerManageDAO.update(po);
	}


	public List<TCustInfo> myCustStatFee(TCustInfo cust, boolean hasAllCustVisual, Page page, TUser u) {
		String sql = " ";
		if(hasAllCustVisual){
			sql = " select t,(select u.userName from TUser u where u.id=t.userId),(select u.userName from TUser u where u.idNo=t.oprId),( select sum(o.acFee) from TTradInfo o where o.custCd = t.custId and o.tradTime>=str_to_date('?', '%Y-%m-%d') and o.tradTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') ) from TCustInfo t where t.status='0' and t.shortName like '%?%' and t.mobileNo like '%?%' and t.custId='?' order by t.custId ";
		}
		else{
			sql = " select t,(select u.userName from TUser u where u.id=t.userId),(select u.userName from TUser u where u.idNo=t.oprId),( select sum(o.acFee) from TTradInfo o where o.custCd = t.custId and o.tradTime>=str_to_date('?', '%Y-%m-%d') and o.tradTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') )  from  TCustInfo t where  t.status='0' and t.userId = '"+u.getId()+"' and t.shortName like '%?%' and t.mobileNo like '%?%'  and t.custId='?' order by t.custId ";
		}
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getDowntime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getShortName()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getMobileNo()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(cust.getCustId()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		
		int total = ((Long)(customerManageDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		List<Object[]> data =  customerManageDAO.queryCustList(querySql, page);
		
		List<TCustInfo> result = new ArrayList<TCustInfo>();
		if(data==null||data.size()==0){
			return result;
		}
		
		for(Object[] rowtemp:data){
			TCustInfo temp = (TCustInfo)rowtemp[0];
			temp.setUserName((String)rowtemp[1]);
			temp.setOprName((String)rowtemp[2]);
			if(rowtemp[3]==null){
				temp.setStatFee(0.0);
			}
			else{
				temp.setStatFee((Double)rowtemp[3]);
			}
			result.add(temp);
		}
		
		return result;
	}


	public List<TUser> userClientStatFee(TUser user, Page page, TUser u) {
		List<TUser> result = new ArrayList<TUser>();
		String sql = " select t,(select sum(v.acFee) from TOrderInfo o, TCustInfo c, TTradInfo v where c.userId = t.idNo and o.custId = c.custId and o.orderTime>=str_to_date('?', '%Y-%m-%d') and o.orderTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  and v.ordernum=o.ordernum and o.dispatchOther='?' and v.feeType='?' ),(select count(*) from TCustInfo c  where c.status='0' and  c.userId = t.idNo)  from TUser t where t.isInvalid<>'2' and t.userAccount like '%?%' and t.userName like '%?%' order by t.userAccount ";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(user.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(user.getDowntime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(user.getDispatchOther()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(user.getPayType()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(user.getUserAccount()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(user.getUserName()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		
		int total = ((Long)(employeeDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		List<Object[]> data = employeeDAO.queryUserInfoList(querySql,page);
		
		if(data==null||data.size()==0){
			return result;
		}
		
		for(Object[] rowtemp:data){
			TUser po = (TUser)rowtemp[0];
			if((Double)rowtemp[1]==null){
				po.setAchFee(0.0D);
			}
			else{
				po.setAchFee((Double)rowtemp[1]);
			}
			po.setClientNum(((Long)rowtemp[2]).intValue());
			
			result.add(po);
		}
		
		return result;
	}


	public TCustInfo loadCustInfoByCustId(String custId, TUser u) {
		
		return customerManageDAO.queryCustByCustId(custId,u);
	}


	public boolean existCustName(TCustInfo cust) {
		
		try{
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("shortName", cust.getShortName());
			String sql = " select count(*) from TCustInfo t where t.shortName =:shortName and t.status<>'1' ";
			Long countTotal = (Long)(customerManageDAO.queryForSingleObject(sql,map));
			
			if(countTotal>0L){
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	
	
	
	
}
