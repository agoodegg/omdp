package com.omdp.webapp.stat.service.impl;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.util.Calendar;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.util.BaseStringUtils;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.finance.bean.IFeeBean;
import com.omdp.webapp.model.TOrderInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.stat.dao.CustomerBusiStatDAO;
import com.omdp.webapp.stat.service.CustomerBusiStatService;


@Transactional
public class CustomerBusiStatServiceImpl implements CustomerBusiStatService {

	@Resource
	private CustomerBusiStatDAO<TOrderInfo> customerBusiStatDAO;

	public List<Object[]> queryBusiAchRank(IFeeBean q, TUser u) {
		List<Object[]> result = new ArrayList<Object[]>();
		
		String sql = " select l.itemType, sum(l.total) from TOrderInfo t,TOrderDetail l,TTradInfo o where  t.ordernum = o.ordernum and t.ordernum=l.ordernum and o.tradTime>=str_to_date('?', '%Y-%m-%d') and o.tradTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s')  and o.feeType='?' and t.dispatchOther='?' group by l.itemType ";

		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getUptime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDowntime()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getPayType()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(q.getDispatchOther()));
		
		sql = SQLCutter.cut(sql, paramsList);
		
		
		List<Object[]> data = customerBusiStatDAO.querySql(sql,u);
		
		if(data==null||data.size()==0){
			return result;
		}
		
