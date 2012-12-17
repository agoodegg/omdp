package com.omdp.webapp.contract.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TContractInfo;
import com.omdp.webapp.model.TContractItem;
import com.omdp.webapp.model.TUser;


public interface IContractService {

	public List<TContractInfo> queryContractInfo(TContractInfo info, TUser u, Page page);

	public void trashContract(TContractInfo info, TUser u);

	public void addNewContract(TContractInfo info, List<TContractItem> items, TUser u);

	public void updateContractTotal(String contractNo);

	public TContractInfo queryContractInfoById(Integer id, TUser u);

	public List<TContractItem> queryContractItem(String contractNo, TUser u);

	public void editContract(TContractInfo info, List<TContractItem> items, TUser u);

}
