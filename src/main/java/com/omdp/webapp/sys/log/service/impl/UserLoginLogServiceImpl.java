package com.omdp.webapp.sys.log.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.util.BaseStringUtils;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserLogin;
import com.omdp.webapp.sys.log.dao.UserLoginLogDAO;
import com.omdp.webapp.sys.log.service.UserLoginLogService;

@Transactional
public class UserLoginLogServiceImpl implements UserLoginLogService {

	@Resource
	private UserLoginLogDAO userLoginLogDAO;
	
	
	public void saveUserLoginLog(TUserLogin userLogin) {
		userLoginLogDAO.saveUserLogin(userLogin);
	}

	public List<TUserLogin> queryUserLogin(TUserLogin log, Page page, TUser u) {
		
		String sql = " from TUserLogin t where t.logType in ('1','0') order by t.logTime desc";
		String countSql = " select count(*) from TUserLogin t where t.logType in ('1','0') ";
		int total = ((Long)(userLoginLogDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		return userLoginLogDAO.queryUserLogin(sql,page);
	}

	public List<TUserLogin> querySystemLog(TUserLogin log, Page page, TUser u) {
		
		String sql = " select t from TUserLogin t where t.logType <>'0' and t.logType<>'1' and t.logType='?' and t.logTime>=str_to_date('?', '%Y-%m-%d') and t.logTime<= str_to_date('? 23:59:59', '%Y-%m-%d %H:%i:%s') order by t.logTime desc";
		
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(BaseStringUtils.ParamTrimToNull(log.getLogType()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(log.getLogTimeU()));
		paramsList.add(BaseStringUtils.ParamTrimToNull(log.getLogTimeD()));
		
		String querySql = SQLCutter.cut(sql, paramsList);
		String countSql = SQLCutter.cutToCountSQL(sql, paramsList);
		
		int total = ((Long)(userLoginLogDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		
		return userLoginLogDAO.queryUserLogin(querySql,page);
		
	}

	public void saveSysLog(TUser u, String oprMsg, String logType) {
		
		userLoginLogDAO.saveSysLog(u, oprMsg, logType);
	}
	
	
	
}
