package com.omdp.webapp.sys.params.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.params.dao.SysParamDAO;
import com.omdp.webapp.sys.params.service.SysParamService;

@Transactional
public class SysParamServiceImpl implements SysParamService {

	@Resource
	private SysParamDAO sysParamDAO;

	public List<TSysParam> queryParamsList(TSysParam param, Page page, TUser u) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