		return data;
	}

	public List<Object[]> queryBusiMonthLine(IFeeBean q, TUser u) {
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select g.month,sum(g.price) from ( select t.price as price ,date_format(t.order_Time,'%m') as month from T_Order_Info t where t.order_Time >= str_to_date('"+q.getYear()+"-01-01', '%Y-%m-%d') and t.order_Time <= str_to_date('"+q.getYear()+"-12-31 23:59:59', '%Y-%m-%d %H:%i:%s') and t.order_Status not like '__1__' ");
		if(StringUtils.trimToNull(q.getDispatchOther()) != null){
			sqlBuf.append(" and t.dispatch_Other = '"+StringUtils.trimToEmpty(q.getDispatchOther()).replaceAll("'", "''")+"' ");
		}
		sqlBuf.append(" ) g group by g.month order by g.month ");
		String sql =  sqlBuf.toString();
		
		List<Object[]> data = customerBusiStatDAO.queryNativeSql(sql,u);
		
		return data;
	}

	public List<Object[]> queryDayCash(IFeeBean q, TUser u, Page page) {
		
		String sql = " select tmp.fee_type,date_format(tmp.trad_date,'%Y-%m-%d'),tmp.yfee_stat,tmp.ac_fee_stat from ( select t.fee_type ,date(t.trad_time) trad_date, sum(yfee) yfee_stat,sum(ac_fee) ac_fee_stat from t_trad_info t where t.fee_type='"+q.getFeeType()+"' group by t.fee_type,date(t.trad_time) ) tmp order by tmp.trad_date desc ";
		
		List<Object[]> data = customerBusiStatDAO.queryNativeSql(sql,u,page);
		
		String countSql = " select count(fee_type) from ( select t.fee_type ,date(t.trad_time) trad_date, sum(yfee) yfee_stat,sum(ac_fee) ac_fee_stat from t_trad_info t where t.fee_type='"+q.getFeeType()+"' group by t.fee_type,date(t.trad_time) ) tmp    ";
		
		List<Object> total = customerBusiStatDAO.nativeQuery(countSql);
		
		page.setTotalCount(((BigInteger)total.get(0)).longValue());
		
		return data;
	}

	public List<Object[]> queryDeltaTop20(IFeeBean q, TUser u, Page page) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(q.getYear(),10));
		cal.set(Calendar.MONTH, Integer.parseInt(q.getMontn(),10)-1);
		
		int maxDay = cal.getMaximum(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.DAY_OF_MONTH,1);
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(cal.getTime());
		
		cal.set(Calendar.DAY_OF_MONTH,maxDay);
		String endDate = sdf.format(cal.getTime());
		
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select g.cust_name, g.total, g.cust_id from ( select t.cust_id, t.cust_name, sum(t.price) as total from T_Order_Info t, t_cust_info b where t.cust_id=b.cust_id and t.order_Time >= str_to_date('"+startDate+"', '%Y-%m-%d') and t.order_Time <= str_to_date('"+endDate+" 23:59:59', '%Y-%m-%d %H:%i:%s') and b.create_time>= str_to_date('"+startDate+"', '%Y-%m-%d') and b.create_time <= str_to_date('"+endDate+" 23:59:59', '%Y-%m-%d %H:%i:%s') and t.order_Status not like '__1__' ");
		if(StringUtils.trimToNull(q.getDispatchOther()) != null){
			sqlBuf.append(" and t.dispatch_Other = '"+StringUtils.trimToEmpty(q.getDispatchOther()).replaceAll("'", "''")+"' ");
		}
		sqlBuf.append(" group by t.cust_id, t.cust_name ) g order by g.total desc limit 20");
		String sql =  sqlBuf.toString();
		
		//System.out.println(sql);
		
		List<Object[]> data = customerBusiStatDAO.queryNativeSql(sql,u);
		
		return data;
		
	}

	public List<Object[]> queryOldTop20(IFeeBean q, TUser u, Page page) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(q.getYear(),10));
		cal.set(Calendar.MONTH, Integer.parseInt(q.getMontn(),10)-1);
		
		int maxDay = cal.getMaximum(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.DAY_OF_MONTH,1);
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(cal.getTime());
		
		cal.set(Calendar.DAY_OF_MONTH,maxDay);
		String endDate = sdf.format(cal.getTime());
		
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select g.cust_name, g.total, g.cust_id from ( select t.cust_id, t.cust_name, sum(t.price) as total from T_Order_Info t, t_cust_info b where t.cust_id=b.cust_id and t.order_Time >= str_to_date('"+startDate+"', '%Y-%m-%d') and t.order_Time <= str_to_date('"+endDate+" 23:59:59', '%Y-%m-%d %H:%i:%s') and b.create_time< str_to_date('"+startDate+"', '%Y-%m-%d') and t.order_Status not like '__1__' ");
		if(StringUtils.trimToNull(q.getDispatchOther()) != null){
			sqlBuf.append(" and t.dispatch_Other = '"+StringUtils.trimToEmpty(q.getDispatchOther()).replaceAll("'", "''")+"' ");
		}
		sqlBuf.append(" group by t.cust_id, t.cust_name ) g order by g.total desc limit 20");
		String sql =  sqlBuf.toString();
		//System.out.println(sql);
		List<Object[]> data = customerBusiStatDAO.queryNativeSql(sql,u);
		
		return data;
	}

	public List<Object[]> queryDayLine(IFeeBean q, TUser u, Page page) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(q.getYear(),10));
		cal.set(Calendar.MONTH, Integer.parseInt(q.getMontn(),10)-1);
		
		int maxDay = cal.getMaximum(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.DAY_OF_MONTH,1);
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(cal.getTime());
		
		cal.set(Calendar.DAY_OF_MONTH,maxDay);
		String endDate = sdf.format(cal.getTime());
		
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select g.day,sum(g.price) from ( select t.price as price ,date_format(t.order_Time,'%d') as day from T_Order_Info t where t.order_Time >= str_to_date('"+startDate+"', '%Y-%m-%d') and t.order_Time <= str_to_date('"+endDate+" 23:59:59', '%Y-%m-%d %H:%i:%s') and t.order_Status not like '__1__' ");
		if(StringUtils.trimToNull(q.getDispatchOther()) != null){
			sqlBuf.append(" and t.dispatch_Other = '"+StringUtils.trimToEmpty(q.getDispatchOther()).replaceAll("'", "''")+"' ");
		}
		sqlBuf.append(" ) g group by g.day order by g.day ");
		String sql =  sqlBuf.toString();
		//System.out.println(sql);
		List<Object[]> data = customerBusiStatDAO.queryNativeSql(sql,u);
		
		return data;
	}

	
	public List<Object[]> queryCustMonthLine(IFeeBean q, TUser u) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select g.month,sum(g.price) from ( select t.price as price ,date_format(t.order_Time,'%m') as month from T_Order_Info t where t.cust_Id='"+q.getCustId().trim()+"' and  t.order_Time >= str_to_date('"+q.getYear()+"-01-01', '%Y-%m-%d') and t.order_Time <= str_to_date('"+q.getYear()+"-12-31 23:59:59', '%Y-%m-%d %H:%i:%s') and t.order_Status not like '__1__' ");
		if(StringUtils.trimToNull(q.getDispatchOther()) != null){
			sqlBuf.append(" and t.dispatch_Other = '"+StringUtils.trimToEmpty(q.getDispatchOther()).replaceAll("'", "''")+"' ");
		}
		sqlBuf.append(" ) g group by g.month order by g.month ");
		String sql =  sqlBuf.toString();
		
		List<Object[]> data = customerBusiStatDAO.queryNativeSql(sql,u);
		
		return data;
	}
	
	
}
