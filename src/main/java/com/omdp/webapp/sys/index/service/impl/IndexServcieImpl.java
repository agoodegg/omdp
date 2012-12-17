package com.omdp.webapp.sys.index.service.impl;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.text.SimpleDateFormat;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.index.dao.IndexDAO;
import com.omdp.webapp.sys.index.service.IndexService;


@Transactional
public class IndexServcieImpl implements IndexService {

	@Resource
	private IndexDAO<TOrderInfo> indexDAO;

	public Long queryTodayOrderNum(TUser u) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String today = sdf.format(cal.getTime());
		
		String sql = " select count(*) from TOrderInfo t where t.orderTime>=str_to_date('"+today+"', '%Y-%m-%d') and t.orderStatus not like '__1__'  and t.dispatchOther='0' ";
		
		Long result = indexDAO.getIntegerResultBySql(sql);
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}
	
	public Long queryWeeklyOrderNum(TUser u) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		String day = sdf.format(cal.getTime());
		
		String sql = " select count(*) from TOrderInfo t where t.orderTime>=str_to_date('"+day+"', '%Y-%m-%d') and t.orderStatus not like '__1__'  and t.dispatchOther='0'  ";
		
		Long result = indexDAO.getIntegerResultBySql(sql);
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}
	

	public Long queryTotalDeliverUnDone(TUser u) {
		String sql = " select count(*) from TSendProduct t where t.doneFlag='0' ";
		Long result = indexDAO.getIntegerResultBySql(sql);
		
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}

	public Long queryUnDoneOrderNum(TUser u) {
		String sql = " select count(*) from TOrderInfo t where  t.orderStatus like '_00__'  and t.dispatchOther='0'  ";
		Long result = indexDAO.getIntegerResultBySql(sql);
		
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}

	public Long queryUnDoneOTOrderNum(TUser u) {
		String sql = " select count(*) from TOrderInfo t where t.orderStatus like '_00__' and t.dispatchOther='1' ";
		Long result = indexDAO.getIntegerResultBySql(sql);
		
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}

	public Long queryUnTradPayOrderNum(TUser u) {
		String sql = "  select count(*) from TOrderInfo t where  t.orderStatus like '_1000' and t.ext1='0'  ";
		Long result = indexDAO.getIntegerResultBySql(sql);
		
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}
	
	public Long queryDelayTradOrderNum(TUser u) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -30);
		String lastMonthDay = sdf.format(cal.getTime());
		
		String sql = " select count(*) from TOrderInfo t where t.orderTime<=str_to_date('"+lastMonthDay+"', '%Y-%m-%d')  and  t.orderStatus like '_1000' and t.ext1='1' ";
		
		Long result = indexDAO.getIntegerResultBySql(sql);
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}
	
	public Long queryUnCheckPayOrderNum(TUser u) {
		String sql = "  select count(*) from TOrderInfo t where  t.orderStatus like '_1010' ";
		Long result = indexDAO.getIntegerResultBySql(sql);
		
		if(result==null){
			return new Long(0);
		}
		else{
			return result;
		}
	}
	
}
