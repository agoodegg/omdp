package com.omdp.webapp.sys.dept.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.util.SQLCutter;
import com.omdp.webapp.model.TDeptInfo;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.dept.dao.DeptDAO;
import com.omdp.webapp.sys.dept.service.DeptService;


@Transactional
public class DeptServiceImpl implements DeptService {

	@Resource
	private DeptDAO<TDeptInfo> deptDAO;

	private String trim(String param){
		return StringUtils.trimToNull(StringUtils.trimToEmpty(param).replaceAll("'", "''"));
	}
	
	public List<TDeptInfo> queryDeptInfoList(TDeptInfo dept, Page page, TUser u) {
		
		String[] params = new String[]{trim(dept.getDeptName())};
		String sql = " select t from TDeptInfo t where t.deptName like '%?%' order by t.id ";
		String querySql = SQLCutter.cut(sql, params);
		String countSql = SQLCutter.cutToCountSQL(sql, params);
		
		int total = ((Long)(deptDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		return deptDAO.queryDeptInfoList(querySql,page);
	}

	public TDeptInfo loadDept(TDeptInfo dept, TUser u) {
		TDeptInfo po = deptDAO.load(dept.getId(),u);
		return po;
	}

	public void updateDeptInfo(TDeptInfo dept, TUser u) {
		TDeptInfo po = deptDAO.load(dept.getId(),u);
		
		po.setDeptName(dept.getDeptName());
		po.setPid(dept.getPid());
		
		deptDAO.update(po);
	}

	public void addDeptInfo(TDeptInfo dept, TUser u) {
		deptDAO.save(dept);
		
		DecimalFormat dFormat = new DecimalFormat("0000");
		dept.setDeptCd(dFormat.format(dept.getId()));
		
		deptDAO.update(dept);
	}

	public void removeDeptInfo(TDeptInfo dept, TUser u) {
		
		String updateSql = "delete TDeptInfo u where u.id = " +dept.getId();
		deptDAO.executeUpdate(updateSql);
	}
	
	
	
}
