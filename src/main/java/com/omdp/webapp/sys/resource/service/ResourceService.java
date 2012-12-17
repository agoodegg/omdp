package com.omdp.webapp.sys.resource.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TSysRes;
import com.omdp.webapp.model.TUser;


public interface ResourceService {

	public List<TSysRes> querySysRes(TSysRes res, Page page, TUser u);

	
}
