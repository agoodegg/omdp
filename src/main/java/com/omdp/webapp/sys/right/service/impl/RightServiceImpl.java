package com.omdp.webapp.sys.right.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.model.TRoleInfo;
import com.omdp.webapp.model.TRoleRes;
import com.omdp.webapp.model.TRoleResId;
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.right.dao.RightDAO;
import com.omdp.webapp.sys.right.service.RightService;

@Transactional
public class RightServiceImpl implements RightService {

	@Resource
	private RightDAO<TRoleInfo> rightDAO;
	
	public List<TRoleInfo> queryRoleInfoList(TRoleInfo role, Page page, TUser u) {
		String[] params = new String[]{trim(role.getRoleName())};
		String sql = " select t from TRoleInfo t where t.roleName like '%?%' order by t.createTime ";
		String querySql = SQLCutter.cut(sql, params);
		String countSql = SQLCutter.cutToCountSQL(sql, params);
		
		int total = ((Long)(rightDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		return rightDAO.queryRoleInfoList(querySql,page);
	}

	private String trim(String param){
		return StringUtils.trimToNull(StringUtils.trimToEmpty(param).replaceAll("'", "''"));
	}

	public void addRoleInfo(TRoleInfo role, TUser u) {
		role.setCreateTime(new Date());
		rightDAO.save(role);
		
		DecimalFormat dFormat = new DecimalFormat("0000");
		role.setRoleId(dFormat.format(role.getId()));
		
		rightDAO.update(role);
	}

	public TRoleInfo loadRoleInfo(TRoleInfo role, TUser u) {
		TRoleInfo po = rightDAO.load(role.getId(),u);
		return po;
	}

	public void removeRoleInfo(TRoleInfo role, TUser u) {
		String updateSql = "delete TRoleInfo u where u.id = " +role.getId();
		rightDAO.executeUpdate(updateSql);
	}

	public void updateRoleInfo(TRoleInfo role, TUser u) {
		TRoleInfo po = rightDAO.load(role.getId(),u);
		
		po.setRoleName(role.getRoleName());
		po.setRoleDesc(role.getRoleDesc());
		
		rightDAO.update(po);
	}

	public List<TSysRes> getRoleRes(TRoleInfo role, TUser u) {

		TRoleInfo po = rightDAO.load(role.getId(),u);
		
		return rightDAO.getRoleRes(po);
	}

	public void saveRoleRes(Integer id, String resIds, TUser u) {
		TRoleInfo po = rightDAO.load(id,u);
		
		String delSql = " delete from TRoleRes t where t.id.roleId = '"+po.getRoleId()+"'";
		rightDAO.executeUpdate(delSql);
		
		
		if(resIds!=null&&resIds.trim().length()>0){
			String[] ids = StringUtils.split(resIds, ",");
			
			for(String resId:ids){
				TRoleResId idObject = new TRoleResId();
				
				idObject.setResId(resId);
				idObject.setRoleId(po.getRoleId());
				
				TRoleRes resObj = new TRoleRes();
				resObj.setCreateTime(new Date());
				resObj.setOprId(u.getIdNo());
				resObj.setId(idObject);
				
				rightDAO.saveRoleRes(resObj);
			}
		}
	}
	
	
	
}
