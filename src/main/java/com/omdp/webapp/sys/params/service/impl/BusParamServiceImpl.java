package com.omdp.webapp.sys.params.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.params.dao.BusParamDAO;
import com.omdp.webapp.sys.params.service.BusParamService;


@Transactional
public class BusParamServiceImpl implements BusParamService {

	@Resource
	private BusParamDAO busParamDAO;

	public List<TSysParam> queryParamsList(TSysParam param, Page page, TUser u) {
		return null;
	}
	
	
}
