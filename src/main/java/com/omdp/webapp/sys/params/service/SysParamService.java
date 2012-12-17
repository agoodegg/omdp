package com.omdp.webapp.sys.params.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.model.TUser;


public interface SysParamService {

	public List<TSysParam> queryParamsList(TSysParam param, Page page, TUser u);

}
