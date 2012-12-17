package com.omdp.webapp.employee.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.employee.dao.EmployeeDAO;
import com.omdp.webapp.employee.service.EmployeeService;
import com.omdp.webapp.model.TRoleInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.model.TUserRole;
import com.omdp.webapp.model.TUserRoleId;


@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeDAO<TUser> employeeDAO;

	public List<TUser> queryUserInfoList(TUser user, Page page, TUser u) {
		
		String[] params = new String[]{trim(user.getUserAccount()),trim(user.getUserName())};
		String sql = " select t from TUser t where t.isInvalid<>'2' and t.userAccount like '%?%' and t.userName like '%?%' order by t.userAccount ";
		String querySql = SQLCutter.cut(sql, params);
		String countSql = SQLCutter.cutToCountSQL(sql, params);
		
		int total = ((Long)(employeeDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		return employeeDAO.queryUserInfoList(querySql,page);
	}
	
	
	private String trim(String param){
		return StringUtils.trimToNull(StringUtils.trimToEmpty(param).replaceAll("'", "''"));
	}


	public void removeUserByMark(TUser user, TUser u) {
		
		String updateSql = "update TUser u set u.isInvalid='2' where u.id = " +user.getId();
		employeeDAO.executeUpdate(updateSql);
	}


	public TUser loadUser(TUser user, TUser u) {
		TUser po = employeeDAO.load(user.getId(),u);
		
		
		List<TRoleInfo> userRoleList = employeeDAO.loadUserRole(po);
		
		if(userRoleList!=null&&userRoleList.size()>0){
			StringBuffer idsBuf = new StringBuffer();
			StringBuffer namesBuf = new StringBuffer();
			
			for(TRoleInfo otemp:userRoleList){
				idsBuf.append(otemp.getRoleId()).append(",");
				namesBuf.append(otemp.getRoleName()).append(";");
			}
			
			if(idsBuf.length()>0){
				po.setRoleIds(idsBuf.substring(0,idsBuf.length()-1));
			}
			if(namesBuf.length()>0){
				po.setRoleNames(namesBuf.substring(0,namesBuf.length()-1));
			}
		}
		
		return po;
	}


	public void updateUserInfo(TUser user, TUser u) {
		
		TUser po = employeeDAO.load(user.getId(), u);
		
		po.setUserName(user.getUserName());
		po.setUserAccount(user.getUserAccount());
		po.setAge(user.getAge());
		po.setAddress(user.getAddress());
		po.setDeptCd(user.getDeptCd());
		po.setEmail(user.getEmail());
		po.setMemoDesc(user.getMemoDesc());
		po.setGender(user.getGender());
		po.setMobileNo(user.getMobileNo());
		po.setIdNo(user.getIdNo());
		
		employeeDAO.update(po);
		
		
		employeeDAO.clearUserRole(user);
		
		String roleIds = user.getRoleIds();
		if(roleIds!=null&&roleIds.trim().length()>0){
			String[] ids = StringUtils.split(roleIds, ",");
			
			for(String s:ids){
				TUserRoleId roleid = new TUserRoleId();
				roleid.setUserId(user.getIdNo());
				roleid.setRoleId(s);
				
				TUserRole userRoleObject = new TUserRole();
				userRoleObject.setOprId(u.getIdNo());
				userRoleObject.setCreateTime(new Date());
				userRoleObject.setId(roleid);
				
				employeeDAO.saveUserRole(userRoleObject);
			}
		}
	}


	public void addUserInfo(TUser user, TUser u) {
		user.setUserPwd(DigestUtils.md5Hex(user.getUserPwd()));
		user.setIsInvalid("0");
		user.setUpdateTime(new Date());
		employeeDAO.save(user);
		
		String roleIds = user.getRoleIds();
		if(roleIds!=null&&roleIds.trim().length()>0){
			String[] ids = StringUtils.split(roleIds, ",");
			
			for(String s:ids){
				TUserRoleId roleid = new TUserRoleId();
				roleid.setUserId(user.getIdNo());
				roleid.setRoleId(s);
				
				TUserRole userRoleObject = new TUserRole();
				userRoleObject.setOprId(u.getIdNo());
				userRoleObject.setCreateTime(new Date());
				userRoleObject.setId(roleid);
				
				employeeDAO.saveUserRole(userRoleObject);
			}
		}
	}


	
	public void resetUserPassword(TUser user, TUser u) {
		
		TUser po = employeeDAO.load(user.getId(), u);
		
		po.setPwdUpdateTime(new Date());
		po.setUserPwd(DigestUtils.md5Hex("123456"));
		employeeDAO.update(po);
	}


	public TUser loadUserByUserId(String userId, TUser u) {
		return employeeDAO.loadUserByUserId(userId,u);
	}
	
	
	
}
