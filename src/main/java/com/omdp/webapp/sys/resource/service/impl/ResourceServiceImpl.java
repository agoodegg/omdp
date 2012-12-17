package com.omdp.webapp.sys.resource.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;
import com.omdp.webapp.sys.resource.dao.ResourceDAO;
import com.omdp.webapp.sys.resource.service.ResourceService;


@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Resource
	private ResourceDAO resourceDAO;

	public List<TSysRes> querySysRes(TSysRes res, Page page, TUser u) {
		return null;
	}
	
	
	
}
